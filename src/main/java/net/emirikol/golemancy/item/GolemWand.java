package net.emirikol.golemancy.item;

import net.emirikol.golemancy.entity.*;
import net.emirikol.golemancy.entity.goal.GolemHelper;

import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.server.world.*;
import net.minecraft.nbt.*;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;

import java.util.List;

public class GolemWand extends Item {
	private static final double TELEPORT_RANGE = 120.0D;
	
	public GolemWand(Settings settings) {
		super(settings);
	}
	
	@Override
	public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
		//Used on a golem, it toggles their follow status. If you're sneaking, it starts linking the golem.
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
		//Used on a block, it links a golem to it.
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
		//If you're not in linking mode, using the wand on a block will teleport all following golems to you.
		List<AbstractGolemEntity> golems = user.world.getEntitiesByClass(AbstractGolemEntity.class, user.getBoundingBox().expand(TELEPORT_RANGE, TELEPORT_RANGE, TELEPORT_RANGE), (entity) -> (entity.isOwner(user) && entity.isFollowingWand()));
		boolean teleported = false;
		for (AbstractGolemEntity golem : golems) {
			if (GolemHelper.tryTeleportTo(golem, user)) teleported = true;
		}
		if (teleported) {
			user.world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.NEUTRAL, 1.5F, 1.0F + (user.world.random.nextFloat() - user.world.random.nextFloat()) * 0.4F);
		}
		return ActionResult.SUCCESS;
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
			TranslatableText text = new TranslatableText("text.golemancy.unlink_linking_wand", entity.getName());
			user.sendMessage(text, false);
			return ActionResult.SUCCESS;
		} else {
			//Otherwise, save the golem's entity ID to the wand's NBT.
			nbt.putInt("golem_id", identifier);
			TranslatableText text = new TranslatableText("text.golemancy.linking_wand", entity.getName());
			user.sendMessage(text, false);
			return ActionResult.SUCCESS;
		}
	}
	
	public ActionResult finishLinking(AbstractGolemEntity entity, ItemStack stack, BlockPos pos, PlayerEntity user, ServerWorld world) {
		//Update entity with its new linked BlockPos.
		entity.linkToBlockPos(pos);
		NbtCompound nbt = stack.getOrCreateNbt();
		nbt.putInt("golem_id", 0);
		String blockKey = world.getBlockState(pos).getBlock().getTranslationKey();
		TranslatableText text = new TranslatableText("text.golemancy.finished_linking_wand", entity.getName(), new TranslatableText(blockKey));
		user.sendMessage(text, false);
		return ActionResult.SUCCESS;
	}
	
	public ActionResult toggleFollow(AbstractGolemEntity entity, PlayerEntity user) {
		//Following functionality.
		entity.toggleFollowingWand();
		TranslatableText text;
		if (entity.isFollowingWand()) {
			text = new TranslatableText("text.golemancy.following_wand", entity.getName());
		} else {
			text = new TranslatableText("text.golemancy.stop_following_wand", entity.getName());
		}
		user.sendMessage(text, false);
		return ActionResult.SUCCESS;
	}
}