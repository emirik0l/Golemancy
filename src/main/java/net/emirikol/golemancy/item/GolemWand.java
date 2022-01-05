package net.emirikol.golemancy.item;

import net.emirikol.golemancy.entity.*;

import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.server.world.*;
import net.minecraft.nbt.*;
import net.minecraft.text.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;

public class GolemWand extends Item {
	
	public GolemWand(Settings settings) {
		super(settings);
	}
	
	@Override
	public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
		if (user.world.isClient()) {
			return ActionResult.PASS;
		}
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
		if ((user == null) || (user.world.isClient())) {
			return ActionResult.PASS;
		}
		ServerWorld world = (ServerWorld) context.getWorld();
		BlockPos pos = context.getBlockPos();
		ItemStack stack = context.getStack();
		NbtCompound nbt = stack.getOrCreateNbt();
		int identifier = nbt.getInt("golem_id");
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
		int identifier = entity.getId();
		ItemStack stack = user.getStackInHand(hand);
		NbtCompound nbt = stack.getOrCreateNbt();
		int oldIdentifier = nbt.getInt("golem_id");
		if (oldIdentifier == identifier) {
			//Linking a golem to itself causes it to become unlinked.
			nbt.putInt("golem_id", 0);
			entity.linkToBlockPos(null);
			MutableText text = new LiteralText("");
			text.append(entity.getName());
			text.append(new TranslatableText("text.golemancy.unlink_linking_wand"));
			user.sendMessage(text, false);
			return ActionResult.SUCCESS;
		} else {
			//Otherwise, save the golem's entity ID to the wand's NBT.
			nbt.putInt("golem_id", identifier);
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
		NbtCompound nbt = stack.getOrCreateNbt();
		nbt.putInt("golem_id", 0);
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