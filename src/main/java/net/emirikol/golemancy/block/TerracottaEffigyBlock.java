package net.emirikol.golemancy.block;

public class TerracottaEffigyBlock extends ClayEffigyBlock {
    public TerracottaEffigyBlock(Settings settings) {
        super(settings);
    }

    @Override
    public boolean isTerracotta() { return true; }
}
