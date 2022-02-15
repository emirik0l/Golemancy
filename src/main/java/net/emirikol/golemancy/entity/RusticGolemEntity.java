package net.emirikol.golemancy.entity;

import net.emirikol.golemancy.Golemancy;
import net.emirikol.golemancy.entity.goal.GolemDropHeldItemGoal;
import net.emirikol.golemancy.entity.goal.GolemMoveToHarvestGoal;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

import java.util.UUID;

public class RusticGolemEntity extends AbstractGolemEntity {
    public RusticGolemEntity(EntityType<? extends RusticGolemEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(6, new GolemMoveToHarvestGoal(this, 3.0F));
        this.goalSelector.add(7, new GolemDropHeldItemGoal(this));
    }

    @Override
    public RusticGolemEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
        RusticGolemEntity golemEntity = Golemancy.RUSTIC_GOLEM_ENTITY.create(serverWorld);
        UUID uUID = this.getOwnerUuid();

        if ((uUID != null) && (golemEntity != null)) {
            golemEntity.setOwnerUuid(uUID);
            golemEntity.setTamed(true);
        }
        return golemEntity;
    }
}
