package net.esromethestrange.esromes_armory.item.material;

import net.esromethestrange.esromes_armory.registry.ArmoryRegistries;
import net.esromethestrange.esromes_armory.data.component.ArmoryComponents;
import net.esromethestrange.esromes_armory.data.material.Material;
import net.esromethestrange.esromes_armory.data.material.Materials;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public interface MaterialItem {
    List<MaterialItem> MATERIAL_ITEMS = new ArrayList<>();

    ItemStack getStack(Material material);
    void addMaterialTooltip(ItemStack stack, List<Text> tooltip, boolean partNameIncluded);

    List<ItemStack> getDefaultStacks(boolean includeNone);
    List<Material> getValidMaterials();
    Material getDefaultMaterial();
    int getBaseFuelTime();

    default int getFuelTime(ItemStack stack){
        return getBaseFuelTime() * getMaterial(stack).fuelTimeMultiplier();
    }

    default void addMaterialTooltip(ItemStack stack, List<Text> tooltip){
        addMaterialTooltip(stack, tooltip, false);
    }

    default void setMaterial(ItemStack stack, Material material){
        Identifier materialId = ArmoryRegistries.MATERIAL.getId(material);
        if(materialId == null)
            return;
        stack.set(ArmoryComponents.MATERIALS, materialId.toString());
    }

    default Material getMaterial(ItemStack stack){
        if(stack.getItem() instanceof MaterialItem){
            String material = stack.get(ArmoryComponents.MATERIALS);
            if(material != null)
                return ArmoryRegistries.MATERIAL.get(Identifier.tryParse(material));
        }
        else if(stack.getItem() instanceof PartBasedItem partBasedItem){
            return partBasedItem.getMaterial(stack, this);
        }
        return Materials.NONEOLD;
    }

    default Identifier getRawIdentifier() {
        return Registries.ITEM.getId((Item)this);
    }
}
