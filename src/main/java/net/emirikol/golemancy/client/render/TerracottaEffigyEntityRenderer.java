package net.emirikol.golemancy.client.render;

import net.emirikol.golemancy.*;
import net.emirikol.golemancy.entity.*;
import net.emirikol.golemancy.client.model.*;

import net.minecraft.util.*;
import net.minecraft.client.render.entity.*;
import net.minecraft.client.render.entity.feature.*;

public class TerracottaEffigyEntityRenderer extends MobEntityRenderer<TerracottaEffigyEntity, TerracottaEffigyEntityModel> {
    public TerracottaEffigyEntityRenderer(EntityRendererFactory.Context context) {
        //First argument is provided to the constructor.
        //The second argument is an instance of our entity model, third argument is the size of the entity's shadow.
        super(context, new TerracottaEffigyEntityModel(context.getPart(GolemancyClient.MODEL_EFFIGY_LAYER)), 0.35f);
        this.addFeature(new HeldItemFeatureRenderer(this));
    }

    @Override
    public Identifier getTexture(TerracottaEffigyEntity entity) {
        //Tells Minecraft where the find the entity's texture.
        return new Identifier("golemancy", "textures/entity/terracotta_effigy/terracotta_effigy.png");
    }
}
