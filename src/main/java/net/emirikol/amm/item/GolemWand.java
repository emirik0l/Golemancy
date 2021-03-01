package net.emirikol.amm.item;

import net.emirikol.amm.entity.*;

import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.server.world.*;
import net.minecraft.nbt.*;
import net.minecraft.text.*;
import net.minecraft.util.*;

import java.util.*;

public class GolemWand extends Item {
	private static float searchRadius = 30.0F;
	
	public GolemWand(Settings settings) {
		super(settings);
	}
	
	@Override
	public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
		if (user.world.isClient()) {
			return ActionResult.PASS;
		}
		ServerWorld world = (ServerWorld) user.world;
		if (entity instanceof ClayEffigyEntity) {
			ClayEffigyEntity clayEffigyEntity = (ClayEffigyEntity) entity;
			if (clayEffigyEntity.isOwner(user)) {
				if (user.isSneaking()) {
					return startLinking();
				} else {
					return toggleFollow(clayEffigyEntity, user);
				}
			}
		}
		return ActionResult.PASS;
	}
	
	public ActionResult startLinking() {
		//Linking functionality.
		System.out.println("Linking not implemented");
		return ActionResult.PASS;
	}
	
	public ActionResult toggleFollow(ClayEffigyEntity entity, PlayerEntity user) {
		//Following functionality.
		entity.toggleFollowingWand();
		MutableText text = new LiteralText("");
		if (entity.isFollowingWand()) {
			text.append(entity.getName());
			text.append(new TranslatableText("text.amm.following_wand"));
		} else {
			text.append(entity.getName());
			text.append(new TranslatableText("text.amm.stop_following_wand"));
		}
		user.sendMessage(text, false);
		return ActionResult.SUCCESS;
	}
	
	/*
	@Override
	public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {

		if (entity instanceof ClayEffigyEntity) {
			ClayEffigyEntity clayEffigyEntity = (ClayEffigyEntity) entity;
			if (clayEffigyEntity.isOwner(user)) {
				UUID identifier = clayEffigyEntity.getUuid();
				CompoundTag tag = stack.getOrCreateTag();
				tag.putString("golem_uuid", identifier.toString());
				return ActionResult.SUCCESS;
			}
		}
		return ActionResult.PASS;
	}
	*/
}