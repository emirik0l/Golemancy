package net.emirikol.golemancy.test;

import net.emirikol.golemancy.GolemancyItemGroup;
import net.emirikol.golemancy.genetics.Gene;
import net.emirikol.golemancy.genetics.Genome;
import net.emirikol.golemancy.genetics.SerializedGenome;
import net.emirikol.golemancy.genetics.SoulTypes;
import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.test.GameTest;
import net.minecraft.test.TestContext;

import static net.emirikol.golemancy.test.Assertions.assertTrue;

public class GeneticsTest implements FabricGameTest {
    @GameTest(structureName = FabricGameTest.EMPTY_STRUCTURE)
    public void genomeSerialization(TestContext context) {
        //Create a Curious genome and serialize it.
        Genome genome = GolemancyItemGroup.creativeGenome(SoulTypes.CURIOUS);
        SerializedGenome serializedGenome = new SerializedGenome(genome);
        //Generate a string from the serialized genome, and deserialize it.
        String genomeString = serializedGenome.toString();
        SerializedGenome deserializedGenome = new SerializedGenome(genomeString);
        //Check that the two SerializedGenome instances have the same active and dormant alleles.
        assertTrue(serializedGenome.activeAlleles.equals(deserializedGenome.activeAlleles), "Genome corruption during serialization of active alleles!");
        assertTrue(serializedGenome.dormantAlleles.equals(deserializedGenome.dormantAlleles), "Genome corruption during serialization of dormant alleles!");
        context.complete();
    }
}
