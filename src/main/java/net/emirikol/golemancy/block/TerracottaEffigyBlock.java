package net.emirikol.golemancy.block;

import net.emirikol.golemancy.entity.AbstractGolemEntity;

public class TerracottaEffigyBlock extends ClayEffigyBlock {
    public TerracottaEffigyBlock(Settings settings) {
        super(settings);
    }

    @Override
    public AbstractGolemEntity.MATERIAL getEffigyMaterial() { return AbstractGolemEntity.MATERIAL.TERRACOTTA; }
}
