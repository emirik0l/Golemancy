package net.emirikol.golemancy.entity;

import net.emirikol.golemancy.Golemancy;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

import java.util.UUID;

public class MeticulousGolemEntity extends AbstractGolemEntity {
    public MeticulousGolemEntity(EntityType<? extends MeticulousGolemEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
    }

    @Override
    public MeticulousGolemEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
        MeticulousGolemEntity golemEntity = Golemancy.METICULOUS_GOLEM_ENTITY.create(serverWorld);
        UUID uUID = this.getOwnerUuid();

        if ((uUID != null) && (golemEntity != null)) {
            golemEntity.setOwnerUuid(uUID);
            golemEntity.setTamed(true);
        }
        return golemEntity;
    }
}
