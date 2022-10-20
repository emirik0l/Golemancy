package net.emirikol.golemancy.event;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.Tooltip;
import net.emirikol.golemancy.Golemancy;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;

@Config(name = "golemancy")
public class ConfigurationHandler implements ConfigData {

    @Tooltip
    public float GRAFT_SPEED_MULTIPLIER = 1.0F;
    @Tooltip
    public float GRAFT_FUEL_MULTIPLIER = 1.0F;
    @Tooltip
    public float GRAFT_POTENCY_MULTIPLIER = 1.0F;
    @Tooltip
    public double GOLEM_ARMOR_VALUE = 8.0D;
    @Tooltip
    public int GOLEM_AI_COOLDOWN = 10;
    @Tooltip
    public int GOLEM_AI_RADIUS = 10;

    public static void syncConfigHook() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ConfigurationHandler config = AutoConfig.getConfigHolder(ConfigurationHandler.class).getConfig();
            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeFloat(config.GRAFT_SPEED_MULTIPLIER);
            buf.writeFloat(config.GRAFT_FUEL_MULTIPLIER);
            buf.writeFloat(config.GRAFT_POTENCY_MULTIPLIER);
            buf.writeDouble(config.GOLEM_ARMOR_VALUE);
            buf.writeInt(config.GOLEM_AI_COOLDOWN);
            buf.writeInt(config.GOLEM_AI_RADIUS);
            ServerPlayNetworking.send(handler.player, Golemancy.ConfigPacketID, buf);
        });
    }

    public static int getGraftDuration() {
        //How many ticks should a soul grafter take to graft two souls?
        ConfigurationHandler config = AutoConfig.getConfigHolder(ConfigurationHandler.class).getConfig();
        float baseGraftDuration = 2400.0F;  //base graft duration is 2400 ticks, or 2 minutes
        float adjustedGraftDuration = baseGraftDuration / config.GRAFT_SPEED_MULTIPLIER;
        int roundedGraftDuration = Math.round(adjustedGraftDuration);
        return Math.max(roundedGraftDuration, 1);
    }

    public static int getFuelValue() {
        //How many ticks of fuel should a piece of bone meal provide?
        ConfigurationHandler config = AutoConfig.getConfigHolder(ConfigurationHandler.class).getConfig();
        float baseFuelValue = 600.0F; //bonemeal burns for 600 ticks, or 30 seconds
        float adjustedFuelValue = baseFuelValue * config.GRAFT_FUEL_MULTIPLIER;
        int roundedFuelValue = Math.round(adjustedFuelValue);
        return Math.max(roundedFuelValue, 1);
    }

    public static float getPotencyMultiplier() {
        //How many soulstones should a soul grafting produce, compared to the default potency?
        ConfigurationHandler config = AutoConfig.getConfigHolder(ConfigurationHandler.class).getConfig();
        return config.GRAFT_POTENCY_MULTIPLIER;
    }

    public static double getGolemArmorValue() {
        //How much armor should a terracotta golem have?
        ConfigurationHandler config = AutoConfig.getConfigHolder(ConfigurationHandler.class).getConfig();
        return Math.max(config.GOLEM_ARMOR_VALUE, 0.5D);
    }

    public static int getGolemCooldown() {
        //How many ticks should a golem wait after certain search-heavy goals?
        ConfigurationHandler config = AutoConfig.getConfigHolder(ConfigurationHandler.class).getConfig();
        return Math.max(config.GOLEM_AI_COOLDOWN, 0);
    }

    public static int getGolemRadius() {
        //What is the "base" search radius for a golem?
        ConfigurationHandler config = AutoConfig.getConfigHolder(ConfigurationHandler.class).getConfig();
        return Math.max(config.GOLEM_AI_RADIUS, 3);
    }
}