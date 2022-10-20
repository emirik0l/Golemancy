package net.emirikol.golemancy.entity.goal;

import net.emirikol.golemancy.entity.AbstractGolemEntity;
import net.emirikol.golemancy.event.ConfigurationHandler;
import net.minecraft.block.entity.JukeboxBlockEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.EnumSet;

public class GolemDanceGoal extends Goal {
    protected final AbstractGolemEntity entity;

    protected BlockPos targetPos;
    protected int cooldown;

    public GolemDanceGoal(AbstractGolemEntity entity) {
        this.entity = entity;
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
    }

    public boolean canStart() {
        if (this.cooldown > 0) {
            --this.cooldown;
            return false;
        }
        this.cooldown = ConfigurationHandler.getGolemCooldown();
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
        float searchRadius = ConfigurationHandler.getGolemRadius();
        ServerWorld world = (ServerWorld) this.entity.world;
        BlockPos pos = this.entity.getBlockPos();
        float r = searchRadius + (searchRadius * entity.getGolemSmarts());
        for (BlockPos curPos : BlockPos.iterateOutwards(pos, (int) r, (int) r, (int) r)) {
            if (world.getBlockEntity(curPos) instanceof JukeboxBlockEntity) {
                JukeboxBlockEntity entity = (JukeboxBlockEntity) world.getBlockEntity(curPos);
                if (!entity.getRecord().isEmpty() && (entity.getRecord().getItem() instanceof MusicDiscItem)) {
                    this.targetPos = curPos;
                    return true;
                }
            }
        }
        return false;
    }
}
