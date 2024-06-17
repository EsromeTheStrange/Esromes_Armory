package net.esromethestrange.esromes_armory.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.data.ArmoryMaterial;
import net.esromethestrange.esromes_armory.item.material.ComponentBasedItem;
import net.esromethestrange.esromes_armory.item.material.MaterialItem;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class WorkbenchRecipe implements Recipe<SimpleInventory> {
    public static final Identifier ID = new Identifier(EsromesArmory.MOD_ID, "workbench");
    public static final int NUM_INPUTS = 3;

    private final Identifier id;
    private final ItemStack output;
    private final DefaultedList<Ingredient> inputs = DefaultedList.ofSize(NUM_INPUTS, Ingredient.EMPTY);

    public WorkbenchRecipe(Identifier id, List<Ingredient> ingredients, ItemStack output){
        this.id = id;
        this.output = output;

        for(int i=0; i<ingredients.size(); i++){
            if(i >= NUM_INPUTS){
                EsromesArmory.LOGGER.error("Too many ingredients provided to workbench recipe: "+id.toString()+"!");
                break;
            }
            inputs.set(i, ingredients.get(i));
        }
    }

    @Override
    public boolean matches(SimpleInventory inventory, World world) {
        if(world.isClient()) return false;

        for(int i=0; i<inputs.size(); i++)
            if (!inputs.get(i).test(inventory.getStack(i)))
                return false;

        return true;
    }

    @Override public ItemStack craft(SimpleInventory inventory, DynamicRegistryManager registryManager) {
        ItemStack craftOutput = output.copy();
        if (!(craftOutput.getItem() instanceof ComponentBasedItem componentBasedItem))
            return craftOutput;

        for(int i=0; i<NUM_INPUTS; i++){
            ItemStack stack = inventory.getStack(i);
            if(stack.isEmpty() || !(stack.getItem() instanceof MaterialItem materialItem))
                continue;

            ArmoryMaterial material = materialItem.getMaterial(stack);
            componentBasedItem.setMaterial(craftOutput, materialItem, material);
        }

        return craftOutput;
    }
    @Override public boolean fits(int width, int height) { return true; }
    @Override public ItemStack getOutput(DynamicRegistryManager registryManager) { return output; }
    @Override public DefaultedList<Ingredient> getIngredients(){ return inputs; }

    @Override public Identifier getId() { return id; }
    @Override public RecipeType<?> getType() { return ModRecipes.WORKBENCH_RECIPE_TYPE; }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return WorkbenchRecipe.Serializer.INSTANCE;
    }

    public static class Serializer implements RecipeSerializer<WorkbenchRecipe> {
        public static final Serializer INSTANCE = new Serializer();

        @Override
        public WorkbenchRecipe read(Identifier id, JsonObject json) {
            JsonArray inputArray = json.getAsJsonArray("inputs");
            List<Ingredient> inputs = new ArrayList<>();
            for(JsonElement input : inputArray)
                inputs.add(Ingredient.fromJson(input));

            ItemStack output = ShapedRecipe.outputFromJson(json.getAsJsonObject("output"));
            return new WorkbenchRecipe(id, inputs, output);
        }

        @Override
        public void write(PacketByteBuf buf, WorkbenchRecipe recipe) {
            buf.writeInt(recipe.inputs.size());
            for(int i=0; i<recipe.inputs.size(); i++)
                recipe.inputs.get(i).write(buf);

            buf.writeItemStack(recipe.output);
        }

        @Override
        public WorkbenchRecipe read(Identifier id, PacketByteBuf buf) {
            int numInputs = buf.readInt();
            List<Ingredient> inputs = new ArrayList<>();
            for(int i=0; i<numInputs; i++)
                inputs.add(Ingredient.fromPacket(buf));

            ItemStack output = buf.readItemStack();
            return new WorkbenchRecipe(id, inputs, output);
        }

        @Override public String toString() { return ID.getPath(); }
    }
}
