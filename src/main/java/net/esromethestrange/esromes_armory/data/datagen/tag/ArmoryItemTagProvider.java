package net.esromethestrange.esromes_armory.data.datagen.tag;

import net.esromethestrange.esromes_armory.fluid.ArmoryFluids;
import net.esromethestrange.esromes_armory.item.ArmoryItems;
import net.esromethestrange.esromes_armory.util.ArmoryTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ArmoryItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public ArmoryItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(ConventionalItemTags.INGOTS).add(ArmoryItems.STEEL_INGOT);
        getOrCreateTagBuilder(ConventionalItemTags.TOOLS).add(
                ArmoryItems.HOE,
                ArmoryItems.AXE,
                ArmoryItems.PICKAXE,
                ArmoryItems.SHOVEL,
                ArmoryItems.SWORD
        );
        getOrCreateTagBuilder(ConventionalItemTags.MELEE_WEAPON_TOOLS).add(
                ArmoryItems.AXE,
                ArmoryItems.SWORD
        );
        getOrCreateTagBuilder(ConventionalItemTags.MINING_TOOL_TOOLS).add(
                ArmoryItems.AXE,
                ArmoryItems.PICKAXE,
                ArmoryItems.SHOVEL
        );

        getOrCreateTagBuilder(ArmoryTags.Items.BUCKETS).add(
                ArmoryFluids.MOLTEN_COPPER_BUCKET,
                ArmoryFluids.MOLTEN_GOLD_BUCKET,
                ArmoryFluids.MOLTEN_IRON_BUCKET,
                ArmoryFluids.MOLTEN_NETHERITE_BUCKET,
                ArmoryFluids.MOLTEN_STEEL_BUCKET
        );
        getOrCreateTagBuilder(ConventionalItemTags.BUCKETS).addTag(ArmoryTags.Items.BUCKETS);
        getOrCreateTagBuilder(ConventionalItemTags.HIDDEN_FROM_RECIPE_VIEWERS)
                .addTag(ArmoryTags.Items.BUCKETS);
    }
}
