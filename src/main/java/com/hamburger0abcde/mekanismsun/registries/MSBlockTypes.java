package com.hamburger0abcde.mekanismsun.registries;

import com.hamburger0abcde.mekanismsun.MekanismSunLang;
import com.hamburger0abcde.mekanismsun.tiles.artificial_sun.TileEntityArtificialSunCasing;
import com.hamburger0abcde.mekanismsun.tiles.artificial_sun.TileEntityArtificialSunPort;
import com.hamburger0abcde.mekanismsun.tiles.machine.TileEntityAlloyer;
import com.hamburger0abcde.mekanismsun.tiles.machine.TileEntityTransmutator;
import mekanism.api.Upgrade;
import mekanism.common.block.attribute.Attributes;
import mekanism.common.config.MekanismConfig;
import mekanism.common.content.blocktype.BlockTypeTile;
import mekanism.common.content.blocktype.Machine;
import mekanism.common.lib.transmitter.TransmissionType;
import mekanism.common.registries.MekanismSounds;
import net.minecraft.world.level.Level;

public class MSBlockTypes {
    public static final Machine<TileEntityAlloyer> ALLOYER = Machine.MachineBuilder
            .createMachine(() -> MSTileEntityTypes.ALLOYER, MekanismSunLang.DESCRIPTION_ALLOYER)
            .withGui(() -> MSContainerTypes.ALLOYER)
            .withSound(MekanismSounds.COMBINER)
            .withEnergyConfig(MekanismConfig.usage.combiner, MekanismConfig.storage.combiner)
            .withSupportedUpgrades(Upgrade.SPEED, Upgrade.ENERGY, Upgrade.MUFFLING)
            .withComputerSupport("alloyer")
            .withSideConfig(TransmissionType.ITEM, TransmissionType.CHEMICAL, TransmissionType.ENERGY)
            .build();

    public static final Machine<TileEntityTransmutator> TRANSMUTATOR = Machine.MachineBuilder
            .createMachine(() -> MSTileEntityTypes.TRANSMUTATOR, MekanismSunLang.DESCRIPTION_TRANSMUTATOR)
            .withGui(() -> MSContainerTypes.TRANSMUTATOR)
            .withSound(MekanismSounds.ANTIPROTONIC_NUCLEOSYNTHESIZER)
            .withEnergyConfig(MekanismConfig.usage.combiner, MekanismConfig.storage.combiner)
            .withSupportedUpgrades(Upgrade.SPEED, Upgrade.ENERGY, Upgrade.MUFFLING)
            .withComputerSupport("transmutator")
            .withSideConfig(TransmissionType.ITEM, TransmissionType.ENERGY)
            .build();

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
