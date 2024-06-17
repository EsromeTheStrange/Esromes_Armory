package net.esromethestrange.esromes_armory.item.material;

import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.data.ArmoryMaterial;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ComponentItem extends Item implements MaterialItem {
    public static final String NBT_MATERIAL = EsromesArmory.MOD_ID + ".material";

    public ComponentItem(Settings settings) {
        super(settings);
    }

    @Override
    public Text getName(ItemStack stack) {
        ArmoryMaterial material = getMaterial(stack);
        String key = "item." + material.modId + "." +
                material.materialName + "_" +
                Registries.ITEM.getId(stack.getItem()).getPath();
        return Text.translatable(key);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);

        String materialId = getMaterial(stack).translatable_name;
        Text materialText = Text.translatable(materialId);
        tooltip.addAll(materialText.getWithStyle(Style.EMPTY.withColor(getMaterial(stack).color)));

        if(EsromesArmory.CONFIG.developerMode()){
            if (stack.getNbt() != null) {
                Text debugText = Text.literal("Material Id: "+stack.getNbt().getString(NBT_MATERIAL));
                tooltip.addAll(debugText.getWithStyle(Style.EMPTY.withColor(0xff00ff)));
            }
        }
    }

    @Override
    public Identifier getIdentifier() {
        return Registries.ITEM.getId(this);
    }
}
