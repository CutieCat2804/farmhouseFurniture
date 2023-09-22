package github.cutiecat2804.farmhousefurniture.enums;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum ChairColor implements StringRepresentable {
    GREY("grey"),
    GREEN("green"),
    BLUE("blue"),
    BEIGE("beige");

    private final String stringRepresentation;

    ChairColor(String stringRepresentation) {
        this.stringRepresentation = stringRepresentation;
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.stringRepresentation;
    }
}
