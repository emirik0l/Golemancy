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
import net.minecraft.util.math.*;

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
	
	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		PlayerEntity user = context.getPlayer();
		if (user.world.isClient()) {
			return ActionResult.PASS;
		}
		ServerWorld world = (ServerWorld) context.getWorld();
		BlockPos pos = context.getBlockPos();
		ItemStack stack = context.getStack();
		CompoundTag tag = stack.getOrCreateTag();
		int identifier = tag.getInt("golem_id");
		if (identifier != 0) {
			Entity entity = world.getEntityById(identifier);
			if (entity instanceof ClayEffigyEntity) {
				ClayEffigyEntity clayEffigyEntity = (ClayEffigyEntity) entity;
				if (clayEffigyEntity.isOwner(user)) {
					return finishLinking(clayEffigyEntity, stack, pos, user, world);
				}
			}
		}
		return ActionResult.PASS;
	}
	
	public ActionResult startLinking(ClayEffigyEntity entity, ItemStack stack, PlayerEntity user) {
		//Linking functionality.
		int identifier = entity.getEntityId();
		CompoundTag tag = stack.getOrCreateTag();
		tag.putInt("golem_id", identifier);
		MutableText text = new LiteralText("");
		text.append(entity.getName());
		text.append(new TranslatableText("text.amm.linking_wand"));
		user.sendMessage(text, false);
		return ActionResult.SUCCESS;
	}
	
	public ActionResult finishLinking(ClayEffigyEntity entity, ItemStack stack, BlockPos pos, PlayerEntity user, ServerWorld world) {
		//Update entity with its new linked BlockPos.
		entity.linkToBlockPos(pos);
		CompoundTag tag = stack.getOrCreateTag();
		tag.putInt("golem_id", 0);
		MutableText text = new LiteralText("");
		text.append(entity.getName());
		text.append(new TranslatableText("text.amm.finished_linking_wand"));
		text.append(world.getBlockState(pos).getBlock().getName());
		text.append(new LiteralText("!"));
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