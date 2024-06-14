package net.esromethestrange.esromes_armory.data;

public class ArmoryMaterial {
    public static final ArmoryMaterial NONE = new ArmoryMaterial("esromes_armory", "none",
            10, 0, 1, 0, 0, 1);

    public final String id;
    public final String modId;
    public final String materialName;

    public final int durability;
    public final int miningLevel;
    public final float miningSpeed;
    public final int attackDamage;
    public final float attackSpeed;
    public final int enchantability;

    public ArmoryMaterial(String modId, String materialName, int durability, int miningLevel, float miningSpeed, int attackDamage, float attackSpeed, int enchantability) {
        this.id = modId + "." + materialName;
        this.modId = modId;
        this.durability = durability;
        this.materialName = materialName;
        this.miningLevel = miningLevel;
        this.miningSpeed = miningSpeed;
        this.attackDamage = attackDamage;
        this.attackSpeed = attackSpeed;
        this.enchantability = enchantability;
    }
}
