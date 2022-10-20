package net.emirikol.golemancy;

import net.minecraft.util.Identifier;

public class GMIdentifier extends Identifier {

    public static final String MODID = "golemancy";
    public GMIdentifier(String path) {
        super(MODID, path);
    }
}
