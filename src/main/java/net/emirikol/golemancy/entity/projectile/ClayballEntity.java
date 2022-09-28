package net.emirikol.golemancy.entity.projectile;

import net.emirikol.golemancy.Golemancy;
import net.emirikol.golemancy.entity.AbstractGolemEntity;
import net.emirikol.golemancy.network.SpawnPacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.Packet;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class ClayballEntity extends ThrownItemEntity {
    private double damage;

    public ClayballEntity(EntityType<? extends ClayballEntity> entityType, World world) {
        super(entityType, world);
    }

    public ClayballEntity(World world, LivingEntity owner) {
        super(Golemancy.CLAYBALL, owner, world);
    }

    public ClayballEntity(World world, double x, double y, double z) {
        super(Golemancy.CLAYBALL, x, y, z, world);
    }

    protected Item getDefaultItem() {
        return Items.CLAY_BALL;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    @Environment(EnvType.CLIENT)
    private ParticleEffect getParticleParameters() {
        return new ItemStackParticleEffect(ParticleTypes.ITEM, new ItemStack(this.getDefaultItem()));
    }

    @Environment(EnvType.CLIENT)
    public void handleStatus(byte status) {
        if (status == 3) {
            ParticleEffect particleEffect = this.getParticleParameters();

            for (int i = 0; i < 8; ++i) {
                this.world.addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    public Packet<?> createSpawnPacket() {
        SpawnPacket.sendSpawnPacket(this);
        return super.createSpawnPacket();
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        if (entity instanceof AbstractGolemEntity) {
            return;
        }
        entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), (float) this.damage);
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.world.isClient) {
            this.world.sendEntityStatus(this, (byte) 3);
            this.discard();
        }
    }
}