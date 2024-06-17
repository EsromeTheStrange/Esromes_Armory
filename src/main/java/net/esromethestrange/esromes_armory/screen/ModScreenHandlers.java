package net.esromethestrange.esromes_armory.screen;

import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.recipe.WorkbenchRecipe;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlers {
    public static final ScreenHandlerType<ForgeScreenHandler> FORGE_SCREEN_HANDLER = Registry.register(Registries.SCREEN_HANDLER,
            new Identifier(EsromesArmory.MOD_ID, "forge"), new ExtendedScreenHandlerType<>(ForgeScreenHandler::new));
    public static final ScreenHandlerType<WorkbenchScreenHandler> WORKBENCH_SCREEN_HANDLER = Registry.register(Registries.SCREEN_HANDLER,
            new Identifier(EsromesArmory.MOD_ID, "workbench"), new ExtendedScreenHandlerType<>(WorkbenchScreenHandler::new));

    public static void registerScreenHandlers() {}
}
