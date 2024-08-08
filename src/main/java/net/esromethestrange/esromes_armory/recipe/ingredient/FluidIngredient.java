package net.esromethestrange.esromes_armory.recipe.ingredient;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.data.material.Material;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredient;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredientSerializer;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Optional;

public record FluidIngredient(Optional<RegistryEntry<Material>> material, FluidVariant fluidVariant, long amount) implements CustomIngredient {
    public static final Identifier ID = Identifier.of(EsromesArmory.MOD_ID, "fluid");

    public boolean test(FluidVariant fluid, long amount) {
        return fluidVariant.equals(fluid) && amount >= this.amount;
    }

    @Override
    public boolean test(ItemStack stack) {
        return false;
    }

    @Override
    public List<ItemStack> getMatchingStacks() {
        return List.of();
    }

    @Override
    public boolean requiresTesting() {
        return false;
    }

    @Override
    public CustomIngredientSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    public static class Serializer implements CustomIngredientSerializer<FluidIngredient> {
        public static Serializer INSTANCE = new Serializer();

        public static final MapCodec<FluidIngredient> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Material.ENTRY_CODEC.optionalFieldOf("material").forGetter(ing -> ing.material),
                FluidVariant.CODEC.fieldOf("fluid").forGetter(ing -> ing.fluidVariant),
                Codec.LONG.fieldOf("amount").forGetter(ing -> ing.amount)
        ).apply(instance, FluidIngredient::new));
        public static final PacketCodec<RegistryByteBuf, FluidIngredient> PACKET_CODEC = PacketCodec.ofStatic(Serializer::write, Serializer::read);

        public static void write(RegistryByteBuf buf, FluidIngredient fluidIngredient) {
            buf.writeBoolean(fluidIngredient.material.isPresent());
            fluidIngredient.material.ifPresent(value -> Material.ENTRY_PACKET_CODEC.encode(buf, value));
            FluidVariant.PACKET_CODEC.encode(buf, fluidIngredient.fluidVariant);
            buf.writeLong(fluidIngredient.amount);
        }

        public static FluidIngredient read(RegistryByteBuf buf) {
            boolean hasMaterial = buf.readBoolean();
            Optional<RegistryEntry<Material>> material = Optional.empty();
            if(hasMaterial)
                material = Optional.ofNullable(Material.ENTRY_PACKET_CODEC.decode(buf));
            FluidVariant fluidVariant = FluidVariant.PACKET_CODEC.decode(buf);
            long amount = buf.readLong();
            return new FluidIngredient(material, fluidVariant, amount);
        }

        @Override
        public Identifier getIdentifier() {
            return ID;
        }

        @Override
        public MapCodec<FluidIngredient> getCodec(boolean allowEmpty) {
            return CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, FluidIngredient> getPacketCodec() {
            return PACKET_CODEC;
        }
    }
}
