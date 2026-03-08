package com.hamburger0abcde.mekanismsun.network;

import com.hamburger0abcde.mekanismsun.network.to_server.PacketMSGuiInteract;
import mekanism.common.lib.Version;
import mekanism.common.network.BasePacketHandler;
import net.neoforged.bus.api.IEventBus;

public class MSPacketHandler extends BasePacketHandler {
    public MSPacketHandler(IEventBus modEventBus, Version version) {
        super(modEventBus, version);
    }

    @Override
    protected void registerClientToServer(PacketRegistrar registrar) {
        registrar.play(PacketMSGuiInteract.TYPE, PacketMSGuiInteract.STREAM_CODEC);
    }

    @Override
    protected void registerServerToClient(PacketRegistrar registrar) {}
}
