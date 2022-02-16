package net.emirikol.golemancy.test;

import net.emirikol.golemancy.genetics.Genome;
import net.emirikol.golemancy.genetics.Genomes;
import net.emirikol.golemancy.genetics.SerializedGenome;
import net.emirikol.golemancy.genetics.SoulTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

import static net.emirikol.golemancy.test.Assertions.assertTrue;

public class GeneticsTestSuite extends AbstractTestSuite {
    public GeneticsTestSuite(World world, PlayerEntity player) {
        super(world, player);
        this.testName = "test_genetics";
    }

    @Override
    public void test() {
        genomeSerialization();
    }

    public void genomeSerialization() {
        //Create a Curious genome and serialize it.
        Genome genome = Genomes.creativeGenome(SoulTypes.CURIOUS);
        SerializedGenome serializedGenome = new SerializedGenome(genome);
        //Generate a string from the serialized genome, and deserialize it.
        String genomeString = serializedGenome.toString();
        SerializedGenome deserializedGenome = new SerializedGenome(genomeString);
        //Check that the two SerializedGenome instances have the same active and dormant alleles.
        assertTrue(serializedGenome.activeAlleles.equals(deserializedGenome.activeAlleles), "Genome corruption during serialization of active alleles!");
        assertTrue(serializedGenome.dormantAlleles.equals(deserializedGenome.dormantAlleles), "Genome corruption during serialization of dormant alleles!");
    }
}
