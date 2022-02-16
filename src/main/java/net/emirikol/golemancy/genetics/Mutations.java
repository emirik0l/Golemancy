package net.emirikol.golemancy.genetics;

import java.util.ArrayList;
import java.util.List;

public class Mutations {
    public static final Mutation CAREFUL = new Mutation(SoulTypes.CURIOUS, SoulTypes.COVETOUS, SoulTypes.CAREFUL, 0.10F);
    public static final Mutation PIOUS = new Mutation(SoulTypes.WEEPING, SoulTypes.TACTILE, SoulTypes.PIOUS, 0.05F);
    public static final Mutation RUSTIC = new Mutation(SoulTypes.ENTROPIC, SoulTypes.COVETOUS, SoulTypes.RUSTIC, 0.10F);
    public static final Mutation VERDANT = new Mutation(SoulTypes.TACTILE, SoulTypes.COVETOUS, SoulTypes.VERDANT, 0.10F);

    public static final List<Mutation> MUTATIONS = new ArrayList<Mutation>() {{
       add(CAREFUL);
       add(PIOUS);
       add(RUSTIC);
       add(VERDANT);
    }};
}
