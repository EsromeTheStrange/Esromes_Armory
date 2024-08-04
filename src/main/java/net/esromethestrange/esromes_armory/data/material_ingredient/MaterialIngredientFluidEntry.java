package net.esromethestrange.esromes_armory.data.material_ingredient;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.esromethestrange.esromes_armory.data.material.Material;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.fluid.Fluid;

public class MaterialIngredientFluidEntry extends MaterialIngredientEntry<Fluid>{
    public final FluidVariant fluid;

    public MaterialIngredientFluidEntry(Material material, FluidVariant fluid){
        this.material = material;
        this.fluid = fluid;
    }
    public MaterialIngredientFluidEntry(Material material, Fluid fluid){
        this(material, FluidVariant.of(fluid));
    }

    @Override
    public boolean test(Object o, long requiredAmount) {
        if(!(o instanceof FluidTarget fluidTarget))
            return false;
        return fluid.equals(fluidTarget.variant) && fluidTarget.amount >= requiredAmount;
    }

    @Override
    public boolean hasObject(Object o) {
        if(!(o instanceof FluidVariant fluidVariant))
            return false;
        return fluid.equals(fluidVariant);
    }

    @Override
    public MaterialIngredientEntrySerializer<? extends MaterialIngredientEntry<Fluid>> getSerializer() {
        return Serializer.INSTANCE;
    }

    public static class Serializer extends MaterialIngredientEntrySerializer<MaterialIngredientFluidEntry>{
        public static Serializer INSTANCE = new Serializer();

        public static final MapCodec<MaterialIngredientFluidEntry> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Material.CODEC.fieldOf("material").forGetter(entry -> entry.material), //TODO fix this
                FluidVariant.CODEC.fieldOf("fluid").forGetter(entry -> entry.fluid)
        ).apply(instance, MaterialIngredientFluidEntry::new));

        @Override
        public MapCodec<MaterialIngredientFluidEntry> createCodec() {
            return CODEC;
        }
    }

    public static class FluidTarget{
        public final FluidVariant variant;
        public final long amount;

        public FluidTarget(FluidVariant variant, long amount){
            this.variant = variant;
            this.amount = amount;
        }

        public static FluidTarget of(FluidVariant variant, long amount){
            return new FluidTarget(variant, amount);
        }
    }
}
