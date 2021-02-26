package net.emirikol.amm;

import net.emirikol.amm.entity.*;
import net.emirikol.amm.component.*;

import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactory;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import net.minecraft.entity.*;
import net.minecraft.util.*;

public class AriseMyMinionsComponents implements EntityComponentInitializer {
	public static final ComponentKey<GolemComponent> GOLEM = ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier("amm:golem"), GolemComponent.class);

	@Override
	public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
		registry.registerFor(ClayEffigyEntity.class, GOLEM, e -> new GolemComponent(e));
	}
} 