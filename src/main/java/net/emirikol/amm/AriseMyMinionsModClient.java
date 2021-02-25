package net.emirikol.amm;

import net.emirikol.amm.*;
import net.emirikol.amm.screen.*;
import net.emirikol.amm.client.render.*;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.*;
import net.fabricmc.fabric.api.client.screenhandler.v1.*;

import net.minecraft.client.render.entity.*;

public class AriseMyMinionsModClient implements ClientModInitializer {
	
	@Override
	public void onInitializeClient() {
		doRegistration();
	}
	
	public void doRegistration() {
		//Register Soul Mirror Screen
		ScreenRegistry.register(AriseMyMinionsMod.SOUL_MIRROR_SCREEN_HANDLER, SoulMirrorScreen::new);
		//Register Soul Grafter Screen
		ScreenRegistry.register(AriseMyMinionsMod.SOUL_GRAFTER_SCREEN_HANDLER, SoulGrafterScreen::new);
		//Register Clay Effigy Renderer
		EntityRendererRegistry.INSTANCE.register(AriseMyMinionsMod.CLAY_EFFIGY_ENTITY, (dispatcher, context) -> {
			return new ClayEffigyEntityRenderer(dispatcher);
		});
	}
}