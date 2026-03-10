package com.hamburger0abcde.mekanismsun.registries;

import com.hamburger0abcde.mekanismsun.MekanismSun;
import com.hamburger0abcde.mekanismsun.tiles.artificial_sun.TileEntityArtificialSunCasing;
import com.hamburger0abcde.mekanismsun.tiles.artificial_sun.TileEntityArtificialSunPort;
import com.hamburger0abcde.mekanismsun.tiles.machine.TileEntityFreezer;
import mekanism.common.attachments.component.AttachedEjector;
import mekanism.common.attachments.component.AttachedSideConfig;
import mekanism.common.attachments.containers.ContainerType;
import mekanism.common.attachments.containers.chemical.ChemicalTanksBuilder;
import mekanism.common.attachments.containers.fluid.FluidTanksBuilder;
import mekanism.common.block.interfaces.IHasDescription;
import mekanism.common.block.prefab.BlockBasicMultiblock;
import mekanism.common.block.prefab.BlockTile.BlockTileModel;
import mekanism.common.content.blocktype.Machine;
import mekanism.common.item.block.ItemBlockTooltip;
import mekanism.common.registration.impl.BlockDeferredRegister;
import mekanism.common.registration.impl.BlockRegistryObject;
import mekanism.common.registries.MekanismAttachmentTypes;
import mekanism.common.registries.MekanismDataComponents;
import mekanism.common.resource.BlockResourceInfo;
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

    public static final BlockRegistryObject<BlockTileModel<TileEntityFreezer, Machine<TileEntityFreezer>>,
            ItemBlockTooltip<BlockTileModel<TileEntityFreezer, Machine<TileEntityFreezer>>>> FREEZER =
                BLOCKS.register("freezer", () -> new BlockTileModel<>(MSBlockTypes.FREEZER,
                        properties -> properties.mapColor(BlockResourceInfo.STEEL.getMapColor())),
                        (block, properties) -> new ItemBlockTooltip<>(
                                block, true, properties
                                .component(MekanismDataComponents.EJECTOR, AttachedEjector.DEFAULT)
                                .component(MekanismDataComponents.SIDE_CONFIG, AttachedSideConfig.ROTARY)
                        )).forItemHolder(holder -> holder
                                .addAttachmentOnlyContainers(ContainerType.CHEMICAL, () -> ChemicalTanksBuilder.builder()
                                        .addBasic(TileEntityFreezer.MAX_CHEMICAL)
                                        .build()
                                ).addAttachmentOnlyContainers(ContainerType.FLUID, () -> FluidTanksBuilder.builder()
                                        .addBasic(TileEntityFreezer.MAX_FLUID)
                                        .build()
                                )
                        );
}
