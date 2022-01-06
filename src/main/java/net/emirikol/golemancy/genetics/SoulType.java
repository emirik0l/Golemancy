package net.emirikol.golemancy.genetics;

import net.emirikol.golemancy.entity.AbstractGolemEntity;
import net.minecraft.entity.EntityType;

public class SoulType {
    private String typeString; // the human-readable string associated with the soul, i.e. "Curious"
    private EntityType<? extends AbstractGolemEntity> entityType; // the golem type spawned by the soul

    public SoulType(String typeString, EntityType<? extends AbstractGolemEntity> entityType) {
        this.typeString = typeString;
        this.entityType = entityType;
    }

    public String getTypeString() { return this.typeString; }
    public EntityType<? extends AbstractGolemEntity> getEntityType() { return this.entityType; }

    @Override
    public String toString() {
        return getTypeString();
    }
}
