package github.cutiecat2804.farmhousefurniture.enums;

import net.minecraft.util.StringRepresentable;

public enum PlateColors implements StringRepresentable {
    WHITE("white"),
    GREEN("green"),
    BLUE("blue"),
    YELLOW("yellow"),
    BEIGE("beige");

    private String stringRepresentation;

    PlateColors(String stringRepresentation) {
        this.stringRepresentation = stringRepresentation;
    }

    @Override
    public String getSerializedName() {
        return this.stringRepresentation;
    }
}
