package net.esromethestrange.esromes_armory.client.model.override;

/**
 * @author Based on code from Chime by emilyploszaj
 */
public interface HeatStateModelOverride {
    void setHeatPredicate(HeatOverridePredicate predicate);
    HeatOverridePredicate getHeatPredicate();

    static boolean test(HeatOverridePredicate predicate){
        return predicate.test();
    }
}
