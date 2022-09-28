package net.emirikol.golemancy.entity;

import net.emirikol.golemancy.Golemancy;
import net.emirikol.golemancy.entity.goal.GolemDepositHeldItemGoal;
import net.emirikol.golemancy.entity.goal.GolemMoveToPickupGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

import java.util.UUID;

public class CovetousGolemEntity extends AbstractGolemEntity {
    public CovetousGolemEntity(EntityType<? extends CovetousGolemEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(5, new GolemDepositHeldItemGoal(this));
        this.goalSelector.add(6, new GolemMoveToPickupGoal(this, 5.0F));
    }

    @Override
    public CovetousGolemEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
        CovetousGolemEntity golemEntity = Golemancy.COVETOUS_GOLEM_ENTITY.create(serverWorld);
        UUID uUID = this.getOwnerUuid();

        if ((uUID != null) && (golemEntity != null)) {
            golemEntity.setOwnerUuid(uUID);
            golemEntity.setTamed(true);
        }
        return golemEntity;
    }
}