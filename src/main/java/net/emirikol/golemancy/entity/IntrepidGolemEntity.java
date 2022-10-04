package net.emirikol.golemancy.entity;

import net.emirikol.golemancy.Golemancy;
import net.emirikol.golemancy.entity.goal.GolemDropHeldItemGoal;
import net.emirikol.golemancy.entity.projectile.ClayballEntity;
import net.emirikol.golemancy.registry.GMEntityTypes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

import java.util.UUID;

public class IntrepidGolemEntity extends AbstractGolemEntity implements RangedAttackMob {
    public IntrepidGolemEntity(EntityType<? extends IntrepidGolemEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(1, new ProjectileAttackGoal(this, 1.25D, 40, 10.0F));
        this.targetSelector.add(1, new TrackOwnerAttackerGoal(this));
        this.targetSelector.add(2, new AttackWithOwnerGoal(this));
        this.targetSelector.add(3, new RevengeGoal(this, AbstractGolemEntity.class).setGroupRevenge());
        this.targetSelector.add(3, new TargetGoal<>(this, MobEntity.class, 5, false, false, (livingEntity) -> {
            return livingEntity instanceof Monster && !(livingEntity instanceof CreeperEntity);
        }));
        this.goalSelector.add(7, new GolemDropHeldItemGoal(this));
    }

    @Override
    public IntrepidGolemEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
        IntrepidGolemEntity golemEntity = GMEntityTypes.INTREPID_GOLEM_ENTITY.create(serverWorld);
        UUID uUID = this.getOwnerUuid();

        if ((uUID != null) && (golemEntity != null)) {
            golemEntity.setOwnerUuid(uUID);
            golemEntity.setTamed(true);
        }
        return golemEntity;
    }

    @Override
    public void attack(LivingEntity target, float pullProgress) {
        ClayballEntity clayballEntity = new ClayballEntity(this.world, this);
        clayballEntity.setDamage(this.getAttackDamageFromStrength());
        double d = target.getEyeY() - (double) 1.1f;
        double e = target.getX() - this.getX();
        double f = d - clayballEntity.getY();
        double g = target.getZ() - this.getZ();
        double h = Math.sqrt(e * e + g * g) * (double) 0.2f;
        clayballEntity.setVelocity(e, f + h, g, 1.6f, 12.0f);
        this.playSound(SoundEvents.ENTITY_SNOW_GOLEM_SHOOT, 1.0F, 0.4F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.tryAttack(target);
        this.world.spawnEntity(clayballEntity);
    }
}