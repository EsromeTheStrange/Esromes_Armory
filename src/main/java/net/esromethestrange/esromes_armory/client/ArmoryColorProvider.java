package net.esromethestrange.esromes_armory.client;

import net.esromethestrange.esromes_armory.data.material.Material;
import net.esromethestrange.esromes_armory.item.material.MaterialItem;
import net.esromethestrange.esromes_armory.util.ResourceHelper;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.item.Item;
import net.minecraft.util.math.ColorHelper;

public class ArmoryColorProvider {
    public static void register(){
        MaterialItem.MATERIAL_ITEMS.forEach(materialItem -> {
            ColorProviderRegistry.ITEM.register((itemStack, tintIndex) -> {
                Material material = materialItem.getMaterial(itemStack);

                if(ResourceHelper.isMaterialModelPresent(materialItem, material))
                    return ColorHelper.Argb.fullAlpha(0xffffff);

                return ColorHelper.Argb.fullAlpha(material.color());
            }, (Item) materialItem);
        });
    }
}
