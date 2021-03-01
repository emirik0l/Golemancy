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
					return startLinking(clayEffigyEntity, stack, user);
				} else {
					return toggleFollow(clayEffigyEntity, user);
				}
			}
		}
		return ActionResult.PASS;
	}
	
	public ActionResult startLinking(ClayEffigyEntity entity, ItemStack stack, PlayerEntity user) {
		//Linking functionality.
		UUID identifier = entity.getUuid();
		CompoundTag tag = stack.getOrCreateTag();
		tag.putString("golem_uuid", identifier.toString());
		MutableText text = new LiteralText("");
		text.append(entity.getName());
		text.append(new TranslatableText("text.amm.linking_wand"));
		user.sendMessage(text, false);
		return ActionResult.SUCCESS;
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
}