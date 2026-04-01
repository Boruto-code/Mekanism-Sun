package com.hamburger0abcde.mekanismsun.client.render.transmitter;

import com.hamburger0abcde.mekanismsun.common.content.network.transmitter.AdvancePressurizedTube;
import com.hamburger0abcde.mekanismsun.common.tiles.transmitter.TileEntityAdvancePressurizedTube;
import com.mojang.blaze3d.vertex.PoseStack;
import mekanism.api.MekanismAPI;
import mekanism.api.annotations.NothingNullByDefault;
import mekanism.client.render.MekanismRenderer;
import mekanism.client.render.transmitter.RenderTransmitterBase;
import mekanism.common.base.ProfilerConstants;
import mekanism.common.content.network.ChemicalNetwork;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

@NothingNullByDefault
public class RenderAdvancePressurizedTube extends RenderTransmitterBase<TileEntityAdvancePressurizedTube> {
    public RenderAdvancePressurizedTube(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected void render(@NotNull TileEntityAdvancePressurizedTube tile, float partialTick, PoseStack matrix, MultiBufferSource renderer,
                          int light, int overlayLight, ProfilerFiller profiler) {
        ChemicalNetwork network = tile.getTransmitter().getTransmitterNetwork();
        matrix.pushPose();
        matrix.translate(0.5, 0.5, 0.5);
        renderModel(tile, matrix, renderer.getBuffer(Sheets.translucentCullBlockSheet()), MekanismRenderer.getTint(network.lastChemical),
                Math.max(0.2F, network.currentScale), LightTexture.FULL_BRIGHT, overlayLight,
                MekanismRenderer.getChemicalTexture(network.lastChemical));
        matrix.popPose();
    }

    @Override
    protected @NotNull String getProfilerSection() {
        return ProfilerConstants.PRESSURIZED_TUBE;
    }

    @Override
    protected boolean shouldRenderTransmitter(@NotNull TileEntityAdvancePressurizedTube tile, @NotNull Vec3 camera) {
        if (super.shouldRenderTransmitter(tile, camera)) {
            AdvancePressurizedTube tube = tile.getTransmitter();
            if (tube.hasTransmitterNetwork()) {
                ChemicalNetwork network = tube.getTransmitterNetwork();
                return !network.lastChemical.is(MekanismAPI.EMPTY_CHEMICAL_KEY) && !network.getChemicalTank().isEmpty()
                        && network.currentScale > 0;
            }
        }
        return false;
    }
}
