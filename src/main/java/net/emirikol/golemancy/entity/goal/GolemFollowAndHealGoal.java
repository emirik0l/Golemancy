package net.emirikol.golemancy.entity.goal;

import net.emirikol.golemancy.entity.AbstractGolemEntity;
import net.emirikol.golemancy.event.ConfigurationHandler;
import net.emirikol.golemancy.network.Particles;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;

import java.util.EnumSet;
import java.util.List;

public class GolemFollowAndHealGoal extends Goal {
    private static final float HEAL_AMOUNT = 2.0F;

    protected final AbstractGolemEntity entity;
    private LivingEntity friend;
    private final EntityNavigation navigation;
    private int updateCountdownTicks;
    private int healingTimer;
    private float oldWaterPathfindingPenalty;
    private final boolean canHealOwner;

    public GolemFollowAndHealGoal(AbstractGolemEntity entity, boolean canHealOwner) {
        this.entity = entity;
        this.navigation = this.entity.getNavigation();
        this.canHealOwner = canHealOwner;
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
        if (!(this.navigation instanceof MobNavigation) && !(this.navigation instanceof BirdNavigation)) {
            throw new IllegalArgumentException("Unsupported mob type for GolemFollowAndHealGoal");
        }
    }

    public boolean canStart() {
        float searchRadius = ConfigurationHandler.getGolemRadius();
        float r = searchRadius + (searchRadius * this.entity.getGolemSmarts());
        List<LivingEntity> list = this.entity.world.getEntitiesByClass(LivingEntity.class, entity.getBoundingBox().expand(r,r,r), (entity) -> {
            if (entity instanceof TameableEntity) {
                TameableEntity tameable = (TameableEntity) entity;
                return this.isWounded(tameable) && (this.entity.getOwnerUuid() != null) && (this.entity.getOwnerUuid().equals(tameable.getOwnerUuid()));
            }
            if (entity instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) entity;
                return this.canHealOwner && this.isWounded(player) && (this.entity.getOwnerUuid() != null) && (this.entity.getOwnerUuid().equals(player.getUuid()));
            }
            return false;
        });
        if (list.size() > 0) {
            this.friend = list.get(0);
            return true;
        }
        return false;
    }

    @Override
    public boolean shouldContinue() {
        return this.friend.isAlive() && this.isWounded(this.friend);
    }

    @Override
    public void start() {
        this.updateCountdownTicks = 0;
        this.oldWaterPathfindingPenalty = this.entity.getPathfindingPenalty(PathNodeType.WATER);
        this.entity.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
    }

    @Override
    public void stop() {
        this.friend = null;
        this.healingTimer = 0;
        this.entity.interruptPray();
        this.navigation.stop();
        this.entity.setPathfindingPenalty(PathNodeType.WATER, this.oldWaterPathfindingPenalty);
    }

    @Override
    public void tick() {
        //Look at friend.
        this.entity.getLookControl().lookAt(this.friend, 10.0F, (float)this.entity.getMaxLookPitchChange());
        //Try to heal friend.
        if (this.isHealing()) {
            this.healingTimer--;
            if (this.healingTimer == 0) {
                this.friend.heal(this.getHealAmount());
                Particles.healParticle(this.friend);
                friend.world.playSound(null, friend.getBlockPos(), SoundEvents.ENTITY_EVOKER_CAST_SPELL, SoundCategory.NEUTRAL, 1F, 1F);
            }
        } else {
            this.setHealing();
            this.entity.tryPray();
        }
        //Follow friend.
        if (--this.updateCountdownTicks <= 0) {
            this.updateCountdownTicks = 10;
            this.navigation.startMovingTo(this.friend, 1);
        }
    }

    @Override
    public boolean shouldRunEveryTick() {
        return true;
    }

    public boolean isWounded(LivingEntity entity) {
        return entity.getHealth() < entity.getMaxHealth();
    }
    public boolean isHealing() {
        return this.healingTimer > 0;
    }
    public void setHealing() {
        this.healingTimer = 80;
    }
    public float getHealAmount() {
        return HEAL_AMOUNT + this.entity.getGolemSmarts();
    }
}
