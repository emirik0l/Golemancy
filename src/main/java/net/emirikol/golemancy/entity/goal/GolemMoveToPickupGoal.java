package net.emirikol.golemancy.entity.goal;

import net.emirikol.golemancy.entity.AbstractGolemEntity;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class GolemMoveToPickupGoal extends GolemMoveGoal {
    public GolemMoveToPickupGoal(AbstractGolemEntity entity, float searchRadius) {
        super(entity, searchRadius);
    }

    public GolemMoveToPickupGoal(AbstractGolemEntity entity, float searchRadius, float maxYDifference) {
        super(entity, searchRadius, maxYDifference);
    }

    @Override
    public boolean canStart() {
        return super.canStart() && entity.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty();
    }

    @Override
    public boolean shouldContinue() { return super.shouldContinue() && entity.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty(); }

    @Override
    public void tick() {
        //Check if there is an item within 1.5 blocks and the golem's hand is empty.
        List<ItemEntity> list = entity.world.getEntitiesByClass(ItemEntity.class, entity.getBoundingBox().expand(1.5F,1.5F,1.5F), (entity) -> true);
        if (!list.isEmpty() && entity.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty()) {
            //Take 1 item from the stack.
            ItemStack stack = list.get(0).getStack();
            entity.equipStack(EquipmentSlot.MAINHAND, stack.split(1));
        }
        //Continue towards targetPos.
        super.tick();
    }

    @Override
    public boolean findTargetPos() {
        float r = this.searchRadius + (10.0F * entity.getGolemSmarts());
        List<ItemEntity> list = entity.world.getEntitiesByClass(ItemEntity.class, entity.getBoundingBox().expand(r, this.maxYDifference, r), (entity) -> true);
        if (list.isEmpty()) { return false; }
        for (ItemEntity itemEntity: list) {
            BlockPos pos = itemEntity.getBlockPos();
            if (this.entity.isInWalkTargetRange(pos) && this.isTargetPos(pos)) {
                this.targetPos = pos;
                return true;
            }
        }
        return false;
    }
}
