package net.esromethestrange.esromes_armory.data;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import java.util.HashMap;

public class ArmoryMaterial {
    public static final ArmoryMaterial NONE = new ArmoryMaterial(
            "esromes_armory", "none", 0xffffff,
            10, 0, 1,
            0, 0, 1);

    public final String translatable_name;
    public final Identifier id;
    public final String modId;
    public final String materialName;
    public final int color;

    public final int durability;
    public final int miningLevel;
    public final float miningSpeed;

    public final int attackDamage;
    public final float attackSpeed;
    public final int enchantability;

    private final HashMap<String, Item> items = new HashMap<>();

    public ArmoryMaterial(String modId, String materialName, int color, int durability, int miningLevel, float miningSpeed, int attackDamage, float attackSpeed, int enchantability) {
        this.translatable_name = modId + ".material." + materialName;
        this.modId = modId;
        this.materialName = materialName;
        this.id = new Identifier(modId, materialName);
        this.color = color;

        this.durability = durability;
        this.miningLevel = miningLevel;
        this.miningSpeed = miningSpeed;

        this.attackDamage = attackDamage;
        this.attackSpeed = attackSpeed;
        this.enchantability = enchantability;
    }

    public void addItem(String itemType, Item item){
        items.put(itemType, item);
    }

    public Item getItem(String itemType){
        if(items.containsKey(itemType)) return items.get(itemType);
        return Items.AIR;
    }
}
