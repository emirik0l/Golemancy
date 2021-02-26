package net.emirikol.amm.client.model;

import net.emirikol.amm.entity.*;

import net.minecraft.client.model.*;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.client.util.math.*;

public class ClayEffigyEntityModel extends EntityModel<ClayEffigyEntity> {
	private final ModelPart Body;
	private final ModelPart Head;
	private final ModelPart LeftLeg;
	private final ModelPart RightLeg;
	private final ModelPart LeftArm;
	private final ModelPart RightArm;
	
	public ClayEffigyEntityModel() {
		textureWidth = 32;
		textureHeight = 32;
		Body = new ModelPart(this);
		Body.setPivot(0.0F, 10.5F, 0.0F);
		Body.setTextureOffset(0, 0).addCuboid(-4.0F, -4.5F, -3.0F, 8.0F, 9.0F, 6.0F, 0.0F, false);

		Head = new ModelPart(this);
		Head.setPivot(0.0F, 3.5F, 0.0F);
		Head.setTextureOffset(8, 21).addCuboid(-3.0F, -2.5F, -3.0F, 6.0F, 5.0F, 6.0F, 0.0F, false);

		LeftLeg = new ModelPart(this);
		LeftLeg.setPivot(2.0F, 15.5F, 0.0F);
		LeftLeg.setTextureOffset(0, 0).addCuboid(-2.0F, -0.5F, -3.0F, 4.0F, 9.0F, 6.0F, 0.0F, false);

		RightLeg = new ModelPart(this);
		RightLeg.setPivot(-2.0F, 15.5F, 0.0F);
		RightLeg.setTextureOffset(0, 0).addCuboid(-2.0F, -0.5F, -3.0F, 4.0F, 9.0F, 6.0F, 0.0F, false);

		LeftArm = new ModelPart(this);
		LeftArm.setPivot(4.5F, 6.0F, 0.0F);
		LeftArm.setTextureOffset(0, 0).addCuboid(-0.5F, 0.0F, -3.0F, 3.0F, 10.0F, 6.0F, 0.0F, false);

		RightArm = new ModelPart(this);
		RightArm.setPivot(-4.5F, 6.0F, 0.0F);
		RightArm.setTextureOffset(0, 0).addCuboid(-2.5F, 0.0F, -3.0F, 3.0F, 10.0F, 6.0F, 0.0F, false);
	}
	
	@Override
	public void setAngles(ClayEffigyEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
			//previously the render function, render code was moved to a method below
	}
	
	@Override
	public void render(MatrixStack matrixStack, VertexConsumer	buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
			
			Body.render(matrixStack, buffer, packedLight, packedOverlay);
			Head.render(matrixStack, buffer, packedLight, packedOverlay);
			LeftLeg.render(matrixStack, buffer, packedLight, packedOverlay);
			RightLeg.render(matrixStack, buffer, packedLight, packedOverlay);
			LeftArm.render(matrixStack, buffer, packedLight, packedOverlay);
			RightArm.render(matrixStack, buffer, packedLight, packedOverlay);
	}
	
	public void setRotationAngle(ModelPart bone, float x, float y, float z) {
			bone.pitch = x;
			bone.yaw = y;
			bone.roll = z;
	}
}