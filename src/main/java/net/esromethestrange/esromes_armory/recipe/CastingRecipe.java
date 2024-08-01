package net.esromethestrange.esromes_armory.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.item.material.MaterialItem;
import net.esromethestrange.esromes_armory.recipe.ingredient.FluidTester;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
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

public class CastingRecipe implements Recipe<CastingRecipe.CastingRecipeInput> {
    public static final Identifier ID = Identifier.of(EsromesArmory.MOD_ID, "casting");

    final ItemStack result;
    final Ingredient input;
    final FluidTester fluidTester;
    final long fluidAmount;

    public CastingRecipe(ItemStack result, Ingredient input, FluidTester fluidTester, long fluidAmount) {
        this.result = result;
        this.input = input;
        this.fluidTester = fluidTester;
        this.fluidAmount = fluidAmount;
    }

    @Override
    public boolean matches(CastingRecipeInput inventory, World world) {
        if (world.isClient()) return false;

        return input.test(inventory.getStackInSlot(0)) &&
                fluidTester.matches(inventory.fluidType) &&
                inventory.fluidAmount >= fluidAmount;
    }

    @Override
    public ItemStack craft(CastingRecipeInput inventory, RegistryWrapper.WrapperLookup lookup) {
        ItemStack craftOutput = result.copy();
        if (!(craftOutput.getItem() instanceof MaterialItem materialItem))
            return craftOutput;

        materialItem.setMaterial(craftOutput, fluidTester.getMaterial(inventory.fluidType));
        return craftOutput;
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        return DefaultedList.ofSize(1, input);
    }

    @Override
    public RecipeType<?> getType() {
        return ArmoryRecipes.CASTING_RECIPE_TYPE;
    }

    @Override
    public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {
        return result;
    }

    public Ingredient getInput(){ return input; }
    public ItemStack getOutput(){ return result; }
    public FluidTester getFluidTester(){ return fluidTester; }
    public long getFluidAmount(){ return fluidAmount; }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    public static CastingRecipeInput inputFrom(ItemStack stack, FluidVariant fluid, long fluidAmount) {
        return new CastingRecipeInput(stack, fluid, fluidAmount);
    }

    public static class Serializer implements RecipeSerializer<CastingRecipe> {
        public static final Serializer INSTANCE = new Serializer();

        MapCodec<CastingRecipe> CASTING_RECIPE_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                ItemStack.VALIDATED_CODEC.fieldOf("result").forGetter(recipe -> recipe.result),
                Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("ingredient").forGetter(recipe -> recipe.input),
                FluidTester.CODEC.fieldOf("fluid_tester").forGetter(recipe -> recipe.fluidTester),
                Codec.LONG.fieldOf("fluid_amount").forGetter(recipe -> recipe.fluidAmount)
        ).apply(instance, CastingRecipe::new));
        public static final PacketCodec<RegistryByteBuf, CastingRecipe> PACKET_CODEC = PacketCodec.ofStatic(Serializer::write, Serializer::read);

        @Override
        public MapCodec<CastingRecipe> codec() {
            return CASTING_RECIPE_CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, CastingRecipe> packetCodec() {
            return PACKET_CODEC;
        }

        private static CastingRecipe read(RegistryByteBuf buf) {
            ItemStack result = ItemStack.PACKET_CODEC.decode(buf);
            Ingredient ingredient = Ingredient.PACKET_CODEC.decode(buf);
            FluidTester fluidTester = FluidTester.PACKET_CODEC.decode(buf);
            long fluidAmount = buf.readLong();
            return new CastingRecipe(result, ingredient, fluidTester, fluidAmount);
        }

        private static void write(RegistryByteBuf buf, CastingRecipe recipe) {
            ItemStack.PACKET_CODEC.encode(buf, recipe.result);
            Ingredient.PACKET_CODEC.encode(buf, recipe.input);
            FluidTester.PACKET_CODEC.encode(buf, recipe.fluidTester);
            buf.writeLong(recipe.fluidAmount);
        }

        @Override
        public String toString() {
            return ID.getPath();
        }
    }

    public static class CastingRecipeInput implements RecipeInput {
        private final ItemStack stack;
        private final FluidVariant fluidType;
        private final long fluidAmount;

        protected CastingRecipeInput(ItemStack stack, FluidVariant fluidType, long fluidAmount) {
            this.stack = stack;
            this.fluidType = fluidType;
            this.fluidAmount = fluidAmount;
        }

        @Override public ItemStack getStackInSlot(int slot) {
            return slot == 0 ? stack : ItemStack.EMPTY;
        }
        @Override public int getSize() {
            return 1;
        }
    }
}
