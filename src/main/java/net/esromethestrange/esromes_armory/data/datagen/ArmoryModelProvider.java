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
import net.minecraft.util.Identifier;

public class ArmoryModelProvider extends FabricModelProvider {
    public ArmoryModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        Identifier WATER_PARTICLE = Identifier.ofVanilla("block/water_still");
        blockStateModelGenerator.registerBuiltinWithParticle(ArmoryFluids.MOLTEN_COPPER_BLOCK, WATER_PARTICLE);
        blockStateModelGenerator.registerBuiltinWithParticle(ArmoryFluids.MOLTEN_GOLD_BLOCK, WATER_PARTICLE);
        blockStateModelGenerator.registerBuiltinWithParticle(ArmoryFluids.MOLTEN_IRON_BLOCK, WATER_PARTICLE);
        blockStateModelGenerator.registerBuiltinWithParticle(ArmoryFluids.MOLTEN_NETHERITE_BLOCK, WATER_PARTICLE);
        blockStateModelGenerator.registerBuiltinWithParticle(ArmoryFluids.MOLTEN_STEEL_BLOCK, WATER_PARTICLE);

        blockStateModelGenerator.registerNorthDefaultHorizontalRotation(ArmoryBlocks.ANVIL);

        blockStateModelGenerator.registerSimpleCubeAll(ArmoryBlocks.STEEL_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ArmoryBlocks.CHARCOAL_BLOCK);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ArmoryItems.STEEL_INGOT, Models.GENERATED);

        itemModelGenerator.register(ArmoryItems.SHOVEL_HEAD_MOLD, Models.GENERATED);
        itemModelGenerator.register(ArmoryItems.AXE_HEAD_MOLD, Models.GENERATED);
        itemModelGenerator.register(ArmoryItems.HOE_HEAD_MOLD, Models.GENERATED);
        itemModelGenerator.register(ArmoryItems.SWORD_GUARD_MOLD, Models.GENERATED);
        itemModelGenerator.register(ArmoryItems.SWORD_BLADE_MOLD, Models.GENERATED);

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

        itemModelGenerator.register(ArmoryFluids.MOLTEN_COPPER_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ArmoryFluids.MOLTEN_IRON_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ArmoryFluids.MOLTEN_GOLD_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ArmoryFluids.MOLTEN_NETHERITE_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ArmoryFluids.MOLTEN_STEEL_BUCKET, Models.GENERATED);
    }
}
