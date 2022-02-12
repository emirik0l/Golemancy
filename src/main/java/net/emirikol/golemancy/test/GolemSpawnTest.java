package net.emirikol.golemancy.test;

import net.emirikol.golemancy.Golemancy;
import net.emirikol.golemancy.GolemancyItemGroup;
import net.emirikol.golemancy.genetics.Genome;
import net.emirikol.golemancy.genetics.SoulTypes;
import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.test.GameTest;
import net.minecraft.test.TestContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import static net.emirikol.golemancy.test.Assertions.assertTrue;

public class GolemSpawnTest implements FabricGameTest {

    @GameTest(structureName = FabricGameTest.EMPTY_STRUCTURE)
    public void clayEffigyBlockSpawnsGolem(TestContext context) {
        // Get necessary parameters from context.
        ServerWorld world = context.getWorld();
        BlockPos pos = world.getSpawnPos().up();
        PlayerEntity player = context.createMockPlayer();
        Hand hand = player.getActiveHand();
        BlockHitResult hit = new BlockHitResult(new Vec3d(pos.getX(), pos.getY(), pos.getZ()), Direction.NORTH, pos, false);

        // Create a clay effigy block at the specified location.
        context.setBlockState(pos, Golemancy.CLAY_EFFIGY_BLOCK.getDefaultState());
        BlockState state = context.getBlockState(pos);

        // Create a Curious soulstone and give it to the dummy player.
        ItemStack stack = new ItemStack(Golemancy.SOULSTONE_FILLED);
        Genome genome = GolemancyItemGroup.creativeGenome(SoulTypes.CURIOUS);
        genome.toItemStack(stack);
        player.setStackInHand(hand, stack);

        // Call onUse() and check whether it returns a success.
        ActionResult result = Golemancy.CLAY_EFFIGY_BLOCK.onUse(state, world, pos, player, hand, hit);
        assertTrue(result == ActionResult.SUCCESS, "Clay Effigy Block did not return ActionResult.SUCCESS when spawning a golem!");

        context.complete();
    }
}
