package com.hamburger0abcde.mekanismsun.registries;

import com.hamburger0abcde.mekanismsun.MekanismSun;
import com.hamburger0abcde.mekanismsun.tile.artificial_sun.TileEntityArtificialSunCasing;
import com.hamburger0abcde.mekanismsun.tile.artificial_sun.TileEntityArtificialSunPort;
import mekanism.common.block.interfaces.IHasDescription;
import mekanism.common.block.prefab.BlockBasicMultiblock;
import mekanism.common.item.block.ItemBlockTooltip;
import mekanism.common.registration.impl.BlockDeferredRegister;
import mekanism.common.registration.impl.BlockRegistryObject;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MapColor;

import java.util.function.Supplier;

public class MSBlocks {
    public static final BlockDeferredRegister BLOCKS = new BlockDeferredRegister(MekanismSun.MODID);

    private static <BLOCK extends Block & IHasDescription> BlockRegistryObject<BLOCK, ItemBlockTooltip<BLOCK>> registerBlock(
            String name, Supplier<? extends BLOCK> blockSupplier) {
        return BLOCKS.register(name, blockSupplier, ItemBlockTooltip::new);
    }

    public static final BlockRegistryObject<BlockBasicMultiblock<TileEntityArtificialSunCasing>,
            ItemBlockTooltip<BlockBasicMultiblock<TileEntityArtificialSunCasing>>> ARTIFICIAL_SUN_CASING = registerBlock(
                    "artificial_sun_casing",
                    () -> new BlockBasicMultiblock<>(MSBlockTypes.ARTIFICIAL_SUN_CASING,
                            properties -> properties.mapColor(MapColor.COLOR_YELLOW))
    );

    public static final BlockRegistryObject<BlockBasicMultiblock<TileEntityArtificialSunPort>,
            ItemBlockTooltip<BlockBasicMultiblock<TileEntityArtificialSunPort>>> ARTIFICIAL_SUN_PORT = registerBlock(
            "artificial_sun_port",
            () -> new BlockBasicMultiblock<>(MSBlockTypes.ARTIFICIAL_SUN_PORT,
                    properties -> properties.mapColor(MapColor.COLOR_YELLOW))
    );
}
