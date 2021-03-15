package net.emirikol.golemancy.entity.projectile;

import net.emirikol.golemancy.*;
import net.emirikol.golemancy.entity.*;
import net.emirikol.golemancy.network.*;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.*;
import net.minecraft.entity.projectile.thrown.*;
import net.minecraft.particle.*;
import net.minecraft.network.*;
import net.minecraft.world.*;
import net.minecraft.util.hit.*;

public class ClayballEntity extends ThrownItemEntity {
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
	
	@Environment(EnvType.CLIENT)
	private ParticleEffect getParticleParameters() {
		ItemStack itemStack = this.getItem();
		return (ParticleEffect)(itemStack.isEmpty() ? ParticleTypes.ITEM_SNOWBALL : new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack));
	}

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status == 3) {
			ParticleEffect particleEffect = this.getParticleParameters();

			for(int i = 0; i < 8; ++i) {
				this.world.addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
			}
		}
	}
	
	@Override
	public Packet createSpawnPacket() {
		return EntitySpawnPacket.create(this, GolemancyClient.PacketID);
	}
	
	@Override
	protected void onEntityHit(EntityHitResult entityHitResult) {
		super.onEntityHit(entityHitResult);
		Entity entity = entityHitResult.getEntity();
		int i = entity instanceof AbstractGolemEntity ? 0 : 3;
		entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), (float)i);
	}

	@Override
	protected void onCollision(HitResult hitResult) {
		super.onCollision(hitResult);
		if (!this.world.isClient) {
			this.world.sendEntityStatus(this, (byte)3);
			this.remove();
		}
	}
}