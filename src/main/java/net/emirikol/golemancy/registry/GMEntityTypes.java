package net.emirikol.golemancy.registry;

import net.emirikol.golemancy.GMIdentifier;
import net.emirikol.golemancy.entity.*;
import net.emirikol.golemancy.entity.projectile.ClayballEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

public class GMEntityTypes {

    private static final Map<Identifier, EntityType<?>> ENTITY_TYPES = new LinkedHashMap<>();

    private static final float GOLEM_WIDTH = 0.7f;
    private static final float GOLEM_HEIGHT = 1.30f;
    public static final EntityType<CarefulGolemEntity> CAREFUL_GOLEM_ENTITY = create("golem_careful", FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, CarefulGolemEntity::new).dimensions(EntityDimensions.fixed(GOLEM_WIDTH, GOLEM_HEIGHT)).build());
    public static final EntityType<CovetousGolemEntity> COVETOUS_GOLEM_ENTITY = create("golem_covetous", FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, CovetousGolemEntity::new).dimensions(EntityDimensions.fixed(GOLEM_WIDTH, GOLEM_HEIGHT)).build());
    public static final EntityType<CuriousGolemEntity> CURIOUS_GOLEM_ENTITY = create("golem_curious", FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, CuriousGolemEntity::new).dimensions(EntityDimensions.fixed(GOLEM_WIDTH, GOLEM_HEIGHT)).build());
    public static final EntityType<EntropicGolemEntity> ENTROPIC_GOLEM_ENTITY = create("golem_entropic", FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, EntropicGolemEntity::new).dimensions(EntityDimensions.fixed(GOLEM_WIDTH, GOLEM_HEIGHT)).build());
    public static final EntityType<HungryGolemEntity> HUNGRY_GOLEM_ENTITY = create("golem_hungry", FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, HungryGolemEntity::new).dimensions(EntityDimensions.fixed(GOLEM_WIDTH, GOLEM_HEIGHT)).build());
    public static final EntityType<IntrepidGolemEntity> INTREPID_GOLEM_ENTITY = create("golem_intrepid", FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, IntrepidGolemEntity::new).dimensions(EntityDimensions.fixed(GOLEM_WIDTH, GOLEM_HEIGHT)).build());
    public static final EntityType<MarshyGolemEntity> MARSHY_GOLEM_ENTITY = create("golem_marshy", FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, MarshyGolemEntity::new).dimensions(EntityDimensions.fixed(GOLEM_WIDTH, GOLEM_HEIGHT)).build());
    public static final EntityType<ParchedGolemEntity> PARCHED_GOLEM_ENTITY = create("golem_parched", FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ParchedGolemEntity::new).dimensions(EntityDimensions.fixed(GOLEM_WIDTH, GOLEM_HEIGHT)).build());
    public static final EntityType<PiousGolemEntity> PIOUS_GOLEM_ENTITY = create("golem_pious",  FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, PiousGolemEntity::new).dimensions(EntityDimensions.fixed(GOLEM_WIDTH, GOLEM_HEIGHT)).build());
    public static final EntityType<RestlessGolemEntity> RESTLESS_GOLEM_ENTITY = create("golem_restless", FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, RestlessGolemEntity::new).dimensions(EntityDimensions.fixed(GOLEM_WIDTH, GOLEM_HEIGHT)).build());
    public static final EntityType<RusticGolemEntity> RUSTIC_GOLEM_ENTITY = create("golem_rustic", FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, RusticGolemEntity::new).dimensions(EntityDimensions.fixed(GOLEM_WIDTH, GOLEM_HEIGHT)).build());
    public static final EntityType<TactileGolemEntity> TACTILE_GOLEM_ENTITY = create("golem_tactile", FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, TactileGolemEntity::new).dimensions(EntityDimensions.fixed(GOLEM_WIDTH, GOLEM_HEIGHT)).build());
    public static final EntityType<ValiantGolemEntity> VALIANT_GOLEM_ENTITY = create("golem_valiant", FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ValiantGolemEntity::new).dimensions(EntityDimensions.fixed(GOLEM_WIDTH, GOLEM_HEIGHT)).build());
    public static final EntityType<VerdantGolemEntity> VERDANT_GOLEM_ENTITY = create("golem_verdant", FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, VerdantGolemEntity::new).dimensions(EntityDimensions.fixed(GOLEM_WIDTH, GOLEM_HEIGHT)).build());
    public static final EntityType<WeepingGolemEntity> WEEPING_GOLEM_ENTITY = create("golem_weeping", FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, WeepingGolemEntity::new).dimensions(EntityDimensions.fixed(GOLEM_WIDTH, GOLEM_HEIGHT)).build());
    public static final EntityType<ClayballEntity> CLAYBALL = create("clayball", FabricEntityTypeBuilder.<ClayballEntity>create(SpawnGroup.MISC, net.emirikol.golemancy.entity.projectile.ClayballEntity::new).dimensions(EntityDimensions.fixed(0.25F, 0.25F)).trackRangeBlocks(4).trackedUpdateRate(10).build());

    private static <T extends LivingEntity> EntityType<T> create(String name, DefaultAttributeContainer.Builder attributes, EntityType<T> type) {
        FabricDefaultAttributeRegistry.register(type, attributes);
        ENTITY_TYPES.put(new GMIdentifier(name), type);
        return type;
    }

    private static <T extends Entity> EntityType<T> create(String name, EntityType<T> type) {
        ENTITY_TYPES.put(new GMIdentifier(name), type);
        return type;
    }

    public static void register() {
        ENTITY_TYPES.keySet().forEach(entry -> Registry.register(Registry.ENTITY_TYPE, entry, ENTITY_TYPES.get(entry)));
    }

}
