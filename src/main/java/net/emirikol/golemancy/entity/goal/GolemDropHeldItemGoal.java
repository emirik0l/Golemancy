package net.emirikol.golemancy.entity.goal;

import net.emirikol.golemancy.entity.AbstractGolemEntity;
import net.emirikol.golemancy.network.Particles;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.item.ItemStack;

public class GolemDropHeldItemGoal extends Goal {
    protected final AbstractGolemEntity entity;

    public GolemDropHeldItemGoal(AbstractGolemEntity entity) {
        this.entity = entity;
    }

    public boolean canStart() {
        //Check whether golem is holding something.
        return !entity.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty();
    }

    @Override
    public void tick() {
        //Emit frustration particle and drop your held item.
        Particles.smokeParticle(this.entity);
        ItemStack stack = entity.getEquippedStack(EquipmentSlot.MAINHAND);
        entity.dropStack(stack);
        entity.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
    }
}
