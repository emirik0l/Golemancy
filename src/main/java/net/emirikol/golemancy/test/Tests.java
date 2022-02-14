package net.emirikol.golemancy.test;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.emirikol.golemancy.entity.FakePlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Tests {
    public static void runAll(World world, PlayerEntity player) {
        new GeneticsTestSuite(world, player).invokeTest();
        new GolemBehaviorTestSuite(world, player).invokeTest();
        new GolemSpawnTestSuite(world, player).invokeTest();
    }

    public static void runAll(ServerCommandSource source) {
        World world = source.getWorld();
        PlayerEntity player;

        try {
            player = source.getPlayer();
        } catch (CommandSyntaxException e) {
            player = new FakePlayerEntity(world, BlockPos.ORIGIN, 0);
        }

        runAll(world, player);
    }
}
