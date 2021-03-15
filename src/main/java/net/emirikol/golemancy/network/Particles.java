package net.emirikol.golemancy.network;

import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import net.minecraft.server.network.*;
import net.minecraft.server.world.*;

public class Particles {
	public static final Identifier HEAL_PARTICLE_ID = new Identifier("golemancy", "heal_particle");
	
	public static void healParticle(LivingEntity target) {
		if (target == null) {
			return;
		}

		PacketByteBuf buf = PacketByteBufs.create();
		buf.writeBlockPos(target.getBlockPos());
		
		for (ServerPlayerEntity user : PlayerLookup.tracking((ServerWorld) target.world, target.getBlockPos())) {
			ServerPlayNetworking.send((ServerPlayerEntity) user, HEAL_PARTICLE_ID, buf);
		}
	}
}