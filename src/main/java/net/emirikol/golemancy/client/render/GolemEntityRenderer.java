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
		String texturePath = "textures/entity/golem/clay_golem.png";
		GolemMaterial material = entity.getMaterial() != null ? entity.getMaterial() : GolemMaterial.CLAY;
		switch(material) {
			case CLAY:
				texturePath = "textures/entity/golem/clay_golem.png";
				break;
			case TERRACOTTA:
				texturePath = "textures/entity/golem/terracotta_golem.png";
				DyeColor color = entity.getColor();
				if (color != null) {
					texturePath = String.format("textures/entity/golem/terracotta_golem_%s.png", color.getName());
				}
				break;
			case OBSIDIAN:
				texturePath = "textures/entity/golem/obsidian_golem.png";
				break;
		}

		return new Identifier("golemancy", texturePath);
	}
}