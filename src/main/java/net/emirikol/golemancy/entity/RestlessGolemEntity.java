package net.emirikol.golemancy.entity;

import net.emirikol.golemancy.Golemancy;
import net.emirikol.golemancy.entity.goal.GolemDanceGoal;
import net.emirikol.golemancy.entity.goal.GolemMoveToPickupFlowerGoal;
import net.emirikol.golemancy.registry.GMEntityTypes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

import java.util.UUID;

public class RestlessGolemEntity extends AbstractGolemEntity {
    public RestlessGolemEntity(EntityType<? extends RestlessGolemEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(8, new WanderAroundFarGoal(this, 1.0D));
        this.goalSelector.add(7, new GolemMoveToPickupFlowerGoal(this, 5.0F));
        this.goalSelector.add(6, new GolemDanceGoal(this));
    }

    @Override
    public RestlessGolemEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
        RestlessGolemEntity golemEntity = GMEntityTypes.RESTLESS_GOLEM_ENTITY.create(serverWorld);
        UUID uUID = this.getOwnerUuid();

        if ((uUID != null) && (golemEntity != null)) {
            golemEntity.setOwnerUuid(uUID);
            golemEntity.setTamed(true);
        }
        return golemEntity;
    }
}