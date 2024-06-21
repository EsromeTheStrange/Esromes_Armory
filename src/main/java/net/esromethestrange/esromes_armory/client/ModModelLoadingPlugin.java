package net.esromethestrange.esromes_armory.client;

import net.esromethestrange.esromes_armory.item.ModItems;
import net.esromethestrange.esromes_armory.item.material.ComponentBasedItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.minecraft.client.util.ModelIdentifier;

import java.util.HashMap;

@Environment(EnvType.CLIENT)
public class ModModelLoadingPlugin implements ModelLoadingPlugin {
    private HashMap<ModelIdentifier, ComponentBasedItemModel> componentBasedItemModels = new HashMap<>();

    @Override
    public void onInitializeModelLoader(Context pluginContext) {
        addComponentBasedModel((ComponentBasedItem) ModItems.PICKAXE);

        pluginContext.modifyModelOnLoad().register((original, context) -> {
            for(ModelIdentifier id : componentBasedItemModels.keySet())
                if (context.id().equals(id))
                    return componentBasedItemModels.get(id);

            return original;
        });
    }

    private void addComponentBasedModel(ComponentBasedItem item){
        ModelIdentifier modelIdentifier = new ModelIdentifier(item.getRawIdentifier(), "inventory");
        componentBasedItemModels.put(modelIdentifier, new ComponentBasedItemModel(item));
    }
}
