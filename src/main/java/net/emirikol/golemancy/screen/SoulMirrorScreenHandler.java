package net.emirikol.golemancy.screen;

import net.minecraft.entity.player.*;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.*;
import net.minecraft.network.*;

import net.emirikol.golemancy.*;

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