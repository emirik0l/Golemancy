package net.emirikol.golemancy.event;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.emirikol.golemancy.entity.FakePlayerEntity;
import net.emirikol.golemancy.test.GeneticsTestSuite;
import net.emirikol.golemancy.test.GolemBehaviorTestSuite;
import net.emirikol.golemancy.test.GolemSpawnTestSuite;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CommandRegistrationHandler {
    public static void commandRegistrationHook() {
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            // Command to run all test suites.
            // For best results, run on a superflat world in creative.
            dispatcher.register(CommandManager.literal("golemancytest").executes(context -> {
                golemancyTest(context);
                return 0;
            }));
        });
    }

    public static void golemancyTest(CommandContext<ServerCommandSource> context) {
        World world = context.getSource().getWorld();

        PlayerEntity player;

        try {
            player = context.getSource().getPlayer();
        } catch (CommandSyntaxException e) {
            player = new FakePlayerEntity(world, BlockPos.ORIGIN, 0);
        }

        new GeneticsTestSuite(world, player).invokeTest();
        new GolemBehaviorTestSuite(world, player).invokeTest();
        new GolemSpawnTestSuite(world, player).invokeTest();
    }
}
