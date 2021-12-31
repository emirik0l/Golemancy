package net.emirikol.golemancy;

import com.terraformersmc.modmenu.api.*;
import me.shedaniel.clothconfig2.api.*;

import net.minecraft.text.*;
import net.minecraft.client.util.math.*;

public class GolemancyConfig implements ModMenuApi {
	
	public static final int BASE_GRAFT_DURATION = 2400; //base graft duration is 2400 ticks, or 2 minutes
	public static final int BASE_FUEL_VALUE = 600; //basebonemeal burns for 600 ticks, or 30 seconds
	
	public static float GRAFT_SPEED_MULTIPLIER = 1.0F;
	public static float GRAFT_FUEL_MULTIPLIER = 1.0F;
	
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return parent -> {
			ConfigBuilder builder = ConfigBuilder.create().setParentScreen(parent).setTitle(new TranslatableText("title.golemancy.config"));
			ConfigCategory general = builder.getOrCreateCategory(new TranslatableText("category.golemancy.general"));
			ConfigEntryBuilder entryBuilder = builder.entryBuilder();
	
			//Grafting Speed
			general.addEntry(entryBuilder.startFloatField(new TranslatableText("option.golemancy.graft_speed_multiplier"), GRAFT_SPEED_MULTIPLIER).setDefaultValue(1.0F).setTooltip(new LiteralText("Multiplier on how fast soul grafting should occur.")).setSaveConsumer(newValue -> GRAFT_SPEED_MULTIPLIER = newValue).build());
			
			//Fuel Value
			general.addEntry(entryBuilder.startFloatField(new TranslatableText("option.golemancy.graft_fuel_multiplier"), GRAFT_FUEL_MULTIPLIER).setDefaultValue(1.0F).setTooltip(new LiteralText("Multiplier on how long bone meal lasts in the soul grafter.")).setSaveConsumer(newValue -> GRAFT_FUEL_MULTIPLIER = newValue).build());
			
			builder.setSavingRunnable(() -> {
				// Serialise the config into the config file. This will be called last after all variables are updated.
				// TODO
			});
			return builder.build();
		};
	}
	
	public static int getGraftDuration() {
		//How many ticks should a soul grafter take to graft two souls?
		float adjustedGraftDuration = (float) BASE_GRAFT_DURATION / GRAFT_SPEED_MULTIPLIER;
		int roundedGraftDuration = Math.round(adjustedGraftDuration);
		if (roundedGraftDuration < 1) {
			return 1;
		}
		return roundedGraftDuration;
	}
	
	public static int getFuelValue() {
		//How many ticks of fuel should a piece of bone meal provide?
		float adjustedFuelValue = (float) BASE_FUEL_VALUE * GRAFT_FUEL_MULTIPLIER;
		int roundedFuelValue = Math.round(adjustedFuelValue);
		if (roundedFuelValue < 1) {
			return 1;
		}
		return roundedFuelValue;
	}
}