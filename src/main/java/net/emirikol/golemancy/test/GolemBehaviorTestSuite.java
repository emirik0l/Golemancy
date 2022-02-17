package net.emirikol.golemancy.test;

import net.emirikol.golemancy.Golemancy;
import net.emirikol.golemancy.entity.CovetousGolemEntity;
import net.emirikol.golemancy.entity.ParchedGolemEntity;
import net.emirikol.golemancy.entity.goal.*;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.FluidFillable;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.enums.ChestType;
import net.minecraft.datafixer.fix.ItemIdFix;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static net.emirikol.golemancy.test.Assertions.assertFalse;
import static net.emirikol.golemancy.test.Assertions.assertTrue;

public class GolemBehaviorTestSuite extends AbstractTestSuite {
    public GolemBehaviorTestSuite(World world, PlayerEntity player) {
        super(world, player);
        this.testName = "test_golem_behavior";
    }

    @Override
    public void test() {
        doubleChestSameInventory();
        goalDepositItem();
        goalExtractItem();
        goalFillVesselFromWaterloggedBlock();
        goalFindItemForPickup();
    }

    public void doubleChestSameInventory() {
        if (this.getWorld().isClient) return;
        //Place two chests next to each other.
        ServerWorld serverWorld = (ServerWorld) this.getWorld();
        BlockPos pos1 = this.getRandomBlockPos();
        BlockPos pos2 = pos1.east();
        serverWorld.setBlockState(pos1, Blocks.CHEST.getDefaultState().with(ChestBlock.CHEST_TYPE, ChestType.LEFT));
        serverWorld.setBlockState(pos2, Blocks.CHEST.getDefaultState().with(ChestBlock.CHEST_TYPE, ChestType.RIGHT));
        serverWorld.getBlockState(pos1).neighborUpdate(serverWorld, pos1, Blocks.CHEST, pos2, false);
        //Test whether they are considered to be part of the same inventory and check the result.
        boolean result = GolemHelper.sameDoubleInventory(pos1, pos2, serverWorld);
        assertTrue(result, "adjacent sides of a double chest are not part of the same inventory");
        //Tear everything down.
        serverWorld.setBlockState(pos1, Blocks.AIR.getDefaultState());
        serverWorld.setBlockState(pos2, Blocks.AIR.getDefaultState());
    }

