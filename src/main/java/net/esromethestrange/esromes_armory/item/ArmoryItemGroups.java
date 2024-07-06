package net.esromethestrange.esromes_armory.item;

import io.wispforest.owo.itemgroup.Icon;
import io.wispforest.owo.itemgroup.OwoItemGroup;
import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.block.ArmoryBlocks;
import net.esromethestrange.esromes_armory.item.material.PartBasedItem;
import net.esromethestrange.esromes_armory.item.material.MaterialItem;
import net.esromethestrange.esromes_armory.data.material.ArmoryMaterial;
import net.esromethestrange.esromes_armory.data.material.ArmoryMaterials;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ArmoryItemGroups {
    static HashMap<ArmoryMaterial, List<ItemStack>> itemMap = new HashMap<>();

    public static final OwoItemGroup ESROMES_ARMORY = OwoItemGroup
            .builder(Identifier.of(EsromesArmory.MOD_ID, "esromes_armory"), () -> Icon.of(ArmoryItems.STEEL_INGOT))
            // additional builder configuration goes between these lines
            .build();

    public static void registerItemGroups() {
        ESROMES_ARMORY.addCustomTab(Icon.of(ArmoryBlocks.FORGE), "default", (context, entries) ->{
            entries.add(ArmoryBlocks.WORKBENCH);
            entries.add(ArmoryBlocks.FORGE);
            entries.add(ArmoryBlocks.ANVIL);

            entries.add(ArmoryBlocks.CHARCOAL_BLOCK);
            entries.add(ArmoryBlocks.STEEL_BLOCK);

            entries.add(ArmoryItems.STEEL_INGOT);
        }, true);

        ESROMES_ARMORY.addCustomTab(Icon.of(ArmoryItems.PICKAXE), "tools", (context, entries) ->{
            itemMap.clear();
            for(PartBasedItem partBasedItem : PartBasedItem.PART_BASED_ITEMS){
                mapItems(partBasedItem.getDefaultStacks(true));
            }
            for(ArmoryMaterial armoryMaterial : ArmoryMaterials.getMaterials()){
                if(!itemMap.containsKey(armoryMaterial))
                    continue;
                List<ItemStack> stacks = itemMap.get(armoryMaterial);
                entries.addAll(stacks);
            }
        }, false);

        ESROMES_ARMORY.addCustomTab(Icon.of(ArmoryItems.PICKAXE_HEAD), "tool_components", (context, entries) ->{
            for(MaterialItem materialItem : MaterialItem.MATERIAL_ITEMS){
                entries.addAll(materialItem.getDefaultStacks(true));
            }
        }, false);

        ESROMES_ARMORY.initialize();
    }

    static void mapItems(List<ItemStack> stacks){
        for(ItemStack stack : stacks){
            if(!(stack.getItem() instanceof PartBasedItem partBasedItem))
                return;
            ArmoryMaterial material = partBasedItem.getPrimaryMaterial(stack);
            if(!itemMap.containsKey(material))
                itemMap.put(material, new ArrayList<>());
            itemMap.get(material).add(stack);
        }
    }
}
