package net.emirikol.amm;

import net.emirikol.amm.component.*;

import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactory;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import net.minecraft.entity.*;
import net.minecraft.util.*;

public class AriseMyMinionsComponents implements EntityComponentInitializer {
	public static final ComponentKey<BooleanComponent> SUMMONED = ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier("amm:summoned"), BooleanComponent.class);
	
	@Override
	public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
		registry.registerFor(LivingEntity.class, SUMMONED, e -> new SummonedComponent());
	}
}