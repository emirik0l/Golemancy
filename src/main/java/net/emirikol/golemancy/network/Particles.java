package net.emirikol.golemancy.network;

import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.entity.*;
import net.minecraft.particle.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.server.network.*;
import net.minecraft.server.world.*;
import net.minecraft.client.*;

import java.util.*;

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
	
	@Environment(EnvType.CLIENT)
	public static void spawnHealParticle(BlockPos pos) {
		Random rand = MinecraftClient.getInstance().world.getRandom();
		for(int i = 0; i<15; i++) {
			double d = 0.5D;
			double m = (double)pos.getX() + rand.nextDouble() * d;
			double n = (double)pos.getY() + rand.nextDouble() * d + 0.5D;
			double o = (double)pos.getZ() + rand.nextDouble() * d;
			double h = rand.nextGaussian() * 0.02D;
			double j = rand.nextGaussian() * 0.02D;
			double k = rand.nextGaussian() * 0.02D;
			MinecraftClient.getInstance().world.addParticle(ParticleTypes.HAPPY_VILLAGER, m, n, o, h, j, k);
		}
	}
}