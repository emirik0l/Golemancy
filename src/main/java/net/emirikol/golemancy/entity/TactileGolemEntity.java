package net.emirikol.golemancy.entity;

import net.emirikol.golemancy.Golemancy;
import net.emirikol.golemancy.entity.goal.GolemPickupGoal;
import net.emirikol.golemancy.entity.goal.GolemUseBlockGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

import java.util.UUID;

public class TactileGolemEntity extends AbstractGolemEntity {
    public TactileGolemEntity(EntityType<? extends TactileGolemEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(5, new GolemUseBlockGoal(this));
        this.goalSelector.add(6, new GolemPickupGoal(this));
    }

    @Override
    public TactileGolemEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
        TactileGolemEntity golemEntity = Golemancy.TACTILE_GOLEM_ENTITY.create(serverWorld);
        UUID uUID = this.getOwnerUuid();

        if ((uUID != null) && (golemEntity != null)) {
            golemEntity.setOwnerUuid(uUID);
            golemEntity.setTamed(true);
        }
        return golemEntity;
    }
}