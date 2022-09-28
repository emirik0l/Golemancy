package net.emirikol.golemancy.entity;

import net.emirikol.golemancy.Golemancy;
import net.emirikol.golemancy.entity.goal.GolemLookAtHeldBlockGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

import java.util.UUID;

public class CuriousGolemEntity extends AbstractGolemEntity {
    public CuriousGolemEntity(EntityType<? extends CuriousGolemEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(5, new GolemLookAtHeldBlockGoal(this, 5.0F));
    }

    @Override
    public CuriousGolemEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
        CuriousGolemEntity golemEntity = Golemancy.CURIOUS_GOLEM_ENTITY.create(serverWorld);
        UUID uUID = this.getOwnerUuid();

        if ((uUID != null) && (golemEntity != null)) {
            golemEntity.setOwnerUuid(uUID);
            golemEntity.setTamed(true);
        }
        return golemEntity;
    }
}