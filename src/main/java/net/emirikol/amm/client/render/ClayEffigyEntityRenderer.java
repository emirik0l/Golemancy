package net.emirikol.amm.client.render;

import net.emirikol.amm.entity.*;
import net.emirikol.amm.client.model.*;

import net.minecraft.util.*;
import net.minecraft.client.render.entity.*;
import net.minecraft.client.render.entity.*;
import net.minecraft.client.render.entity.feature.*;

public class ClayEffigyEntityRenderer extends MobEntityRenderer<ClayEffigyEntity, ClayEffigyEntityModel> {

	public ClayEffigyEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
		//First argument is provided to the constructor.
		//The second argument is an instance of our entity model, third argument is the size of the entity's shadow.
		super(entityRenderDispatcher, new ClayEffigyEntityModel(), 0.35f);
		this.addFeature(new HeldItemFeatureRenderer(this));
	}
	
	@Override
	public Identifier getTexture(ClayEffigyEntity entity) {
		//Tells Minecraft where the find the entity's texture.
		return new Identifier("amm", "textures/entity/clay_effigy/clay_effigy.png");
	}
}