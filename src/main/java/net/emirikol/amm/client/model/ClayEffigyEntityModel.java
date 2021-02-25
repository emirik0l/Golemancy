package net.emirikol.amm.client.model;

import net.emirikol.amm.entity.*;

import net.minecraft.client.model.*;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.client.util.math.*;

public class ClayEffigyEntityModel extends EntityModel<ClayEffigyEntity> {
	private final ModelPart base;
	
	public ClayEffigyEntityModel() {
		super(RenderLayer::getEntityCutout);
		//The default texture size is 64 wide and 32 tall.
		this.textureHeight = 64;
		this.textureWidth = 64;
		base = new ModelPart(this, 0, 0);
		base.setTextureOffset(0, 0).addCuboid(-4.0F, -6.0F, -3.0F, 7.0F, 7.0F, 7.0F, 0.0F, true); //body
		base.setTextureOffset(44, 54).addCuboid(-3.0F, -11.0F, -2.0F, 5.0F, 5.0F, 5.0F, 0.0F, true); //head
		base.setTextureOffset(0, 0).addCuboid(-3.0F, -6.0F, -5.0F, 5.0F, 9.0F, 2.0F, 0.0F, false); //arm
		base.setTextureOffset(0, 0).addCuboid(-3.0F, -6.0F, 4.0F, 5.0F, 9.0F, 2.0F, 0.0F, false); //arm
		base.setTextureOffset(0, 0).addCuboid(-4.0F, 1.0F, 0.0F, 7.0F, 5.0F, 4.0F, 0.0F, false); //leg
		base.setTextureOffset(0, 0).addCuboid(-4.0F, 1.0F, -3.0F, 7.0F, 5.0F, 3.0F, 0.0F, false); //leg
	}
	
	@Override
	public void setAngles(ClayEffigyEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		//TODO
	}
	
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		//Translate the model down slightly, because standard vanilla models appear higher than the entity hitbox.
		matrices.translate(0, 1.125, 0);
		//Render the model.
		base.render(matrices, vertices, light, overlay);
	}
}