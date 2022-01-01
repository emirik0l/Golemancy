package net.emirikol.golemancy;

import me.shedaniel.autoconfig.*;
import me.shedaniel.autoconfig.annotation.*;

@Config(name = "golemancy")
public class GolemancyConfig implements ConfigData {	
	public float GRAFT_SPEED_MULTIPLIER = 1.0F;
	public float GRAFT_FUEL_MULTIPLIER = 1.0F;
	public float GRAFT_POTENCY_MULTIPLIER = 1.0F;

	public static int getGraftDuration() {
		//How many ticks should a soul grafter take to graft two souls?
		GolemancyConfig config = AutoConfig.getConfigHolder(GolemancyConfig.class).getConfig();
		float baseGraftDuration = 2400.0F;  //base graft duration is 2400 ticks, or 2 minutes
		float adjustedGraftDuration = baseGraftDuration / config.GRAFT_SPEED_MULTIPLIER;
		int roundedGraftDuration = Math.round(adjustedGraftDuration);
		if (roundedGraftDuration < 1) {
			return 1;
		}
		return roundedGraftDuration;
	}
	
	public static int getFuelValue() {
		//How many ticks of fuel should a piece of bone meal provide?
		GolemancyConfig config = AutoConfig.getConfigHolder(GolemancyConfig.class).getConfig();
		float baseFuelValue = 600.0F; //bonemeal burns for 600 ticks, or 30 seconds
		float adjustedFuelValue = baseFuelValue * config.GRAFT_FUEL_MULTIPLIER; 
		int roundedFuelValue = Math.round(adjustedFuelValue);
		if (roundedFuelValue < 1) {
			return 1;
		}
		return roundedFuelValue;
	}
	
	public static float getPotencyMultiplier() {
		//How many soulstones should a soul grafting produce, compared to the default potency?
		GolemancyConfig config = AutoConfig.getConfigHolder(GolemancyConfig.class).getConfig();
		return config.GRAFT_POTENCY_MULTIPLIER;
	}
}