package com.hamburger0abcde.mekanismsun.client.render.multiblock;

import com.hamburger0abcde.mekanismsun.common.multiblock.artificial_sun.ArtificialSunMultiblockData;
import com.hamburger0abcde.mekanismsun.common.tiles.multiblock.artificial_sun.TileEntityArtificialSunCasing;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import mekanism.api.annotations.NothingNullByDefault;
import mekanism.api.text.EnumColor;
import mekanism.client.model.ModelEnergyCore;
import mekanism.client.render.tileentity.MultiblockTileEntityRenderer;
import mekanism.client.render.tileentity.RenderEnergyCube;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.util.Mth;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.phys.Vec3;

@NothingNullByDefault
public class RenderArtificialSun extends MultiblockTileEntityRenderer<ArtificialSunMultiblockData, TileEntityArtificialSunCasing> {
    private final ModelEnergyCore core;

    public RenderArtificialSun(BlockEntityRendererProvider.Context context) {
        super(context);
        core = new ModelEnergyCore(context.getModelSet());
    }

    @Override
    protected void render(TileEntityArtificialSunCasing tile, ArtificialSunMultiblockData multiblock, float partialTick,
                          PoseStack matrix, MultiBufferSource renderer, int light, int overlayLight, ProfilerFiller profiler) {
        if (multiblock.lastBurnRate <= 0) {
            return;
        }

        float ticks = Minecraft.getInstance().levelRenderer.getTicks() + partialTick;

        long activity = Math.max(1L, multiblock.lastBurnRate);

        double centerX = (multiblock.getMinPos().getX() + multiblock.getMaxPos().getX() + 1) / 2.0;

        double centerY = (multiblock.getMinPos().getY() + multiblock.getMaxPos().getY() + 1) / 2.0;

        double centerZ = (multiblock.getMinPos().getZ() + multiblock.getMaxPos().getZ() + 1) / 2.0;

        double relativeX = centerX - tile.getBlockPos().getX();

        double relativeY = centerY - tile.getBlockPos().getY();

        double relativeZ = centerZ - tile.getBlockPos().getZ();

        VertexConsumer buffer = renderer.getBuffer(core.RENDER_TYPE);

        matrix.pushPose();

        matrix.translate(relativeX, relativeY, relativeZ);

        float scale = 1.0F + 0.7F * sinDegrees(3.14F * activity + 135F);

        renderPart(matrix, buffer, overlayLight, EnumColor.RED, scale, ticks, -6, -7, 0, 36);

        scale = 1.0F + 0.8F * sinDegrees(3.0F * activity);

        renderPart(
                matrix,
                buffer,
                overlayLight,
                EnumColor.PINK,
                scale,
                ticks,
                4,
                4,
                0,
                36
        );

        scale = 1.0F - 0.9F * sinDegrees(4.0F * activity + 90F);

        renderPart(matrix, buffer, overlayLight, EnumColor.ORANGE, scale, ticks, 5, -3, -35, 106);

        matrix.popPose();

        endIfNeeded(renderer, core.RENDER_TYPE);
    }

    private void renderPart(PoseStack matrix, VertexConsumer buffer, int overlayLight, EnumColor color,
                            float scale, float ticks, int mult1, int mult2, int shift1, int shift2) {
        matrix.pushPose();

        matrix.scale(scale, scale, scale);

        matrix.mulPose(Axis.YP.rotationDegrees(ticks * mult1 + shift1));

        matrix.mulPose(RenderEnergyCube.coreVec.rotationDegrees(ticks * mult2 + shift2));

        core.render(matrix, buffer, LightTexture.FULL_BRIGHT, overlayLight, color, 1);

        matrix.popPose();
    }

    private static float sinDegrees(float degrees) {
        return Mth.sin((degrees % 360F) * Mth.DEG_TO_RAD);
    }

    @Override
    protected String getProfilerSection() {
        return "artificial_sun";
    }

    @Override
    protected boolean shouldRender(TileEntityArtificialSunCasing tile, ArtificialSunMultiblockData multiblock, Vec3 camera) {
        return multiblock.lastBurnRate > 0;
    }
}
