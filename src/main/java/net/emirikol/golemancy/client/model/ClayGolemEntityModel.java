package net.emirikol.golemancy.client.model;

import net.emirikol.golemancy.entity.*;

import net.minecraft.util.math.*;
import net.minecraft.client.model.*;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.client.util.math.*;
import net.minecraft.util.*;

public class ClayGolemEntityModel extends EntityModel<AbstractGolemEntity> implements ModelWithArms {
	private final ModelPart Body;
	private final ModelPart Head;
	private final ModelPart LeftLeg;
	private final ModelPart RightLeg;
	private final ModelPart LeftArm;
	private final ModelPart RightArm;

	public ClayGolemEntityModel(ModelPart modelPart) {
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
		modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -4.5F, -3.0F, 8.0F, 9.0F, 6.0F), ModelTransform.pivot(0F, 10.5F, 0F));
		modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(28, 0).cuboid(-3.0F, -2.5F, -3.0F, 6.0F, 5.0F, 6.0F), ModelTransform.pivot(0F, 3.5F, 0F));
		modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create().uv(0, 15).cuboid(-2.0F, -1.5F, -3.0F, 4.0F, 10.0F, 6.0F), ModelTransform.pivot(2.0F, 15.5F, 0.0F));
		modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create().uv(20, 15).cuboid(-2.0F, -1.5F, -3.0F, 4.0F, 10.0F, 6.0F), ModelTransform.pivot(-2.0F, 15.5F, 0.0F));
		modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create().uv(0, 31).cuboid(-0.5F, 0.0F, -3.0F, 3.0F, 10.0F, 6.0F), ModelTransform.pivot(4.5F, 6.0F, 0.0F));
		modelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create().uv(18, 31).cuboid(-2.5F, 0.0F, -3.0F, 3.0F, 10.0F, 6.0F), ModelTransform.pivot(-4.5F, 6.0F, 0.0F));
		
		//The size of the texture, in pixels. The default texture size is 64 wide and 32 tall.
		return TexturedModelData.of(modelData, 64, 64);
	}
	
	@Override
	public void setAngles(AbstractGolemEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
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
		
		//Attacking animation.
		int i = entity.getAttackTicksLeft();
		if (i > 0) {
			ModelPart attackArm = this.getArm(entity.getMainArm());
			float attackPitch = -1.0F + 1.5F * MathHelper.wrap((float)i, 10.0F);
			setRotationAngle(attackArm, attackPitch, 0, 0);
		}
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