package net.emirikol.amm.component;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;

public interface BooleanComponent extends ComponentV3 {
    boolean getValue();
	void setValue(boolean value);
}