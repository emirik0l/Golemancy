package net.emirikol.golemancy;

import me.shedaniel.autoconfig.*;
import com.terraformersmc.modmenu.api.*;
import net.emirikol.golemancy.event.ConfigurationHandler;

public class GolemancyModMenu implements ModMenuApi {
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		//Causes the config screen generated by AutoConfig to be opened when the ModMenu configuration button is clicked.
		return parent -> AutoConfig.getConfigScreen(ConfigurationHandler.class, parent).get();
	}
}