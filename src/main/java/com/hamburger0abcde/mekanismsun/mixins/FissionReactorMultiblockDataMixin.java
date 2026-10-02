package com.hamburger0abcde.mekanismsun.mixins;

import com.hamburger0abcde.mekanismsun.MekanismSun;
import com.hamburger0abcde.mekanismsun.common.multiblock.fission.FissionFluidCoolant;
import com.hamburger0abcde.mekanismsun.common.multiblock.fission.MSFissionCoolants;
import mekanism.api.Action;
import mekanism.api.AutomationType;
import mekanism.api.MekanismAPI;
import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.chemical.IChemicalTank;
import mekanism.api.fluid.IExtendedFluidTank;
import mekanism.api.math.MathUtils;
import mekanism.common.capabilities.heat.VariableHeatCapacitor;
import mekanism.common.capabilities.merged.MergedTank;
import mekanism.common.lib.multiblock.MultiblockData;
import mekanism.common.registries.MekanismChemicals;
import mekanism.common.util.HeatUtils;
import mekanism.common.util.MekanismUtils;
import mekanism.generators.common.content.fission.FissionReactorMultiblockData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Predicate;

@Mixin(value = FissionReactorMultiblockData.class, remap = false)
public abstract class FissionReactorMultiblockDataMixin extends MultiblockData {
    @Shadow
    @Final
    public MergedTank coolantTank;

    @Shadow
    @Final
    public IChemicalTank heatedCoolantTank;

    @Shadow
    @Final
    public VariableHeatCapacitor heatCapacitor;

    @Shadow
    public long lastBoilRate;

    @Shadow
    public abstract double getBoilEfficiency();

    protected FissionReactorMultiblockDataMixin() {
        super(null);
    }

    @ModifyArg(
            method = "<init>",
            at = @At(value = "INVOKE", target = "Lmekanism/common/capabilities/fluid/VariableCapacityFluidTank;input(Lmekanism/common/lib/multiblock/MultiblockData;Ljava/util/function/IntSupplier;Ljava/util/function/Predicate;Lmekanism/api/IContentsListener;)Lmekanism/common/capabilities/fluid/VariableCapacityFluidTank;"),
            index = 2, remap = false
    )
    private Predicate<FluidStack> mekanismSun$allowCustomCoolant(Predicate<FluidStack> original) {
        return stack -> {
            if (original.test(stack)) {
                return true;
            }

            return MSFissionCoolants.isCoolant(stack.getFluid());
        };
    }

    @ModifyArg(
            method = "<init>",
            at = @At(value = "INVOKE", target = "Lmekanism/common/capabilities/chemical/VariableCapacityChemicalTank;output(Lmekanism/common/lib/multiblock/MultiblockData;Ljava/util/function/LongSupplier;Ljava/util/function/Predicate;Lmekanism/api/IContentsListener;)Lmekanism/api/chemical/IChemicalTank;", ordinal = 0),
            index = 2, remap = false
    )
    private Predicate<ChemicalStack> mekanismSun$allowHeliumOutput(
            Predicate<ChemicalStack> original
    ) {
        return stack -> original.test(stack) || mekanismSun$isHelium(stack);
    }

    private static boolean mekanismSun$isHelium(ChemicalStack stack) {
        if (stack.isEmpty()) {
            return false;
        }
        var key = stack.getChemicalHolder().getKey();
        return key != null && ResourceLocation.fromNamespaceAndPath(MekanismSun.MODID, "helium").equals(key.location());
    }

    @Inject(
            method = "handleCoolant",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private void mekanismSun$handleCustomFluidCoolant(CallbackInfo ci) {
        if (coolantTank.getCurrentType() != MergedTank.CurrentType.FLUID) {
            return;
        }

        IExtendedFluidTank fluidTank = coolantTank.getFluidTank();
        if (fluidTank.isEmpty()) {
            return;
        }

        Fluid fluid = fluidTank.getFluid().getFluid();
        FissionFluidCoolant coolant = MSFissionCoolants.get(fluid);
        if (coolant == null) {
            return;
        }

        double heat = getBoilEfficiency() * (heatCapacitor.getHeat() - HeatUtils.BASE_BOIL_TEMP * heatCapacitor.getHeatCapacity());

        if (heat <= 0) {
            lastBoilRate = 0;
            ci.cancel();
            return;
        }

        double coolantHeat = heat * coolant.conductivity();
        double heatedAmount = coolantHeat / coolant.thermalEnthalpy();

        long boilRate = Mth.clamp(
                MathUtils.clampToLong(heatedAmount),
                0,
                fluidTank.getFluidAmount()
        );

        lastBoilRate = boilRate;

        if (boilRate > 0) {
            Chemical helium = MekanismAPI.CHEMICAL_REGISTRY
                    .get(ResourceLocation.fromNamespaceAndPath(MekanismSun.MODID, "helium"));

            if (helium == null) {
                lastBoilRate = 0;
                ci.cancel();
                return;
            }

            MekanismUtils.logMismatchedStackSize(
                    fluidTank.shrinkStack((int) boilRate, Action.EXECUTE),
                    boilRate
            );

            ChemicalStack heliumStack = new ChemicalStack(
                    MekanismAPI.CHEMICAL_REGISTRY.wrapAsHolder(helium),
                    boilRate
            );

            heatedCoolantTank.insert(
                    heliumStack,
                    Action.EXECUTE,
                    AutomationType.INTERNAL
            );

            double removedHeat = boilRate * coolant.thermalEnthalpy();
            heatCapacitor.handleHeat(-removedHeat);
        }

        ci.cancel();
    }
}
