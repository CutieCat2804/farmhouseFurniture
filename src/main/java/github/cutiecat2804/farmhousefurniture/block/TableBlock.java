package github.cutiecat2804.farmhousefurniture.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class TableBlock extends Block {
    public static final BooleanProperty NORTH = PipeBlock.NORTH;
    public static final BooleanProperty EAST = PipeBlock.EAST;
    public static final BooleanProperty SOUTH = PipeBlock.SOUTH;
    public static final BooleanProperty WEST = PipeBlock.WEST;

    private final Boolean broadTableTop;
    private final Boolean broadTableLegs;

    public TableBlock(Properties properties, Boolean broadTableTop, Boolean broadTableLegs) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(NORTH, false)
                .setValue(EAST, false)
                .setValue(SOUTH, false)
                .setValue(WEST, false));
        this.broadTableTop = broadTableTop;
        this.broadTableLegs = broadTableLegs;
    }

    public boolean connectsTo(BlockState blockState) {
        boolean isSameTable = blockState.is(this);
        return !isExceptionForConnection(blockState) && isSameTable;
    }

    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        Level level = blockPlaceContext.getLevel();
        BlockPos blockPos = blockPlaceContext.getClickedPos();

        BlockPos blockposNorth = blockPos.north();
        BlockPos blockposEast = blockPos.east();
        BlockPos blockposSouth = blockPos.south();
        BlockPos blockposWest = blockPos.west();

        BlockState blockstateNorth = level.getBlockState(blockposNorth);
        BlockState blockstateEast = level.getBlockState(blockposEast);
        BlockState blockstateSouth = level.getBlockState(blockposSouth);
        BlockState blockstateWest = level.getBlockState(blockposWest);

        if (blockstateNorth.is(this)) {
            level.setBlockAndUpdate(
                    blockposNorth,
                    blockstateNorth.setValue(SOUTH, true)
            );
        }

        if (blockstateEast.is(this)) {
            level.setBlockAndUpdate(
                    blockposEast,
                    blockstateEast.setValue(WEST, true)
            );
        }

        if (blockstateSouth.is(this)) {
            level.setBlockAndUpdate(
                    blockposSouth,
                    blockstateSouth.setValue(NORTH, true)
            );
        }

        if (blockstateWest.is(this)) {
            level.setBlockAndUpdate(
                    blockposWest,
                    blockstateWest.setValue(EAST, true)
            );
        }

        return this.defaultBlockState()
                .setValue(NORTH, this.connectsTo(blockstateNorth))
                .setValue(EAST, this.connectsTo(blockstateEast))
                .setValue(SOUTH, this.connectsTo(blockstateSouth))
                .setValue(WEST, this.connectsTo(blockstateWest));

    }

    @Override
    public void destroy(@NotNull LevelAccessor levelAccessor, @NotNull BlockPos blockPos, @NotNull BlockState blockState) {

        if (!(levelAccessor instanceof Level level)) {
            return;
        }

        BlockPos blockposNorth = blockPos.north();
        BlockPos blockposEast = blockPos.east();
        BlockPos blockposSouth = blockPos.south();
        BlockPos blockposWest = blockPos.west();

        BlockState blockstateNorth = levelAccessor.getBlockState(blockposNorth);
        BlockState blockstateEast = levelAccessor.getBlockState(blockposEast);
        BlockState blockstateSouth = levelAccessor.getBlockState(blockposSouth);
        BlockState blockstateWest = levelAccessor.getBlockState(blockposWest);

        if (blockstateNorth.is(this)) {
            level.setBlockAndUpdate(
                    blockposNorth,
                    blockstateNorth.setValue(SOUTH, false)
            );
        }

        if (blockstateEast.is(this)) {
            level.setBlockAndUpdate(
                    blockposEast,
                    blockstateEast.setValue(WEST, false)
            );
        }

        if (blockstateSouth.is(this)) {
            level.setBlockAndUpdate(
                    blockposSouth,
                    blockstateSouth.setValue(NORTH, false)
            );
        }

        if (blockstateWest.is(this)) {
            level.setBlockAndUpdate(
                    blockposWest,
                    blockstateWest.setValue(EAST, false)
            );
        }
    }

    public @NotNull VoxelShape getShape(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos, @NotNull CollisionContext collisionContext) {
        VoxelShape shape = Shapes.empty();

//        Shape für den drei drei Tisch
//        shape = Shapes.join(shape, Shapes.box(0.0625, 0, 0.0625, 0.25, 0.8125, 0.25), BooleanOp.OR);
//        shape = Shapes.join(shape, Shapes.box(0.75, 0, 0.0625, 0.9375, 0.8125, 0.25), BooleanOp.OR);
//        shape = Shapes.join(shape, Shapes.box(0.0625, 0, 0.75, 0.25, 0.8125, 0.9375), BooleanOp.OR);
//        shape = Shapes.join(shape, Shapes.box(0.75, 0, 0.75, 0.9375, 0.8125, 0.9375), BooleanOp.OR);
//        shape = Shapes.join(shape, Shapes.box(0, 0.8125, 0, 1, 1, 1), BooleanOp.OR);

        // Fügt den Shape zusammen je nachdem was die Nachbarblöcke sind
        if (this.broadTableTop) {
            shape = Shapes.join(shape, Shapes.box(0, 0.8125, 0, 1, 1, 1), BooleanOp.OR);
        } else {
            shape = Shapes.join(shape, Shapes.box(0, 0.875, 0, 1, 1, 1), BooleanOp.OR);
        }

        if (!blockState.getValue(SOUTH) && !blockState.getValue(EAST)) {
            if (this.broadTableLegs) {
                shape = Shapes.join(shape, Shapes.box(0.75, 0, 0.75, 0.9375, 0.8125, 0.9375), BooleanOp.OR);
            } else if (this.broadTableTop) {
                shape = Shapes.join(shape, Shapes.box(0.75, 0, 0.75, 0.9375, 0.875, 0.9375), BooleanOp.OR);
            } else {
                shape = Shapes.join(shape, Shapes.box(0.8125, 0, 0.8125, 0.9375, 0.875, 0.9375), BooleanOp.OR);

            }
        }
        if (!blockState.getValue(SOUTH) && !blockState.getValue(WEST)) {
            if (this.broadTableLegs) {
                shape = Shapes.join(shape, Shapes.box(0.0625, 0, 0.75, 0.25, 0.8125, 0.9375), BooleanOp.OR);
            } else if (this.broadTableTop) {
                shape = Shapes.join(shape, Shapes.box(0.0625, 0, 0.8125, 0.1875, 0.8125, 0.9375), BooleanOp.OR);
            }  else {
                shape = Shapes.join(shape, Shapes.box(0.0625, 0, 0.8125, 0.1875, 0.875, 0.9375), BooleanOp.OR);
            }
        }
        if (!blockState.getValue(NORTH) && !blockState.getValue(EAST)) {
            if (this.broadTableLegs) {
                shape = Shapes.join(shape, Shapes.box(0.75, 0, 0.0625, 0.9375, 0.8125, 0.25), BooleanOp.OR);
            } else if (this.broadTableTop) {
                shape = Shapes.join(shape, Shapes.box(0.8125, 0, 0.0625, 0.9375, 0.8125, 0.1875), BooleanOp.OR);
            } else {
                shape = Shapes.join(shape, Shapes.box(0.8125, 0, 0.0625, 0.9375, 0.875, 0.1875), BooleanOp.OR);
            }
        }
        if (!blockState.getValue(NORTH) && !blockState.getValue(WEST)) {
            if (this.broadTableLegs) {
                shape = Shapes.join(shape, Shapes.box(0.0625, 0, 0.0625, 0.25, 0.8125, 0.25), BooleanOp.OR);
            } else if (this.broadTableTop) {
                shape = Shapes.join(shape, Shapes.box(0.0625, 0, 0.0625, 0.1875, 0.8125, 0.1875), BooleanOp.OR);
            }  else {
                shape = Shapes.join(shape, Shapes.box(0.0625, 0, 0.0625, 0.1875, 0.875, 0.1875), BooleanOp.OR);
            }
        }

        return shape;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> blockBlockStateBuilder) {
        blockBlockStateBuilder.add(NORTH, EAST, SOUTH, WEST);
    }
}
