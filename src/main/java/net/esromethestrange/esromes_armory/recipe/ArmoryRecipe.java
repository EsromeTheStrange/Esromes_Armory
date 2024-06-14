package net.esromethestrange.esromes_armory.recipe;

import com.google.gson.JsonObject;
import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.item.tools.ArmoryMiningToolItem;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class ArmoryRecipe implements CraftingRecipe {
    public static final Identifier ID = new Identifier(EsromesArmory.MOD_ID, "armory_recipe");
    private final ItemStack output;
    private final Ingredient input;

    public ArmoryRecipe(Ingredient input, ItemStack output){
        this.output = output;
        this.input = input;
    }

    @Override public Identifier getId() { return ID; }

    @Override
    public boolean matches(RecipeInputInventory recipeInputInventory, World world) {
        return input.test(recipeInputInventory.getStack(1));
    }

    @Override
    public ItemStack craft(RecipeInputInventory inventory, DynamicRegistryManager registryManager) {
        return this.getOutput(registryManager).copy();
    }

    @Override public boolean fits(int width, int height) { return width >= 3 && height >= 3; }
    @Override public ItemStack getOutput(DynamicRegistryManager registryManager) { return output; }
    @Override
    public DefaultedList<Ingredient> getIngredients(){
        DefaultedList<Ingredient> list = DefaultedList.of();
        list.add(input);
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
        ItemStack stack = ShapedRecipe.outputFromJson(json);
        NbtCompound nbt = new NbtCompound();
        nbt.putString(ArmoryMiningToolItem.NBTKEY_MATERIAL, json.get("material").getAsString());
        stack.setNbt(nbt);
        return stack;
    }

    public static class Serializer implements RecipeSerializer<ArmoryRecipe> {
        public static final ArmoryRecipe.Serializer INSTANCE = new ArmoryRecipe.Serializer();

        @Override
        public ArmoryRecipe read(Identifier id, JsonObject json) {
            Ingredient input = Ingredient.fromJson(json.get("input"));
            ItemStack output = ArmoryRecipe.outputFromJson(json.getAsJsonObject("output"));
            return new ArmoryRecipe(input, output);
        }

        @Override
        public ArmoryRecipe read(Identifier id, PacketByteBuf buf) {
            Ingredient input = Ingredient.fromPacket(buf);
            ItemStack output = buf.readItemStack();
            return new ArmoryRecipe(input, output);
        }

        @Override
        public void write(PacketByteBuf buf, ArmoryRecipe recipe) {
            recipe.input.write(buf);
            buf.writeItemStack(recipe.output);
        }

        @Override public String toString() { return ID.getPath(); }
    }
}
