package net.emirikol.golemancy;

import me.shedaniel.autoconfig.*;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.Tooltip;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;

@Config(name = "golemancy")
public class GolemancyConfig implements ConfigData {

	@Tooltip public float GRAFT_SPEED_MULTIPLIER = 1.0F;
	@Tooltip public float GRAFT_FUEL_MULTIPLIER = 1.0F;
	@Tooltip public float GRAFT_POTENCY_MULTIPLIER = 1.0F;
	@Tooltip public double TERRACOTTA_ARMOR_VALUE = 8.0D;
	@Tooltip public int GOLEM_AI_COOLDOWN = 10;

	public static void syncConfigHook() {
		ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
			GolemancyConfig config = AutoConfig.getConfigHolder(GolemancyConfig.class).getConfig();
			PacketByteBuf buf = PacketByteBufs.create();
			buf.writeFloat(config.GRAFT_SPEED_MULTIPLIER);
			buf.writeFloat(config.GRAFT_FUEL_MULTIPLIER);
			buf.writeFloat(config.GRAFT_POTENCY_MULTIPLIER);
			buf.writeDouble(config.TERRACOTTA_ARMOR_VALUE);
			buf.writeInt(config.GOLEM_AI_COOLDOWN);
			ServerPlayNetworking.send(handler.player, Golemancy.ConfigPacketID, buf);
		});
	}

	public static int getGraftDuration() {
		//How many ticks should a soul grafter take to graft two souls?
		GolemancyConfig config = AutoConfig.getConfigHolder(GolemancyConfig.class).getConfig();
		float baseGraftDuration = 2400.0F;  //base graft duration is 2400 ticks, or 2 minutes
		float adjustedGraftDuration = baseGraftDuration / config.GRAFT_SPEED_MULTIPLIER;
		int roundedGraftDuration = Math.round(adjustedGraftDuration);
		return Math.max(roundedGraftDuration, 1);
	}
	
	public static int getFuelValue() {
		//How many ticks of fuel should a piece of bone meal provide?
		GolemancyConfig config = AutoConfig.getConfigHolder(GolemancyConfig.class).getConfig();
		float baseFuelValue = 600.0F; //bonemeal burns for 600 ticks, or 30 seconds
		float adjustedFuelValue = baseFuelValue * config.GRAFT_FUEL_MULTIPLIER; 
		int roundedFuelValue = Math.round(adjustedFuelValue);
		return Math.max(roundedFuelValue, 1);
	}
	
	public static float getPotencyMultiplier() {
		//How many soulstones should a soul grafting produce, compared to the default potency?
		GolemancyConfig config = AutoConfig.getConfigHolder(GolemancyConfig.class).getConfig();
		return config.GRAFT_POTENCY_MULTIPLIER;
	}

	public static double getTerracottaArmorValue() {
		//How much armor should a terracotta golem have?
		GolemancyConfig config = AutoConfig.getConfigHolder(GolemancyConfig.class).getConfig();
		return Math.max(config.TERRACOTTA_ARMOR_VALUE, 0.5D);
	}

	public static int getGolemCooldown() {
		//How many ticks should a golem wait after certain search-heavy goals?
		GolemancyConfig config = AutoConfig.getConfigHolder(GolemancyConfig.class).getConfig();
		return Math.max(config.GOLEM_AI_COOLDOWN, 0);
	}
}