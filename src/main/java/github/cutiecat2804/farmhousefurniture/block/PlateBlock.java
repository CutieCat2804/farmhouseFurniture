package github.cutiecat2804.farmhousefurniture.block;

import github.cutiecat2804.farmhousefurniture.enums.PlateColors;
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
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class PlateBlock extends Block {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    // (position front (z), position bottom (y), position left (x), position back (z), position top (y), position right (X))
    private static final VoxelShape SHAPE_ONE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 1.0D, 12.0D);
    private static final VoxelShape SHAPE_TWO = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 2.0D, 12.0D);
    private static final VoxelShape SHAPE_THREE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 3.0D, 12.0D);
    private static final VoxelShape SHAPE_WITH_CUP = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);

    public static final IntegerProperty PLATES = IntegerProperty.create("plates", 1, 3);
    public static final IntegerProperty CUPS = IntegerProperty.create("cups", 0, 1);

    public static final EnumProperty<PlateColors> COLOR = EnumProperty.create("color", PlateColors.class);

    public PlateBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(PLATES, 1)
                .setValue(CUPS, 0)
                .setValue(FACING, Direction.NORTH)
                .setValue(COLOR, PlateColors.GREEN));
    }

    public boolean canSurvive(@NotNull BlockState blockState, @NotNull LevelReader levelReader, BlockPos blockPos) {
        return Block.canSupportCenter(levelReader, blockPos.below(), Direction.UP);
    }

    public boolean canBeReplaced(@NotNull BlockState blockState, BlockPlaceContext blockPlaceContext) {
        return !blockPlaceContext.isSecondaryUseActive()
                && blockPlaceContext.getItemInHand().getItem() == this.asItem()
                && blockState.getValue(PLATES) < 3
                && blockState.getValue(CUPS) < 1
                || super.canBeReplaced(blockState, blockPlaceContext);
    }

    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {

        BlockState blockstate = blockPlaceContext.getLevel().getBlockState(blockPlaceContext.getClickedPos());

        if (blockstate.is(this)) {
            return blockstate
                    .setValue(PLATES, Math.min(3, blockstate.getValue(PLATES) + 1));
        } else {
            return this.defaultBlockState().setValue(FACING, blockPlaceContext.getHorizontalDirection().getOpposite());
        }
    }

    // Damit beim Klicken mit einer Tasse auf den Teller sich der Block ändert
    public @NotNull InteractionResult use(@NotNull BlockState blockState, @NotNull Level level, @NotNull BlockPos blockPos, Player player, @NotNull InteractionHand interactionHand, @NotNull BlockHitResult blockHitResult) {
        if (player.getItemInHand(interactionHand).getItem() == ItemInit.CUP.get() && blockState.getValue(PLATES) == 1 && blockState.getValue(CUPS) != 1) {
            // updated block state und setzt ihn neu. Wichtig, weil ihr ein anderer Block angezeigt werden soll
            level.setBlockAndUpdate(
                    blockPos,
                    this.defaultBlockState().setValue(PLATES, 1)
                            .setValue(CUPS, 1)
                            .setValue(FACING, blockState.getValue(FACING))
            );

            if (!player.isCreative()) {
                player.getItemInHand(interactionHand).setCount(player.getItemInHand(interactionHand).getCount() - 1);
            }

            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        if (player.isShiftKeyDown() && player.getItemInHand(interactionHand).isEmpty() && blockState.getValue(PLATES) > 1) {
            level.setBlockAndUpdate(
                    blockPos,
                    this.defaultBlockState().setValue(PLATES, blockState.getValue(PLATES) - 1)
            );
            if (!player.isCreative()) {
                player.addItem(ItemInit.PLATE.get().getDefaultInstance());
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        return InteractionResult.PASS;
    }

    public @NotNull VoxelShape getShape(BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos, @NotNull CollisionContext collisionContext) {
        VoxelShape shape = switch (blockState.getValue(PLATES)) {
            default -> SHAPE_ONE;
            case 2 -> SHAPE_TWO;
            case 3 -> SHAPE_THREE;
        };

        if (blockState.getValue(PLATES) == 1 && blockState.getValue(CUPS) == 1) {
            shape = SHAPE_WITH_CUP;
        }

        return shape;


    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> blockBlockStateBuilder) {
        blockBlockStateBuilder.add(PLATES, CUPS, FACING, COLOR);
    }
}
