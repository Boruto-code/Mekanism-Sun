package com.hamburger0abcde.mekanismsun.client.render.item.block;

import com.hamburger0abcde.mekanismsun.MekanismSun;
import com.hamburger0abcde.mekanismsun.client.model.ColorModelEnergyCore;
import com.hamburger0abcde.mekanismsun.client.render.tile_entity.RenderAdvanceEnergyCube;
import com.hamburger0abcde.mekanismsun.item.block.MSItemBlockEnergyCube;
import com.hamburger0abcde.mekanismsun.tiers.storage.AdvanceEnergyCubeTier;
import com.hamburger0abcde.mekanismsun.tiles.storage.TileEntityAdvanceEnergyCube;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mekanism.api.RelativeSide;
import mekanism.client.render.MekanismRenderer;
import mekanism.client.render.item.MekanismISTER;
import mekanism.common.attachments.component.AttachedSideConfig;
import mekanism.common.lib.transmitter.TransmissionType;
import mekanism.common.tile.component.config.DataType;
import mekanism.common.tile.component.config.IPersistentConfigInfo;
import mekanism.common.util.EnumUtils;
import mekanism.common.util.StorageUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;

public class RenderAdvanceEnergyCubeItem extends MekanismISTER {
    public static final RenderAdvanceEnergyCubeItem ADVANCE_RENDERER = new RenderAdvanceEnergyCubeItem();
    private ColorModelEnergyCore core;

    @Override
    public void onResourceManagerReload(@NotNull ResourceManager resourceManager) {
        core = new ColorModelEnergyCore(getEntityModels());
    }

    @Override
    public void renderByItem(@NotNull ItemStack stack, @NotNull ItemDisplayContext displayContext, @NotNull PoseStack matrix,
                             @NotNull MultiBufferSource renderer, int light, int overlayLight) {
        AdvanceEnergyCubeTier tier = ((MSItemBlockEnergyCube) stack.getItem()).getAdvancedTier();
        TileEntityAdvanceEnergyCube.CubeSideState[] sideStates = new TileEntityAdvanceEnergyCube.CubeSideState[EnumUtils.SIDES.length];
        AttachedSideConfig fallback = MSItemBlockEnergyCube.SIDE_CONFIG;
        IPersistentConfigInfo sideConfig = AttachedSideConfig.getStoredConfigInfo(stack, fallback, TransmissionType.ENERGY);
        for (RelativeSide side : EnumUtils.SIDES) {
            DataType dataType = sideConfig.getDataType(side);
            TileEntityAdvanceEnergyCube.CubeSideState state = TileEntityAdvanceEnergyCube.CubeSideState.INACTIVE;
            if (dataType != DataType.NONE) {
                state = dataType.canOutput() ? TileEntityAdvanceEnergyCube.CubeSideState.ACTIVE_LIT
                        : TileEntityAdvanceEnergyCube.CubeSideState.ACTIVE_UNLIT;
            }
            sideStates[side.ordinal()] = state;
        }
        ModelData modelData = ModelData.builder().with(TileEntityAdvanceEnergyCube.SIDE_STATE_PROPERTY, sideStates).build();
        renderBlockItem(stack, displayContext, matrix, renderer, light, overlayLight, modelData);
        double energyPercentage = StorageUtils.getEnergyRatio(stack);
        if (energyPercentage > 0) {
            float ticks = Minecraft.getInstance().levelRenderer.getTicks() + MekanismRenderer.getPartialTick();
            float scaledTicks = 4 * ticks;
            matrix.pushPose();
            matrix.translate(0.5, 0.5, 0.5);
            matrix.scale(0.4F, 0.4F, 0.4F);
            matrix.translate(0, Math.sin(Math.toRadians(3 * ticks)) / 7, 0);
            matrix.mulPose(Axis.YP.rotationDegrees(scaledTicks));
            matrix.mulPose(RenderAdvanceEnergyCube.coreVec.rotationDegrees(36F + scaledTicks));
            core.render(matrix, renderer, LightTexture.FULL_BRIGHT, overlayLight, tier.getAdvanceTier(), (float) energyPercentage);
            matrix.popPose();
        }
    }
}
