package net.emirikol.golemancy.test;

import net.emirikol.golemancy.Golemancy;
import net.emirikol.golemancy.GolemancyItemGroup;
import net.emirikol.golemancy.genetics.Genome;
import net.emirikol.golemancy.genetics.SoulTypes;
import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.fabricmc.fabric.impl.gametest.FabricGameTestHelper;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.test.GameTest;
import net.minecraft.test.TestContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.lang.reflect.Method;

import static net.emirikol.golemancy.test.Assertions.assertTrue;

public class GolemSpawnTest implements FabricGameTest {
    ServerWorld world;
    BlockPos startPos;
    PlayerEntity player;

    @Override
    public void invokeTestMethod(TestContext context, Method method) {
        this.world = context.getWorld();
        this.startPos = world.getSpawnPos().up();
        this.player = context.createMockPlayer();
        FabricGameTestHelper.invokeTestMethod(context, method, this);
        this.player.kill();
    }

    @GameTest(structureName = FabricGameTest.EMPTY_STRUCTURE)
    public void clayEffigyBlockSpawnsGolem(TestContext context) {
        // Create a clay effigy block at the specified location.
        context.setBlockState(startPos, Golemancy.CLAY_EFFIGY_BLOCK.getDefaultState());
        BlockState state = context.getBlockState(startPos);
        // Create a Curious soulstone and give it to the dummy player.
        ItemStack stack = new ItemStack(Golemancy.SOULSTONE_FILLED);
        Genome genome = GolemancyItemGroup.creativeGenome(SoulTypes.CURIOUS);
        genome.toItemStack(stack);
        player.setStackInHand(player.getActiveHand(), stack);
        // Call onUse() and check whether it returns a success.
        BlockHitResult hit = new BlockHitResult(new Vec3d(startPos.getX(), startPos.getY(), startPos.getZ()), Direction.NORTH, startPos, false);
        ActionResult result = Golemancy.CLAY_EFFIGY_BLOCK.onUse(state, world, startPos, player, player.getActiveHand(), hit);
        assertTrue(result == ActionResult.SUCCESS, "Clay Effigy Block did not return ActionResult.SUCCESS when spawning a golem!");
        context.complete();
    }
}
