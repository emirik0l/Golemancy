package net.emirikol.golemancy.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

public class TerracottaEffigyEntity extends ClayEffigyEntity{
    public TerracottaEffigyEntity(EntityType<? extends ClayEffigyEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public boolean isTerracotta() {
        return true;
    }
}
