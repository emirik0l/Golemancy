package net.emirikol.golemancy.test;

import net.emirikol.golemancy.Golemancy;
import net.emirikol.golemancy.entity.ParchedGolemEntity;
import net.emirikol.golemancy.entity.goal.GolemFillVesselGoal;
import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.fabricmc.fabric.impl.gametest.FabricGameTestHelper;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidFillable;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.test.GameTest;
import net.minecraft.test.TestContext;
import net.minecraft.util.math.BlockPos;

import java.lang.reflect.Method;

import static net.emirikol.golemancy.test.Assertions.assertTrue;

public class GolemBehaviorTest implements FabricGameTest {
    ServerWorld world;
    BlockPos startPos;

    @Override
    public void invokeTestMethod(TestContext context, Method method) {
        this.world = context.getWorld();
        this.startPos = world.getSpawnPos().up();
        FabricGameTestHelper.invokeTestMethod(context, method, this);
    }

    @GameTest(structureName = FabricGameTest.EMPTY_STRUCTURE)
    public void drainWaterloggedBlock(TestContext context) {
        BlockState state = Blocks.OAK_FENCE.getDefaultState();
        ((FluidFillable) Blocks.OAK_FENCE).tryFillWithFluid(this.world, this.startPos, state, Fluids.WATER.getDefaultState());
        context.setBlockState(this.startPos, state);
        ParchedGolemEntity entity = context.spawnEntity(Golemancy.PARCHED_GOLEM_ENTITY, this.startPos.north());
        GolemFillVesselGoal goal = new GolemFillVesselGoal(entity);
        entity.equipStack(EquipmentSlot.MAINHAND, Items.BUCKET.getDefaultStack());
        ItemStack result = goal.drainFluid(this.startPos);
        assertTrue(result.getItem() == Items.WATER_BUCKET, "Draining a waterlogged block with a bucket did not produce a water bucket!");
        context.complete();
    }
}
