package net.esromethestrange.esromes_armory.data;

import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

import java.util.HashMap;

public class ArmoryMaterialInfo{
    public final Identifier id;
    public final int color;

    public final int durability;
    public final int miningLevel;
    public final float miningSpeed;

    public final int attackDamage;
    public final float attackSpeed;
    public final int enchantability;

    public HashMap<String, Item> items = new HashMap<>();

    public ArmoryMaterialInfo(Identifier id, int color, int durability, int miningLevel, float miningSpeed, int attackDamage, float attackSpeed, int enchantability) {
        this.id = id;
        this.color = color;

        this.durability = durability;
        this.miningLevel = miningLevel;
        this.miningSpeed = miningSpeed;

        this.attackDamage = attackDamage;
        this.attackSpeed = attackSpeed;
        this.enchantability = enchantability;
    }

    public ArmoryMaterialInfo addItem(String key, Item item){
        items.put(key, item);
        return this;
    }
}