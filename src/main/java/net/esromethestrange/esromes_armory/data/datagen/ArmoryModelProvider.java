package net.esromethestrange.esromes_armory.data.datagen;

import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.block.ArmoryBlocks;
import net.esromethestrange.esromes_armory.data.material.Material;
import net.esromethestrange.esromes_armory.fluid.ArmoryFluids;
import net.esromethestrange.esromes_armory.item.ArmoryItems;
import net.esromethestrange.esromes_armory.item.material.MaterialItem;
import net.esromethestrange.esromes_armory.data.material.Materials;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.*;
import net.minecraft.item.Item;

public class ArmoryModelProvider extends FabricModelProvider {
    public ArmoryModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleState(ArmoryBlocks.WORKBENCH);
        blockStateModelGenerator.registerSimpleState(ArmoryBlocks.FORGE);
        blockStateModelGenerator.registerNorthDefaultHorizontalRotation(ArmoryBlocks.ANVIL);

        blockStateModelGenerator.registerSimpleCubeAll(ArmoryBlocks.STEEL_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ArmoryBlocks.CHARCOAL_BLOCK);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ArmoryItems.STEEL_INGOT, Models.GENERATED);

        for(MaterialItem item : MaterialItem.MATERIAL_ITEMS){
            Models.GENERATED.upload(ModelIds.getItemModelId((Item)item),
                    TextureMap.layer0(item.getRawIdentifier()
                            .withSuffixedPath("_"+ EsromesArmory.MOD_ID +"_"+ Materials.NONE.materialName)
                            .withPrefixedPath("item/")),
                    itemModelGenerator.writer);
            for(Material material : item.getValidMaterials()){
                itemModelGenerator.register((Item)item,
                        "_" + material.id.getNamespace() + "_" + material.id.getPath(),
                        Models.GENERATED);
            }
            itemModelGenerator.register((Item)item,
                    "_" + Materials.NONE.modId + "_" + Materials.NONE.materialName,
                    Models.GENERATED);
        }

        itemModelGenerator.register(ArmoryFluids.STEEL_BUCKET, Models.GENERATED);
    }
}
