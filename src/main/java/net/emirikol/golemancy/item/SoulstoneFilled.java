package net.emirikol.golemancy.item;

import net.emirikol.golemancy.genetics.Gene;
import net.emirikol.golemancy.genetics.Genome;
import net.emirikol.golemancy.genetics.SoulType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

public class SoulstoneFilled extends Item {

    public SoulstoneFilled(Settings settings) {
        super(settings.group(ItemGroup.SEARCH));
    }

    @Override
    public Text getName(ItemStack stack) {
        Genome genome = new Genome(stack);
        Gene<SoulType> gene = genome.getSoulType("type");
        if (gene == null || gene.getActive() == null) return super.getName(stack);
        String type = gene.getActive().getTypeString();
        if (type.length() > 0) {
            return Text.translatable(this.getTranslationKey(stack), gene.getActive().getTypeText());
        } else {
            return super.getName(stack);
        }
    }
}