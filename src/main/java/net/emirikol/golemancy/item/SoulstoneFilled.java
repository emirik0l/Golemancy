package net.emirikol.golemancy.item;

import net.emirikol.golemancy.genetics.*;

import net.minecraft.item.*;
import net.minecraft.text.*;

public class SoulstoneFilled extends Item {
	
	public SoulstoneFilled(Settings settings) {
		super(settings);
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