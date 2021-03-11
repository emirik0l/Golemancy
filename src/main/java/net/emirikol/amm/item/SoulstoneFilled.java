package net.emirikol.amm.item;

import net.emirikol.amm.genetics.*;
import net.emirikol.amm.util.*;

import net.minecraft.item.*;
import net.minecraft.text.*;

public class SoulstoneFilled extends Item {
	
	public SoulstoneFilled(Settings settings) {
		super(settings);
	}
	
	@Override
	public Text getName(ItemStack stack) {
		Text baseName = new TranslatableText(this.getTranslationKey(stack));
		Genome genome = new Genome(stack);
		Gene<String> gene = genome.get("type");
		String type = gene.getActive();
		if (type.length() > 0) {
			MutableText output = GolemHelper.getGolemText(type).append(new LiteralText(" ")).append(baseName);
			return output;
		} else {
			return baseName;
		}
	}
}