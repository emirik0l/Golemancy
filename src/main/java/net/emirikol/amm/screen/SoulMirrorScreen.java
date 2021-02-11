package net.emirikol.amm.screen;

import net.emirikol.amm.block.entity.*;
import net.emirikol.amm.genetics.*;
import net.emirikol.amm.screen.*;

import net.minecraft.entity.player.*;
import net.minecraft.screen.*;
import net.minecraft.text.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.client.util.math.*;
import net.minecraft.client.gui.screen.ingame.*;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.*;

public class SoulMirrorScreen extends HandledScreen<ScreenHandler> {
	//A path to the GUI texture to use.
	private static final Identifier TEXTURE = new Identifier("amm", "textures/gui/container/soul_mirror.png");
	public static final int TITLE_Y = 10;
	public static final int COLUMN_HEADER_Y = 25;
	public static final int COLUMN_DOM_X = 70;
	public static final int COLUMN_REC_X = 119;
	public static final int ROW_START_X = 15;
	public static final int SPECIES_ROW_Y = 40;
	public static final int POTENCY_ROW_Y = 50;
	public static final int DAMAGE_ROW_Y = 60;
	public static final int KNOCKBACK_ROW_Y = 70;
	public static final int ARMOR_ROW_Y = 80;
	public static final int SPEED_ROW_Y = 90;
	
	public SoulMirrorScreen(ScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title);
	}
	
	@Override
	protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		client.getTextureManager().bindTexture(TEXTURE);
		int x = (width - backgroundWidth) / 2;
		int y = (height - backgroundHeight) / 2;
		drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight);
	}
	
	@Override
	protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
		//Get genome data from server and deserialize.
		String soulData = ((SoulMirrorScreenHandler) this.handler).getSoulData();
		SerializedGenome serializedGenome = new SerializedGenome(soulData);
		//Draw text for soul species.
		Text soulText = new LiteralText(serializedGenome.name + " SOUL");
		int x = (backgroundWidth - textRenderer.getWidth(soulText)) / 2;
		this.textRenderer.draw(matrices, soulText, (float) x, (float) TITLE_Y, serializedGenome.getNameColor(serializedGenome.name));
		//Draw column headers.
		Text dominantText = new TranslatableText("text.amm.dom_column");
		Text recessiveText = new TranslatableText("text.amm.rec_column");
		this.textRenderer.draw(matrices, dominantText, (float) COLUMN_DOM_X, (float) COLUMN_HEADER_Y, 4210752);
		this.textRenderer.draw(matrices, recessiveText, (float) COLUMN_REC_X, (float) COLUMN_HEADER_Y, 4210752);
		//Draw species row.
		Text speciesText = new TranslatableText("text.amm.species");
		this.textRenderer.draw(matrices, speciesText, (float) ROW_START_X, (float) SPECIES_ROW_Y, 4210752);
		this.textRenderer.draw(matrices, serializedGenome.dominantAlleles.get("species"), (float) COLUMN_DOM_X, (float) SPECIES_ROW_Y, serializedGenome.getNameColor(serializedGenome.dominantAlleles.get("species")));
		this.textRenderer.draw(matrices, serializedGenome.recessiveAlleles.get("species"), (float) COLUMN_REC_X, (float) SPECIES_ROW_Y, serializedGenome.getNameColor(serializedGenome.recessiveAlleles.get("species")));
		//Draw potency row.
		Text potencyText = new TranslatableText("text.amm.potency");
		this.textRenderer.draw(matrices, potencyText, (float) ROW_START_X, (float) POTENCY_ROW_Y, 4210752);
		this.textRenderer.draw(matrices, serializedGenome.dominantAlleles.get("potency"), (float) COLUMN_DOM_X, (float) POTENCY_ROW_Y, 4210752);
		this.textRenderer.draw(matrices, serializedGenome.recessiveAlleles.get("potency"), (float) COLUMN_REC_X, (float) POTENCY_ROW_Y, 4210752);
		//Draw damage row.
		Text damageText = new TranslatableText("text.amm.damage");
		this.textRenderer.draw(matrices, damageText, (float) ROW_START_X, (float) DAMAGE_ROW_Y, 4210752);
		this.textRenderer.draw(matrices, serializedGenome.dominantAlleles.get("damage"), (float) COLUMN_DOM_X, (float) DAMAGE_ROW_Y, 4210752);
		this.textRenderer.draw(matrices, serializedGenome.recessiveAlleles.get("damage"), (float) COLUMN_REC_X, (float) DAMAGE_ROW_Y, 4210752);
		//Draw knockback row.
		Text knockbackText = new TranslatableText("text.amm.knockback");
		this.textRenderer.draw(matrices, knockbackText, (float) ROW_START_X, (float) KNOCKBACK_ROW_Y, 4210752);
		this.textRenderer.draw(matrices, serializedGenome.dominantAlleles.get("knockback"), (float) COLUMN_DOM_X, (float) KNOCKBACK_ROW_Y, 4210752);
		this.textRenderer.draw(matrices, serializedGenome.recessiveAlleles.get("knockback"), (float) COLUMN_REC_X, (float) KNOCKBACK_ROW_Y, 4210752);
		//Draw armor row.
		Text armorText = new TranslatableText("text.amm.armor");
		this.textRenderer.draw(matrices, armorText, (float) ROW_START_X, (float) ARMOR_ROW_Y, 4210752);
		this.textRenderer.draw(matrices, serializedGenome.dominantAlleles.get("armor"), (float) COLUMN_DOM_X, (float) ARMOR_ROW_Y, 4210752);
		this.textRenderer.draw(matrices, serializedGenome.recessiveAlleles.get("armor"), (float) COLUMN_REC_X, (float) ARMOR_ROW_Y, 4210752);
		//Draw speed row.
		Text speedText = new TranslatableText("text.amm.speed");
		this.textRenderer.draw(matrices, speedText, (float) ROW_START_X, (float) SPEED_ROW_Y, 4210752);
		this.textRenderer.draw(matrices, serializedGenome.dominantAlleles.get("movement_speed"), (float) COLUMN_DOM_X, (float) SPEED_ROW_Y, 4210752);
		this.textRenderer.draw(matrices, serializedGenome.recessiveAlleles.get("movement_speed"), (float) COLUMN_REC_X, (float) SPEED_ROW_Y, 4210752);
	}
	
	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		renderBackground(matrices);
		super.render(matrices, mouseX, mouseY, delta);
		drawMouseoverTooltip(matrices, mouseX, mouseY);
	}
	
	@Override
	protected void init() {
		super.init();
	}
}