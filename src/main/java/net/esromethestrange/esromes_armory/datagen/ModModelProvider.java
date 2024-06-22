package net.esromethestrange.esromes_armory.datagen;

import com.google.common.io.Resources;
import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.block.ModBlocks;
import net.esromethestrange.esromes_armory.data.ArmoryMaterial;
import net.esromethestrange.esromes_armory.data.ArmoryMaterialInfo;
import net.esromethestrange.esromes_armory.data.ArmoryMaterials;
import net.esromethestrange.esromes_armory.item.ModItems;
import net.esromethestrange.esromes_armory.item.material.MaterialItem;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.*;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }
    protected List<String> materialKeys = new ArrayList<>();
    protected List<Item> materialItems = new ArrayList<>();

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

        //Material Setup
        addMaterial(ArmoryMaterial.NONE);

        addMaterial(ArmoryMaterials.OAK);

        addMaterial(ArmoryMaterials.IRON);
        addMaterial(ArmoryMaterials.STEEL);

        addMaterialItems();

        for(Item item : materialItems){
            Models.GENERATED.upload(ModelIds.getItemModelId(item),
                    TextureMap.layer0(((MaterialItem)item).getRawIdentifier()
                            .withSuffixedPath("_"+ EsromesArmory.MOD_ID +"_"+ArmoryMaterial.NONE.materialName)),
                    itemModelGenerator.writer);
            for(String materialKey : materialKeys){
                itemModelGenerator.register(item, materialKey, Models.GENERATED);
            }
        }
    }

    protected void addMaterialItems(){
        addMaterialItem(ModItems.PICKAXE_HEAD);
        addMaterialItem(ModItems.TOOL_HANDLE);
    }

    protected void addMaterial(ArmoryMaterial material){
        String key = "_" + material.id.getNamespace() + "_" + material.id.getPath();
        materialKeys.add(key);
    }

    protected void addMaterial(ArmoryMaterialInfo material){
        String key = "_" + material.id.getNamespace() + "_" + material.id.getPath();
        materialKeys.add(key);
    }

    protected void addMaterialItem(Item item){
        materialItems.add(item);
    }
}
