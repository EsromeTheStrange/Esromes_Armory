package net.esromethestrange.esromes_armory.recipe.ingredient;

import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.data.material.Material;
import net.esromethestrange.esromes_armory.data.material.Materials;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MaterialIngredientData {
    public static MaterialIngredientData EMPTY = new MaterialIngredientData(Identifier.of(EsromesArmory.MOD_ID, "empty"));

    public final Identifier id;
    public final HashMap<Identifier, List<Item>> validItems = new HashMap<>();
    public final List<ItemStack> matchingStacks = new ArrayList<>();

    public MaterialIngredientData(Identifier id){
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

    public Material getMaterial(ItemStack stack){
        if(!isValid(stack))
            return Materials.NONE;

        Item item = stack.getItem();
        for(Identifier material : validItems.keySet())
            if(validItems.get(material).contains(item))
                return Materials.getMaterial(material);

        return Materials.NONE;
    }
}
