package net.esromethestrange.esromes_armory.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.item.material.MaterialItem;
import net.esromethestrange.esromes_armory.item.material.PartBasedItem;
import net.esromethestrange.esromes_armory.data.material.Material;
import net.esromethestrange.esromes_armory.recipe.ingredient.MaterialIngredient;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.input.RecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class WorkbenchRecipe implements Recipe<WorkbenchRecipe.WorkbenchRecipeInput> {
    public static final Identifier ID = Identifier.of(EsromesArmory.MOD_ID, "workbench");
    public static final int NUM_INPUTS = 3;

    final ItemStack result;
    final DefaultedList<Ingredient> inputs = DefaultedList.ofSize(NUM_INPUTS, Ingredient.EMPTY);

    public WorkbenchRecipe(List<Ingredient> ingredients, ItemStack result){
        this.result = result;

        for(int i=0; i<ingredients.size(); i++){
            if(i >= NUM_INPUTS){
                EsromesArmory.LOGGER.error("Too many ingredients provided to workbench recipe!");
                break;
            }
            inputs.set(i, ingredients.get(i));
        }
    }

    @Override
    public boolean matches(WorkbenchRecipeInput inventory, World world) {
        if(world.isClient()) return false;

        for(int i=0; i<inputs.size(); i++){
            if(inputs.get(i) == Ingredient.EMPTY)
                continue;
            if (!inputs.get(i).test(inventory.getStackInSlot(i)))
                return false;
        }

        return true;
    }

    @Override
    public ItemStack craft(WorkbenchRecipeInput inventory, RegistryWrapper.WrapperLookup lookup) {
        ItemStack craftOutput = result.copy();

        if (craftOutput.getItem() instanceof PartBasedItem partBasedItem){
            for(int i=0; i<NUM_INPUTS; i++){
                ItemStack stack = inventory.getStackInSlot(i);
                if(stack.isEmpty() || !(stack.getItem() instanceof MaterialItem materialItem))
                    continue;

                Material material = materialItem.getMaterial(stack);
                partBasedItem.setMaterial(craftOutput, materialItem, material);
            }
        }
        else if(craftOutput.getItem() instanceof MaterialItem materialItem){
            for(int i=0; i<NUM_INPUTS; i++){
                ItemStack stack = inventory.getStackInSlot(i);
                if(stack.isEmpty())
                    continue;
                if(inputs.get(i).getCustomIngredient() instanceof MaterialIngredient materialIngredient){
                    materialItem.setMaterial(craftOutput, materialIngredient.getMaterial(stack));
                    break;
                }
                if(stack.getItem() instanceof MaterialItem materialItemInput){
                    materialItem.setMaterial(craftOutput, materialItemInput.getMaterial(stack));
                    break;
                }
            }
        }

        return craftOutput;
    }
    @Override public boolean fits(int width, int height) { return true; }
    @Override public DefaultedList<Ingredient> getIngredients(){ return inputs; }
    @Override public RecipeType<?> getType() { return ArmoryRecipes.WORKBENCH_RECIPE_TYPE; }

    @Override
    public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {
        return result;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return WorkbenchRecipe.Serializer.INSTANCE;
    }

    public static WorkbenchRecipeInput inputFrom(SimpleInventory inventory) { return new WorkbenchRecipeInput(inventory); }

    public static class Serializer implements RecipeSerializer<WorkbenchRecipe> {
        public static final Serializer INSTANCE = new Serializer();

        MapCodec<WorkbenchRecipe> WORKBENCH_RECIPE_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Ingredient.DISALLOW_EMPTY_CODEC.listOf().fieldOf("ingredients").forGetter(WorkbenchRecipe::getIngredients),
                ItemStack.VALIDATED_CODEC.fieldOf("result").forGetter(recipe -> recipe.result)
        ).apply(instance, WorkbenchRecipe::new));
        public static final PacketCodec<RegistryByteBuf, WorkbenchRecipe> PACKET_CODEC = PacketCodec.ofStatic(Serializer::write, Serializer::read);

        @Override public MapCodec<WorkbenchRecipe> codec() { return WORKBENCH_RECIPE_CODEC; }
        @Override public PacketCodec<RegistryByteBuf, WorkbenchRecipe> packetCodec() { return PACKET_CODEC; }

        private static WorkbenchRecipe read(RegistryByteBuf buf) {
            List<Ingredient> ingredients = new ArrayList<>();
            for(int i=0; i<NUM_INPUTS; i++){
                ingredients.add(Ingredient.PACKET_CODEC.decode(buf));
            }
            ItemStack result = ItemStack.PACKET_CODEC.decode(buf);
            return new WorkbenchRecipe(ingredients, result);
        }

        private static void write(RegistryByteBuf buf, WorkbenchRecipe recipe) {
            for(Ingredient ingredient : recipe.getIngredients()){
                Ingredient.PACKET_CODEC.encode(buf, ingredient);
            }
            ItemStack.PACKET_CODEC.encode(buf, recipe.result);
        }

        @Override public String toString() { return ID.getPath(); }
    }

    public static class WorkbenchRecipeInput implements RecipeInput {
        private final List<ItemStack> stacks;

        protected WorkbenchRecipeInput(SimpleInventory inventory){
            this.stacks = inventory.heldStacks;
        }

        @Override
        public ItemStack getStackInSlot(int slot) {
            return stacks.get(slot);
        }

        @Override
        public int getSize() {
            return NUM_INPUTS;
        }
    }
}