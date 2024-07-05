package net.esromethestrange.esromes_armory.block.entity;

import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.block.ArmoryBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ArmoryBlockEntities {
    public static final BlockEntityType<WorkbenchBlockEntity> WORKBENCH_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE,
            Identifier.of(EsromesArmory.MOD_ID, "workbench"), FabricBlockEntityTypeBuilder.create(WorkbenchBlockEntity::new, ArmoryBlocks.WORKBENCH).build());

    public static void registerBlockEntities() {}
}