    public void goalDepositItem() {
        if (this.getWorld().isClient) return;
        //Create a golem and give it an item, and give it a nearby chest.
        ServerWorld serverWorld = (ServerWorld) this.getWorld();
        BlockPos startPos = this.getRandomBlockPos();
        BlockPos chestPos = startPos.north();
        CovetousGolemEntity entity = Golemancy.COVETOUS_GOLEM_ENTITY.create(serverWorld, null, null, null, startPos, SpawnReason.SPAWN_EGG, true, true);
        serverWorld.spawnNewEntityAndPassengers(entity);
        entity.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.DIRT));
        serverWorld.setBlockState(chestPos, Blocks.CHEST.getDefaultState());
        GolemDepositHeldItemGoal goal = new GolemDepositHeldItemGoal(entity);
        //Link the golem to the chest and check whether it can deposit.
        entity.linkToBlockPos(chestPos);
        assertTrue(goal.canStart(), "golem could not deposit into a nearby linked container");
        //Unlink the golem and check whether it can deposit.
        entity.linkToBlockPos(null);
        assertFalse(goal.canStart(), "golem thinks it can deposit when unlinked");
        //Tear everything down.
        entity.discard();
        serverWorld.setBlockState(chestPos, Blocks.AIR.getDefaultState());
    }

    public void goalExtractItem() {
        if (this.getWorld().isClient) return;
        //Initialise world parameters.
        ServerWorld serverWorld = (ServerWorld) this.getWorld();
        BlockPos startPos = this.getRandomBlockPos();
        BlockPos chestPos = startPos.north();
        //Create a parched golem with a GolemExtractItemGoal goal.
        ParchedGolemEntity entity = Golemancy.PARCHED_GOLEM_ENTITY.create(serverWorld, null, null, null, startPos, SpawnReason.SPAWN_EGG, true, true);
        serverWorld.spawnNewEntityAndPassengers(entity);
        GolemExtractItemGoal goal = new GolemExtractItemGoal(entity);
        goal.add(Items.BUCKET);
        //Create a chest with a bucket in it.
        serverWorld.setBlockState(chestPos, Blocks.CHEST.getDefaultState());
        ChestBlockEntity blockEntity = (ChestBlockEntity) serverWorld.getBlockEntity(chestPos);
        blockEntity.setStack(0, new ItemStack(Items.BUCKET));
        //Link the golem to the chest and check whether it can extract.
        entity.linkToBlockPos(chestPos);
        assertTrue(goal.canStart(), "parched golem could not extract from chest containing bucket");
        //Unlink the golem and check whether it can extract.
        entity.linkToBlockPos(null);
        assertFalse(goal.canStart(), "parched golem thinks it can extract when unlinked");
        //Tear everything down.
        entity.discard();
        blockEntity.setStack(0, ItemStack.EMPTY);
        serverWorld.setBlockState(chestPos, Blocks.AIR.getDefaultState());
    }

    public void goalFillVesselFromWaterloggedBlock() {
        if (this.getWorld().isClient) return;
        //Generate a waterlogged oak fence and put it in the world.
        ServerWorld serverWorld = (ServerWorld) this.getWorld();
        BlockPos startPos = this.getRandomBlockPos();
        BlockState state = Blocks.OAK_FENCE.getDefaultState();
        serverWorld.setBlockState(startPos, state);
        ((FluidFillable) Blocks.OAK_FENCE).tryFillWithFluid(serverWorld, startPos, state, Fluids.WATER.getDefaultState());
        //Create a golem entity and equip them with an empty bucket.
        ParchedGolemEntity entity = Golemancy.PARCHED_GOLEM_ENTITY.create(serverWorld, null, null, null, startPos, SpawnReason.SPAWN_EGG, true, true);
        serverWorld.spawnNewEntityAndPassengers(entity);
        GolemFillVesselGoal goal = new GolemFillVesselGoal(entity);
        entity.equipStack(EquipmentSlot.MAINHAND, Items.BUCKET.getDefaultStack());
        //Attempt to drain the waterlogged block and check the result.
        ItemStack result = goal.drainFluid(startPos);
        assertTrue(result.getItem() == Items.WATER_BUCKET, "draining a waterlogged block with a bucket did not produce a water bucket");
        //Tear everything down.
        entity.discard();
        serverWorld.setBlockState(startPos, Blocks.AIR.getDefaultState());
    }

    public void goalFindItemForPickup() {
        if (this.getWorld().isClient) return;
        //Create a golem and associated movement goal.
        ServerWorld serverWorld = (ServerWorld) this.getWorld();
        BlockPos startPos = this.getRandomBlockPos();
        BlockPos itemPos = startPos.north();
        CovetousGolemEntity entity = Golemancy.COVETOUS_GOLEM_ENTITY.create(serverWorld, null, null, null, startPos, SpawnReason.SPAWN_EGG, true, true);
        serverWorld.spawnNewEntityAndPassengers(entity);
        GolemMoveToPickupGoal goal = new GolemMoveToPickupGoal(entity, 5.0F);
        //Spawn an item near the golem.
        ItemEntity itemEntity = new ItemEntity(serverWorld, itemPos.getX(), itemPos.getY(), itemPos.getZ(), new ItemStack(Items.DIAMOND));
        serverWorld.spawnEntity(itemEntity);
        //Tick the entity and test findTargetPos().
        serverWorld.tickEntity(entity);
        assertTrue(goal.findTargetPos(), "GolemMoveToPickupGoal failed to identify a nearby item");
    }
}
