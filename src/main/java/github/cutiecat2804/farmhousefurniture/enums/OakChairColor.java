package github.cutiecat2804.farmhousefurniture.enums;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum OakChairColor implements StringRepresentable {
    WHITE("white"),
    BLUE("blue"),
    ORANGE("orange"),
    YELLOW("yellow");

    private final String stringRepresentation;

    OakChairColor(String stringRepresentation) {
        this.stringRepresentation = stringRepresentation;
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.stringRepresentation;
    }
}
