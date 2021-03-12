package net.emirikol.golemancy;

import net.emirikol.golemancy.*;
import net.emirikol.golemancy.entity.*;
import net.emirikol.golemancy.screen.*;
import net.emirikol.golemancy.client.render.*;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.*;
import net.fabricmc.fabric.api.client.screenhandler.v1.*;
import net.minecraft.entity.*;
import net.minecraft.client.render.entity.*;

import java.util.*;

public class GolemancyClient implements ClientModInitializer {
	
	@Override
	public void onInitializeClient() {
		doRegistration();
	}
	
	public void doRegistration() {
		//Register Soul Mirror Screen
		ScreenRegistry.register(Golemancy.SOUL_MIRROR_SCREEN_HANDLER, SoulMirrorScreen::new);
		//Register Soul Grafter Screen
		ScreenRegistry.register(Golemancy.SOUL_GRAFTER_SCREEN_HANDLER, SoulGrafterScreen::new);
		//Register Clay Effigy Renderer
		EntityRendererRegistry.INSTANCE.register(Golemancy.CLAY_EFFIGY_ENTITY, (dispatcher, context) -> {
			return new ClayEffigyEntityRenderer(dispatcher);
		});
		//Register Golem Renderers
		for(EntityType type: Golems.getTypes()) {
			EntityRendererRegistry.INSTANCE.register(type, (dispatcher, context) -> {
				return new ClayGolemEntityRenderer(dispatcher);
			});
		}
	}
}