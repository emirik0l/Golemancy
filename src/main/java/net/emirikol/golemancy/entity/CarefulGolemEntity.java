package net.emirikol.golemancy.entity;

import net.emirikol.golemancy.Golemancy;
import net.emirikol.golemancy.entity.goal.GolemExtractItemToSortGoal;
import net.emirikol.golemancy.entity.goal.GolemMoveToSortGoal;
import net.emirikol.golemancy.registry.GMEntityTypes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

import java.util.UUID;

public class CarefulGolemEntity extends AbstractGolemEntity {
    public CarefulGolemEntity(EntityType<? extends CarefulGolemEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(5, new GolemExtractItemToSortGoal(this, 5.0F));
        this.goalSelector.add(6, new GolemMoveToSortGoal(this, 5.0F));
    }

    @Override
    public CarefulGolemEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
        CarefulGolemEntity golemEntity = GMEntityTypes.CAREFUL_GOLEM_ENTITY.create(serverWorld);
        UUID uUID = this.getOwnerUuid();

        if ((uUID != null) && (golemEntity != null)) {
            golemEntity.setOwnerUuid(uUID);
            golemEntity.setTamed(true);
        }
        return golemEntity;
    }
}
