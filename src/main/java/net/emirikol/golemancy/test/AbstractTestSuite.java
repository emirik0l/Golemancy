package net.emirikol.golemancy.test;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.world.World;

public abstract class AbstractTestSuite {
    private World world;
    private PlayerEntity player;

    public AbstractTestSuite(World world, PlayerEntity player) {
        this.world = world;
        this.player = player;
    }

    public World getWorld() { return this.world; }
    public PlayerEntity getPlayer() { return player; }

    public void printMessage(String msg) {
        System.out.println(msg);
        if (!this.world.isClient) this.player.sendMessage(new LiteralText(msg), false);
    }

    public void invokeTest() {
        try {
            this.test();
        } catch (Exception e) {
            String msg = e.getMessage();
            this.printMessage(msg);
        }
    }

    public abstract void test();
}
