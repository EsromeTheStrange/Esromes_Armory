package net.esromethestrange.esromes_armory.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.data.component.ArmoryComponents;
import net.esromethestrange.esromes_armory.data.component.HeatComponent;
import net.esromethestrange.esromes_armory.data.heat.HeatLevel;
import net.esromethestrange.esromes_armory.data.material.Material;
import net.esromethestrange.esromes_armory.data.material.Materials;
import net.esromethestrange.esromes_armory.recipe.ingredient.MaterialIngredient;
import net.esromethestrange.esromes_armory.item.material.MaterialItem;
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

public class AnvilRecipe implements Recipe<AnvilRecipe.AnvilRecipeInput> {
    public static final Identifier ID = Identifier.of(EsromesArmory.MOD_ID, "anvil");
    public static final int NUM_INPUTS = 2;

    final ItemStack result;
    final HeatLevel requiredHeat;
    final DefaultedList<Ingredient> inputs = DefaultedList.ofSize(NUM_INPUTS, Ingredient.EMPTY);

    public AnvilRecipe(List<Ingredient> ingredients, String requiredHeat, ItemStack result) {
        this.result = result;
        this.requiredHeat = HeatLevel.tryParse(requiredHeat);

        for (int i = 0; i < ingredients.size(); i++) {
            if (i >= NUM_INPUTS) {
                EsromesArmory.LOGGER.error("Too many ingredients provided to anvil recipe!");
                break;
            }
            inputs.set(i, ingredients.get(i));
        }
    }

    @Override
    public boolean matches(AnvilRecipe.AnvilRecipeInput inventory, World world) {
        if (world.isClient()) return false;

        Material currentMaterial = null;
        for (int i = 0; i < inputs.size(); i++){
            if(inputs.get(i) == Ingredient.EMPTY)
                continue;

            HeatComponent stackHeat = inventory.getStackInSlot(i).get(ArmoryComponents.HEAT);
            if(stackHeat == null || stackHeat.getTemperature() < requiredHeat.temperature)
                return false;
            if (!inputs.get(i).test(inventory.getStackInSlot(i)))
                return false;

            Material inputMaterial = getMaterialForInput(inventory, i);
            if(inputMaterial == null)
                continue;
            if(currentMaterial == null)
                currentMaterial = inputMaterial;
            if(inputMaterial != currentMaterial)
                return false;
        }

        return true;
    }

    @Override
    public ItemStack craft(AnvilRecipe.AnvilRecipeInput inventory, RegistryWrapper.WrapperLookup lookup) {
        ItemStack craftOutput = result.copy();
        if (!(craftOutput.getItem() instanceof MaterialItem materialItem))
            return craftOutput;

        Material outputMaterial = Materials.NONE;
        for(int i=0; i<inputs.size(); i++){
            Material inputMaterial = getMaterialForInput(inventory, i);
            if(inputMaterial == null)
                continue;
            outputMaterial = inputMaterial;
            break;
        }

        materialItem.setMaterial(craftOutput, outputMaterial);
        return craftOutput;
    }

    private Material getMaterialForInput(AnvilRecipe.AnvilRecipeInput inventory, int index){
        if(inputs.get(index).getCustomIngredient() instanceof MaterialIngredient materialIngredient)
            return materialIngredient.getMaterial(inventory.getStackInSlot(index));
        ItemStack inputStack = inventory.getStackInSlot(index);
        if(inputStack.getItem() instanceof MaterialItem materialItem)
            return materialItem.getMaterial(inputStack);
        return null;
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        return inputs;
    }

    @Override
    public RecipeType<?> getType() {
        return ArmoryRecipes.ANVIL_RECIPE_TYPE;
    }

    @Override
    public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {
        return result;
    }

    public HeatLevel getRequiredHeatLevel(){ return requiredHeat; }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    public static AnvilRecipe.AnvilRecipeInput inputFrom(DefaultedList<ItemStack> inventory) {
        return new AnvilRecipe.AnvilRecipeInput(inventory);
    }

    public static class Serializer implements RecipeSerializer<AnvilRecipe> {
        public static final AnvilRecipe.Serializer INSTANCE = new AnvilRecipe.Serializer();

        MapCodec<AnvilRecipe> ANVIL_RECIPE_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Ingredient.DISALLOW_EMPTY_CODEC.listOf().fieldOf("ingredients").forGetter(AnvilRecipe::getIngredients),
                Codec.STRING.optionalFieldOf("required_heat", HeatLevel.ROOM_TEMPERATURE.toString().toLowerCase()).forGetter(recipe -> recipe.requiredHeat.toString().toLowerCase()),
                ItemStack.VALIDATED_CODEC.fieldOf("result").forGetter(recipe -> recipe.result)
        ).apply(instance, AnvilRecipe::new));
        public static final PacketCodec<RegistryByteBuf, AnvilRecipe> PACKET_CODEC = PacketCodec.ofStatic(AnvilRecipe.Serializer::write, AnvilRecipe.Serializer::read);

        @Override
        public MapCodec<AnvilRecipe> codec() {
            return ANVIL_RECIPE_CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, AnvilRecipe> packetCodec() {
            return PACKET_CODEC;
        }

        private static AnvilRecipe read(RegistryByteBuf buf) {
            List<Ingredient> ingredients = new ArrayList<>();
            for (int i = 0; i < NUM_INPUTS; i++) {
                ingredients.add(Ingredient.PACKET_CODEC.decode(buf));
            }
            String requiredHeatLevel = buf.readString();
            ItemStack result = ItemStack.PACKET_CODEC.decode(buf);
            return new AnvilRecipe(ingredients, requiredHeatLevel, result);
        }

        private static void write(RegistryByteBuf buf, AnvilRecipe recipe) {
            for (Ingredient ingredient : recipe.getIngredients()) {
                Ingredient.PACKET_CODEC.encode(buf, ingredient);
            }
            buf.writeString(recipe.requiredHeat.toString().toLowerCase());
            ItemStack.PACKET_CODEC.encode(buf, recipe.result);
        }

        @Override
        public String toString() {
            return ID.getPath();
        }
    }

    public static class AnvilRecipeInput implements RecipeInput {
        private final List<ItemStack> stacks;

        protected AnvilRecipeInput(DefaultedList<ItemStack> inventory) {
            this.stacks = inventory;
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
