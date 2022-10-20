package net.emirikol.golemancy.client.model;

import net.emirikol.golemancy.entity.AbstractGolemEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.util.Arm;
import net.minecraft.util.math.MathHelper;

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

        if (entity.getPrayTicksLeft() > 0) {
            this.prayAnimation(entity);
            return;
        }

        //Dancing animation.
        if (entity.getDanceTicksLeft() > 0) {
            this.danceAnimation(entity);
            return;
        }
    }

    public void attackAnimation(AbstractGolemEntity entity) {
        int i = entity.getAttackTicksLeft();
        ModelPart attackArm = this.getArm(entity.getMainArm());
        float attackPitch = -1.0F + 1.5F * MathHelper.wrap((float) i, 10.0F);
        setRotationAngle(attackArm, attackPitch, 0, 0);
    }

    public void armSwingAnimation(AbstractGolemEntity entity) {
        int i = entity.getSwingTicksLeft();
        float swingPitch = -0.5F + MathHelper.wrap((float) i, 1.25F);
        setRotationAngle(this.getArm(Arm.LEFT), swingPitch, 0, 0);
        setRotationAngle(this.getArm(Arm.RIGHT), swingPitch, 0, 0);
    }

    public void prayAnimation(AbstractGolemEntity entity) {
        int i = entity.getPrayTicksLeft();
        ModelPart leftArm = this.getArm(Arm.LEFT);
        ModelPart rightArm = this.getArm(Arm.RIGHT);
        float prayPitch = -((2.0F * (80.0F - i)) / 80.0F);
        setRotationAngle(leftArm, prayPitch, 0, -0.5F);
        setRotationAngle(rightArm, prayPitch, 0, 0.5F);
    }

    public void danceAnimation(AbstractGolemEntity entity) {
        int i = entity.getDanceTicksLeft();
        ModelPart head = this.getHead();
        ModelPart leftArm = this.getArm(Arm.LEFT);
        ModelPart rightArm = this.getArm(Arm.RIGHT);
        //Head rolls from side to side twice per second (10 ticks).
        int headRollTicks = i % 10;
        float headRoll = MathHelper.sin(headRollTicks * 0.628319F) * 0.3F;
        setRotationAngle(head, 0, 0, headRoll);
        //Arms go up and down alternating like cha-cha-cha, twice per second (10 ticks).
        int armPitchTicks = i % 10;
        float leftArmPitch = 5.25F + (MathHelper.sin(armPitchTicks * 0.628319F) * 0.75F);
        float rightArmPitch = 5.25F + (MathHelper.sin(armPitchTicks * -0.628319F) * 0.75F);
        setRotationAngle(leftArm, leftArmPitch, 0, 0);
        setRotationAngle(rightArm, rightArmPitch, 0, 0);
    }
}