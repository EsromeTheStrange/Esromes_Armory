package net.esromethestrange.esromes_armory.data.material;

import net.esromethestrange.esromes_armory.data.ArmoryRegistries;
import net.minecraft.util.Identifier;

public record Material(int color,
                       float durability,
                       int miningLevel, float miningSpeed,
                       int attackDamage,float attackSpeed,
                       int enchantability, int fuelTimeMultiplier) {

    public String getTranslatableName() {
        Identifier id = ArmoryRegistries.MATERIAL.getId(this);
        if (id == null)
            return "material.invalid";
        return id.getNamespace() + ".material." + id.getPath();
    }
}
