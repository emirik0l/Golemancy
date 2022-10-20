package net.emirikol.golemancy.entity;

import com.mojang.authlib.GameProfile;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.network.encryption.PlayerPublicKey;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class FakePlayerEntity extends PlayerEntity {

    public FakePlayerEntity(World world, BlockPos pos, float yaw, GameProfile gameProfile, @Nullable PlayerPublicKey publicKey) {
        super(world, pos, yaw, gameProfile, publicKey);
    }

    public boolean isCreative() {
        return false;
    }

    public boolean isSpectator() {
        return false;
    }

    public void copyFromEntity(LivingEntity entity) {
        //Equip items.
        ItemStack stack = entity.getEquippedStack(EquipmentSlot.MAINHAND);
        this.setStackInHand(this.getActiveHand(), stack);
    }

    public void copyToEntity(LivingEntity entity) {
        //Equip items.
        ItemStack stack = this.getStackInHand(this.getActiveHand());
        entity.equipStack(EquipmentSlot.MAINHAND, stack);
    }

    public ActionResult useBlock(BlockPos pos) {
        BlockState state = this.world.getBlockState(pos);
        BlockHitResult hit = new BlockHitResult(new Vec3d(pos.getX(), pos.getY(), pos.getZ()), Direction.UP, pos, false);
        //First, try using the held item on the block.
        ItemUsageContext context = new ItemUsageContext(this, this.getActiveHand(), hit);
        ItemStack stack = this.getEquippedStack(EquipmentSlot.MAINHAND);
        ActionResult result = stack.getItem().useOnBlock(context);
        if (result == ActionResult.PASS) {
            //If that doesn't do anything, try just using the block.
            return state.getBlock().onUse(state, this.world, pos, this, this.getActiveHand(), hit);
        } else {
            return result;
        }
    }
}