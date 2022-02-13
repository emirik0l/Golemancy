package net.emirikol.golemancy.entity.goal;

import net.emirikol.golemancy.entity.AbstractGolemEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;

import java.util.List;

public class GolemPickupGoal extends Goal {
    protected static final float PICKUP_RANGE = 1.5F;

    protected final AbstractGolemEntity entity;

    public GolemPickupGoal(AbstractGolemEntity entity) {
        this.entity = entity;
    }

    public boolean canStart() {
        return entity.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty();
    }

    @Override
    public void tick() {
        //Check if there is an item within 1.5 blocks and the golem's hand is empty.
        List<ItemEntity> list = entity.world.getEntitiesByClass(ItemEntity.class, entity.getBoundingBox().expand(PICKUP_RANGE, PICKUP_RANGE, PICKUP_RANGE), (entity) -> this.canPickUp(entity));
        if (!list.isEmpty() && entity.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty()) {
            //Take 1 item from the stack.
            ItemStack stack = list.get(0).getStack();
            entity.equipStack(EquipmentSlot.MAINHAND, stack.split(1));
            this.entity.world.playSound(null, this.entity.getBlockPos(), SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.NEUTRAL, 1F, 1F);
        }
    }

    public boolean canPickUp(ItemEntity entity) {
        return entity != null;
    }
}
