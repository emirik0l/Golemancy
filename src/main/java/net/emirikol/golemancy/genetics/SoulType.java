package net.emirikol.golemancy.genetics;

import net.emirikol.golemancy.entity.AbstractGolemEntity;
import net.minecraft.entity.EntityType;

public class SoulType {
    private String typeString;
    private EntityType<? extends AbstractGolemEntity> entityType;

    public SoulType(String typeString, EntityType<? extends AbstractGolemEntity> entityType) {
        this.typeString = typeString;
        this.entityType = entityType;
    }

    public String getTypeString() { return this.typeString; }
    public EntityType<? extends AbstractGolemEntity> getEntityType() { return this.entityType; }
}
