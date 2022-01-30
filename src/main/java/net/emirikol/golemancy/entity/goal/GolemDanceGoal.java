package net.emirikol.golemancy.entity.goal;

import net.emirikol.golemancy.GolemancyConfig;
import net.emirikol.golemancy.entity.AbstractGolemEntity;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.JukeboxBlock;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.EnumSet;

public class GolemDanceGoal extends Goal {
    protected final AbstractGolemEntity entity;

    protected BlockPos targetPos;
    protected float searchRadius;
    protected int cooldown;

    public GolemDanceGoal(AbstractGolemEntity entity, float searchRadius) {
        this.entity = entity;
        this.searchRadius = searchRadius;
        this.setControls(EnumSet.of(Goal.Control.MOVE,Goal.Control.LOOK));
    }

    public boolean canStart() {
        if (this.cooldown > 0) {
            --this.cooldown;
            return false;
        }
        this.cooldown = GolemancyConfig.getGolemCooldown();
        return this.canHearMusic();
    }

    @Override
    public boolean shouldContinue() {
        return this.canHearMusic();
    }

    @Override
    public void tick() {
        //Look at jukebox.
        Vec3d lookPos = Vec3d.ofCenter(this.targetPos);
        this.entity.getLookControl().lookAt(lookPos.getX(), lookPos.getY(), lookPos.getZ());
        //Stop.
        this.entity.getNavigation().stop();
        //Hammer time!
        this.entity.tryDance();
    }

    public boolean canHearMusic() {
        ServerWorld world = (ServerWorld) this.entity.world;
        BlockPos pos = this.entity.getBlockPos();
        float r = this.searchRadius + (10.0F * entity.getGolemSmarts());
        for (BlockPos curPos: BlockPos.iterateOutwards(pos, (int)r, (int)r, (int)r)) {
            BlockState state = world.getBlockState(curPos);
            if (state.getBlock() == Blocks.JUKEBOX) {
                this.targetPos = curPos;
                return state.get(JukeboxBlock.HAS_RECORD);
            }
        }
        return false;
    }
}
