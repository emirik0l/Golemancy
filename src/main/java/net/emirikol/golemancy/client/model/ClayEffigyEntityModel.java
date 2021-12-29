package net.emirikol.golemancy.client.model;

import net.emirikol.golemancy.entity.*;

import net.minecraft.util.math.*;
import net.minecraft.client.model.*;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.client.util.math.*;
import net.minecraft.util.*;

public class ClayEffigyEntityModel extends EntityModel<ClayEffigyEntity> implements ModelWithArms {
	private final ModelPart Body;
	private final ModelPart Head;
	private final ModelPart LeftLeg;
	private final ModelPart RightLeg;
	private final ModelPart LeftArm;
	private final ModelPart RightArm;
	
	public ClayEffigyEntityModel(ModelPart modelPart) {
		this.Body = modelPart.getChild(EntityModelPartNames.BODY);
		this.Head = modelPart.getChild(EntityModelPartNames.HEAD);
		this.LeftLeg = modelPart.getChild(EntityModelPartNames.LEFT_LEG);
		this.RightLeg = modelPart.getChild(EntityModelPartNames.RIGHT_LEG);
		this.LeftArm = modelPart.getChild(EntityModelPartNames.LEFT_ARM);
		this.RightArm = modelPart.getChild(EntityModelPartNames.RIGHT_ARM);
	}
	
	public static TexturedModelData getTexturedModelData() {
		//This is where you add new ModelParts to the model. For a simple cube, there is only one ModelPart and it starts at [0,0].
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(0, 0).cuboid(-5.5F, -4.5F, -3.0F, 11.0F, 5.0F, 6.0F).uv(20, 11).cuboid(-3.5F, 0.5F, -2.0F, 7.0F, 3.0F, 4.0F), ModelTransform.pivot(-0.5F, 12.5F, 0.0F)); //Body contains two cuboids with separate textures, top and bottom
		modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(20,18).cuboid(-3.0F, -5.0F, -2.0F, 5.0F, 5.0F, 4.0F), ModelTransform.pivot(0.0F, 8.0F, 0.0F));
		modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create().uv(0, 30).cuboid(-1.5F, 0.0F, -1.0F, 3.0F, 8.0F, 2.0F), ModelTransform.pivot(2.5F, 16.0F, 0.0F));
		modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create().uv(20, 27).cuboid(-1.5F, 0.0F, -1.0F, 3.0F, 8.0F, 2.0F), ModelTransform.pivot(-3.5F, 16.0F, 0.0F));
		modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create().uv(10,11).cuboid(0.0F, 0.0F, -1.5F, 2.0F, 12.0F, 3.0F), ModelTransform.pivot(5.0F, 8.0F, 0.5F));
		modelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create().uv(0,11).cuboid(-2.0F, 0.0F, -1.5F, 2.0F, 12.0F, 3.0F), ModelTransform.pivot(-6.0F, 8.0F, 0.5F));
		
		//The size of the texture, in pixels. The default texture size is 64 wide and 32 tall.
		return TexturedModelData.of(modelData, 64, 64);
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
	public void setArmAngle(Arm arm, MatrixStack matrices) {
		this.getArm(arm).rotate(matrices);
	}
	
	protected ModelPart getArm(Arm arm) {
		return arm == Arm.LEFT ? this.LeftArm : this.RightArm;
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