package net.emirikol.amm.client.model;

import net.emirikol.amm.entity.*;

import net.minecraft.util.math.*;
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
		LeftLeg.setTextureOffset(0, 0).addCuboid(-2.0F, -1.5F, -3.0F, 4.0F, 10.0F, 6.0F, 0.0F, false);

		RightLeg = new ModelPart(this);
		RightLeg.setPivot(-2.0F, 15.5F, 0.0F);
		RightLeg.setTextureOffset(0, 0).addCuboid(-2.0F, -1.5F, -3.0F, 4.0F, 10.0F, 6.0F, 0.0F, false);

		LeftArm = new ModelPart(this);
		LeftArm.setPivot(4.5F, 6.0F, 0.0F);
		LeftArm.setTextureOffset(0, 0).addCuboid(-0.5F, 0.0F, -3.0F, 3.0F, 10.0F, 6.0F, 0.0F, false);

		RightArm = new ModelPart(this);
		RightArm.setPivot(-4.5F, 6.0F, 0.0F);
		RightArm.setTextureOffset(0, 0).addCuboid(-2.5F, 0.0F, -3.0F, 3.0F, 10.0F, 6.0F, 0.0F, false);
	}
	
	@Override
	public void setAngles(ClayEffigyEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		//Rotate the head.
		setRotationAngle(Head, headPitch * 0.017453292F, netHeadYaw * 0.017453292F, 0);
		
		//Rotate arms.
		float k = (float) entity.getVelocity().lengthSquared();
		k /= 0.2F;
		k *= k * k;
		if (k < 1.0F ) { k = 1.0F; }
		
		float rightArmPitch = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount / k;
		float leftArmPitch = -(MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F / k);
		setRotationAngle(RightArm, rightArmPitch, 0, 0);
		setRotationAngle(LeftArm, leftArmPitch, 0, 0);
		
		//Rotate legs.
		float rightLegPitch = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount / k;
		float leftLegPitch = MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * 1.4F * limbSwingAmount / k;
		setRotationAngle(RightLeg, rightArmPitch, 0, 0);
		setRotationAngle(LeftLeg, leftLegPitch, 0, 0);
	}
	
	@Override
	public void render(MatrixStack matrixStack, VertexConsumer	buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
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