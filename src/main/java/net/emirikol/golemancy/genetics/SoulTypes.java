package net.emirikol.golemancy.genetics;

import net.emirikol.golemancy.Golemancy;
import net.emirikol.golemancy.entity.AbstractGolemEntity;

import net.minecraft.entity.EntityType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class SoulTypes {
    public static final SoulType GENERIC = new SoulType("text.golemancy.type.generic", Golemancy.RESTLESS_GOLEM_ENTITY);

    public static final SoulType COVETOUS = new SoulType("text.golemancy.type.covetous", Golemancy.COVETOUS_GOLEM_ENTITY);
    public static final SoulType CURIOUS = new SoulType("text.golemancy.type.curious", Golemancy.CURIOUS_GOLEM_ENTITY);
    public static final SoulType ENTROPIC =  new SoulType("text.golemancy.type.entropic", Golemancy.ENTROPIC_GOLEM_ENTITY);
    public static final SoulType HUNGRY = new SoulType("text.golemancy.type.hungry", Golemancy.HUNGRY_GOLEM_ENTITY);
    public static final SoulType INTREPID = new SoulType("text.golemancy.type.intrepid", Golemancy.INTREPID_GOLEM_ENTITY);
    public static final SoulType MARSHY = new SoulType("text.golemancy.type.marshy", Golemancy.MARSHY_GOLEM_ENTITY);
    public static final SoulType PARCHED = new SoulType("text.golemancy.type.parched", Golemancy.PARCHED_GOLEM_ENTITY);
    public static final SoulType RESTLESS = new SoulType("text.golemancy.type.restless", Golemancy.RESTLESS_GOLEM_ENTITY);
    public static final SoulType RUSTIC = new SoulType("text.golemancy.type.rustic", Golemancy.RUSTIC_GOLEM_ENTITY);
    public static final SoulType TACTILE = new SoulType("text.golemancy.type.tactile", Golemancy.TACTILE_GOLEM_ENTITY);
    public static final SoulType VALIANT = new SoulType("text.golemancy.type.valiant", Golemancy.VALIANT_GOLEM_ENTITY);
    public static final SoulType VERDANT = new SoulType("text.golemancy.type.verdant", Golemancy.VERDANT_GOLEM_ENTITY);
    public static final SoulType WEEPING = new SoulType("text.golemancy.type.weeping", Golemancy.WEEPING_GOLEM_ENTITY);

    public static final List<SoulType> SOUL_TYPES = Arrays.asList(GENERIC, COVETOUS, CURIOUS, ENTROPIC, HUNGRY, INTREPID, MARSHY, PARCHED, RESTLESS, RUSTIC, TACTILE, VALIANT, VERDANT, WEEPING);

    public static SoulType get(String typeString) {
        //Find a SoulType from the type string, i.e. "text.golemancy.type.curious".
        for (SoulType soulType : SOUL_TYPES) {
            if (soulType.getTypeString().equals(typeString)) { return soulType; }
        }
        return null;
    }

    public static Collection<EntityType<? extends AbstractGolemEntity>> getEntityTypes() {
        return new ArrayList<EntityType<? extends  AbstractGolemEntity>>() {{
            for (SoulType soulType : SOUL_TYPES) {
                add(soulType.getEntityType());
            }
        }};
    }
}
