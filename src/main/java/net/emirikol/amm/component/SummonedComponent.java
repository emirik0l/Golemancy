package net.emirikol.amm.component;

import net.minecraft.nbt.*;

public class SummonedComponent implements BooleanComponent {
	private boolean value = false;
	@Override public boolean getValue() { return this.value; }
	@Override public void setValue(boolean value) { this.value = value; }
    @Override public void readFromNbt(CompoundTag tag) { this.value = tag.getBoolean("amm_summoned"); }
    @Override public void writeToNbt(CompoundTag tag) { tag.putBoolean("amm_summoned", this.value); }
}