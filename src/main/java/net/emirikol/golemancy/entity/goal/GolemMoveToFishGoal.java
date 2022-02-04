package net.emirikol.golemancy.entity.goal;

import net.emirikol.golemancy.entity.AbstractGolemEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.EnumSet;
import java.util.List;

public class GolemMoveToFishGoal extends GolemMoveGoal {
    protected static final double FISH_RANGE = 3.0D;
    protected static final int BASE_FISH_TIME = 1200;

    public GolemMoveToFishGoal(AbstractGolemEntity entity, float searchRadius, float maxYDifference) {
        super(entity, searchRadius, maxYDifference);
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
    }

    @Override
    public boolean canStart() {
        return this.hasRod() && super.canStart();
    }

    @Override
    public boolean shouldContinue() { return this.hasRod() && super.shouldContinue(); }

    @Override
    public void tick() {
        //Attempt to look at fluid.
        this.entity.getLookControl().lookAt(this.targetPos.getX(), this.targetPos.getY(), this.targetPos.getZ());
        //Attempt to fish.
        if (this.canFish(this.targetPos)) {
            this.entity.getNavigation().stop();
            if (this.entity.getRandom().nextInt(this.getFishTime()) == 0) {
                this.entity.tryAttack();
                this.fish(this.targetPos);
            }
        }
        //Continue towards targetPos.
        super.tick();
    }

    @Override
    public boolean isTargetPos(BlockPos pos) {
        ServerWorld world = (ServerWorld) this.entity.world;
        FluidState fluidState = world.getBlockState(pos).getFluidState();
        return (fluidState.getFluid() == Fluids.WATER) && fluidState.isStill() && !fluidState.isEmpty() && super.isTargetPos(pos);
    }

    public boolean hasRod() {
        ItemStack stack = this.entity.getEquippedStack(EquipmentSlot.MAINHAND);
        return stack.getItem() == Items.FISHING_ROD;
    }

    public boolean canFish(BlockPos pos) {
        //Check if water is close enough to fish, and if we are holding a rod.
        if (pos.isWithinDistance(this.entity.getPos(), FISH_RANGE)) {
            return this.isTargetPos(pos) && this.hasRod();
        }
        return false;
    }

    public void fish(BlockPos pos) {
        ServerWorld world = (ServerWorld) this.entity.world;
        ItemStack stack = this.entity.getEquippedStack(EquipmentSlot.MAINHAND);

        Vec3d originPos = new Vec3d(pos.getX(), pos.getY(), pos.getZ());
        LootContext.Builder builder = new LootContext.Builder(world)
                .parameter(LootContextParameters.ORIGIN, originPos)
                .parameter(LootContextParameters.TOOL, stack)
                .parameter(LootContextParameters.THIS_ENTITY, this.entity)
                .random(this.entity.getRandom())
                .luck(this.entity.getLuckFromSmarts() + EnchantmentHelper.getLuckOfTheSea(stack)); //Each level of Smarts adds 1 point of luck.
        LootTable lootTable = world.getServer().getLootManager().getTable(LootTables.FISHING_GAMEPLAY);
        List<ItemStack> list = lootTable.generateLoot(builder.build(LootContextTypes.FISHING));
        for (ItemStack fishy : list) {
            ItemEntity itemEntity = new ItemEntity(world, this.entity.getX(), this.entity.getY(), this.entity.getZ(), fishy);
            world.spawnEntity(itemEntity);
        }
        world.playSound(null, this.entity.getX(), this.entity.getY(), this.entity.getZ(), SoundEvents.ENTITY_FISHING_BOBBER_THROW, SoundCategory.NEUTRAL, 1.5F, 1.0F + (world.random.nextFloat() - world.random.nextFloat()) * 0.4F);
    }

    public int getFishTime() {
        //Each level of Agility reduces fishing time by 10% - to a minimum of 200 ticks, or 10 seconds.
        float modifier = this.entity.getGolemAgility() * (BASE_FISH_TIME / 10.0F);
        float fishTime = BASE_FISH_TIME - modifier;
        return Math.max(200, (int) fishTime);
    }
}
