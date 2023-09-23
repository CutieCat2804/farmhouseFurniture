package github.cutiecat2804.farmhousefurniture.block.chair;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

public class DefaultChairBlock extends ChairBlock {
    public DefaultChairBlock(Properties properties) {
        super(properties);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> blockBlockStateBuilder) {
        blockBlockStateBuilder.add(ChairBlock.FACING, IS_TOP);
    }
}
