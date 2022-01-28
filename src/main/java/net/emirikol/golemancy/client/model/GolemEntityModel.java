package net.emirikol.golemancy.client.model;

import net.emirikol.golemancy.entity.*;

import net.minecraft.util.math.*;
import net.minecraft.client.model.*;
import net.minecraft.util.*;

public class GolemEntityModel extends AbstractGolemEntityModel<AbstractGolemEntity> {

	public GolemEntityModel(ModelPart modelPart) {
		super(modelPart);
	}
	
	@Override
	public void setAngles(AbstractGolemEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		super.setAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

		//Attacking animation.
		if (entity.getAttackTicksLeft() > 0) {
			this.attackAnimation(entity);
			return;
		}
		
		//Arm swinging animation.
		if (entity.getSwingTicksLeft() > 0) {
			this.armSwingAnimation(entity);
			return;
		}
	}

	public void attackAnimation(AbstractGolemEntity entity) {
		int i = entity.getAttackTicksLeft();
		ModelPart attackArm = this.getArm(entity.getMainArm());
		float attackPitch = -1.0F + 1.5F * MathHelper.wrap((float)i, 10.0F);
		setRotationAngle(attackArm, attackPitch, 0, 0);
	}

	public void armSwingAnimation(AbstractGolemEntity entity) {
		int i = entity.getSwingTicksLeft();
		float swingPitch = -0.5F + MathHelper.wrap((float)i, 1.25F);
		setRotationAngle(this.getArm(Arm.LEFT), swingPitch, 0, 0);
		setRotationAngle(this.getArm(Arm.RIGHT), swingPitch, 0, 0);
	}
}