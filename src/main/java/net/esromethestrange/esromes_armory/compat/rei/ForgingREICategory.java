package net.esromethestrange.esromes_armory.compat.rei;

import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.esromethestrange.esromes_armory.EsromesArmory;
import net.esromethestrange.esromes_armory.block.ModBlocks;
import net.esromethestrange.esromes_armory.recipe.ForgingRecipe;
import net.esromethestrange.esromes_armory.screen.ForgeScreen;
import net.esromethestrange.esromes_armory.screen.ForgeScreenHandler;
import net.minecraft.text.Text;

import java.util.LinkedList;
import java.util.List;

public class ForgingREICategory implements DisplayCategory<BasicDisplay> {
    public static final CategoryIdentifier<ForgingREIDisplay> FORGING = CategoryIdentifier.of(EsromesArmory.MOD_ID, ForgingRecipe.ID);
    /** The height of the texture that will be rendered in REI. */
    private final int DISPLAY_HEIGHT = 90;

    @Override
    public CategoryIdentifier<? extends BasicDisplay> getCategoryIdentifier() {
        return FORGING;
    }

    @Override
    public Text getTitle() {
        return Text.translatable("rei."+EsromesArmory.MOD_ID+"."+ ForgingRecipe.ID);
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModBlocks.FORGE.asItem().getDefaultStack());
    }

    @Override
    public List<Widget> setupDisplay(BasicDisplay display, Rectangle bounds) {
        //TODO fix this when I change forging
        final Point startPoint = new Point(bounds.getCenterX() - 87, bounds.getCenterY() - 35);
        List<Widget> widgets = new LinkedList<>();

        widgets.add(Widgets.createTexturedWidget(ForgeScreen.TEXTURE, new Rectangle(startPoint.x, startPoint.y, 175, 82)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + ForgeScreenHandler.INPUT_POS[0], startPoint.y + ForgeScreenHandler.INPUT_POS[1]))
                .entries(display.getInputEntries().get(0)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + ForgeScreenHandler.OUTPUT_POS[0], startPoint.y + ForgeScreenHandler.OUTPUT_POS[1]))
                .markOutput().entries(display.getOutputEntries().get(0)));

        return widgets;
    }

    @Override
    public int getDisplayHeight() { return DISPLAY_HEIGHT; }
}
