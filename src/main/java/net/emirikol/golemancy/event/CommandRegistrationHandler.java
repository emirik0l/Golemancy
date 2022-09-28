package net.emirikol.golemancy.event;

import com.mojang.brigadier.context.CommandContext;
import net.emirikol.golemancy.test.EffigyTestSuite;
import net.emirikol.golemancy.test.GeneticsTestSuite;
import net.emirikol.golemancy.test.GolemBehaviorTestSuite;
import net.emirikol.golemancy.test.SoulGrafterTestSuite;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;

public class CommandRegistrationHandler {
    public static void commandRegistrationHook() {
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated, environment) -> {
            // Command to run all test suites.
            // For best results, run on a superflat world in creative.
            dispatcher.register(CommandManager.literal("golemancytest").executes(context -> {
                golemancyTest(context);
                return 0;
            }));
        });
    }

    public static void golemancyTest(CommandContext<ServerCommandSource> context) {
        ServerWorld world = context.getSource().getWorld();
        PlayerEntity player;

        player = context.getSource().getPlayer();

        new EffigyTestSuite(world, player).invokeTest();
        new GeneticsTestSuite(world, player).invokeTest();
        new GolemBehaviorTestSuite(world, player).invokeTest();
        new SoulGrafterTestSuite(world, player).invokeTest();
    }
}
