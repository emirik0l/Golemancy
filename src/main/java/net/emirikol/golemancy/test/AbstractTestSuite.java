package net.emirikol.golemancy.test;

import net.emirikol.golemancy.Golemancy;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public abstract class AbstractTestSuite {
    private final World world;
    private final PlayerEntity player;
    protected String testName = "test_generic";

    public AbstractTestSuite(World world, PlayerEntity player) {
        this.world = world;
        this.player = player;
    }

    public void invokeTest() {
        try {
            this.test();
            String msg = String.format("PASS: %s", this.testName);
            this.printMessage(msg);
        } catch (Exception e) {
            String msg = String.format("FAIL: %s (%s)", this.testName, e.getMessage());
            this.printMessage(msg);
        }
    }

    //To create a test suite, implement this method and call your unit tests.
    //To create test cases that can fail with an appropriate error, use the Assertions class or throw exceptions.
    public abstract void test();

    public void printMessage(String msg) {
        if (this.world.isClient) {
            String clientMsg = String.format("%s [CLIENT]", msg);
            Golemancy.LOGGER.info(clientMsg);
        } else {
            String serverMsg = String.format("%s [SERVER]", msg);
            Golemancy.LOGGER.info(serverMsg);
            this.player.sendMessage(Text.literal(msg), false);
        }
    }

    public World getWorld() {
        return this.world;
    }

    public PlayerEntity getPlayer() {
        return player;
    }

    public BlockPos getRandomBlockPos() {
        Random rand = (Random) this.world.getRandom();
        int distance = rand.nextInt(10) + 1;
        int direction = rand.nextInt(4);
        switch (direction) {
            case 0:
                return this.player.getBlockPos().north(distance);
            case 1:
                return this.player.getBlockPos().west(distance);
            case 2:
                return this.player.getBlockPos().east(distance);
            case 3:
                return this.player.getBlockPos().south(distance);
        }
        return this.player.getBlockPos();
    }
}
