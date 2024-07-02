package net.esromethestrange.esromes_armory.item;

import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.item.material.MaterialItem;
import net.esromethestrange.esromes_armory.material.ArmoryMaterial;
import net.esromethestrange.esromes_armory.material.ArmoryMaterials;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class ComponentItem extends Item implements MaterialItem {
    public List<ArmoryMaterial> defaultMaterials;

    public ComponentItem(Settings settings, List<ArmoryMaterial> defaultMaterials) {
        super(settings);
        this.defaultMaterials = defaultMaterials;
        MATERIAL_ITEMS.add(this);
    }

    @Override
    public ItemStack getStack(ArmoryMaterial material) {
        ItemStack stack = getDefaultStack();
        setMaterial(stack, material);
        return stack;
    }

    @Override
    public Text getName(ItemStack stack) {
        MutableText materialText = Text.translatable(getMaterial(stack).translatable_name);
        Text toolText = super.getName(stack);
        return materialText.append(toolText);
    }

    @Override
    public List<ItemStack> getDefaultStacks(boolean includeNone) {
        List<ItemStack> defaultStacks = new ArrayList<>();
        if(includeNone)
            defaultStacks.add(getStack(ArmoryMaterials.NONE));
        for(ArmoryMaterial material : defaultMaterials){
            defaultStacks.add(getStack(material));
        }
        return defaultStacks;
    }

    @Override
    public ArmoryMaterial getDefaultMaterial() {
        return defaultMaterials.getFirst();
    }

    @Override
    public List<ArmoryMaterial> getValidMaterials() {
        return defaultMaterials;
    }


    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);

        if(EsromesArmory.CONFIG.materialTooltips())
            addMaterialTooltip(stack, tooltip);
    }

    @Override
    public void addMaterialTooltip(ItemStack stack, List<Text> tooltip, boolean partNameIncluded) {
        String materialId = getMaterial(stack).translatable_name;
        MutableText materialText = Text.translatable(materialId).setStyle(Style.EMPTY.withColor(getMaterial(stack).color));

        if(partNameIncluded){
            MutableText componentName = Text.translatable(getTranslationKey());
            materialText = componentName.append(": ").append(materialText);
        }

        tooltip.add(materialText);
    }
}
