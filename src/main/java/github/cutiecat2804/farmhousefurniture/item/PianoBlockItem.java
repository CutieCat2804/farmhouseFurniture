package github.cutiecat2804.farmhousefurniture.item;

import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class PianoBlockItem extends BlockItem {
    public PianoBlockItem(Block block, Item.Properties properties) {
        super(block, properties);
    }

    @Override
    public @NotNull InteractionResult place(BlockPlaceContext blockPlaceContext) {
        if (blockPlaceContext.getItemInHand().getItem() == this) {
            Direction direction = blockPlaceContext.getHorizontalDirection().getOpposite();

            if (blockPlaceContext.getLevel().getBlockState(blockPlaceContext.getClickedPos().above()).isAir() &&
                    blockPlaceContext.getLevel().getBlockState(blockPlaceContext.getClickedPos().relative(direction.getCounterClockWise())).isAir() &&
                    blockPlaceContext.getLevel().getBlockState(blockPlaceContext.getClickedPos().relative(direction.getCounterClockWise()).above()).isAir()) {
                return super.place(blockPlaceContext);
            } else {
                return InteractionResult.FAIL;
            }
        }
        return super.place(blockPlaceContext);
    }
}
