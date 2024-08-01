package net.esromethestrange.esromes_armory.item;

import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.data.material.Material;
import net.esromethestrange.esromes_armory.data.material.Materials;
import net.esromethestrange.esromes_armory.item.material.MaterialItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class ComponentItem extends Item implements MaterialItem {
    public List<Material> defaultMaterials;
    protected int baseFuelTime = 200;

    public ComponentItem(Settings settings, List<Material> defaultMaterials) {
        super(settings);
        this.defaultMaterials = defaultMaterials;
        MATERIAL_ITEMS.add(this);
    }

    @Override
    public ItemStack getStack(Material material) {
        ItemStack stack = getDefaultStack();
        setMaterial(stack, material);
        return stack;
    }

    @Override
    public Text getName(ItemStack stack) {
        MutableText materialText = Text.translatable(getMaterial(stack).getTranslatableName());
        Text toolText = super.getName(stack);
        return materialText.append(toolText);
    }

    @Override
    public List<ItemStack> getDefaultStacks(boolean includeNone) {
        List<ItemStack> defaultStacks = new ArrayList<>();
        if(includeNone)
            defaultStacks.add(getStack(Materials.NONE));
        for(Material material : defaultMaterials){
            defaultStacks.add(getStack(material));
        }
        return defaultStacks;
    }

    @Override
    public Material getDefaultMaterial() {
        return defaultMaterials.getFirst();
    }

    @Override
    public List<Material> getValidMaterials() {
        return defaultMaterials;
    }

    @Override
    public int getBaseFuelTime() {
        return baseFuelTime;
    }

    public ComponentItem setBaseFuelTime(int baseFuelTime){
        this.baseFuelTime = baseFuelTime;
        return this;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);

        if(EsromesArmory.CONFIG.materialTooltips())
            addMaterialTooltip(stack, tooltip);
    }

    @Override
    public void addMaterialTooltip(ItemStack stack, List<Text> tooltip, boolean partNameIncluded) {
        String materialId = getMaterial(stack).getTranslatableName();
        MutableText materialText = Text.translatable(materialId).setStyle(Style.EMPTY.withColor(getMaterial(stack).color()));

        if(partNameIncluded){
            MutableText componentName = Text.translatable(getTranslationKey());
            materialText = componentName.append(": ").append(materialText);
        }

        tooltip.add(materialText);
    }
}
