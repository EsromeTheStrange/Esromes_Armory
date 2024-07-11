package net.esromethestrange.esromes_armory.client.screen;

import net.esromethestrange.esromes_armory.EsromesArmory;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ArmoryScreenHandlers {
    public static final ScreenHandlerType<WorkbenchScreenHandler> WORKBENCH_SCREEN_HANDLER = Registry.register(Registries.SCREEN_HANDLER,
            Identifier.of(EsromesArmory.MOD_ID, "workbench"), new ExtendedScreenHandlerType(
                    (syncId, inventory, data) -> {
                        if(data instanceof PacketByteBuf buf)
                            return new WorkbenchScreenHandler(syncId, inventory, WorkbenchData.PACKET_CODEC.decode(buf));
                        if(data instanceof WorkbenchData workbenchData)
                            return new WorkbenchScreenHandler(syncId, inventory, workbenchData);
                        return null;
                    },
                    WorkbenchData.PACKET_CODEC));

    public static void registerScreenHandlers() {}
}
