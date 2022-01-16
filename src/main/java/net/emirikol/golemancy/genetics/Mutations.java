package net.emirikol.golemancy.genetics;

import java.util.Arrays;
import java.util.List;

public class Mutations {
    public static final Mutation RUSTIC = new Mutation(SoulTypes.ENTROPIC, SoulTypes.COVETOUS, SoulTypes.RUSTIC, 0.10F);
    public static final Mutation VERDANT = new Mutation(SoulTypes.TACTILE, SoulTypes.COVETOUS, SoulTypes.VERDANT, 0.10F);

    public static final List<Mutation> MUTATIONS = Arrays.asList(RUSTIC, VERDANT);
}
