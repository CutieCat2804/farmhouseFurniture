package github.cutiecat2804.farmhousefurniture.enums;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum PianoPart implements StringRepresentable {
    BOTTOM_LEFT("bottom_left"),
    BOTTOM_RIGHT("bottom_right"),
    TOP_LEFT("top_left"),
    TOP_RIGHT("top_right");

    private final String name;

    PianoPart(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public @NotNull String getSerializedName() {
        return this.name;
    }
}