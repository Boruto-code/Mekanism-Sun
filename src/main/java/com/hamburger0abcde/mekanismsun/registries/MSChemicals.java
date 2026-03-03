package com.hamburger0abcde.mekanismsun.registries;

import com.hamburger0abcde.mekanismsun.MekanismSun;
import mekanism.api.chemical.Chemical;
import mekanism.common.registration.impl.ChemicalDeferredRegister;
import mekanism.common.registration.impl.DeferredChemical;

public class MSChemicals {
    public static final ChemicalDeferredRegister CHEMICALS = new ChemicalDeferredRegister(MekanismSun.MODID);

    public static final DeferredChemical<Chemical> HELIUM = CHEMICALS.register("helium", 0x9CF5FF);
}
