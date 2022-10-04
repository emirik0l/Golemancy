package net.emirikol.golemancy;

import net.emirikol.golemancy.genetics.Genome;
import net.emirikol.golemancy.genetics.Genomes;
import net.emirikol.golemancy.genetics.SoulTypes;
import net.emirikol.golemancy.registry.GMObjects;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.List;

public class GolemancyItemGroup {
    public static final ItemGroup GOLEMANCY_ITEM_GROUP = FabricItemGroupBuilder.create(new GMIdentifier("golemancy_items"))
                .icon(() -> new ItemStack(GMObjects.CLAY_EFFIGY))
                .appendItems(stacks -> {
                    stacks.add(new ItemStack(GMObjects.SOUL_MIRROR));
                    stacks.add(new ItemStack(GMObjects.SOUL_GRAFTER));
                    stacks.add(new ItemStack(GMObjects.GOLEM_WAND));
                    stacks.add(new ItemStack(GMObjects.CLAY_EFFIGY));
                    stacks.add(new ItemStack(GMObjects.TERRACOTTA_EFFIGY));
                    stacks.add(new ItemStack(GMObjects.OBSIDIAN_EFFIGY));
                    stacks.add(new ItemStack(GMObjects.SOULSTONE_EMPTY));
                    List<Genome> genomes = Arrays.asList(
                            //Natural genomes
                            Genomes.creativeGenome(SoulTypes.COVETOUS),
                            Genomes.creativeGenome(SoulTypes.CURIOUS),
                            Genomes.creativeGenome(SoulTypes.ENTROPIC),
                            Genomes.creativeGenome(SoulTypes.HUNGRY),
                            Genomes.creativeGenome(SoulTypes.INTREPID),
                            Genomes.creativeGenome(SoulTypes.MARSHY),
                            Genomes.creativeGenome(SoulTypes.PARCHED),
                            Genomes.creativeGenome(SoulTypes.RESTLESS),
                            Genomes.creativeGenome(SoulTypes.TACTILE),
                            Genomes.creativeGenome(SoulTypes.VALIANT),
                            Genomes.creativeGenome(SoulTypes.WEEPING),
                            //Mutated genomes
                            Genomes.creativeGenome(SoulTypes.CAREFUL),
                            Genomes.creativeGenome(SoulTypes.PIOUS),
                            Genomes.creativeGenome(SoulTypes.RUSTIC),
                            Genomes.creativeGenome(SoulTypes.VERDANT)
                    );
                    for (Genome genome : genomes) {
                        ItemStack stack = new ItemStack(GMObjects.SOULSTONE_FILLED);
                        genome.toItemStack(stack);
                        stacks.add(stack);
                    }
                }).build();
}