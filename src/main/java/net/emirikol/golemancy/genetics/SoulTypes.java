package net.emirikol.golemancy.genetics;

import net.emirikol.golemancy.Golemancy;
import net.emirikol.golemancy.entity.AbstractGolemEntity;
import net.emirikol.golemancy.registry.GMEntityTypes;
import net.minecraft.entity.EntityType;

import java.util.*;

public class SoulTypes {
    public static final SoulType GENERIC = new SoulType("text.golemancy.type.generic", GMEntityTypes.RESTLESS_GOLEM_ENTITY);

    public static final SoulType CAREFUL = new SoulType("text.golemancy.type.careful", GMEntityTypes.CAREFUL_GOLEM_ENTITY);
    public static final SoulType COVETOUS = new SoulType("text.golemancy.type.covetous", GMEntityTypes.COVETOUS_GOLEM_ENTITY);
    public static final SoulType CURIOUS = new SoulType("text.golemancy.type.curious", GMEntityTypes.CURIOUS_GOLEM_ENTITY);
    public static final SoulType ENTROPIC = new SoulType("text.golemancy.type.entropic", GMEntityTypes.ENTROPIC_GOLEM_ENTITY);
    public static final SoulType HUNGRY = new SoulType("text.golemancy.type.hungry", GMEntityTypes.HUNGRY_GOLEM_ENTITY);
    public static final SoulType INTREPID = new SoulType("text.golemancy.type.intrepid", GMEntityTypes.INTREPID_GOLEM_ENTITY);
    public static final SoulType MARSHY = new SoulType("text.golemancy.type.marshy", GMEntityTypes.MARSHY_GOLEM_ENTITY);
    public static final SoulType PARCHED = new SoulType("text.golemancy.type.parched", GMEntityTypes.PARCHED_GOLEM_ENTITY);
    public static final SoulType PIOUS = new SoulType("text.golemancy.type.pious", GMEntityTypes.PIOUS_GOLEM_ENTITY);
    public static final SoulType RESTLESS = new SoulType("text.golemancy.type.restless", GMEntityTypes.RESTLESS_GOLEM_ENTITY);
    public static final SoulType RUSTIC = new SoulType("text.golemancy.type.rustic", GMEntityTypes.RUSTIC_GOLEM_ENTITY);
    public static final SoulType TACTILE = new SoulType("text.golemancy.type.tactile", GMEntityTypes.TACTILE_GOLEM_ENTITY);
    public static final SoulType VALIANT = new SoulType("text.golemancy.type.valiant", GMEntityTypes.VALIANT_GOLEM_ENTITY);
    public static final SoulType VERDANT = new SoulType("text.golemancy.type.verdant", GMEntityTypes.VERDANT_GOLEM_ENTITY);
    public static final SoulType WEEPING = new SoulType("text.golemancy.type.weeping", GMEntityTypes.WEEPING_GOLEM_ENTITY);

    public static final List<SoulType> SOUL_TYPES = new ArrayList<SoulType>() {{
        add(GENERIC);
        add(CAREFUL);
        add(COVETOUS);
        add(CURIOUS);
        add(ENTROPIC);
        add(HUNGRY);
        add(INTREPID);
        add(MARSHY);
        add(PARCHED);
        add(PIOUS);
        add(RESTLESS);
        add(RUSTIC);
        add(TACTILE);
        add(VALIANT);
        add(VERDANT);
        add(WEEPING);
    }};

    public static final Map<SoulType, Integer> TEXTURE_VARIANTS = new HashMap<SoulType, Integer>() {{
        put(RESTLESS, 1);
        put(CURIOUS, 2);
        put(HUNGRY, 3);
        put(COVETOUS, 4);
        put(VALIANT, 5);
        put(PARCHED, 6);
        put(ENTROPIC, 7);
        put(TACTILE, 8);
        put(INTREPID, 9);
        put(WEEPING, 10);
        put(RUSTIC, 11);
        put(VERDANT, 12);
        put(MARSHY, 13);
        put(CAREFUL, 14);
        put(PIOUS, 15);
    }};

    public static SoulType get(String typeString) {
        //Find a SoulType from the type string, i.e. "text.golemancy.type.curious".
        for (SoulType soulType : SOUL_TYPES) {
            if (soulType.getTypeString().equals(typeString)) {
                return soulType;
            }
        }
        return null;
    }

    public static Collection<EntityType<? extends AbstractGolemEntity>> getEntityTypes() {
        return new ArrayList<EntityType<? extends AbstractGolemEntity>>() {{
            for (SoulType soulType : SOUL_TYPES) {
                add(soulType.getEntityType());
            }
        }};
    }
}
