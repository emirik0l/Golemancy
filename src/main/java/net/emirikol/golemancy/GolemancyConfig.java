package net.emirikol.golemancy;

import com.terraformersmc.modmenu.api.*;
import me.shedaniel.clothconfig2.api.*;

import net.minecraft.text.*;

public class GolemancyConfig implements ModMenuApi {
	
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return parent -> {
			ConfigBuilder builder = ConfigBuilder.create().setParentScreen(parent).setTitle(new TranslatableText("title.golemancy.config"));
			ConfigCategory general = builder.getOrCreateCategory(new TranslatableText("category.golemancy.general"));
			ConfigEntryBuilder entryBuilder = builder.entryBuilder();
	
			/*
			general.addEntry(entryBuilder.startStrField(new TranslatableText("option.golemancy.test_option"), testValue).setDefaultValue("beep").setTooltip(new LiteralText("this is a test")).setSaveConsumer(newValue -> testValue = newValue).build());
			*/
			
			builder.setSavingRunnable(() -> {
				// Serialise the config into the config file. This will be called last after all variables are updated.
				// TODO
			});
			return builder.build();
		};
	}
}