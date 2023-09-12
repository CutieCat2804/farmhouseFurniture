package github.cutiecat2804.farmhousefurniture.enums;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum DishColor implements StringRepresentable {
    WHITE("white"),
    GREEN("green"),
    BLUE("blue"),
    YELLOW("yellow"),
    BEIGE("beige");

    private final String stringRepresentation;

    DishColor(String stringRepresentation) {
        this.stringRepresentation = stringRepresentation;
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.stringRepresentation;
    }
}
