package net.emirikol.golemancy.test;

import net.emirikol.golemancy.Golemancy;
import net.emirikol.golemancy.entity.ParchedGolemEntity;
import net.emirikol.golemancy.entity.goal.GolemFillVesselGoal;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidFillable;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static net.emirikol.golemancy.test.Assertions.assertTrue;

public class GolemBehaviorTestSuite extends AbstractTestSuite {
    public GolemBehaviorTestSuite(World world, PlayerEntity player) {
        super(world, player);
    }

    @Override
    public void test() {
        drainWaterloggedBlock();
        this.printMessage("Golem behavior test completed successfully!");
    }

    public void drainWaterloggedBlock() {
        if (this.getWorld().isClient) return;
        //Generate a waterlogged oak fence and put it in the world.
        ServerWorld serverWorld = (ServerWorld) this.getWorld();
        BlockPos startPos = this.getPlayer().getBlockPos().north(2);
        BlockState state = Blocks.OAK_FENCE.getDefaultState();
        serverWorld.setBlockState(startPos, state);
        ((FluidFillable) Blocks.OAK_FENCE).tryFillWithFluid(serverWorld, startPos, state, Fluids.WATER.getDefaultState());
        //Create a golem entity and equip them with an empty bucket.
        ParchedGolemEntity entity = Golemancy.PARCHED_GOLEM_ENTITY.create(serverWorld, null, null, null, startPos, SpawnReason.SPAWN_EGG, true, true);
        serverWorld.spawnNewEntityAndPassengers(entity);
        GolemFillVesselGoal goal = new GolemFillVesselGoal(entity);
        entity.equipStack(EquipmentSlot.MAINHAND, Items.BUCKET.getDefaultStack());
        //Attempt to drain the waterlogged block and check the result.
        ItemStack result = goal.drainFluid(startPos);
        assertTrue(result.getItem() == Items.WATER_BUCKET, "Draining a waterlogged block with a bucket did not produce a water bucket!");
        //Tear everything down.
        entity.discard();
        serverWorld.setBlockState(startPos, Blocks.AIR.getDefaultState());
    }
}
