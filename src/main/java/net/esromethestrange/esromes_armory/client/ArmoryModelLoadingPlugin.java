package net.esromethestrange.esromes_armory.client;

import net.esromethestrange.esromes_armory.item.material.PartBasedItem;
import net.esromethestrange.esromes_armory.item.material.MaterialItem;
import net.esromethestrange.esromes_armory.data.material.Materials;
import net.esromethestrange.esromes_armory.util.MaterialHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.minecraft.util.Identifier;

import java.util.HashMap;

@Environment(EnvType.CLIENT)
public class ArmoryModelLoadingPlugin implements ModelLoadingPlugin {
    private final HashMap<Identifier, PartBasedItemModel> componentBasedItemModels = new HashMap<>();
    private final HashMap<Identifier, MaterialItemModel> materialItemModels = new HashMap<>();

    @Override
    public void onInitializeModelLoader(Context pluginContext) {
        MaterialItem.MATERIAL_ITEMS.forEach(this::addMaterialItemModel);
        PartBasedItem.PART_BASED_ITEMS.forEach(this::addComponentBasedModel);

        for(Identifier materialId : Materials.getMaterialIds()){
            for(MaterialItem materialItem : MaterialItem.MATERIAL_ITEMS){
                Identifier id = MaterialHelper.getItemModelIdentifier(materialId, materialItem.getRawIdentifier());
                pluginContext.addModels(id);
            }
        }

        pluginContext.modifyModelOnLoad().register((original, context) -> {
            if(context.resourceId()==null)
                return original;

            for(Identifier id : componentBasedItemModels.keySet())
                if (context.resourceId().equals(id))
                    return componentBasedItemModels.get(id);


            for(Identifier id : materialItemModels.keySet())
                if(context.resourceId().equals(id))
                    return materialItemModels.get(id);

            return original;
        });
    }

    private void addComponentBasedModel(PartBasedItem item){
        Identifier modelIdentifier = item.getRawIdentifier().withPrefixedPath("item/");
        componentBasedItemModels.put(modelIdentifier, new PartBasedItemModel(item));
    }

    private void addMaterialItemModel(MaterialItem item){
        Identifier modelIdentifier = item.getRawIdentifier().withPrefixedPath("item/");
        materialItemModels.put(modelIdentifier, new MaterialItemModel(item));
    }
}
