package net.emirikol.golemancy.entity;

import com.mojang.authlib.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;

import java.util.*;

public class FakePlayerEntity extends PlayerEntity {
	public FakePlayerEntity(World world, BlockPos pos, float yaw) {
		super(world, pos, yaw, new GameProfile(UUID.randomUUID(), UUID.randomUUID().toString()));
	}
	
	public boolean isCreative() {
		return false;
	}
	
	public boolean isSpectator() {
		return false;
	}
	
	public void copyFromEntity(LivingEntity entity) {
		//Equip items.
		ItemStack stack = entity.getEquippedStack(EquipmentSlot.MAINHAND);
		this.setStackInHand(this.getActiveHand(), stack);
	}
	
	public void copyToEntity(LivingEntity entity) {
		//Equip items.
		ItemStack stack = this.getStackInHand(this.getActiveHand());
		entity.equipStack(EquipmentSlot.MAINHAND, stack);
	}
}