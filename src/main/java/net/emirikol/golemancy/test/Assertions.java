package net.emirikol.golemancy.test;

import net.minecraft.test.GameTestException;

public class Assertions {
    public static void assertTrue(boolean condition, String message) throws GameTestException {
        if (!condition) {
            throw new GameTestException(message);
        }
    }

    public static void assertFalse(boolean condition, String message) throws GameTestException {
        if (condition) {
            throw new GameTestException(message);
        }
    }
}
