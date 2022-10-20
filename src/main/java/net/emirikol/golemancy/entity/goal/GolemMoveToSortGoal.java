package net.emirikol.golemancy.entity.goal;

import net.emirikol.golemancy.entity.AbstractGolemEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;

import java.util.EnumSet;

public class GolemMoveToSortGoal extends GolemMoveGoal {
    protected static final double DEPOSIT_RANGE = 3.0D;

    public GolemMoveToSortGoal(AbstractGolemEntity entity, float maxYDifference) {
        super(entity, maxYDifference);
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
    }

    @Override
    public boolean canStart() {
        return this.hasSomething() && super.canStart();
    }

    @Override
    public boolean shouldContinue() {
        return this.hasSomething() && super.shouldContinue();
    }

    @Override
    public void tick() {
        //Attempt to look at block.
        this.entity.getLookControl().lookAt(this.targetPos.getX(), this.targetPos.getY(), this.targetPos.getZ());
        //Attempt to deposit into block.
        if (this.canDeposit(this.targetPos)) {
            this.deposit(this.targetPos);
            this.entity.world.playSound(null, this.entity.getBlockPos(), SoundEvents.ITEM_BUNDLE_INSERT, SoundCategory.NEUTRAL, 1F, 1F);
        }
        //Continue towards targetPos.
        super.tick();
    }

    @Override
    public boolean isTargetPos(BlockPos pos) {
        //We should move to a block if it is an inventory that we can insert our currently held item into, and if it already contains the item in question.
        //Also, it can't be the same as our linked inventory.
        ServerWorld world = (ServerWorld) this.entity.world;
        ItemStack stack = entity.getEquippedStack(EquipmentSlot.MAINHAND);

        Inventory inventory = GolemHelper.getInventory(pos, world);
        BlockPos linkedBlockPos = this.entity.getLinkedBlockPos();
        if (inventory == null || pos.equals(linkedBlockPos) || (linkedBlockPos != null && GolemHelper.sameDoubleInventory(pos, linkedBlockPos, world)))
            return false;

        return GolemHelper.canInsert(stack, inventory) && this.validForSorting(stack, inventory) && super.isTargetPos(pos);
    }

    @Override
    public double getDesiredDistanceToTarget() {
        return DEPOSIT_RANGE;
    }

    public boolean hasSomething() {
        ItemStack stack = this.entity.getEquippedStack(EquipmentSlot.MAINHAND);
        return !stack.isEmpty();
    }

    protected boolean validForSorting(ItemStack stack, Inventory inventory) {
        Item item = stack.getItem();
        return inventory.count(item) > 0;
    }

    public boolean canDeposit(BlockPos pos) {
        //Check if a block is close enough to deposit into, and if it can be deposited into.
        if (pos.isWithinDistance(this.entity.getBlockPos(), DEPOSIT_RANGE)) {
            return this.hasSomething() && this.isTargetPos(pos);
        }
        return false;
    }

    public void deposit(BlockPos pos) {
        ServerWorld world = (ServerWorld) this.entity.world;
        ItemStack stack = this.entity.getEquippedStack(EquipmentSlot.MAINHAND);
        Inventory inventory = GolemHelper.getInventory(pos, world);
        GolemHelper.tryInsert(stack, inventory);
    }
}
