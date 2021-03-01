package net.emirikol.amm.item;

import net.emirikol.amm.entity.*;

import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.server.world.*;
import net.minecraft.util.*;

import java.util.*;

public class GolemWand extends Item {
	private static float searchRadius = 30.0F;
	
	public GolemWand(Settings settings) {
		super(settings);
	}
	
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		if (world.isClient()) {
			return super.use(world, user, hand);
		}
		float r = this.searchRadius;
		List<ClayEffigyEntity> list = world.getEntitiesByClass(ClayEffigyEntity.class, user.getBoundingBox().expand(r,r,r), null);
		for (ClayEffigyEntity e: list) {
			if (e.isOwner(user)) {
				if (user.isSneaking()) {
					e.setUnsummoned();
				} else {
					e.setSummoned();
				}
			}
		}
		return super.use(world, user, hand);
	}
	
	@Override
	public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
		System.out.println("used wand on golem");
		return ActionResult.SUCCESS;
	}
}