package net.esromethestrange.esromes_armory.block.entity;

import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.block.ArmoryBlocks;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ArmoryBlockEntities {
    public static final BlockEntityType<WorkbenchBlockEntity> WORKBENCH_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE,
            Identifier.of(EsromesArmory.MOD_ID, "workbench"), BlockEntityType.Builder.create(WorkbenchBlockEntity::new, ArmoryBlocks.WORKBENCH).build());
    public static final BlockEntityType<ForgeBlockEntity> FORGE_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE,
            Identifier.of(EsromesArmory.MOD_ID, "forge"), BlockEntityType.Builder.create(ForgeBlockEntity::new, ArmoryBlocks.FORGE).build());
    public static final BlockEntityType<ArmoryAnvilBlockEntity> ANVIL_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE,
            Identifier.of(EsromesArmory.MOD_ID, "anvil"), BlockEntityType.Builder.create(ArmoryAnvilBlockEntity::new, ArmoryBlocks.ANVIL).build());
    public static final BlockEntityType<SmelteryBlockEntity> SMELTERY_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE,
            Identifier.of(EsromesArmory.MOD_ID, "smeltery"), BlockEntityType.Builder.create(SmelteryBlockEntity::new, ArmoryBlocks.SMELTERY).build());

    public static void registerBlockEntities() {
        FluidStorage.SIDED.registerForBlockEntity((myTank, direction) -> myTank.fluidStorage, SMELTERY_BLOCK_ENTITY);
    }
}
