package net.esromethestrange.esromes_armory.recipe.ingredient;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Optional;

public class FluidTester {
    public static final MapCodec<FluidTester> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Identifier.CODEC.optionalFieldOf("material_ingredient").forGetter(tester -> Optional.ofNullable(tester.id)),
            FluidVariant.CODEC.optionalFieldOf("fluid_variant").forGetter(tester -> Optional.of(tester.matchingFluid))
    ).apply(instance, FluidTester::new));

    public static final PacketCodec<RegistryByteBuf, FluidTester> PACKET_CODEC = PacketCodec.of(
            (value, buf) -> {
                buf.writeBoolean(value.singleTarget);
                if(value.singleTarget)
                    FluidVariant.PACKET_CODEC.encode(buf, value.matchingFluid);
                else
                    buf.writeIdentifier(value.id);
            },
            buf -> {
                if(buf.readBoolean())
                    return new FluidTester(FluidVariant.PACKET_CODEC.decode(buf));
                return new FluidTester(buf.readIdentifier());
            }
    );

    private final boolean singleTarget;
    private final FluidVariant matchingFluid;
    private final Identifier id;

    public FluidTester(FluidVariant fluid){
        this.id = null;
        this.matchingFluid = fluid;
        this.singleTarget = true;
    }

    public FluidTester(Identifier id){
        this.id = id;
        this.matchingFluid = null;
        this.singleTarget = false;
    }

    public FluidTester(Optional<Identifier> id, Optional<FluidVariant> fluid){
        this.id = id.orElse(null);
        this.matchingFluid = fluid.orElse(null);
        this.singleTarget = fluid.isPresent();
    }

    public boolean matches(FluidVariant fluid){
        if(singleTarget)
            return matchingFluid.getFluid().equals(fluid);
        return ArmoryIngredients.getMaterialIngredient(id).isValid(fluid);
    }

    public List<FluidVariant> getFluids(){
        if(singleTarget)
            return List.of(matchingFluid);
        return ArmoryIngredients.getMaterialIngredient(id).getFluids();
    }
}
