package github.cutiecat2804.farmhousefurniture.block;

import github.cutiecat2804.farmhousefurniture.init.ItemInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class NewspaperBlock extends Block {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    // (position front (z), position bottom (y), position left (x), position back (z), position top (y), position right (X))
    private static final VoxelShape SHAPE_ONE = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 1.0D, 13.0D);
    private static final VoxelShape SHAPE_TWO = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 2.0D, 13.0D);
    private static final VoxelShape SHAPE_THREE = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 3.0D, 13.0D);

    public static final IntegerProperty NEWSPAPERS = IntegerProperty.create("newspapers", 1, 3);

    public NewspaperBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(NEWSPAPERS, 1)
                .setValue(DishBlockUtils.FACING, Direction.NORTH));
    }

    public boolean canSurvive(@NotNull BlockState blockState, @NotNull LevelReader levelReader, BlockPos blockPos) {
        return Block.canSupportCenter(levelReader, blockPos.below(), Direction.UP);
    }

    public boolean canBeReplaced(@NotNull BlockState blockState, BlockPlaceContext blockPlaceContext) {
        return !blockPlaceContext.isSecondaryUseActive()
                && blockPlaceContext.getItemInHand().getItem() == this.asItem()
                && blockState.getValue(NEWSPAPERS) < 3
                || super.canBeReplaced(blockState, blockPlaceContext);
    }

    public BlockState getStateForPlacement(@NotNull BlockPlaceContext blockPlaceContext) {
        return DishBlockUtils.getStateForPlacement(blockPlaceContext, this, NEWSPAPERS);
    }

    public @NotNull InteractionResult use(@NotNull BlockState blockState, @NotNull Level level, @NotNull BlockPos blockPos, @NotNull Player player, @NotNull InteractionHand interactionHand, @NotNull BlockHitResult blockHitResult) {
        if (DishBlockUtils.removeDishWithShift(ItemInit.NEWSPAPER.get().getDefaultInstance(), NEWSPAPERS, blockState, level, blockPos, player, interactionHand)) {
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        return InteractionResult.PASS;
    }

    public @NotNull VoxelShape getShape(BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos, @NotNull CollisionContext collisionContext) {
        return switch (blockState.getValue(NEWSPAPERS)) {
            default -> SHAPE_ONE;
            case 2 -> SHAPE_TWO;
            case 3 -> SHAPE_THREE;
        };
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> blockBlockStateBuilder) {
        blockBlockStateBuilder.add(NEWSPAPERS, DishBlockUtils.FACING);
    }
}
