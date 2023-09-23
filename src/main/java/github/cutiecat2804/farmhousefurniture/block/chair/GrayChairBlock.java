package github.cutiecat2804.farmhousefurniture.block.chair;

import github.cutiecat2804.farmhousefurniture.enums.GrayChairColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public class GrayChairBlock extends ChairBlock<GrayChairColor> {
    public static final EnumProperty<GrayChairColor> COLOR = EnumProperty.create("color", GrayChairColor.class);

    public GrayChairBlock(Properties properties) {
        super(properties, GrayChairColor.class);
        super.COLOR = COLOR;
        registerDefaultState(GrayChairColor.GRAY);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> blockBlockStateBuilder) {
        blockBlockStateBuilder.add(ChairBlock.FACING, IS_TOP, COLOR);
    }
}
