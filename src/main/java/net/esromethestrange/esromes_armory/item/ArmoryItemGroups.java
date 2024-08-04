package net.esromethestrange.esromes_armory.item;

import io.wispforest.owo.itemgroup.Icon;
import io.wispforest.owo.itemgroup.OwoItemGroup;
import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.block.ArmoryBlocks;
import net.esromethestrange.esromes_armory.registry.ArmoryRegistries;
import net.esromethestrange.esromes_armory.data.material.Material;
import net.esromethestrange.esromes_armory.fluid.ArmoryFluids;
import net.esromethestrange.esromes_armory.item.material.MaterialItem;
import net.esromethestrange.esromes_armory.item.material.PartBasedItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ArmoryItemGroups {
    static HashMap<Material, List<ItemStack>> itemMap = new HashMap<>();

    public static final OwoItemGroup ESROMES_ARMORY = OwoItemGroup
            .builder(Identifier.of(EsromesArmory.MOD_ID, "esromes_armory"), () -> Icon.of(ArmoryItems.STEEL_INGOT))
            // additional builder configuration goes between these lines
            .build();

    public static void registerItemGroups() {
        ESROMES_ARMORY.addCustomTab(Icon.of(ArmoryBlocks.FORGE), "default", (context, entries) ->{
            entries.add(ArmoryBlocks.WORKBENCH);
            entries.add(ArmoryBlocks.FORGE);
            entries.add(ArmoryBlocks.ANVIL);
            entries.add(ArmoryBlocks.SMELTERY);

            entries.add(ArmoryBlocks.CHARCOAL_BLOCK);
            entries.add(ArmoryBlocks.STEEL_BLOCK);

            entries.add(ArmoryItems.STEEL_INGOT);

            entries.add(ArmoryItems.SHOVEL_HEAD_MOLD);
            entries.add(ArmoryItems.AXE_HEAD_MOLD);
            entries.add(ArmoryItems.HOE_HEAD_MOLD);
            entries.add(ArmoryItems.SWORD_GUARD_MOLD);
            entries.add(ArmoryItems.SWORD_BLADE_MOLD);
        }, true);

        ESROMES_ARMORY.addCustomTab(Icon.of(ArmoryItems.PICKAXE), "tools", (context, entries) ->{
            itemMap.clear();
            for(PartBasedItem partBasedItem : PartBasedItem.PART_BASED_ITEMS){
                mapItems(partBasedItem.getDefaultStacks(true));
            }
            ArmoryRegistries.MATERIAL.stream().forEach(material -> {
                if(!itemMap.containsKey(material))
                    return;
                List<ItemStack> stacks = itemMap.get(material);
                entries.addAll(stacks);
            });
        }, false);

        ESROMES_ARMORY.addCustomTab(Icon.of(ArmoryItems.PICKAXE_HEAD), "tool_components", (context, entries) ->{
            for(MaterialItem materialItem : MaterialItem.MATERIAL_ITEMS){
                entries.addAll(materialItem.getDefaultStacks(true));
            }
        }, false);

        ESROMES_ARMORY.addCustomTab(Icon.of(ArmoryFluids.MOLTEN_STEEL_BUCKET), "fluids", (context, entries) ->{
            entries.add(ArmoryFluids.MOLTEN_COPPER_BUCKET);
            entries.add(ArmoryFluids.MOLTEN_IRON_BUCKET);
            entries.add(ArmoryFluids.MOLTEN_GOLD_BUCKET);
            entries.add(ArmoryFluids.MOLTEN_NETHERITE_BUCKET);
            entries.add(ArmoryFluids.MOLTEN_STEEL_BUCKET);
        }, false);

        ESROMES_ARMORY.initialize();
    }

    static void mapItems(List<ItemStack> stacks){
        for(ItemStack stack : stacks){
            if(!(stack.getItem() instanceof PartBasedItem partBasedItem))
                return;
            Material material = partBasedItem.getPrimaryMaterial(stack);
            if(!itemMap.containsKey(material))
                itemMap.put(material, new ArrayList<>());
            itemMap.get(material).add(stack);
        }
    }
}
