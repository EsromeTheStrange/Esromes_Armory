package net.esromethestrange.esromes_armory.data;

public class ArmoryMaterial {
    public final String id;
    public final String modId;
    public final String materialName;
    public final int miningLevel;

    public ArmoryMaterial(String modId, String materialName, int miningLevel) {
        this.id = modId + "." + materialName;
        this.modId = modId;
        this.materialName = materialName;
        this.miningLevel = miningLevel;
    }
}
