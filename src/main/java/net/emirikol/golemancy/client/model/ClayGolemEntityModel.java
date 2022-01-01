package net.emirikol.golemancy.client.model;

import net.emirikol.golemancy.entity.*;

import net.minecraft.util.math.*;
import net.minecraft.client.model.*;
import net.minecraft.util.*;

public class ClayGolemEntityModel extends AbstractGolemEntityModel<AbstractGolemEntity> {

	public ClayGolemEntityModel(ModelPart modelPart) {
		super(modelPart);
	}
	
	@Override
	public void setAngles(AbstractGolemEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		super.setAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		
		//Attacking animation.
		int i = entity.getAttackTicksLeft();
		if (i > 0) {
			ModelPart attackArm = this.getArm(entity.getMainArm());
			float attackPitch = -1.0F + 1.5F * MathHelper.wrap((float)i, 10.0F);
			setRotationAngle(attackArm, attackPitch, 0, 0);
		}
		
		//Arm swinging animation.
		int j = entity.getSwingTicksLeft();
		if (j > 0) {
			float swingPitch = -0.5F + MathHelper.wrap((float)j, 1.25F);
			setRotationAngle(this.getArm(Arm.LEFT), swingPitch, 0, 0);
			setRotationAngle(this.getArm(Arm.RIGHT), swingPitch, 0, 0);
		}
	}
}