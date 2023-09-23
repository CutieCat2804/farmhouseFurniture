package github.cutiecat2804.farmhousefurniture.block.chair;

import github.cutiecat2804.farmhousefurniture.enums.GrayChairColor;
import github.cutiecat2804.farmhousefurniture.enums.OakChairColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public class OakChairBlock extends ChairBlock<OakChairColor> {
    public static final EnumProperty<OakChairColor> COLOR = EnumProperty.create("color", OakChairColor.class);


    public OakChairBlock(Properties properties) {
        super(properties, OakChairColor.class);
        super.COLOR = COLOR;
        registerDefaultState(OakChairColor.WHITE);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> blockBlockStateBuilder) {
        blockBlockStateBuilder.add(ChairBlock.FACING, IS_TOP, COLOR);
    }
}
