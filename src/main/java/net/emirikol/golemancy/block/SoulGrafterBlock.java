package net.emirikol.golemancy.block;

import net.emirikol.golemancy.block.entity.*;

import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.screen.*;
import net.minecraft.state.*;
import net.minecraft.state.property.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.shape.*;
import net.minecraft.util.hit.*;
import net.minecraft.particle.*;

import java.util.*;

public class SoulGrafterBlock extends BlockWithEntity {
	public static final BooleanProperty GRAFTING = BooleanProperty.of("grafting");
	
	public SoulGrafterBlock(Settings settings) {
		super(settings);
		this.setDefaultState(getStateManager().getDefaultState().with(GRAFTING, false));
	}

	//Override the appendProperties() function to add the state to the block's property list.
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
		stateManager.add(GRAFTING);
	}
	
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new SoulGrafterBlockEntity(pos, state);
	}
	
	@Override
	public BlockRenderType getRenderType(BlockState state) {
		//BlockWithEntity defaults to INVISIBLE, so override this to give your block a model.
		return BlockRenderType.MODEL;
	}
	
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
		return VoxelShapes.cuboid(0f, 0f, 0f, 1f, 0.75, 1.0f);
	}
	
	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, pos);
		if (screenHandlerFactory != null) {
			player.openHandledScreen(screenHandlerFactory);
		}
		return ActionResult.SUCCESS;
	}
	
	@Override
	public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
		if (!state.isOf(newState.getBlock())) {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof Inventory) {
				ItemScatterer.spawn(world, pos, (Inventory)blockEntity);
				world.updateComparators(pos, this);
			}

			super.onStateReplaced(state, world, pos, newState, moved);
		}
	}
	
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
		return checkType(type, Golemancy.SOUL_GRAFTER_ENTITY, () -> SoulGrafterBlockEntity.tick());
	}
	
   @Environment(EnvType.CLIENT)
   public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
	   if ((Boolean)state.get(GRAFTING)) {
		   double d = (double)pos.getX() + 0.5D;
		   double e = (double)pos.getY() + 0.75D;
		   double f = (double)pos.getZ() + 0.5D;
		   Direction direction = Direction.random(random);
		   Direction.Axis axis = direction.getAxis();
		   double g = 0.52D;
		   double h = random.nextDouble() * 0.6D - 0.3D;
		   double i = axis == Direction.Axis.X ? (double)direction.getOffsetX() * 0.52D : h;
		   double j = random.nextDouble() * 6.0D / 16.0D;
		   double k = axis == Direction.Axis.Z ? (double)direction.getOffsetZ() * 0.52D : h;
		   world.addParticle(ParticleTypes.SOUL_FIRE_FLAME, d+i, e+j, f+k, 0.0D, 0.0D, 0.0D);
	   }
   }
}