package net.emirikol.golemancy.block;

import net.emirikol.golemancy.entity.AbstractGolemEntity;
import net.emirikol.golemancy.entity.GolemMaterial;
import net.emirikol.golemancy.genetics.Gene;
import net.emirikol.golemancy.genetics.Genome;
import net.emirikol.golemancy.genetics.SoulType;
import net.emirikol.golemancy.item.SoulstoneFilled;
import net.minecraft.block.*;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class ClayEffigyBlock extends Block {
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;

    public ClayEffigyBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(((this.stateManager.getDefaultState()).with(FACING, Direction.NORTH)));
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(FACING);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        Direction blockFacing = state.get(FACING);
        switch (blockFacing) {
            case EAST:
            case WEST:
                return VoxelShapes.cuboid(0.3F, 0F, 0.13F, 0.65F, 1.05F, 0.87F);
            case NORTH:
            case SOUTH:
                return VoxelShapes.cuboid(0.13F, 0F, 0.3F, 0.87F, 1.05F, 0.65F);
            default:
                return super.getOutlineShape(state, view, pos, context);
        }

    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return super.onUse(state, world, pos, player, hand, hit);
        }
        ServerWorld serverWorld = (ServerWorld) world;
        ItemStack stack = player.getStackInHand(hand);
        if (stack.getItem() instanceof SoulstoneFilled) {
            //Load genome from soulstone.
            Genome genome = new Genome(stack);
            Gene<SoulType> typeGene = genome.getSoulType("type");
            Gene<Integer> strengthGene = genome.getInteger("strength");
            Gene<Integer> agilityGene = genome.getInteger("agility");
            Gene<Integer> vigorGene = genome.getInteger("vigor");
            Gene<Integer> smartsGene = genome.getInteger("smarts");
            //Get entity type.
            EntityType<? extends AbstractGolemEntity> golemType = typeGene.getActive().getEntityType();
            if (golemType == null) {
                return ActionResult.PASS;
            } //shouldn't throw an error, as this can happen w/ soulstones that have invalid data (i.e. "generic soulstone")
            AbstractGolemEntity entity = golemType.create(serverWorld, null, null, null, pos, SpawnReason.SPAWN_EGG, true, true);
            if (entity == null) {
                throw new java.lang.RuntimeException("Attempt to create golem entity from soulstone returned NULL entity!");
            }
            //Update tracked values from genome.
            entity.setGolemStats(strengthGene.getActive(), agilityGene.getActive(), vigorGene.getActive(), smartsGene.getActive());
            //Update golem material based on what kind of effigy this is.
            entity.setMaterial(this.getEffigyMaterial());
            //Update golem attack damage, speed, and so on based on their stats.
            entity.updateAttributes();
            //Set the golem as tamed.
            entity.setOwner(player);
            //Remove the soulstone.
            stack.decrement(1);
            //Replace this block with air and spawn the new entity.
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
            serverWorld.spawnEntityAndPassengers(entity);

            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    public GolemMaterial getEffigyMaterial() {
        return GolemMaterial.CLAY;
    }
}
