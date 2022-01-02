package net.emirikol.golemancy.item;

import net.emirikol.golemancy.Golemancy;
import net.minecraft.entity.*;

public class TerracottaEffigy extends ClayEffigy {
    public TerracottaEffigy(Settings settings) {
        super(settings);
    }

    @Override
    public EntityType getEntityType() {
        return Golemancy.TERRACOTTA_EFFIGY_ENTITY;
    }
}
