package net.emirikol.golemancy.event;

import net.emirikol.golemancy.Golemancy;
import net.emirikol.golemancy.genetics.Genome;
import net.emirikol.golemancy.genetics.Genomes;
import net.emirikol.golemancy.registry.GMObjects;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;

public class SoulstoneFillHandler {
    public static void soulstoneFillHook() {
        ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register((world, entity, killed) -> {
            //Check if a player killed them and if the target's soul can be captured.
            if (checkPlayer(entity) && checkSoul(killed)) {
                //Check if the player has empty soulstones.
                PlayerEntity playerEntity = (PlayerEntity) entity;
                PlayerInventory inventory = playerEntity.getInventory();
                if (checkSoulstones(inventory)) {
                    //Remove an empty soulstone from the player's inventory.
                    ItemStack soulstoneStack = getSoulstones(inventory);
                    if (soulstoneStack != null) {
                        soulstoneStack.decrement(1);
                        //Create a new filled soulstone.
                        ItemStack newSoulstoneStack = new ItemStack(GMObjects.SOULSTONE_FILLED);
                        //Get the corresponding genome and apply it to the new soulstone.
                        Genome genome = Genomes.get(killed.getType());
                        genome.toItemStack(newSoulstoneStack);
                        //Add the new soulstone to the player's inventory.
                        inventory.offerOrDrop(newSoulstoneStack);
                        inventory.markDirty();
                    }
                }
            }
        });
    }

    //Check if an entity is a player.
    private static boolean checkPlayer(Entity entity) {
        return entity instanceof PlayerEntity;
    }

    //Check if an entity has a capturable soul.
    private static boolean checkSoul(LivingEntity entity) {
        EntityType<?> entityType = entity.getType();
        for (EntityType<?> key : Genomes.getEntityTypes()) {
            if (entityType == key) {
                return true;
            }
        }
        return false;
    }

    //Check if a PlayerInventory contains soulstones.
    private static boolean checkSoulstones(PlayerInventory inventory) {
        return inventory.contains(new ItemStack(GMObjects.SOULSTONE_EMPTY));
    }

    //Get the ItemStack in a PlayerInventory that contains soulstones.
    private static ItemStack getSoulstones(PlayerInventory inventory) {
        for (ItemStack stack : inventory.main) {
            if (stack.getItem() == GMObjects.SOULSTONE_EMPTY) {
                return stack;
            }
        }
        return null;
    }
}