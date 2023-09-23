package github.cutiecat2804.farmhousefurniture.item;

import github.cutiecat2804.farmhousefurniture.init.ItemInit;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class TwoBlockHighBlockItem extends BlockItem {
    public TwoBlockHighBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public @NotNull InteractionResult place(BlockPlaceContext blockPlaceContext) {
        if (blockPlaceContext.getItemInHand().getItem() == ItemInit.GREY_WOOD_CHAIR.get()) {
            if (blockPlaceContext.getLevel().getBlockState(blockPlaceContext.getClickedPos().above()).isAir()) {
                return super.place(blockPlaceContext);
            } else {
                return InteractionResult.FAIL;
            }
        }
        return super.place(blockPlaceContext);
    }
}
