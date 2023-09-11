package github.cutiecat2804.farmhousefurniture.block;

import github.cutiecat2804.farmhousefurniture.enums.PlateColors;
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
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class CupBlock extends Block {
    // (position front (z), position bottom (y), position left (x), position back (z), position top (y), position right (X))
    private static final VoxelShape SHAPE_ONE = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 3.0D, 10.0D);
    private static final VoxelShape SHAPE_TWO = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 6.0D, 10.0D);
    private static final VoxelShape SHAPE_THREE = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 9.0D, 10.0D);

    // Setzt Range wie viele Cups auf einem Block sein dürfen
    public static final IntegerProperty CUPS = IntegerProperty.create("cups", 1, 3);

    public CupBlock(BlockBehaviour.Properties properties) {
        super(properties);
        // Setzt den default BlockState
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(CUPS, 1)
                .setValue(DishBlockUtils.FACING, Direction.NORTH)
                .setValue(DishBlockUtils.COLOR, PlateColors.WHITE));
    }

    // Kann nicht in die Luft gesetzt werden, wird von Wasser zerstört
    public boolean canSurvive(@NotNull BlockState blockState, @NotNull LevelReader levelReader, BlockPos blockPos) {
        return Block.canSupportCenter(levelReader, blockPos.below(), Direction.UP);
    }

    // Wird aufgerufen, wenn ein neuer Block zu dem existierenden hinzugefügt wird.
    // Checkt, ob es der richtige Klick und Block ist und ob die Anzahl kleiner 3 ist
    // bestimmt darüber, ob der Block noch ersetzt werden kann
    public boolean canBeReplaced(@NotNull BlockState blockState, BlockPlaceContext blockPlaceContext) {
        return !blockPlaceContext.isSecondaryUseActive()
                && blockPlaceContext.getItemInHand().getItem() == this.asItem()
                && blockState.getValue(CUPS) < 3
                || super.canBeReplaced(blockState, blockPlaceContext);
    }

    // Wird aufgerufen, wenn ein neuer Block zu dem existierenden hinzugefügt wird
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext blockPlaceContext) {
        return DishBlockUtils.getStateForPlacement(blockPlaceContext, this, CUPS);
    }

    public @NotNull InteractionResult use(@NotNull BlockState blockState, @NotNull Level level, @NotNull BlockPos blockPos, Player player, @NotNull InteractionHand interactionHand, @NotNull BlockHitResult blockHitResult) {
        if (DishBlockUtils.removeDishWithShift(this, CUPS, blockState, level, blockPos, player, interactionHand)) {
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        if (DishBlockUtils.changeColor(blockState, level, blockPos, player, interactionHand)) {
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        return InteractionResult.PASS;
    }

    //  Setzt den Shape je nach Anzahl der Cups
    public @NotNull VoxelShape getShape(BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos, @NotNull CollisionContext collisionContext) {
        return switch (blockState.getValue(CUPS)) {
            default -> SHAPE_ONE;
            case 2 -> SHAPE_TWO;
            case 3 -> SHAPE_THREE;
        };
    }

    // Setzt den BlockState
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> blockBlockStateBuilder) {
        blockBlockStateBuilder.add(CUPS, DishBlockUtils.FACING, DishBlockUtils.COLOR);
    }

}
