package net.emirikol.golemancy.entity;

import net.emirikol.golemancy.Golemancy;
import net.emirikol.golemancy.entity.goal.GolemEatHeldItemGoal;
import net.emirikol.golemancy.entity.goal.GolemExtractItemGoal;
import net.emirikol.golemancy.entity.goal.GolemMoveToPickupGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

import java.util.UUID;

public class HungryGolemEntity extends AbstractGolemEntity {
    public HungryGolemEntity(EntityType<? extends HungryGolemEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(5, new GolemEatHeldItemGoal(this));
        this.goalSelector.add(5, new GolemExtractItemGoal(this));
        this.goalSelector.add(6, new GolemMoveToPickupGoal(this, 5.0F));
    }

    @Override
    public HungryGolemEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
        HungryGolemEntity golemEntity = Golemancy.HUNGRY_GOLEM_ENTITY.create(serverWorld);
        UUID uUID = this.getOwnerUuid();

        if ((uUID != null) && (golemEntity != null)) {
            golemEntity.setOwnerUuid(uUID);
            golemEntity.setTamed(true);
        }
        return golemEntity;
    }
}