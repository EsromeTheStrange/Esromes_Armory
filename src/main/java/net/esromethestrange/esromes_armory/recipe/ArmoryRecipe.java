package net.esromethestrange.esromes_armory.recipe;

import com.google.gson.JsonObject;
import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.item.tools.ArmoryMiningToolItem;
import net.esromethestrange.esromes_armory.recipe.ingredient.MaterialIngredient;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredient;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class ArmoryRecipe implements CraftingRecipe {
    public static final Identifier ID = new Identifier(EsromesArmory.MOD_ID, "armory_recipe");

    private final Identifier id;
    private final ItemStack output;
    private final Ingredient input;

    public ArmoryRecipe(Identifier id, Ingredient input, ItemStack output){
        this.id = id;
        this.output = output;
        this.input = input;
    }

    @Override public Identifier getId() { return id; }

    @Override
    public boolean matches(RecipeInputInventory recipeInputInventory, World world) {
        return  input.test(recipeInputInventory.getStack(0)) &&
                input.test(recipeInputInventory.getStack(1)) &&
                input.test(recipeInputInventory.getStack(2));
    }

    @Override
    public ItemStack craft(RecipeInputInventory inventory, DynamicRegistryManager registryManager) {
        ItemStack craftOutput = this.getOutput(registryManager).copy();
        CustomIngredient customIngredient = input.getCustomIngredient();
        if(!(customIngredient instanceof MaterialIngredient))
            return craftOutput;

        String material = ((MaterialIngredient)customIngredient).getMaterialName(inventory.getStack(0));
        NbtCompound nbt = new NbtCompound();
        nbt.putString(ArmoryMiningToolItem.NBTKEY_MATERIAL, material);
        craftOutput.setNbt(nbt);
        return craftOutput;
    }


    @Override public boolean fits(int width, int height) { return width >= 3 && height >= 3; }
    @Override public ItemStack getOutput(DynamicRegistryManager registryManager){ return output; }
    @Override
    public DefaultedList<Ingredient> getIngredients(){
        DefaultedList<Ingredient> list = DefaultedList.of();
        //TODO list.add(input);
        return list;
    }


    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public CraftingRecipeCategory getCategory() {
        return CraftingRecipeCategory.EQUIPMENT;
    }

    public static ItemStack outputFromJson(JsonObject json){
        return ShapedRecipe.outputFromJson(json);
    }

    public static class Serializer implements RecipeSerializer<ArmoryRecipe> {
        public static final ArmoryRecipe.Serializer INSTANCE = new ArmoryRecipe.Serializer();

        @Override
        public ArmoryRecipe read(Identifier id, JsonObject json) {
            Ingredient input = Ingredient.fromJson(json.get("input"));
            ItemStack output = ArmoryRecipe.outputFromJson(json.getAsJsonObject("output"));
            return new ArmoryRecipe(id, input, output);
        }

        @Override
        public ArmoryRecipe read(Identifier id, PacketByteBuf buf) {
            MaterialIngredient input = MaterialIngredient.Serializer.INSTANCE.read(buf);
            ItemStack output = buf.readItemStack();
            return new ArmoryRecipe(id, input.toVanilla(), output);
        }

        @Override
        public void write(PacketByteBuf buf, ArmoryRecipe recipe) {
            recipe.input.write(buf);
            buf.writeItemStack(recipe.output);
        }

        @Override public String toString() { return ID.getPath(); }
    }
}
