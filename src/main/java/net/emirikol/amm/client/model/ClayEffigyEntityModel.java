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
		//Set height and width to 64, so it's cube shaped. The default texture size is 64 wide and 32 tall.
		this.textureHeight = 64;
		this.textureWidth = 64;
		//For a simple cube, there is only one ModelPart and it starts at [0,0].
		base = new ModelPart(this, 0, 0);
		//Now we add a cuboid to the base. The cuboid is 12 wide, or 75% of a block. This is the same size as the dimensions we gave the entity earlier.
		base.setTextureOffset(0, 0).addCuboid(-4.0F, -6.0F, -3.0F, 7.0F, 12.0F, 7.0F); //body
		base.setTextureOffset(44, 54).addCuboid(-3.0F, -11.0F, -2.0F, 5.0F, 5.0F, 5.0F); //head
		base.setTextureOffset(0, 0).addCuboid(-3.0F, -6.0F, 4.0F, 5.0F, 9.0F, 2.0F); //arm
		base.setTextureOffset(0, 0).addCuboid(-3.0F, -6.0F, -5.0F, 5.0F, 9.0F, 2.0F); //arm
	}
	
	@Override
	public void setAngles(ClayEffigyEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		//We can leave this empty for a simple cube with only one part.
	}
	
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		//Translate the model down slightly, because standard vanilla models appear higher than the entity hitbox.
		matrices.translate(0, 1.125, 0);
		//Render the model.
		base.render(matrices, vertices, light, overlay);
	}
}