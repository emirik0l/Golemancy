package net.emirikol.golemancy;

import net.emirikol.golemancy.entity.*;
import net.emirikol.golemancy.component.*;

import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentFactory;
import net.minecraft.entity.*;
import net.minecraft.util.*;

public class GolemancyComponents implements EntityComponentInitializer {
	public static final ComponentKey<GolemComponent> GOLEM = ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier("golemancy:golem"), GolemComponent.class);

	@Override
	public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
		registry.registerFor(AbstractGolemEntity.class, GOLEM, e -> new GolemComponent(e));
	}
} 