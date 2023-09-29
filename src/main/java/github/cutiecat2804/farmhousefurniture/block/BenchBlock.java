package github.cutiecat2804.farmhousefurniture.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class BenchBlock extends Block {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public static final BooleanProperty LEFT = BooleanProperty.create("left");
    public static final BooleanProperty RIGHT = BooleanProperty.create("right");

    public BenchBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(LEFT, false)
                .setValue(RIGHT, false));
    }

    public boolean connectsTo(BlockState blockState, Direction facing) {
        boolean isSameBench = blockState.is(this);
        boolean isSameFacing = isSameBench && (blockState.getValue(FACING) == facing);
        return !isExceptionForConnection(blockState) && isSameBench && isSameFacing;
    }

    public BlockState getStateForPlacement(@NotNull BlockPlaceContext blockPlaceContext) {
        Level level = blockPlaceContext.getLevel();
        BlockPos blockPos = blockPlaceContext.getClickedPos();

        Direction facing = switch (blockPlaceContext.getHorizontalDirection().getOpposite()) {
            default -> Direction.NORTH;
            case WEST, EAST -> Direction.WEST;
        };


        BlockPos blockPosRight = blockPos.relative(facing.getClockWise());
        BlockPos blockPosLeft = blockPos.relative(facing.getCounterClockWise());

        updateLeftAndRightBlocks(level, blockPosRight, blockPosLeft, true, facing);

        BlockState blockstateRight = level.getBlockState(blockPosRight);
        BlockState blockstateLeft = level.getBlockState(blockPosLeft);


        return this.defaultBlockState()
                .setValue(FACING, facing)
                .setValue(LEFT, connectsTo(blockstateRight, facing))
                .setValue(RIGHT, connectsTo(blockstateLeft, facing));

    }

    @Override
    public void destroy(@NotNull LevelAccessor levelAccessor, @NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        if (!(levelAccessor instanceof Level level)) {
            return;
        }

        Direction facing = blockState.getValue(FACING);

        BlockPos blockPosRight = blockPos.relative(facing.getClockWise());
        BlockPos blockPosLeft = blockPos.relative(facing.getCounterClockWise());

        updateLeftAndRightBlocks(level, blockPosRight, blockPosLeft, false, facing);
    }

    private void updateLeftAndRightBlocks(Level level, BlockPos blockPosRight, BlockPos blockPosLeft, boolean setValueToTrue, Direction facing) {
        BlockState blockstateRight = level.getBlockState(blockPosRight);
        BlockState blockstateLeft = level.getBlockState(blockPosLeft);

        if (blockstateRight.is(this) && (blockstateRight.getValue(FACING) == facing || blockstateRight.getValue(FACING) == facing.getOpposite())) {
            level.setBlockAndUpdate(
                    blockPosRight,
                    blockstateRight.setValue(RIGHT, setValueToTrue)
            );
        }

        if (blockstateLeft.is(this) && (blockstateLeft.getValue(FACING) == facing || blockstateLeft.getValue(FACING) == facing.getOpposite())) {
            level.setBlockAndUpdate(
                    blockPosLeft,
                    blockstateLeft.setValue(LEFT, setValueToTrue)
            );

        }
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos, @NotNull CollisionContext collisionContext) {
        VoxelShape shape = Shapes.empty();

        if (blockState.getValue(FACING) == Direction.NORTH || blockState.getValue(FACING) == Direction.SOUTH) {
            shape = Shapes.box(0, 0, 0.1875, 1, 0.5625, 0.8125);
        } else if (blockState.getValue(FACING) == Direction.EAST || blockState.getValue(FACING) == Direction.WEST) {
            shape = Shapes.box(0.1875, 0, 0, 0.8125, 0.5625, 1);
        }

        return shape;

    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> blockBlockStateBuilder) {
        blockBlockStateBuilder.add(LEFT, RIGHT, FACING);
    }
}
