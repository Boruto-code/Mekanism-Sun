package com.hamburger0abcde.mekanismsun.registries;

import com.hamburger0abcde.mekanismsun.MekanismSunLang;
import com.hamburger0abcde.mekanismsun.tile.artificial_sun.TileEntityArtificialSunCasing;
import com.hamburger0abcde.mekanismsun.tile.artificial_sun.TileEntityArtificialSunPort;
import mekanism.common.block.attribute.Attribute;
import mekanism.common.block.attribute.Attributes;
import mekanism.common.content.blocktype.BlockTypeTile;
import mekanism.common.registries.MekanismSounds;

public class MSBlockTypes {
    public static final BlockTypeTile<TileEntityArtificialSunCasing> ARTIFICIAL_SUN_CASING = BlockTypeTile.BlockTileBuilder
            .createBlock(() -> MSTileEntityTypes.ARTIFICIAL_SUN_CASING, MekanismSunLang.DESCRIPTION_ARTIFICIAL_SUN_CASING)
            .withGui(() -> MSContainerTypes.ARTIFICIAL_SUN, MekanismSunLang.ARTIFICIAL_SUN)
            .withSound(MekanismSounds.SPS)
            .with(Attributes.ACTIVE, Attributes.COMPARATOR)
            .externalMultiblock()
            .build();

    public static final BlockTypeTile<TileEntityArtificialSunPort> ARTIFICIAL_SUN_PORT = BlockTypeTile.BlockTileBuilder
            .createBlock(() -> MSTileEntityTypes.ARTIFICIAL_SUN_PORT, MekanismSunLang.DESCRIPTION_ARTIFICIAL_SUN_CASING)
            .withGui(() -> MSContainerTypes.ARTIFICIAL_SUN, MekanismSunLang.ARTIFICIAL_SUN)
            .withSound(MekanismSounds.SPS)
            .with(Attributes.ACTIVE, Attributes.COMPARATOR)
            .externalMultiblock()
            .build();
}
