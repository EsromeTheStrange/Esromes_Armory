package net.esromethestrange.esromes_armory.data.heat;

import net.esromethestrange.esromes_armory.EsromesArmory;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.HashMap;

public class HeatData {
    public static HeatData EMPTY = new HeatData(Identifier.of(EsromesArmory.MOD_ID, "empty"));

    private HashMap<HeatLevel, HeatingResult> heatingResults = new HashMap();
    public final Identifier id;

    public HeatData(Identifier id){
        this.id = id;
    }

    public void addEntry(HeatLevel heatLevel, HeatingResult heatingResult){
        heatingResults.put(heatLevel, heatingResult);
    }

    public boolean matches(Item item){
        for(HeatingResult heatingResult : heatingResults.values()){
            try {
                if(heatingResult.type == ResultType.ITEM &&
                    heatingResult.getItemOutput() == item)
                    return true;
            } catch (ResultTypeException e) {
                //This will never happen.
                return false;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        if(heatingResults.isEmpty())
            return "\"" + id.toString() + "\": {}";

        StringBuilder output = new StringBuilder();
        for(HeatLevel heatLevel : heatingResults.keySet()){
            output.append(", ").append(heatLevel).append(": ").append(heatingResults.get(heatLevel));
        }
        output = new StringBuilder("{" + output.substring(2) + "}");
        return "\"" + id.toString() + "\": " + output.toString();
    }

    public static class HeatingResult{
        public final ResultType type;
        private final Item itemOutput;
        private final Fluid fluidOutput;

        public HeatingResult(Item item){
            this(ResultType.ITEM, item, null);
        }
        public HeatingResult(Fluid fluid){
            this(ResultType.FLUID, null, fluid);
        }

        public HeatingResult(ResultType resultType, Item item, Fluid fluid){
            this.type = resultType;
            this.itemOutput = item;
            this.fluidOutput = fluid;
        }

        public Item getItemOutput() throws ResultTypeException {
            checkOutput(ResultType.ITEM);
            return itemOutput;
        }

        public Fluid getFluidOutput() throws ResultTypeException {
            checkOutput(ResultType.FLUID);
            return fluidOutput;
        }

        public Identifier getOutputIdentifier(ResultType resultType){
            try{
                return switch (resultType){
                    case ResultType.FLUID -> Registries.FLUID.getId(getFluidOutput());
                    default -> Registries.ITEM.getId(getItemOutput());
                };
            } catch (ResultTypeException e){
                //This will never happen, it only exists to get rid of the unhandled exception error
                return Identifier.of("funny silly identifier");
            }
        }

        private void checkOutput(ResultType requestedType) throws ResultTypeException {
            if (type != requestedType)
                throw new ResultTypeException(requestedType, type);
        }

        @Override
        public String toString() {
            return type.toString() + "-" + getOutputIdentifier(type);
        }
    }

    public enum ResultType{
        ITEM,
        FLUID
    }

    public static class ResultTypeException extends Exception{
        public final ResultType requestedResultType;
        public final ResultType actualResultType;

        public ResultTypeException(ResultType requestedResultType, ResultType actualResultType) {
            super(requestedResultType + " output was requested on a HeatingResult with " + actualResultType + " output type!");
            EsromesArmory.LOGGER.error(this.toString());
            this.requestedResultType = requestedResultType;
            this.actualResultType = actualResultType;
        }
    }
}
