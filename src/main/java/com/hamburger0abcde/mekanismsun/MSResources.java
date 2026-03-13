package com.hamburger0abcde.mekanismsun;

import mekanism.common.resource.IResource;

public enum MSResources implements IResource {
    ELECTRUM("electrum"),
    SILVER("silver")
    ;

    private final String registrySuffix;

    MSResources(String registrySuffix) {
        this.registrySuffix = registrySuffix;
    }

    @Override
    public String getRegistrySuffix() {
        return registrySuffix;
    }
}
