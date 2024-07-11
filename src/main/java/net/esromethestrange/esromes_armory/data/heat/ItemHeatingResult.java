package net.esromethestrange.esromes_armory.data.heat;

import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;

public class ItemHeatingResult extends HeatingResult{
    public final Item item;

    public ItemHeatingResult(Item item){
        this.item = item;
    }

    @Override
    public boolean matches(Object o) {
        if(o instanceof Item compareItem)
            return item == compareItem;
        if(o instanceof ItemStack itemStack)
            return itemStack.getItem() == item;
        return false;
    }

    @Override
    public String toString() {
        return "item-" + Registries.ITEM.getId(item);
    }
}
