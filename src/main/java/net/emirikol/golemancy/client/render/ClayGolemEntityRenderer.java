package net.emirikol.golemancy.client.render;

import net.emirikol.golemancy.entity.*;
import net.emirikol.golemancy.client.model.*;

import net.minecraft.util.*;
import net.minecraft.client.render.entity.*;
import net.minecraft.client.render.entity.*;
import net.minecraft.client.render.entity.feature.*;

public class ClayGolemEntityRenderer extends MobEntityRenderer<AbstractGolemEntity, ClayGolemEntityModel> {

	public ClayGolemEntityRenderer(EntityRendererFactory.Context context) {
		//First argument is provided to the constructor.
		//The second argument is an instance of our entity model, third argument is the size of the entity's shadow.
		super(context, new ClayGolemEntityModel(), 0.35f);
		this.addFeature(new HeldItemFeatureRenderer(this));
	}
	
	@Override
	public Identifier getTexture(AbstractGolemEntity entity) {
		//Tells Minecraft where the find the entity's texture.
		return new Identifier("golemancy", "textures/entity/clay_effigy/clay_effigy.png");
	}
}