package net.emirikol.golemancy.entity.goal;

import net.emirikol.golemancy.GolemancyConfig;
import net.emirikol.golemancy.entity.AbstractGolemEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class GolemExtractItemToSortGoal extends GolemExtractItemGoal {
    protected final float searchRadius;
    protected final float maxYDifference;

    protected int cooldown;

    public GolemExtractItemToSortGoal(AbstractGolemEntity entity, float searchRadius, float maxYDifference) {
        super(entity);
        this.searchRadius = searchRadius;
        this.maxYDifference = maxYDifference;
    }

    @Override
    public boolean canStart() {
        if (this.cooldown > 0) {
            --this.cooldown;
            return false;
        }
        this.cooldown = GolemancyConfig.getGolemCooldown();
        return super.canStart();
    }

    @Override
    protected boolean canTake(ItemStack stack) {
        for (Inventory inventory : this.findInventories()) {
            if (GolemHelper.canInsert(stack, inventory) && this.validForSorting(stack, inventory) && super.canTake(stack)) {
                return true;
            }
        }
        return false;
    }

    protected List<Inventory> findInventories() {
        List<Inventory> output = new ArrayList<>();

        ServerWorld world = (ServerWorld) this.entity.world;
        BlockPos linkedBlockPos = this.entity.getLinkedBlockPos();
        if (linkedBlockPos == null) { return output; } //only search around linked block

        float r = this.searchRadius + (this.searchRadius * entity.getGolemSmarts());
        for (BlockPos curPos: BlockPos.iterateOutwards(linkedBlockPos, (int)r, (int) this.maxYDifference, (int)r)) {
            Inventory result = GolemHelper.getInventory(curPos, world);
            if (result != null && !curPos.equals(linkedBlockPos) && !GolemHelper.sameDoubleInventory(curPos, linkedBlockPos, world)) {
                output.add(result);
            }
        }
        return output;
    }

    protected boolean validForSorting(ItemStack stack, Inventory inventory) {
        Item item = stack.getItem();
        return inventory.count(item) > 0;
    }
}
