package net.emirikol.golemancy;

import net.emirikol.golemancy.genetics.*;

import net.fabricmc.fabric.api.client.itemgroup.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

import java.util.*;

public class GolemancyItemGroup {
	public static ItemGroup GOLEMANCY_ITEM_GROUP;
	
	public static void buildGolemancyItemGroup() {
		GOLEMANCY_ITEM_GROUP = FabricItemGroupBuilder.create(new Identifier("golemancy", "golemancy_items"))
		.icon(() -> new ItemStack(Golemancy.CLAY_EFFIGY))
		.appendItems(stacks -> {
			stacks.add(new ItemStack(Golemancy.SOUL_MIRROR));
			stacks.add(new ItemStack(Golemancy.SOUL_GRAFTER_ITEM));
			stacks.add(new ItemStack(Golemancy.GOLEM_WAND));
			stacks.add(new ItemStack(Golemancy.CLAY_EFFIGY));
			stacks.add(new ItemStack(Golemancy.TERRACOTTA_EFFIGY));
			stacks.add(new ItemStack(Golemancy.SOULSTONE_EMPTY));
			List<Genome> genomes = Arrays.asList(
				//Natural genomes
				creativeGenome(SoulTypes.COVETOUS),
				creativeGenome(SoulTypes.CURIOUS),
				creativeGenome(SoulTypes.ENTROPIC),
				creativeGenome(SoulTypes.HUNGRY),
				creativeGenome(SoulTypes.INTREPID),
				creativeGenome(SoulTypes.MARSHY),
				creativeGenome(SoulTypes.PARCHED),
				creativeGenome(SoulTypes.RESTLESS),
				creativeGenome(SoulTypes.TACTILE),
				creativeGenome(SoulTypes.VALIANT),
				creativeGenome(SoulTypes.WEEPING),
				//Mutated genomes
				creativeGenome(SoulTypes.RUSTIC),
				creativeGenome(SoulTypes.VERDANT)
			);
			for(Genome genome: genomes) {
				ItemStack stack = new ItemStack(Golemancy.SOULSTONE_FILLED);
				genome.toItemStack(stack);
				stacks.add(stack);
			}
		})
		.build();
	}

	public static Genome creativeGenome(SoulType soulType) {
		//Create a "perfect" genome of the given type, for use in the creative menu.
		return new Genome() {{
			put("type", new Gene<SoulType>(soulType));
			put("potency", new Gene<Integer>(5));
			put("strength", new Gene<Integer>(3));
			put("agility", new Gene<Integer>(3));
			put("vigor", new Gene<Integer>(3));
			put("smarts", new Gene<Integer>(3));
		}};
	}
}