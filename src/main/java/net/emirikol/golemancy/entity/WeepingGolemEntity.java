package net.emirikol.golemancy.entity;

import net.emirikol.golemancy.Golemancy;
import net.emirikol.golemancy.entity.goal.GolemDropHeldItemGoal;
import net.emirikol.golemancy.entity.goal.GolemFollowAndHealGoal;
import net.emirikol.golemancy.registry.GMEntityTypes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

import java.util.UUID;

public class WeepingGolemEntity extends AbstractGolemEntity {
    public WeepingGolemEntity(EntityType<? extends WeepingGolemEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(6, new GolemFollowAndHealGoal(this, false));
        this.goalSelector.add(7, new GolemDropHeldItemGoal(this));
    }

    @Override
    public WeepingGolemEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
        WeepingGolemEntity golemEntity = GMEntityTypes.WEEPING_GOLEM_ENTITY.create(serverWorld);
        UUID uUID = this.getOwnerUuid();

        if ((uUID != null) && (golemEntity != null)) {
            golemEntity.setOwnerUuid(uUID);
            golemEntity.setTamed(true);
        }
        return golemEntity;
    }
}