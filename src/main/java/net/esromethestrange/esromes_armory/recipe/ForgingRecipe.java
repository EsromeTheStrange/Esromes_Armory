package net.esromethestrange.esromes_armory.recipe;

import com.google.gson.JsonObject;
import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.block.entity.ForgeBlockEntity;
import net.esromethestrange.esromes_armory.material.ArmoryMaterial;
import net.esromethestrange.esromes_armory.item.material.MaterialItem;
import net.esromethestrange.esromes_armory.recipe.ingredient.MaterialIngredient;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class ForgingRecipe implements Recipe<SimpleInventory> {
    public static final Identifier ID = new Identifier(EsromesArmory.MOD_ID, "forging");
    private final Identifier id;
    private final ItemStack output;
    private final Ingredient input;

    public ForgingRecipe(Identifier id, Ingredient input, ItemStack output){
        this.id = id;
        this.output = output;
        this.input = input;
    }

    @Override
    public boolean matches(SimpleInventory inventory, World world) {
        if(world.isClient()) return false;
        return input.test(inventory.getStack(ForgeBlockEntity.INPUT_SLOT));
    }

    @Override public ItemStack craft(SimpleInventory inventory, DynamicRegistryManager registryManager) {
        ItemStack craftOutput = output.copy();
        if((input.getCustomIngredient() instanceof MaterialIngredient materialIngredient) &&
                (craftOutput.getItem() instanceof MaterialItem materialItem)){
            ArmoryMaterial outputMaterial = materialIngredient.getMaterial(inventory.getStack(ForgeBlockEntity.INPUT_SLOT));
            materialItem.setMaterial(craftOutput, outputMaterial);
        }
        return craftOutput;
    }
    @Override public boolean fits(int width, int height) { return true; }
    @Override public ItemStack getOutput(DynamicRegistryManager registryManager) { return output; }
    @Override
    public DefaultedList<Ingredient> getIngredients(){
        DefaultedList<Ingredient> list = DefaultedList.of();
        list.add(input);
        return list;
    }

    @Override public Identifier getId() { return id; }
    @Override public RecipeType<?> getType() { return ModRecipes.FORGE_RECIPE_TYPE; }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    public static class Serializer implements RecipeSerializer<ForgingRecipe> {
        public static final Serializer INSTANCE = new Serializer();

        @Override
        public ForgingRecipe read(Identifier id, JsonObject json) {
            Ingredient input = Ingredient.fromJson(json.get("input"));
            ItemStack output = ShapedRecipe.outputFromJson(json.getAsJsonObject("output"));
            return new ForgingRecipe(id, input, output);
        }

        @Override
        public ForgingRecipe read(Identifier id, PacketByteBuf buf) {
            Ingredient input = Ingredient.fromPacket(buf);
            ItemStack output = buf.readItemStack();
            return new ForgingRecipe(id, input, output);
        }

        @Override
        public void write(PacketByteBuf buf, ForgingRecipe recipe) {
            recipe.input.write(buf);
            buf.writeItemStack(recipe.output);
        }

        @Override public String toString() { return ID.getPath(); }
    }
}
