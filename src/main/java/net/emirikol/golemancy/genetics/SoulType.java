package net.emirikol.golemancy.genetics;

import net.emirikol.golemancy.entity.AbstractGolemEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class SoulType {
    private String typeString; // the translation key associated with the soul, i.e. "text.golemancy.type.covetous"
    private EntityType<? extends AbstractGolemEntity> entityType; // the golem type spawned by the soul

    public SoulType(String typeString, EntityType<? extends AbstractGolemEntity> entityType) {
        this.typeString = typeString;
        this.entityType = entityType;
    }

    public String getTypeString() { return this.typeString; }
    public Text getTypeText() { return new TranslatableText(this.typeString); }
    public EntityType<? extends AbstractGolemEntity> getEntityType() { return this.entityType; }

    @Override
    public String toString() {
        return getTypeText().getString();
    }
}
