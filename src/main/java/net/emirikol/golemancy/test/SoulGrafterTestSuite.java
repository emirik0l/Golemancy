package net.emirikol.golemancy.test;

import net.emirikol.golemancy.Golemancy;
import net.emirikol.golemancy.block.entity.SoulGrafterBlockEntity;
import net.emirikol.golemancy.genetics.Genomes;
import net.emirikol.golemancy.genetics.SoulTypes;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import static net.emirikol.golemancy.test.Assertions.assertFalse;
import static net.emirikol.golemancy.test.Assertions.assertTrue;

public class SoulGrafterTestSuite extends AbstractTestSuite {
    public SoulGrafterTestSuite(World world, PlayerEntity player) {
        super(world,player);
        this.testName = "test_soul_grafter";
    }

    @Override
    public void test() {
        canInsert();
        canExtract();
        checkGraft();
    }

    public void canInsert() {
        //Create a soul grafter block at the specified location.
        ServerWorld serverWorld = (ServerWorld) this.getWorld();
        BlockPos startPos = this.getRandomBlockPos();
        serverWorld.setBlockState(startPos, Golemancy.SOUL_GRAFTER.getDefaultState());
        SoulGrafterBlockEntity entity = (SoulGrafterBlockEntity) serverWorld.getBlockEntity(startPos);
        assertTrue(entity != null, "Soul grafter block entity returned null!");
        //Test insertion into parent slots.
        for (int slot : SoulGrafterBlockEntity.PARENT_SLOTS) {
            ItemStack valid = new ItemStack(Golemancy.SOULSTONE_FILLED);
            Genomes.creativeGenome(SoulTypes.CURIOUS).toItemStack(valid);
            assertTrue(entity.canInsert(slot, valid, Direction.NORTH), "Could not insert a valid soulstone into soul grafter's parent slot!");
            ItemStack invalid = new ItemStack(Items.DIRT);
            assertFalse(entity.canInsert(slot, invalid, Direction.NORTH), "Could insert invalid item into soul grafter's parent slot!");
        }
        //Test insertion into empty soulstone slots.
        for (int slot : SoulGrafterBlockEntity.EMPTYSTONE_SLOTS) {
            ItemStack valid = new ItemStack(Golemancy.SOULSTONE_EMPTY);
            assertTrue(entity.canInsert(slot, valid, Direction.NORTH), "Could not insert a valid soulstone into soul grafter's empty stone slot!");
            ItemStack invalid = new ItemStack(Items.DIRT);
            assertFalse(entity.canInsert(slot, invalid, Direction.NORTH), "Could insert invalid item into soul grafter's empty stone slot!");
        }
        //Test insertion into fuel slots.
        for (int slot : SoulGrafterBlockEntity.FUEL_SLOTS) {
            ItemStack valid = new ItemStack(Items.BONE_MEAL);
            assertTrue(entity.canInsert(slot, valid, Direction.NORTH), "Could not insert bone meal into soul grafter's fuel slot!");
            ItemStack invalid = new ItemStack(Items.DIRT);
            assertFalse(entity.canInsert(slot, invalid, Direction.NORTH), "Could insert invalid item into soul grafter's fuel slot!");
        }
        //Test insertion into output slots.
        for (int slot : SoulGrafterBlockEntity.OUTPUT_SLOTS) {
            ItemStack valid = new ItemStack(Golemancy.SOULSTONE_FILLED);
            Genomes.creativeGenome(SoulTypes.CURIOUS).toItemStack(valid);
            assertFalse(entity.canInsert(slot, valid, Direction.NORTH), "Could not insert an item into soul grafter's output-only slot!");
        }
        //Tear everything down.
        serverWorld.setBlockState(startPos, Blocks.AIR.getDefaultState());
    }

    public void canExtract() {
        //Create a soul grafter block at the specified location.
        ServerWorld serverWorld = (ServerWorld) this.getWorld();
        BlockPos startPos = this.getRandomBlockPos();
        serverWorld.setBlockState(startPos, Golemancy.SOUL_GRAFTER.getDefaultState());
        SoulGrafterBlockEntity entity = (SoulGrafterBlockEntity) serverWorld.getBlockEntity(startPos);
        assertTrue(entity != null, "Soul grafter block entity returned null!");
        //Check we can extract from output slots.
        for (int slot : SoulGrafterBlockEntity.OUTPUT_SLOTS) {
            ItemStack valid = new ItemStack(Items.DIRT);
            assertTrue(entity.canExtract(slot, valid, Direction.NORTH), "Could not extract from a soul grafter's output slot!");
        }
        //Check we cannot extract from other slots.
        for (int slot : SoulGrafterBlockEntity.INPUT_SLOTS) {
            ItemStack valid = new ItemStack(Items.DIRT);
            assertFalse(entity.canExtract(slot, valid, Direction.NORTH), "Could extract from a soul grafter's input slot!");
        }
        //Tear everything down.
        serverWorld.setBlockState(startPos, Blocks.AIR.getDefaultState());
    }

    public void checkGraft() {
        //Create a soul grafter block at the specified location.
        ServerWorld serverWorld = (ServerWorld) this.getWorld();
        BlockPos startPos = this.getRandomBlockPos();
        serverWorld.setBlockState(startPos, Golemancy.SOUL_GRAFTER.getDefaultState());
        SoulGrafterBlockEntity entity = (SoulGrafterBlockEntity) serverWorld.getBlockEntity(startPos);
        assertTrue(entity != null, "Soul grafter block entity returned null!");
        //Insert parents, empty soulstones, and fuel into the grafter.
        for (int slot : SoulGrafterBlockEntity.PARENT_SLOTS) {
            ItemStack parent = new ItemStack(Golemancy.SOULSTONE_FILLED);
            Genomes.creativeGenome(SoulTypes.CURIOUS).toItemStack(parent);
            entity.setStack(slot, parent);
        }
        for (int slot : SoulGrafterBlockEntity.EMPTYSTONE_SLOTS) {
            ItemStack stones = new ItemStack(Golemancy.SOULSTONE_EMPTY);
            stones.setCount(64);
            entity.setStack(slot, stones);
        }
        for (int slot : SoulGrafterBlockEntity.FUEL_SLOTS) {
            ItemStack fuel = new ItemStack(Items.BONE_MEAL);
            fuel.setCount(64);
            entity.setStack(slot, fuel);
        }
        //Perform graft validity check and test result.
        assertTrue(entity.checkGraft(), "Soul grafting failed despite valid inputs!");
        //Tear everything down.
        serverWorld.setBlockState(startPos, Blocks.AIR.getDefaultState());
    }
}
