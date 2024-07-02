package net.esromethestrange.esromes_armory.client;

import net.esromethestrange.esromes_armory.item.material.PartBasedItem;
import net.esromethestrange.esromes_armory.item.material.MaterialItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.minecraft.client.util.ModelIdentifier;

import java.util.HashMap;

@Environment(EnvType.CLIENT)
public class ModModelLoadingPlugin implements ModelLoadingPlugin {
    private final HashMap<ModelIdentifier, PartBasedItemModel> componentBasedItemModels = new HashMap<>();
    private final HashMap<ModelIdentifier, MaterialItemModel> materialItemModels = new HashMap<>();

    @Override
    public void onInitializeModelLoader(Context pluginContext) {
        MaterialItem.MATERIAL_ITEMS.forEach(this::addMaterialItemModel);
        PartBasedItem.PART_BASED_ITEMS.forEach(this::addComponentBasedModel);

        pluginContext.modifyModelOnLoad().register((original, context) -> {
            for(ModelIdentifier id : componentBasedItemModels.keySet())
                if (context.resourceId().equals(id.id()))
                    return componentBasedItemModels.get(id);

            for(ModelIdentifier id : materialItemModels.keySet())
                if(context.resourceId().equals(id.id()))
                    return materialItemModels.get(id);

            return original;
        });
    }

    private void addComponentBasedModel(PartBasedItem item){
        ModelIdentifier modelIdentifier = new ModelIdentifier(item.getRawIdentifier(), "inventory");
        componentBasedItemModels.put(modelIdentifier, new PartBasedItemModel(item));
    }

    private void addMaterialItemModel(MaterialItem item){
        ModelIdentifier modelIdentifier = new ModelIdentifier(item.getRawIdentifier(), "inventory");
        materialItemModels.put(modelIdentifier, new MaterialItemModel(item));
    }
}
