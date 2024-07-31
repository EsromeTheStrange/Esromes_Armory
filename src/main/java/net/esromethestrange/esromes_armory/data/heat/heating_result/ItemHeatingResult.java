package net.esromethestrange.esromes_armory.data.heat.heating_result;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class ItemHeatingResult extends HeatingResult{
    public final Item item;

    public ItemHeatingResult(Identifier id){
        this(Registries.ITEM.get(id));
    }

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

    public Identifier itemId(){ return Registries.ITEM.getId(item); }

    @Override
    public HeatingResultSerializer<ItemHeatingResult> getSerializer() {
        return Serializer.INSTANCE;
    }

    public static class Serializer extends HeatingResultSerializer<ItemHeatingResult>{
        public static final Serializer INSTANCE = new Serializer();

        @Override
        public MapCodec<ItemHeatingResult> createCodec() {
            return RecordCodecBuilder.mapCodec(instance -> instance.group(
                    Identifier.CODEC.fieldOf("item").forGetter(ItemHeatingResult::itemId)
            ).apply(instance, ItemHeatingResult::new));
        }
    }
}
