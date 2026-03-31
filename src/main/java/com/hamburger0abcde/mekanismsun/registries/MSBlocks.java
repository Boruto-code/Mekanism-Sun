package com.hamburger0abcde.mekanismsun.registries;

import com.hamburger0abcde.mekanismsun.MekanismSun;
import com.hamburger0abcde.mekanismsun.attachments.containers.MSComponentBackedBinInventorySlot;
import com.hamburger0abcde.mekanismsun.attachments.containers.MSComponentBackedChemicalTankTank;
import com.hamburger0abcde.mekanismsun.attachments.containers.MSComponentBackedFluidTankFluidTank;
import com.hamburger0abcde.mekanismsun.block.attribute.MSAttributeTier;
import com.hamburger0abcde.mekanismsun.block.basic.BlockAdvanceBin;
import com.hamburger0abcde.mekanismsun.block.basic.BlockAdvanceEnergyCube;
import com.hamburger0abcde.mekanismsun.block.basic.BlockAdvanceFluidTank;
import com.hamburger0abcde.mekanismsun.item.block.MSItemBlockBin;
import com.hamburger0abcde.mekanismsun.item.block.MSItemBlockChemicalTank;
import com.hamburger0abcde.mekanismsun.item.block.MSItemBlockEnergyCube;
import com.hamburger0abcde.mekanismsun.item.block.MSItemBlockFluidTank;
import com.hamburger0abcde.mekanismsun.item.block.transmitter.MSItemBlockMechanicalPipe;
import com.hamburger0abcde.mekanismsun.item.block.transmitter.MSItemBlockUniversalCable;
import com.hamburger0abcde.mekanismsun.recipes.MSInputRecipeCache;
import com.hamburger0abcde.mekanismsun.recipes.MSRecipeType;
import com.hamburger0abcde.mekanismsun.tiers.AdvancedTier;
import com.hamburger0abcde.mekanismsun.tiers.IAdvancedTier;
import com.hamburger0abcde.mekanismsun.tiers.storage.AdvanceBinTier;
import com.hamburger0abcde.mekanismsun.tiers.storage.AdvanceChemicalTankTier;
import com.hamburger0abcde.mekanismsun.tiers.storage.AdvanceEnergyCubeTier;
import com.hamburger0abcde.mekanismsun.tiers.storage.AdvanceFluidTankTier;
import com.hamburger0abcde.mekanismsun.tiles.artificial_sun.TileEntityArtificialSunCasing;
import com.hamburger0abcde.mekanismsun.tiles.artificial_sun.TileEntityArtificialSunPort;
import com.hamburger0abcde.mekanismsun.tiles.machine.TileEntityAlloyer;
import com.hamburger0abcde.mekanismsun.tiles.machine.TileEntityElectricNeutronActivator;
import com.hamburger0abcde.mekanismsun.tiles.machine.TileEntityTransmutator;
import com.hamburger0abcde.mekanismsun.tiles.storage.TileEntityAdvanceBin;
import com.hamburger0abcde.mekanismsun.tiles.storage.TileEntityAdvanceChemicalTank;
import com.hamburger0abcde.mekanismsun.tiles.storage.TileEntityAdvanceEnergyCube;
import com.hamburger0abcde.mekanismsun.tiles.storage.TileEntityAdvanceFluidTank;
import com.hamburger0abcde.mekanismsun.tiles.transmitter.TileEntityAdvanceMechanicalPipe;
import com.hamburger0abcde.mekanismsun.tiles.transmitter.TileEntityAdvanceUniversalCable;
import com.hamburger0abcde.mekanismsun.utils.MSAttachedSideConfig;
import com.hamburger0abcde.mekanismsun.world.MSOreType;
import mekanism.common.attachments.component.AttachedEjector;
import mekanism.common.attachments.component.AttachedSideConfig;
import mekanism.common.attachments.containers.ContainerType;
import mekanism.common.attachments.containers.chemical.ChemicalTanksBuilder;
import mekanism.common.attachments.containers.fluid.FluidTanksBuilder;
import mekanism.common.attachments.containers.item.ItemSlotsBuilder;
import mekanism.common.block.BlockEnergyCube;
import mekanism.common.block.BlockOre;
import mekanism.common.block.interfaces.IHasDescription;
import mekanism.common.block.prefab.BlockBasicMultiblock;
import mekanism.common.block.prefab.BlockTile.BlockTileModel;
import mekanism.common.block.transmitter.BlockLargeTransmitter;
import mekanism.common.block.transmitter.BlockSmallTransmitter;
import mekanism.common.content.blocktype.BlockTypeTile;
import mekanism.common.content.blocktype.Machine;
import mekanism.common.item.block.ItemBlockTooltip;
import mekanism.common.recipe.MekanismRecipeType;
import mekanism.common.recipe.lookup.cache.InputRecipeCache;
import mekanism.common.registration.impl.BlockDeferredRegister;
import mekanism.common.registration.impl.BlockRegistryObject;
import mekanism.common.registries.MekanismDataComponents;
import mekanism.common.resource.BlockResourceInfo;
import mekanism.common.resource.ore.OreBlockType;
import mekanism.common.resource.ore.OreType;
import mekanism.common.util.EnumUtils;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class MSBlocks {
    public static final BlockDeferredRegister BLOCKS = new BlockDeferredRegister(MekanismSun.MODID);

    public static final Map<OreType, OreBlockType> ORES = new LinkedHashMap<>();

    static {
        for (OreType ore : EnumUtils.ORE_TYPES) {
            if (ore == MSOreType.SILVER) {
                ORES.put(ore, registerOre(ore));
            }
        }
    }

    private static <BLOCK extends Block & IHasDescription> BlockRegistryObject<BLOCK,
            ItemBlockTooltip<BLOCK>> registerBlock(String name, Supplier<? extends BLOCK> blockSupplier) {
        return BLOCKS.register(name, blockSupplier, ItemBlockTooltip::new);
    }

    private static OreBlockType registerOre(OreType ore) {
        String name = ore.getResource().getRegistrySuffix() + "_ore";
        BlockRegistryObject<BlockOre, ItemBlockTooltip<BlockOre>> stoneOre = registerBlock(name, () -> new BlockOre(ore));
        BlockRegistryObject<BlockOre, ItemBlockTooltip<BlockOre>> deepslateOre = BLOCKS.register("deepslate_" + name,
                () -> new BlockOre(ore, BlockBehaviour.Properties.ofLegacyCopy(stoneOre.value()).mapColor(MapColor.DEEPSLATE)
                        .strength(4.5F, 3).sound(SoundType.DEEPSLATE)), ItemBlockTooltip::new);
        return new OreBlockType(stoneOre, deepslateOre);
    }

    private static <BLOCK extends Block, ITEM extends BlockItem> BlockRegistryObject<BLOCK, ITEM> registerTieredBlock(
            IAdvancedTier tier, String suffix, Function<MapColor, ? extends BLOCK> blockSupplier,
            BiFunction<BLOCK, Item.Properties, ITEM> itemCreator) {
        return registerTieredBlock(tier, suffix, () -> blockSupplier.apply(tier.getAdvanceTier().getMapColor()), itemCreator);
    }

    private static <BLOCK extends Block, ITEM extends BlockItem> BlockRegistryObject<BLOCK, ITEM> registerTieredBlock(
            IAdvancedTier tier, String suffix, Supplier<? extends BLOCK> blockSupplier,
            BiFunction<BLOCK, Item.Properties, ITEM> itemCreator) {
        return BLOCKS.register(tier.getAdvanceTier().getLowerName() + suffix, blockSupplier, itemCreator);
    }

    private static <BLOCK extends Block, ITEM extends BlockItem> BlockRegistryObject<BLOCK, ITEM> registerTieredBlock(
            String registerName, Supplier<? extends BLOCK> blockSupplier, BiFunction<BLOCK, Item.Properties, ITEM> itemCreator) {
        return BLOCKS.register(registerName, blockSupplier, itemCreator);
    }

    private static BlockRegistryObject<BlockTileModel<TileEntityAdvanceChemicalTank, Machine<TileEntityAdvanceChemicalTank>>,
            MSItemBlockChemicalTank> registerChemicalTank(Machine<TileEntityAdvanceChemicalTank> type) {
        AdvanceChemicalTankTier tier = (AdvanceChemicalTankTier) Objects.requireNonNull(type.get(MSAttributeTier.class)).tier();
        return registerTieredBlock(tier, "_chemical_tank", color ->
                new BlockTileModel<>(type, properties -> properties.mapColor(color)), MSItemBlockChemicalTank::new)
                .forItemHolder(holder -> holder
                        .addAttachedContainerCapabilities(ContainerType.CHEMICAL, () -> ChemicalTanksBuilder.builder()
                                .addTank(MSComponentBackedChemicalTankTank::create).build()
                        ).addAttachmentOnlyContainers(ContainerType.ITEM, () -> ItemSlotsBuilder.builder()
                                .addChemicalDrainSlot(0)
                                .addChemicalFillSlot(0)
                                .build()
                        )
                );
    }

    private static BlockRegistryObject<BlockAdvanceFluidTank, MSItemBlockFluidTank> registerFluidTank(
            Machine<TileEntityAdvanceFluidTank> type) {
        AdvanceFluidTankTier tier = (AdvanceFluidTankTier) Objects.requireNonNull(type.get(MSAttributeTier.class)).tier();
        return registerTieredBlock(tier, "_fluid_tank", () -> new BlockAdvanceFluidTank(type), MSItemBlockFluidTank::new)
                .forItemHolder(holder -> holder
                        .addAttachedContainerCapabilities(ContainerType.FLUID, () -> FluidTanksBuilder.builder()
                                .addTank(MSComponentBackedFluidTankFluidTank::create)
                                .build()
                        ).addAttachmentOnlyContainers(ContainerType.ITEM, () -> ItemSlotsBuilder.builder()
                                .addFluidInputSlot(0)
                                .addOutput()
                                .build()
                        )
                );
    }

    private static BlockRegistryObject<BlockAdvanceEnergyCube, MSItemBlockEnergyCube> registerEnergyCube(
            Machine<TileEntityAdvanceEnergyCube> type) {
        AdvanceEnergyCubeTier tier = (AdvanceEnergyCubeTier) Objects.requireNonNull(type.get(MSAttributeTier.class)).tier();
        return registerTieredBlock(tier, "_energy_cube", () -> new BlockAdvanceEnergyCube(type), MSItemBlockEnergyCube::new)
                .forItemHolder(holder -> holder
                        .addAttachmentOnlyContainers(ContainerType.ITEM, () -> ItemSlotsBuilder.builder()
                                .addEnergy()
                                .addDrainEnergy()
                                .build()
                        )
                );
    }

    private static BlockRegistryObject<BlockAdvanceBin, MSItemBlockBin> registerBin(Machine<TileEntityAdvanceBin> type) {
        AdvanceBinTier tier = (AdvanceBinTier) Objects.requireNonNull(type.get(MSAttributeTier.class)).tier();
        return registerTieredBlock(tier, "_bin", color ->
                new BlockAdvanceBin(type, properties -> properties.mapColor(color)), MSItemBlockBin::new)
                .forItemHolder(holder -> holder
                        .addAttachmentOnlyContainers(ContainerType.ITEM, () -> ItemSlotsBuilder.builder()
                                .addSlot(MSComponentBackedBinInventorySlot::create)
                                .build()
                        )
                );
    }

    private static <BLOCK extends Block, ITEM extends BlockItem> BlockRegistryObject<BLOCK, ITEM> registerTieredTransmitter(
            IAdvancedTier tier, String suffix, Function<MapColor, ? extends BLOCK> blockSupplier,
            BiFunction<BLOCK, Item.Properties, ITEM> itemCreator) {
        return registerTieredBlock(tier, suffix, () -> blockSupplier.apply(tier.getAdvanceTier().getMapColor()), itemCreator);
    }

    private static BlockRegistryObject<BlockSmallTransmitter<TileEntityAdvanceUniversalCable>,
            MSItemBlockUniversalCable> registerUniversalCable(AdvancedTier tier, BlockTypeTile<TileEntityAdvanceUniversalCable> type) {
        String nameTier = tier.getLowerName();
        return registerTieredBlock(nameTier + "_universal_cable", () -> new BlockSmallTransmitter<>(type),
                MSItemBlockUniversalCable::new);
    }

    private static BlockRegistryObject<BlockLargeTransmitter<TileEntityAdvanceMechanicalPipe>,
            MSItemBlockMechanicalPipe> registerMechanicalPipe(AdvancedTier tier, BlockTypeTile<TileEntityAdvanceMechanicalPipe> type) {
        String nameTier = tier.getLowerName();
        return registerTieredBlock(nameTier + "_mechanical_pipe", () -> new BlockLargeTransmitter<>(type),
                MSItemBlockMechanicalPipe::new);
    }

    //TODO: recipes

    public static final BlockRegistryObject<BlockTileModel<TileEntityAlloyer, Machine<TileEntityAlloyer>>,
            ItemBlockTooltip<BlockTileModel<TileEntityAlloyer, Machine<TileEntityAlloyer>>>> ALLOYER = BLOCKS.register(
                    "alloyer", () -> new BlockTileModel<>(MSBlockTypes.ALLOYER,
                        properties -> properties.mapColor(BlockResourceInfo.STEEL.getMapColor())),
                    (block, properties) -> new ItemBlockTooltip<>(
                            block, true, properties
                                .component(MekanismDataComponents.EJECTOR, AttachedEjector.DEFAULT)
                                .component(MekanismDataComponents.SIDE_CONFIG, MSAttachedSideConfig.ALLOYER_MACHINE)
                    )).forItemHolder(holder -> holder
                    .addAttachmentOnlyContainers(ContainerType.ITEM, () -> ItemSlotsBuilder.builder()
                            .addInput(MSRecipeType.ALLOYING, MSInputRecipeCache.ItemItemChemical::containsInputA)
                            .addInput(MSRecipeType.ALLOYING, MSInputRecipeCache.ItemItemChemical::containsInputB)
                            .addOutput().addEnergy().build()
                    ).addAttachmentOnlyContainers(ContainerType.CHEMICAL, () -> ChemicalTanksBuilder.builder()
                            .addBasic(TileEntityAlloyer.MAX_GAS, MSRecipeType.ALLOYING,
                                    MSInputRecipeCache.ItemItemChemical::containsInputC).build()));

    public static final BlockRegistryObject<BlockTileModel<TileEntityTransmutator, Machine<TileEntityTransmutator>>,
            ItemBlockTooltip<BlockTileModel<TileEntityTransmutator, Machine<TileEntityTransmutator>>>> TRANSMUTATOR = BLOCKS.register(
                    "transmutator", () -> new BlockTileModel<>(MSBlockTypes.TRANSMUTATOR,
                        properties -> properties.mapColor(BlockResourceInfo.STEEL.getMapColor())),
                    (block, properties) -> new ItemBlockTooltip<>(
                            block, true, properties
                                .component(MekanismDataComponents.EJECTOR, AttachedEjector.DEFAULT)
                                .component(MekanismDataComponents.SIDE_CONFIG, MSAttachedSideConfig.TRANSMUTATOR_MACHINE)
                    )).forItemHolder(holder -> holder
                            .addAttachmentOnlyContainers(ContainerType.ITEM, () -> ItemSlotsBuilder.builder()
                                    .addInput(MSRecipeType.TRANSMUTATION, InputRecipeCache.SingleItem::containsInput)
                                    .addOutput().addEnergy().build())
                    );

    public static final BlockRegistryObject<
                BlockTileModel<TileEntityElectricNeutronActivator, Machine<TileEntityElectricNeutronActivator>>,
                ItemBlockTooltip<BlockTileModel<TileEntityElectricNeutronActivator, Machine<TileEntityElectricNeutronActivator>>>
            > ELECTRIC_NEUTRON_ACTIVATOR = BLOCKS.register("electric_neutron_activator",
                    () -> new BlockTileModel<>(MSBlockTypes.ELECTRIC_NEUTRON_ACTIVATOR,
                            properties -> properties.mapColor(BlockResourceInfo.STEEL.getMapColor())),
                    (block, properties) -> new ItemBlockTooltip<>(
                            block, true, properties
                                .component(MekanismDataComponents.EJECTOR, AttachedEjector.DEFAULT)
                                .component(MekanismDataComponents.SIDE_CONFIG, AttachedSideConfig.CENTRIFUGE)
                    )).forItemHolder(holder -> holder
                            .addAttachmentOnlyContainers(ContainerType.CHEMICAL, () -> ChemicalTanksBuilder.builder()
                                    .addBasic(TileEntityElectricNeutronActivator.MAX_GAS, MekanismRecipeType.ACTIVATING,
                                            InputRecipeCache.SingleChemical::containsInput)
                                    .addBasic(TileEntityElectricNeutronActivator.MAX_GAS)
                                    .build()
                            ).addAttachmentOnlyContainers(ContainerType.ITEM, () -> ItemSlotsBuilder.builder()
                                    .addChemicalFillSlot(0)
                                    .addChemicalDrainSlot(1)
                                    .build()
                            )
                    );

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

    public static final BlockRegistryObject<BlockTileModel<TileEntityAdvanceChemicalTank, Machine<TileEntityAdvanceChemicalTank>>,
            MSItemBlockChemicalTank> SUPERNOVA_CHEMICAL_TANK = registerChemicalTank(MSBlockTypes.SUPERNOVA_CHEMICAL_TANK);

    public static final BlockRegistryObject<BlockAdvanceFluidTank, MSItemBlockFluidTank> SUPERNOVA_FLUID_TANK =
            registerFluidTank(MSBlockTypes.SUPERNOVA_FLUID_TANK);

    public static final BlockRegistryObject<BlockAdvanceEnergyCube, MSItemBlockEnergyCube> SUPERNOVA_ENERGY_CUBE =
            registerEnergyCube(MSBlockTypes.SUPERNOVA_ENERGY_CUBE);

    public static final BlockRegistryObject<BlockAdvanceBin, MSItemBlockBin> SUPERNOVA_BIN =
            registerBin(MSBlockTypes.SUPERNOVA_BIN);

    public static final BlockRegistryObject<BlockSmallTransmitter<TileEntityAdvanceUniversalCable>, MSItemBlockUniversalCable>
            SUPERNOVA_UNIVERSAL_CABLE = registerUniversalCable(AdvancedTier.SUPERNOVA, MSBlockTypes.SUPERNOVA_UNIVERSAL_CABLE);

    public static final BlockRegistryObject<BlockLargeTransmitter<TileEntityAdvanceMechanicalPipe>, MSItemBlockMechanicalPipe>
            SUPERNOVA_MECHANICAL_PIPE = registerMechanicalPipe(AdvancedTier.SUPERNOVA, MSBlockTypes.SUPERNOVA_MECHANICAL_PIPE);
}
