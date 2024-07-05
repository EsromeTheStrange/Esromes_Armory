package net.esromethestrange.esromes_armory.data.material;

import net.minecraft.util.Identifier;

public class ArmoryMaterial {
    public final String translatable_name;
    public final Identifier id;
    public final String modId;
    public final String materialName;
    public final int color;

    public final float durability;
    public final int miningLevel;
    public final float miningSpeed;

    public final int attackDamage;
    public final float attackSpeed;
    public final int enchantability;

    public ArmoryMaterial(String modId, String materialName, int color, float durability, int miningLevel, float miningSpeed, int attackDamage, float attackSpeed, int enchantability) {
        this.translatable_name = modId + ".material." + materialName;
        this.modId = modId;
        this.materialName = materialName;
        this.id = Identifier.of(modId, materialName);
        this.color = color;

        this.durability = durability;
        this.miningLevel = miningLevel;
        this.miningSpeed = miningSpeed;

        this.attackDamage = attackDamage;
        this.attackSpeed = attackSpeed;
        this.enchantability = enchantability;

        ArmoryMaterials.addMaterial(this);
    }
}
