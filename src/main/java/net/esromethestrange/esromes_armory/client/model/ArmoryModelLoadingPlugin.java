package net.esromethestrange.esromes_armory.client.model;

import net.esromethestrange.esromes_armory.item.material.MaterialItem;
import net.esromethestrange.esromes_armory.item.material.PartBasedItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.List;

@Environment(EnvType.CLIENT)
public class ArmoryModelLoadingPlugin implements ModelLoadingPlugin {
    private final HashMap<Identifier, PartBasedItemModel> componentBasedItemModels = new HashMap<>();
    private final HashMap<Identifier, MaterialItemModel> materialItemModels = new HashMap<>();

    private final HashMap<MaterialItem, List<Identifier>> materialModelVariants = new HashMap<>();

    @Override
    public void onInitializeModelLoader(Context pluginContext) {
        for(MaterialItem materialItem : MaterialItem.MATERIAL_ITEMS){
            materialModelVariants.put(materialItem, List.of());

            ResourceManager manager = MinecraftClient.getInstance().getResourceManager();
            Identifier itemId = materialItem.getRawIdentifier();

            for(Identifier materialVariant : manager.findResources("models/item", i -> {
                String[] pathParts = i.getPath().split("/");
                return i.toString().endsWith(".json") &&
                        pathParts[pathParts.length - 1].startsWith(itemId.getPath()) &&
                        i.getNamespace().equals(itemId.getNamespace());
            }).keySet()){
                String path = materialVariant.getPath();
                Identifier materialVariantId = Identifier.of(
                        materialVariant.getNamespace(),
                        path.substring("models/".length(), path.length() - ".json".length())
                );
                pluginContext.addModels(materialVariantId);

                materialModelVariants.get(materialItem).add(materialVariantId);
            }
        }

        MaterialItem.MATERIAL_ITEMS.forEach(this::addMaterialItemModel);
        PartBasedItem.PART_BASED_ITEMS.forEach(this::addComponentBasedModel);

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
        materialItemModels.put(modelIdentifier, new MaterialItemModel(item, materialModelVariants.get(item)));
    }
}
