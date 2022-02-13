package net.emirikol.golemancy.entity;

import net.emirikol.golemancy.Golemancy;
import net.emirikol.golemancy.entity.goal.GolemMoveToFishGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

import java.util.UUID;

public class MarshyGolemEntity extends AbstractGolemEntity {
    public MarshyGolemEntity(EntityType<? extends MarshyGolemEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(6, new GolemMoveToFishGoal(this, 5.0F));
    }

    @Override
    public MarshyGolemEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
        MarshyGolemEntity golemEntity = Golemancy.MARSHY_GOLEM_ENTITY.create(serverWorld);
        UUID uUID = this.getOwnerUuid();

        if ((uUID != null) && (golemEntity != null)) {
            golemEntity.setOwnerUuid(uUID);
            golemEntity.setTamed(true);
        }
        return golemEntity;
    }
}
