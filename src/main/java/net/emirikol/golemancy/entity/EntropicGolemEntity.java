package net.emirikol.golemancy.entity;

import net.emirikol.golemancy.Golemancy;
import net.emirikol.golemancy.entity.goal.GolemDropHeldItemGoal;
import net.emirikol.golemancy.entity.goal.GolemMoveToMineGoal;
import net.emirikol.golemancy.registry.GMEntityTypes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

import java.util.UUID;

public class EntropicGolemEntity extends AbstractGolemEntity {
    public EntropicGolemEntity(EntityType<? extends EntropicGolemEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(6, new GolemMoveToMineGoal(this, 3.0F));
        this.goalSelector.add(7, new GolemDropHeldItemGoal(this));
    }

    @Override
    public EntropicGolemEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
        EntropicGolemEntity golemEntity = GMEntityTypes.ENTROPIC_GOLEM_ENTITY.create(serverWorld);
        UUID uUID = this.getOwnerUuid();

        if ((uUID != null) && (golemEntity != null)) {
            golemEntity.setOwnerUuid(uUID);
            golemEntity.setTamed(true);
        }
        return golemEntity;
    }
}