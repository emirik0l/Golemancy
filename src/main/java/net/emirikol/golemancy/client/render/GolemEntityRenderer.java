package net.emirikol.golemancy.client.render;

import net.emirikol.golemancy.*;
import net.emirikol.golemancy.entity.*;
import net.emirikol.golemancy.client.model.*;

import net.minecraft.util.*;
import net.minecraft.client.render.entity.*;
import net.minecraft.client.render.entity.feature.*;

public class GolemEntityRenderer extends MobEntityRenderer<AbstractGolemEntity, GolemEntityModel> {

	public GolemEntityRenderer(EntityRendererFactory.Context context) {
		//First argument is provided to the constructor.
		//The second argument is an instance of our entity model, third argument is the size of the entity's shadow.
		super(context, new GolemEntityModel(context.getPart(GolemancyClient.MODEL_GOLEM_LAYER)), 0.35f);
		this.addFeature(new HeldItemFeatureRenderer<>(this));
	}
	
	@Override
	public Identifier getTexture(AbstractGolemEntity entity) {
		//Tells Minecraft where the find the entity's texture.
		if (entity.isBaked()) {
			String texturePath = "textures/entity/clay_golem/terracotta_golem.png";
			DyeColor color = entity.getColor();
			if (color != null) {
				texturePath = String.format("textures/entity/clay_golem/terracotta_golem_%s.png", color.getName());
			}
			return new Identifier("golemancy", texturePath);
		}

		return new Identifier("golemancy", "textures/entity/clay_golem/clay_golem.png");
	}
}