package net.emirikol.golemancy.item;

import net.emirikol.golemancy.entity.*;

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
		if (entity instanceof AbstractGolemEntity) {
			AbstractGolemEntity golemEntity = (AbstractGolemEntity) entity;
			if (golemEntity.isOwner(user)) {
				if (user.isSneaking()) {
					return startLinking(golemEntity, user, hand);
				} else {
					return toggleFollow(golemEntity, user);
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
			if (entity instanceof AbstractGolemEntity) {
				AbstractGolemEntity golemEntity = (AbstractGolemEntity) entity;
				if (golemEntity.isOwner(user)) {
					return finishLinking(golemEntity, stack, pos, user, world);
				}
			}
		}
		return ActionResult.PASS;
	}
	
	public ActionResult startLinking(AbstractGolemEntity entity, PlayerEntity user, Hand hand) {
		//Linking functionality.
		int identifier = entity.getEntityId();
		ItemStack stack = user.getStackInHand(hand);
		CompoundTag tag = stack.getOrCreateTag();
		int oldIdentifier = tag.getInt("golem_id");
		if (oldIdentifier == identifier) {
			//Linking a golem to itself causes it to become unlinked.
			tag.putInt("golem_id", 0);
			entity.linkToBlockPos(null);
			MutableText text = new LiteralText("");
			text.append(entity.getName());
			text.append(new TranslatableText("text.golemancy.unlink_linking_wand"));
			user.sendMessage(text, false);
			return ActionResult.SUCCESS;
		} else {
			//Otherwise, save the golem's entity ID to the wand's NBT.
			tag.putInt("golem_id", identifier);
			MutableText text = new LiteralText("");
			text.append(entity.getName());
			text.append(new TranslatableText("text.golemancy.linking_wand"));
			user.sendMessage(text, false);
			return ActionResult.SUCCESS;
		}
	}
	
	public ActionResult finishLinking(AbstractGolemEntity entity, ItemStack stack, BlockPos pos, PlayerEntity user, ServerWorld world) {
		//Update entity with its new linked BlockPos.
		entity.linkToBlockPos(pos);
		CompoundTag tag = stack.getOrCreateTag();
		tag.putInt("golem_id", 0);
		MutableText text = new LiteralText("");
		text.append(entity.getName());
		text.append(new TranslatableText("text.golemancy.finished_linking_wand"));
		text.append(new TranslatableText(world.getBlockState(pos).getBlock().getTranslationKey()));
		text.append(new LiteralText("!"));
		user.sendMessage(text, false);
		return ActionResult.SUCCESS;
	}
	
	public ActionResult toggleFollow(AbstractGolemEntity entity, PlayerEntity user) {
		//Following functionality.
		entity.toggleFollowingWand();
		MutableText text = new LiteralText("");
		if (entity.isFollowingWand()) {
			text.append(entity.getName());
			text.append(new TranslatableText("text.golemancy.following_wand"));
		} else {
			text.append(entity.getName());
			text.append(new TranslatableText("text.golemancy.stop_following_wand"));
		}
		user.sendMessage(text, false);
		return ActionResult.SUCCESS;
	}
}