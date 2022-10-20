package net.emirikol.golemancy.screen;

import net.emirikol.golemancy.Golemancy;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;

public class SoulMirrorScreenHandler extends ScreenHandler {
    String soulData;

    public SoulMirrorScreenHandler(int syncId, PlayerInventory playerInventory) {
        super(Golemancy.SOUL_MIRROR_SCREEN_HANDLER, syncId);
        soulData = "";
    }

    public SoulMirrorScreenHandler(int syncId, PlayerInventory playerInventory, PacketByteBuf buf) {
        super(Golemancy.SOUL_MIRROR_SCREEN_HANDLER, syncId);
        soulData = buf.readString();
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int index) {
        return null;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    public String getSoulData() {
        return soulData;
    }
}