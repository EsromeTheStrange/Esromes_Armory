package net.esromethestrange.esromes_armory.item.material;

import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.data.ArmoryMaterial;
import net.esromethestrange.esromes_armory.data.MaterialHandler;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
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
    public ArmoryMaterial getMaterial(ItemStack stack){
        NbtCompound nbt = stack.getNbt();
        if(nbt != null)
            return MaterialHandler.getMaterial(Identifier.tryParse(nbt.getString(NBT_MATERIAL)));
        return ArmoryMaterial.NONE;
    }

    @Override
    public void setMaterial(ItemStack stack, ArmoryMaterial material){
        NbtCompound nbt = stack.getNbt();
        if(nbt == null) nbt = new NbtCompound();
        nbt.putString(NBT_MATERIAL, material.id.toString());
        stack.setNbt(nbt);
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
}
