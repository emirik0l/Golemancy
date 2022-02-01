package net.emirikol.golemancy.block;

import net.emirikol.golemancy.entity.AbstractGolemEntity;
import net.emirikol.golemancy.genetics.Gene;
import net.emirikol.golemancy.genetics.Genome;
import net.emirikol.golemancy.genetics.SoulType;
import net.emirikol.golemancy.item.SoulstoneFilled;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ClayEffigyBlock extends Block {
    public ClayEffigyBlock(AbstractBlock.Settings settings) {
        super(settings);
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
            Gene<SoulType> typeGene = genome.get("type");
            Gene<Integer> strengthGene = genome.get("strength");
            Gene<Integer> agilityGene = genome.get("agility");
            Gene<Integer> vigorGene = genome.get("vigor");
            Gene<Integer> smartsGene = genome.get("smarts");
            //Get entity type.
            EntityType<? extends AbstractGolemEntity> golemType = typeGene.getActive().getEntityType();
            if (golemType == null) { return ActionResult.PASS; } //shouldn't throw an error, as this can happen w/ soulstones that have invalid data (i.e. "generic soulstone")
            AbstractGolemEntity entity = golemType.create(serverWorld, null, null, null, pos, SpawnReason.SPAWN_EGG, true, true);
            if (entity == null) { throw new java.lang.RuntimeException("Attempt to create golem entity from soulstone returned NULL entity!"); }
            //Replace this block with air and spawn the new entity.
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
            serverWorld.spawnEntityAndPassengers(entity);
            //Update tracked values from genome.
            entity.setGolemStats(strengthGene.getActive(), agilityGene.getActive(), vigorGene.getActive(), smartsGene.getActive());
            //Update golem "baked" value based on whether effigy was terracotta.
            entity.setBaked(this.isTerracotta());
            //Update golem attack damage, speed, and so on based on their stats.
            entity.updateAttributes();
            //Set the golem as tamed.
            entity.setOwner(player);
            //Remove the soulstone.
            stack.decrement(1);

            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    public boolean isTerracotta() { return false; }
}
