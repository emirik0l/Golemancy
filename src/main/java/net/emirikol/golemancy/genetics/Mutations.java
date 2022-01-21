package net.emirikol.golemancy.genetics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Mutations {
    public static final Mutation METICULOUS = new Mutation(SoulTypes.CURIOUS, SoulTypes.COVETOUS, SoulTypes.METICULOUS, 0.10F);
    public static final Mutation RUSTIC = new Mutation(SoulTypes.ENTROPIC, SoulTypes.COVETOUS, SoulTypes.RUSTIC, 0.10F);
    public static final Mutation VERDANT = new Mutation(SoulTypes.TACTILE, SoulTypes.COVETOUS, SoulTypes.VERDANT, 0.10F);

    public static final List<Mutation> MUTATIONS = new ArrayList<Mutation>() {{
       add(METICULOUS);
       add(RUSTIC);
       add(VERDANT);
    }};
}
