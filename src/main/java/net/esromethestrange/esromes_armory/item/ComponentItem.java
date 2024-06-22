package net.esromethestrange.esromes_armory.item;

import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.data.ArmoryMaterial;
import net.esromethestrange.esromes_armory.data.ArmoryMaterialHandler;
import net.esromethestrange.esromes_armory.data.MaterialTypes;
import net.esromethestrange.esromes_armory.item.material.MaterialItem;
import net.esromethestrange.esromes_armory.util.MaterialHelper;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ComponentItem extends Item implements MaterialItem {
    public static final String NBT_MATERIAL = EsromesArmory.MOD_ID + ".material";
    public Identifier[] defaultMaterials;

    public ComponentItem(Settings settings, Identifier[] defaultMaterials) {
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
    public List<ItemStack> getDefaultStacks() {
        List<ItemStack> defaultStacks = new ArrayList<>();
        for(Identifier material : defaultMaterials){
            defaultStacks.add(getStack(ArmoryMaterialHandler.getMaterial(material)));
        }
        return defaultStacks;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);

        if(EsromesArmory.CONFIG.materialTooltips())
            addMaterialTooltip(stack, tooltip);
    }

    @Override
    public void addMaterialTooltip(ItemStack stack, List<Text> tooltip, boolean componentNameIncluded) {
        String materialId = getMaterial(stack).translatable_name;
        MutableText materialText = Text.translatable(materialId).setStyle(Style.EMPTY.withColor(getMaterial(stack).color));

        if(componentNameIncluded){
            MutableText componentName = Text.translatable(getTranslationKey());
            materialText = componentName.append(": ").append(materialText);
        }

        tooltip.add(materialText);
    }
}
