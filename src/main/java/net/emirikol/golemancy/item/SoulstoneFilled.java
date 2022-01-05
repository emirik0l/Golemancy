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
		Text baseName = new TranslatableText(this.getTranslationKey(stack));
		Genome genome = new Genome(stack);
		Gene<String> gene = genome.get("type");
		String type = gene.getActive();
		if (type.length() > 0) {
			return typeToText(type).append(new LiteralText(" ")).append(baseName);
		} else {
			return baseName;
		}
	}
	
	public TranslatableText typeToText(String type) {
		return new TranslatableText("text.golemancy.type." + type.toLowerCase());
	}
}