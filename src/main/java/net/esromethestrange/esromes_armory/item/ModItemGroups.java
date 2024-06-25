package net.esromethestrange.esromes_armory.item;

import io.wispforest.owo.itemgroup.Icon;
import io.wispforest.owo.itemgroup.OwoItemGroup;
import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.block.ModBlocks;
import net.esromethestrange.esromes_armory.item.material.ComponentBasedItem;
import net.esromethestrange.esromes_armory.item.material.MaterialItem;
import net.esromethestrange.esromes_armory.material.ArmoryMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ModItemGroups {
    static HashMap<ArmoryMaterial, List<ItemStack>> itemMap = new HashMap<>();

    public static final OwoItemGroup ESROMES_ARMORY = OwoItemGroup
            .builder(new Identifier(EsromesArmory.MOD_ID, "esromes_armory"), () -> Icon.of(ModItems.STEEL_INGOT))
            // additional builder configuration goes between these lines
            .build();

    public static void registerItemGroups() {
        ESROMES_ARMORY.addCustomTab(Icon.of(ModBlocks.FORGE), "default", (context, entries) ->{
            entries.add(ModBlocks.FORGE);
            entries.add(ModBlocks.WORKBENCH);

            entries.add(ModBlocks.CHARCOAL_BLOCK);
            entries.add(ModBlocks.STEEL_BLOCK);

            entries.add(ModItems.STEEL_INGOT);
        }, true);

        ESROMES_ARMORY.addCustomTab(Icon.of(ModItems.PICKAXE), "tools", (context, entries) ->{
            itemMap.clear();
            for(ComponentBasedItem componentBasedItem : ComponentBasedItem.COMPONENT_BASED_ITEMS){
                mapItems(componentBasedItem.getDefaultStacks());
            }
            for(List<ItemStack> stacks : itemMap.values()){
                entries.addAll(stacks);
            }
        }, false);

        ESROMES_ARMORY.addCustomTab(Icon.of(ModItems.PICKAXE_HEAD), "tool_components", (context, entries) ->{
            for(MaterialItem materialItem : MaterialItem.MATERIAL_ITEMS){
                entries.addAll(materialItem.getDefaultStacks());
            }
        }, false);

        ESROMES_ARMORY.initialize();
    }

    static void mapItems(List<ItemStack> stacks){
        for(ItemStack stack : stacks){
            if(!(stack.getItem() instanceof ComponentBasedItem componentBasedItem))
                return;
            ArmoryMaterial material = componentBasedItem.getPrimaryMaterial(stack);
            if(!itemMap.containsKey(material))
                itemMap.put(material, new ArrayList<>());
            itemMap.get(material).add(stack);
        }
    }
}
