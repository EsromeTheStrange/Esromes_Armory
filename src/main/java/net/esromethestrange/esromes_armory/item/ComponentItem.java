package net.esromethestrange.esromes_armory.item;

import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.data.material.Material;
import net.esromethestrange.esromes_armory.data.material.Materials;
import net.esromethestrange.esromes_armory.item.material.MaterialItem;
import net.esromethestrange.esromes_armory.registry.ArmoryRegistryKeys;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class ComponentItem extends Item implements MaterialItem {
    public List<RegistryEntry<Material>> defaultMaterials;
    protected int baseFuelTime = 200;

    public ComponentItem(Settings settings, List<RegistryKey<Material>> defaultMaterials) {
        super(settings);
        List<RegistryEntry<Material>> entryList = new ArrayList<>();
        for (RegistryKey<Material> material : defaultMaterials) {
            //entryList.add(MinecraftClient.getInstance().world.getRegistryManager().get(ArmoryRegistryKeys.MATERIAL).getEntry(material).get()); //TODO look into this
        }
        this.defaultMaterials = entryList;
        MATERIAL_ITEMS.add(this);
    }

    @Override
    public ItemStack getStack(RegistryEntry<Material> material) {
        ItemStack stack = getDefaultStack();
        setMaterial(stack, material);
        return stack;
    }

    @Override
    public Text getName(ItemStack stack) {
        MutableText materialText = Text.translatable(getMaterial(stack).value().getTranslatableName());
        Text toolText = super.getName(stack);
        return materialText.append(toolText);
    }

    @Override
    public List<ItemStack> getDefaultStacks(boolean includeNone) {
        List<ItemStack> defaultStacks = new ArrayList<>();
        if(includeNone)
            defaultStacks.add(getStack(MinecraftClient.getInstance().world.getRegistryManager().get(ArmoryRegistryKeys.MATERIAL).getEntry(Materials.NONE).get())); // TODO fix this
        for(RegistryEntry<Material> material : defaultMaterials){
            defaultStacks.add(getStack(material));
        }
        return defaultStacks;
    }

    @Override
    public RegistryEntry<Material> getDefaultMaterial() {
        return MinecraftClient.getInstance().world.getRegistryManager().get(ArmoryRegistryKeys.MATERIAL).getEntry(Materials.NONE).get();//TODO defaultMaterials.getFirst();
    }

    @Override
    public List<RegistryEntry<Material>> getValidMaterials() {
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
        String materialId = getMaterial(stack).value().getTranslatableName();
        MutableText materialText = Text.translatable(materialId).setStyle(Style.EMPTY.withColor(getMaterial(stack).value().color()));

        if(partNameIncluded){
            MutableText componentName = Text.translatable(getTranslationKey());
            materialText = componentName.append(": ").append(materialText);
        }

        tooltip.add(materialText);
    }
}
