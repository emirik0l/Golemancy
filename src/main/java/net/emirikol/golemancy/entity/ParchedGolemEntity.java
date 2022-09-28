package net.emirikol.golemancy.entity;

import net.emirikol.golemancy.Golemancy;
import net.emirikol.golemancy.entity.goal.GolemDepositVesselGoal;
import net.emirikol.golemancy.entity.goal.GolemExtractItemGoal;
import net.emirikol.golemancy.entity.goal.GolemFillVesselGoal;
import net.emirikol.golemancy.entity.goal.GolemMoveToFluidGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

import java.util.UUID;

public class ParchedGolemEntity extends AbstractGolemEntity {
    public ParchedGolemEntity(EntityType<? extends ParchedGolemEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(5, new GolemFillVesselGoal(this));
        this.goalSelector.add(5, new GolemDepositVesselGoal(this));
        this.goalSelector.add(5, new GolemExtractItemGoal(this) {{
            add(Items.BUCKET);
        }});
        this.goalSelector.add(6, new GolemExtractItemGoal(this) {{
            add(Items.GLASS_BOTTLE);
        }});
        this.goalSelector.add(7, new GolemMoveToFluidGoal(this, 5.0F));
    }

    @Override
    public ParchedGolemEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
        ParchedGolemEntity golemEntity = Golemancy.PARCHED_GOLEM_ENTITY.create(serverWorld);
        UUID uUID = this.getOwnerUuid();

        if ((uUID != null) && (golemEntity != null)) {
            golemEntity.setOwnerUuid(uUID);
            golemEntity.setTamed(true);
        }
        return golemEntity;
    }
}