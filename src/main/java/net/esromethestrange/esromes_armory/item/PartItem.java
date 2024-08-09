package net.esromethestrange.esromes_armory.item;

import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.data.material.Material;
import net.esromethestrange.esromes_armory.data.material.Materials;
import net.esromethestrange.esromes_armory.item.material.MaterialItem;
import net.esromethestrange.esromes_armory.registry.ArmoryRegistryKeys;
import net.esromethestrange.esromes_armory.util.MaterialHelper;
import net.fabricmc.fabric.api.event.lifecycle.v1.CommonLifecycleEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PartItem extends Item implements MaterialItem {
    public TagKey<Material> defaultMaterials;
    protected int baseFuelTime = 200;
    protected List<RegistryEntry<Material>> materials = new ArrayList<>();
    protected List<ItemStack> defaultStacks = new ArrayList<>();

    public PartItem(Settings settings, TagKey<Material> defaultMaterials) {
        super(settings);
        this.defaultMaterials = defaultMaterials;
        MATERIAL_ITEMS.add(this);
        CommonLifecycleEvents.TAGS_LOADED.register(this::onTagsLoaded);
    }

    @Override
    public void setupMaterials(RegistryWrapper.Impl<Material> materialRegistry){
        addMaterials(materialRegistry.getOrThrow(defaultMaterials));
    }

    protected void onTagsLoaded(DynamicRegistryManager registryManager, boolean client){
        materials.clear();
        materials.add(registryManager.get(ArmoryRegistryKeys.MATERIAL).getEntry(Materials.NONE).get());

        Registry<Material> materialRegistry = registryManager.get(ArmoryRegistryKeys.MATERIAL);
        Optional<RegistryEntryList.Named<Material>> materialNamed = materialRegistry.getEntryList(defaultMaterials);
        materialNamed.ifPresent(this::addMaterials);

        defaultStacks.clear();
        for(RegistryEntry<Material> material : materials)
            defaultStacks.add(getStack(material));
    }

    protected void addMaterials(RegistryEntryList.Named<Material> materialNamed){
        for(RegistryEntry<Material> material : materialNamed)
            materials.add(material);
    }

    @Override
    public ItemStack getStack(RegistryEntry<Material> material) {
        ItemStack stack = getDefaultStack();
        setMaterial(stack, material);
        return stack;
    }

    @Override
    public Text getName(ItemStack stack) {
        MutableText materialText = Text.translatable(MaterialHelper.getTranslatableName(getMaterial(stack)));
        Text toolText = super.getName(stack);
        return materialText.append(toolText);
    }

    @Override
    public List<ItemStack> getDefaultStacks() {
        return defaultStacks;
    }

    @Override
    public List<RegistryEntry<Material>> getValidMaterials() {
        return materials;
    }

    @Override
    public int getBaseFuelTime() {
        return baseFuelTime;
    }

    public PartItem setBaseFuelTime(int baseFuelTime){
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
        String materialId = MaterialHelper.getTranslatableName(getMaterial(stack));
        MutableText materialText = Text.translatable(materialId).setStyle(Style.EMPTY.withColor(getMaterial(stack).value().color()));

        if(partNameIncluded){
            MutableText componentName = Text.translatable(getTranslationKey());
            materialText = componentName.append(": ").append(materialText);
        }

        tooltip.add(materialText);
    }
}
