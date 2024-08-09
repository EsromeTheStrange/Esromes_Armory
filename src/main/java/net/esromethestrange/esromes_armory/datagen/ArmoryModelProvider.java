package net.esromethestrange.esromes_armory.datagen;

import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.block.ArmoryBlocks;
import net.esromethestrange.esromes_armory.data.ArmoryTags;
import net.esromethestrange.esromes_armory.data.material.Material;
import net.esromethestrange.esromes_armory.data.material.Materials;
import net.esromethestrange.esromes_armory.datagen.tag.ArmoryMaterialTagProvider;
import net.esromethestrange.esromes_armory.fluid.ArmoryFluids;
import net.esromethestrange.esromes_armory.item.ArmoryItems;
import net.esromethestrange.esromes_armory.item.PartItem;
import net.esromethestrange.esromes_armory.item.material.MaterialItem;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.*;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ArmoryModelProvider extends FabricModelProvider {
    private final RegistryWrapper.WrapperLookup wrapperLookup;
    public ArmoryModelProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output);
        try {
            this.wrapperLookup = registriesFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
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

        generatePartItemModels(itemModelGenerator);

        itemModelGenerator.register(ArmoryFluids.MOLTEN_COPPER_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ArmoryFluids.MOLTEN_IRON_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ArmoryFluids.MOLTEN_GOLD_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ArmoryFluids.MOLTEN_NETHERITE_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ArmoryFluids.MOLTEN_STEEL_BUCKET, Models.GENERATED);
    }

    private void generatePartItemModels(ItemModelGenerator itemModelGenerator){
        Identifier noneId = Materials.NONE.getValue();

        HashMap<TagKey<Material>, List<RegistryKey<Material>>> tagMap = createTagMap();
        for(MaterialItem item : MaterialItem.MATERIAL_ITEMS){
            if(!(item instanceof PartItem partItem))
                continue;

            Models.GENERATED.upload(ModelIds.getItemModelId((Item)item),
                    TextureMap.layer0(item.getRawIdentifier()
                            .withSuffixedPath("_"+ EsromesArmory.MOD_ID +"_"+ noneId.getPath())
                            .withPrefixedPath("item/")),
                    itemModelGenerator.writer);

            List<RegistryKey<Material>> materials = new ArrayList<>(tagMap.get(partItem.defaultMaterials));
            materials.add(Materials.NONE);

            for(RegistryKey<Material> material : materials){
                Identifier materialId = material.getValue();
                itemModelGenerator.register((Item)item,
                        "_" + materialId.getNamespace() + "_" + materialId.getPath(),
                        Models.GENERATED);
            }
        }
    }

    private HashMap<TagKey<Material>, List<RegistryKey<Material>>> createTagMap(){
        HashMap<TagKey<Material>, List<RegistryKey<Material>>> map = new HashMap<>();

        map.put(ArmoryTags.Materials.WOOD, ArmoryMaterialTagProvider.WOODS);
        map.put(ArmoryTags.Materials.METAL, ArmoryMaterialTagProvider.METALS);
        map.put(ArmoryTags.Materials.BINDING, ArmoryMaterialTagProvider.BINDINGS);

        return map;
    }
}
