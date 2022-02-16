package net.emirikol.golemancy.test;

import net.emirikol.golemancy.Golemancy;
import net.emirikol.golemancy.entity.CuriousGolemEntity;
import net.emirikol.golemancy.genetics.Genome;
import net.emirikol.golemancy.genetics.Genomes;
import net.emirikol.golemancy.genetics.SoulTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import static net.emirikol.golemancy.test.Assertions.assertTrue;

public class GolemSpawnTestSuite extends AbstractTestSuite {
    public GolemSpawnTestSuite(World world, PlayerEntity player) {
        super(world, player);
        this.testName = "test_golem_spawning";
    }

    @Override
    public void test() {
        clayEffigyBlockSpawnsGolem();
    }

    public void clayEffigyBlockSpawnsGolem() {
        if (this.getWorld().isClient) return;
        // Create a clay effigy block at the specified location.
        ServerWorld serverWorld = (ServerWorld) this.getWorld();
        BlockPos startPos = this.getRandomBlockPos();
        serverWorld.setBlockState(startPos, Golemancy.CLAY_EFFIGY_BLOCK.getDefaultState());
        BlockState state = serverWorld.getBlockState(startPos);
        // Create a Curious soulstone and give it to the dummy player.
        ItemStack stack = new ItemStack(Golemancy.SOULSTONE_FILLED);
        Genome genome = Genomes.creativeGenome(SoulTypes.CURIOUS);
        genome.toItemStack(stack);
        this.getPlayer().setStackInHand(this.getPlayer().getActiveHand(), stack);
        // Call onUse() and check whether it returns a success.
        BlockHitResult hit = new BlockHitResult(new Vec3d(startPos.getX(), startPos.getY(), startPos.getZ()), Direction.NORTH, startPos, false);
        ActionResult result = Golemancy.CLAY_EFFIGY_BLOCK.onUse(state, serverWorld, startPos, this.getPlayer(), this.getPlayer().getActiveHand(), hit);
        assertTrue(result == ActionResult.SUCCESS, "Clay Effigy Block did not return ActionResult.SUCCESS when spawning a golem!");
        //Tear everything down.
        serverWorld.setBlockState(startPos, Blocks.AIR.getDefaultState());
        for (CuriousGolemEntity entity : serverWorld.getEntitiesByClass(CuriousGolemEntity.class, new Box(startPos).expand(3), x -> true)) {
            entity.discard();
        }
    }
}
