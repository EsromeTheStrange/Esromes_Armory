package net.esromethestrange.esromes_armory.data.material;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.esromethestrange.esromes_armory.registry.ArmoryRegistries;
import net.esromethestrange.esromes_armory.registry.ArmoryRegistryKeys;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryFixedCodec;
import net.minecraft.util.Identifier;

public record Material(int color,
                       float durability,
                       int miningLevel, float miningSpeed,
                       int attackDamage,float attackSpeed,
                       int enchantability, int fuelTimeMultiplier) {

    public static Codec<Material> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("color").forGetter(mat -> mat.color),
            Codec.FLOAT.fieldOf("durability").forGetter(mat -> mat.durability),
            Codec.INT.fieldOf("miningLevel").forGetter(mat -> mat.miningLevel),
            Codec.FLOAT.fieldOf("miningSpeed").forGetter(mat -> mat.miningSpeed),
            Codec.INT.fieldOf("attackDamage").forGetter(mat -> mat.attackDamage),
            Codec.FLOAT.fieldOf("attackSpeed").forGetter(mat -> mat.attackSpeed),
            Codec.INT.fieldOf("enchantability").forGetter(mat -> mat.enchantability),
            Codec.INT.fieldOf("fuelTimeMultiplier").forGetter(mat -> mat.fuelTimeMultiplier)
    ).apply(instance, Material::new));
    public static PacketCodec<RegistryByteBuf, Material> PACKET_CODEC = PacketCodec.ofStatic(Material::writePacket, Material::readPacket);
    public static final Codec<RegistryEntry<Material>> ENTRY_CODEC = RegistryFixedCodec.of(ArmoryRegistryKeys.MATERIAL);
    public static final PacketCodec<RegistryByteBuf, RegistryEntry<Material>> ENTRY_PACKET_CODEC = PacketCodecs.registryEntry(ArmoryRegistryKeys.MATERIAL);

    public String getTranslatableName() {
        Identifier id = ArmoryRegistries.MATERIAL.getId(this);
        if (id == null)
            return "material.invalid";
        return id.getNamespace() + ".material." + id.getPath();
    }

    public static void writePacket(RegistryByteBuf buf, Material material){
        buf.writeInt(material.color);

        buf.writeFloat(material.durability);

        buf.writeInt(material.miningLevel);
        buf.writeFloat(material.miningSpeed);

        buf.writeInt(material.attackDamage);
        buf.writeFloat(material.attackSpeed);

        buf.writeInt(material.enchantability);
        buf.writeInt(material.fuelTimeMultiplier);
    }

    public static Material readPacket(RegistryByteBuf buf){
        int color = buf.readInt();

        float durability = buf.readFloat();

        int miningLevel = buf.readInt();
        float miningSpeed = buf.readFloat();

        int attackDamage = buf.readInt();
        float attackSpeed = buf.readFloat();

        int enchantability = buf.readInt();
        int fuelTimeMultiplier = buf.readInt();

        return new Material(color,
                durability,
                miningLevel, miningSpeed,
                attackDamage, attackSpeed,
                enchantability, fuelTimeMultiplier);
    }
}
