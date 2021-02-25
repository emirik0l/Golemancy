package net.emirikol.amm.screen;

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
	public static final int COLUMN_HEADER_Y = 35;
	public static final int COLUMN_DOM_X = 70;
	public static final int COLUMN_REC_X = 120;
	public static final int ROW_START_X = 15;
	public static final int TYPE_ROW_Y = 50;
	public static final int POTENCY_ROW_Y = 60;
	public static final int STRENGTH_ROW_Y = 70;
	public static final int AGILITY_ROW_Y = 80;
	public static final int VIGOR_ROW_Y = 90;
	public static final int SMARTS_ROW_Y = 100;
	
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
		//Draw title.
		Text titleText = new TranslatableText("item.amm.soul_mirror");
		int x = (backgroundWidth - textRenderer.getWidth(titleText)) / 2;
		this.textRenderer.draw(matrices, titleText, (float) x, (float) TITLE_Y, 4210752);
		//Draw column headers.
		Text activeText = new TranslatableText("text.amm.active_column");
		Text dormantText = new TranslatableText("text.amm.dormant_column");
		this.textRenderer.draw(matrices, activeText, (float) COLUMN_DOM_X, (float) COLUMN_HEADER_Y, 0xff0000);
		this.textRenderer.draw(matrices, dormantText, (float) COLUMN_REC_X, (float) COLUMN_HEADER_Y, 0x00acff);
		//Draw type row.
		Text typeText = new TranslatableText("text.amm.type");
		this.textRenderer.draw(matrices, typeText, (float) ROW_START_X, (float) TYPE_ROW_Y, 4210752);
		this.textRenderer.draw(matrices, serializedGenome.activeAlleles.get("type"), (float) COLUMN_DOM_X, (float) TYPE_ROW_Y, 4210752);
		this.textRenderer.draw(matrices, serializedGenome.dormantAlleles.get("type"), (float) COLUMN_REC_X, (float) TYPE_ROW_Y, 4210752);
		//Draw potency row.
		Text potencyText = new TranslatableText("text.amm.potency");
		this.textRenderer.draw(matrices, potencyText, (float) ROW_START_X, (float) POTENCY_ROW_Y, 4210752);
		this.textRenderer.draw(matrices, serializedGenome.activeAlleles.get("potency"), (float) COLUMN_DOM_X, (float) POTENCY_ROW_Y, 4210752);
		this.textRenderer.draw(matrices, serializedGenome.dormantAlleles.get("potency"), (float) COLUMN_REC_X, (float) POTENCY_ROW_Y, 4210752);
		//Draw strength row.
		Text strengthText = new TranslatableText("text.amm.strength");
		this.textRenderer.draw(matrices, strengthText, (float) ROW_START_X, (float) STRENGTH_ROW_Y, 4210752);
		this.textRenderer.draw(matrices, geneToText(serializedGenome.activeAlleles.get("strength")), (float) COLUMN_DOM_X, (float) STRENGTH_ROW_Y, 4210752);
		this.textRenderer.draw(matrices, geneToText(serializedGenome.dormantAlleles.get("strength")), (float) COLUMN_REC_X, (float) STRENGTH_ROW_Y, 4210752);
		//Draw agility row.
		Text agilityText = new TranslatableText("text.amm.agility");
		this.textRenderer.draw(matrices, agilityText, (float) ROW_START_X, (float) AGILITY_ROW_Y, 4210752);
		this.textRenderer.draw(matrices, geneToText(serializedGenome.activeAlleles.get("agility")), (float) COLUMN_DOM_X, (float) AGILITY_ROW_Y, 4210752);
		this.textRenderer.draw(matrices, geneToText(serializedGenome.dormantAlleles.get("agility")), (float) COLUMN_REC_X, (float) AGILITY_ROW_Y, 4210752);
		//Draw vigor row.
		Text vigorText = new TranslatableText("text.amm.vigor");
		this.textRenderer.draw(matrices, vigorText, (float) ROW_START_X, (float) VIGOR_ROW_Y, 4210752);
		this.textRenderer.draw(matrices, geneToText(serializedGenome.activeAlleles.get("vigor")), (float) COLUMN_DOM_X, (float) VIGOR_ROW_Y, 4210752);
		this.textRenderer.draw(matrices, geneToText(serializedGenome.dormantAlleles.get("vigor")), (float) COLUMN_REC_X, (float) VIGOR_ROW_Y, 4210752);
		//Draw smarts row.
		Text smartsText = new TranslatableText("text.amm.smarts");
		this.textRenderer.draw(matrices, smartsText, (float) ROW_START_X, (float) SMARTS_ROW_Y, 4210752);
		this.textRenderer.draw(matrices, geneToText(serializedGenome.activeAlleles.get("smarts")), (float) COLUMN_DOM_X, (float) SMARTS_ROW_Y, 4210752);
		this.textRenderer.draw(matrices, geneToText(serializedGenome.dormantAlleles.get("smarts")), (float) COLUMN_REC_X, (float) SMARTS_ROW_Y, 4210752);
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
	
	public Text geneToText(String geneValue) {
		switch (geneValue) {
			case "0":
				return new TranslatableText("text.amm.gene_low");
			case "1":
				return new TranslatableText("text.amm.gene_average");
			case "2":
				return new TranslatableText("text.amm.gene_high");
			case "3":
				return new TranslatableText("text.amm.gene_perfect");
			default:
				return new LiteralText("???");
		}
	}
}