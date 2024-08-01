package net.esromethestrange.esromes_armory.recipe.ingredient;

import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.data.ArmoryRegistries;
import net.esromethestrange.esromes_armory.data.material.Material;
import net.esromethestrange.esromes_armory.data.material.Materials;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.fluid.Fluid;
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
    public final HashMap<Identifier, List<Fluid>> validFluids = new HashMap<>();
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

    public boolean isValid(FluidVariant fluid){
        for(List<Fluid> fluidList : validFluids.values())
            for(Fluid validFluid : fluidList)
                if(fluid.getFluid().matchesType(validFluid))
                    return true;
        return false;
    }

    public void addEntry(Identifier material, Item item){
        if(!validItems.containsKey(material))
            validItems.put(material, new ArrayList<>());
        validItems.get(material).add(item);
        matchingStacks.add(item.getDefaultStack());
    }

    public void addEntry(Identifier material, Fluid fluid){
        if(!validFluids.containsKey(material))
            validFluids.put(material, new ArrayList<>());
        validFluids.get(material).add(fluid);
    }

    public Material getMaterial(ItemStack stack){
        if(!isValid(stack))
            return Materials.NONE;

        Item item = stack.getItem();
        for(Identifier material : validItems.keySet())
            if(validItems.get(material).contains(item))
                return ArmoryRegistries.MATERIAL.get(material);

        return Materials.NONE;
    }

    public Material getMaterial(FluidVariant fluid){
        if(!isValid(fluid))
            return Materials.NONE;

        for(Identifier material : validFluids.keySet())
            for(Fluid validFluid : validFluids.get(material))
                if(validFluid.matchesType(fluid.getFluid()))
                    return ArmoryRegistries.MATERIAL.get(material);

        return Materials.NONE;
    }

    public List<Material> getItemMaterials(){
        List<Material> materials = new ArrayList<>();
        for(Identifier material : validItems.keySet())
            materials.add(ArmoryRegistries.MATERIAL.get(material));
        return materials;
    }

    public List<FluidVariant> getFluids(){
        List<FluidVariant> fluids = new ArrayList<>();
        for(List<Fluid> fluidList : validFluids.values())
            for(Fluid fluid : fluidList)
                fluids.add(FluidVariant.of(fluid));
        return fluids;
    }
}
