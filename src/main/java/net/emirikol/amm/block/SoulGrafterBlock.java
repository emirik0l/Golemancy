package net.emirikol.amm.block;

import net.emirikol.amm.block.entity.*;

import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.screen.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.shape.*;
import net.minecraft.util.hit.*;

public class SoulGrafterBlock extends BlockWithEntity {
	public SoulGrafterBlock(Settings settings) {
		super(settings);
	}
	
	@Override
	public BlockEntity createBlockEntity(BlockView blockView) {
		return new SoulGrafterBlockEntity();
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
}