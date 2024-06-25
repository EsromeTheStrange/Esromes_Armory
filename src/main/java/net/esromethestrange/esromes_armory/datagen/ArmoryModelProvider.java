package net.esromethestrange.esromes_armory.datagen;

import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.block.ModBlocks;
import net.esromethestrange.esromes_armory.material.ArmoryMaterial;
import net.esromethestrange.esromes_armory.item.ModItems;
import net.esromethestrange.esromes_armory.item.material.MaterialItem;
import net.esromethestrange.esromes_armory.material.ArmoryMaterials;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.*;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class ArmoryModelProvider extends FabricModelProvider {
    public ArmoryModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleState(ModBlocks.FORGE);
        blockStateModelGenerator.registerSimpleState(ModBlocks.WORKBENCH);

        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.STEEL_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.CHARCOAL_BLOCK);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.STEEL_INGOT, Models.GENERATED);

        for(MaterialItem item : MaterialItem.MATERIAL_ITEMS){
            Models.GENERATED.upload(ModelIds.getItemModelId((Item)item),
                    TextureMap.layer0(item.getRawIdentifier()
                            .withSuffixedPath("_"+ EsromesArmory.MOD_ID +"_"+ ArmoryMaterials.NONE.materialName)),
                    itemModelGenerator.writer);
            for(ArmoryMaterial material : item.getValidMaterials()){
                itemModelGenerator.register((Item)item,
                        "_" + material.id.getNamespace() + "_" + material.id.getPath(),
                        Models.GENERATED);
            }
            itemModelGenerator.register((Item)item,
                    "_" + ArmoryMaterials.NONE.modId + "_" + ArmoryMaterials.NONE.materialName,
                    Models.GENERATED);
        }
    }
}
