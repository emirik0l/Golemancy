package net.emirikol.golemancy.block;

import net.emirikol.golemancy.entity.GolemMaterial;

public class TerracottaEffigyBlock extends ClayEffigyBlock {
    public TerracottaEffigyBlock(Settings settings) {
        super(settings);
    }

    @Override
    public GolemMaterial getEffigyMaterial() { return GolemMaterial.TERRACOTTA; }
}
