package com.hamburger0abcde.mekanismsun.client.recipe_viewer.recipe.jei.machine;

import com.hamburger0abcde.mekanismsun.common.MekanismSunLang;
import com.hamburger0abcde.mekanismsun.common.recipes.freeze.FreezeRecipe;
import com.mojang.serialization.Codec;
import fr.iglee42.evolvedmekanism.client.bars.GuiCustomDynamicHorizontalRateBar;
import mekanism.client.gui.element.GuiInnerScreen;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiFluidGauge;
import mekanism.client.gui.element.gauge.GuiGauge;
import mekanism.client.recipe_viewer.jei.BaseRecipeCategory;
import mekanism.client.recipe_viewer.jei.MekanismJEI;
import mekanism.client.recipe_viewer.type.RVRecipeTypeWrapper;
import mekanism.common.MekanismLang;
import mekanism.common.lib.Color;
import mekanism.common.tile.component.config.DataType;
import mekanism.common.util.text.EnergyDisplay;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.ICodecHelper;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.IRecipeManager;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FreezeRecipeCategory extends BaseRecipeCategory<RecipeHolder<FreezeRecipe>> {
    private final GuiGauge<?> input;
    private final GuiGauge<?> output;
    private final GuiCustomDynamicHorizontalRateBar bar;

    public FreezeRecipeCategory(IGuiHelper helper, RVRecipeTypeWrapper<?, FreezeRecipe, ?> recipeType) {
        super(helper, MekanismJEI.holderRecipeType(recipeType), MekanismSunLang.VACUUM_FREEZER.translate(), createIcon(helper, recipeType),
                recipeType.xOffset(), recipeType.yOffset(), recipeType.width(), recipeType.height());
        input = addElement(GuiFluidGauge.getDummy(GaugeType.STANDARD.with(DataType.INPUT), this, 7, 17));
        output = addElement(GuiFluidGauge.getDummy(GaugeType.STANDARD.with(DataType.OUTPUT), this, 151, 17));
        bar = addElement(new GuiCustomDynamicHorizontalRateBar(this, getBarProgressTimer(), 6, 79, 160, c -> Color.WHITE));
        addElement(new GuiInnerScreen(this, 47, 17, 82, 60, () -> {
            List<Component> list = new ArrayList<>();
            list.add(MekanismLang.STATUS.translate(MekanismLang.ACTIVE));
            list.add(MekanismLang.USING.translate(EnergyDisplay.of(8_000_000_000L)));
            return list;
        }));
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, RecipeHolder<FreezeRecipe> recipe, @NotNull IFocusGroup focusGroup) {
        initFluid(builder, RecipeIngredientRole.INPUT, input, recipe.value().getInput().getRepresentations());
        initFluid(builder, RecipeIngredientRole.OUTPUT, output, recipe.value().getOutputDefinition());
        bar.setColorFunction(p -> Color.rgb(0x5D7391));
    }

    @NotNull
    @Override
    public ResourceLocation getRegistryName(RecipeHolder<FreezeRecipe> recipe) {
        return recipe.id();
    }

    @Override
    public Codec<RecipeHolder<FreezeRecipe>> getCodec(ICodecHelper codecHelper, IRecipeManager recipeManager) {
        return codecHelper.getRecipeHolderCodec();
    }
}
