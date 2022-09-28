package net.emirikol.golemancy.block;

import net.emirikol.golemancy.entity.GolemMaterial;

public class ObsidianEffigyBlock extends ClayEffigyBlock {
    public ObsidianEffigyBlock(Settings settings) {
        super(settings);
    }

    @Override
    public GolemMaterial getEffigyMaterial() {
        return GolemMaterial.OBSIDIAN;
    }
}
