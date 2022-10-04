package net.emirikol.golemancy.test;

import net.emirikol.golemancy.Golemancy;
import net.emirikol.golemancy.genetics.*;
import net.emirikol.golemancy.registry.GMObjects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

import static net.emirikol.golemancy.test.Assertions.assertFalse;
import static net.emirikol.golemancy.test.Assertions.assertTrue;

public class GeneticsTestSuite extends AbstractTestSuite {
    public GeneticsTestSuite(World world, PlayerEntity player) {
        super(world, player);
        this.testName = "test_genetics";
    }

    @Override
    public void test() {
        genomeHasValidData();
        genomeSaveAndLoad();
        genomeSerialization();
        mutationAreParentsValid();
        mutationIsApplied();
    }

    public void genomeHasValidData() {
        //Create ItemStacks with valid and invalid data to test on.
        ItemStack validStack = new ItemStack(GMObjects.SOULSTONE_FILLED);
        Genomes.ZOMBIE.toItemStack(validStack);
        ItemStack invalidStack = new ItemStack(GMObjects.SOULSTONE_FILLED);
        ItemStack incorrectStack = new ItemStack(Items.DIRT);
        //Extract NBT data and test whether it is valid.
        boolean valid = Genomes.ZOMBIE.validNbt(validStack.getOrCreateNbt());
        assertTrue(valid, "valid soulstone was considered to contain invalid NBT data");
        boolean invalid = Genomes.ZOMBIE.validNbt(invalidStack.getOrCreateNbt()) || Genomes.ZOMBIE.validNbt(incorrectStack.getOrCreateNbt());
        assertFalse(invalid, "invalid soulstone was considered to contain valid NBT data");
    }

    public void genomeSaveAndLoad() {
        //Create a genome and save it to an ItemStack.
        Genome baseGenome = new Genome() {{
            put("type", new Gene<SoulType>(SoulTypes.GENERIC));
            put("potency", new Gene<Integer>(4));
            put("strength", new Gene<Integer>(3));
            put("agility", new Gene<Integer>(2));
            put("vigor", new Gene<Integer>(1));
            put("smarts", new Gene<Integer>(0));
        }};
        ItemStack stack = new ItemStack(GMObjects.SOULSTONE_FILLED);
        baseGenome.toItemStack(stack);
        //Create an empty genome and load it from the ItemStack.
        Genome newGenome = new Genome();
        newGenome.fromItemStack(stack);
        //Check that the values in the new genome conform to our expectations.
        boolean correctSoulType = newGenome.getSoulType("type").getRandom() == SoulTypes.GENERIC;
        boolean correctPotency = newGenome.getInteger("potency").getRandom() == 4;
        boolean correctStrength = newGenome.getInteger("strength").getRandom() == 3;
        boolean correctAgility = newGenome.getInteger("agility").getRandom() == 2;
        boolean correctVigor = newGenome.getInteger("vigor").getRandom() == 1;
        boolean correctSmarts = newGenome.getInteger("smarts").getRandom() == 0;
        boolean result = correctSoulType && correctPotency && correctStrength && correctAgility && correctVigor && correctSmarts;
        assertTrue(result, "saving and loading genome to a soulstone corrupted it");
    }

    public void genomeSerialization() {
        //Create a Curious genome and serialize it.
        Genome genome = Genomes.creativeGenome(SoulTypes.CURIOUS);
        SerializedGenome serializedGenome = new SerializedGenome(genome);
        //Generate a string from the serialized genome, and deserialize it.
        String genomeString = serializedGenome.toString();
        SerializedGenome deserializedGenome = new SerializedGenome(genomeString);
        //Check that the two SerializedGenome instances have the same active and dormant alleles.
        assertTrue(serializedGenome.activeAlleles.equals(deserializedGenome.activeAlleles), "genome corruption during serialization of active alleles");
        assertTrue(serializedGenome.dormantAlleles.equals(deserializedGenome.dormantAlleles), "genome corruption during serialization of dormant alleles");
    }

    public void mutationAreParentsValid() {
        //Create a new mutation and a few SoulTypes to create a valid and an invalid test case.
        Mutation mutation = new Mutation(SoulTypes.TACTILE, SoulTypes.COVETOUS, SoulTypes.ENTROPIC, 1.0F);
        boolean validResult = mutation.areParentsValid(SoulTypes.TACTILE, SoulTypes.COVETOUS);
        assertTrue(validResult, "valid soultypes for a mutation were rejected by areParentsValid()");
        boolean invalidResult = mutation.areParentsValid(SoulTypes.TACTILE, SoulTypes.RESTLESS) || mutation.areParentsValid(SoulTypes.COVETOUS, SoulTypes.INTREPID);
        assertFalse(invalidResult, "invalid soultypes for a mutation where rejected by areParentsValid()");
    }

    public void mutationIsApplied() {
        //Create a new mutation with a 100% mutation rate, along with a valid genome for it.
        Mutation mutation = new Mutation(SoulTypes.TACTILE, SoulTypes.COVETOUS, SoulTypes.PIOUS, 1.0F);
        Genome baseGenome = Genomes.GENERIC;
        Gene<SoulType> typeGene = new Gene<>(SoulTypes.COVETOUS, SoulTypes.TACTILE);
        baseGenome.put("type", typeGene);
        //Mutate the genome and check that at least one gene has been replaced with a pious soultype.
        Genome mutatedGenome = mutation.applyMutation(baseGenome);
        typeGene = mutatedGenome.getSoulType("type");
        boolean result = typeGene.getActive() == SoulTypes.PIOUS || typeGene.getDormant() == SoulTypes.PIOUS;
        assertTrue(result, "applying a mutation with 100% success rate did not produce a mutated soul");
    }
}
