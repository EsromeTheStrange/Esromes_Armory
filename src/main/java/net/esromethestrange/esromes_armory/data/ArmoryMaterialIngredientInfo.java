package net.esromethestrange.esromes_armory.data;

import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.recipe.ingredient.MaterialIngredient;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ArmoryMaterialIngredientInfo {
    public static final ArmoryMaterialIngredientInfo NONE = new ArmoryMaterialIngredientInfo(new Identifier(EsromesArmory.MOD_ID, "none"));

    public final Identifier id;
    public final HashMap<Identifier, List<Item>> validItems = new HashMap<>();
    public final List<ItemStack> matchingStacks = new ArrayList<>();

    public ArmoryMaterialIngredientInfo(Identifier id){
        this.id = id;
    }

    public boolean isValid(ItemStack stack){
        for(Identifier material : validItems.keySet())
            if(validItems.get(material).contains(stack.getItem()))
                return true;
        return false;
    }

    public void addEntry(Identifier material, Item item){
        if(!validItems.containsKey(material))
            validItems.put(material, new ArrayList<>());
        validItems.get(material).add(item);
        matchingStacks.add(item.getDefaultStack());
    }

    public ArmoryMaterial getMaterial(ItemStack stack){
        if(!isValid(stack))
            return ArmoryMaterial.NONE;

        Item item = stack.getItem();
        for(Identifier material : validItems.keySet())
            if(validItems.get(material).contains(item))
                return ArmoryMaterialHandler.getMaterial(material);

        return ArmoryMaterial.NONE;
    }
}
