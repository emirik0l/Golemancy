package net.emirikol.golemancy.entity;

import net.emirikol.golemancy.Golemancy;
import net.emirikol.golemancy.entity.goal.GolemExtractSeedsGoal;
import net.emirikol.golemancy.entity.goal.GolemMoveToPlantGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

import java.util.UUID;

public class VerdantGolemEntity extends AbstractGolemEntity {
    public VerdantGolemEntity(EntityType<? extends VerdantGolemEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(5, new GolemExtractSeedsGoal(this));
        this.goalSelector.add(6, new GolemMoveToPlantGoal(this, 3.0F));
    }

    @Override
    public VerdantGolemEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
        VerdantGolemEntity golemEntity = Golemancy.VERDANT_GOLEM_ENTITY.create(serverWorld);
        UUID uUID = this.getOwnerUuid();

        if ((uUID != null) && (golemEntity != null)) {
            golemEntity.setOwnerUuid(uUID);
            golemEntity.setTamed(true);
        }
        return golemEntity;
    }
}
