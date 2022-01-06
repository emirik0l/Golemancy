package net.emirikol.golemancy.genetics;

import java.util.Random;

public class Mutation {
    //The strings in this class each represent a soulstone "type string", such as "Restless" or "Curious".

    private SoulType[] parentTypes;
    private SoulType childType;
    private float chance;

    public Mutation(SoulType leftParent, SoulType rightParent, SoulType childType, float chance) {
        parentTypes = new SoulType[2];
        parentTypes[0] = leftParent;
        parentTypes[1] = rightParent;
        this.childType = childType;
        this.chance = Math.min(chance, 1.0F);
    }

    public boolean areParentsValid(SoulType leftParent, SoulType rightParent) {
        //Check if a set of 2 parents matches the parameters for this mutation.
        boolean forwards = leftParent.equals(parentTypes[0]) && rightParent.equals(parentTypes[1]);
        boolean backwards = leftParent.equals(parentTypes[1]) && rightParent.equals(parentTypes[0]);
        return forwards || backwards;
    }

    public boolean rollMutation() {
        //Roll for a mutation; returns true if the mutation should appear.
        Random rand = new Random();
        return rand.nextFloat() <= this.chance;
    }

    public Genome applyMutation(Genome genome, Gene<SoulType> leftParent, Gene<SoulType> rightParent) {
        //Attempt to roll for and potentially apply this mutation to a genome based on two parent genes.
        //Returns the modified Genome object after any mutations have been applied.
        Gene<SoulType> gene = genome.get("type");
        //Attempt to mutate active genes.
        if (areParentsValid(leftParent.getActive(), rightParent.getActive()) && rollMutation()) {
            gene.setActive(this.childType);
        }
        //Attempt to mutate dormant genes.
        if (areParentsValid(leftParent.getDormant(), rightParent.getDormant()) && rollMutation()) {
            gene.setDormant(this.childType);
        }
        genome.put("type", gene);
        return genome;
    }
}
