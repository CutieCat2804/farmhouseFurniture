package github.cutiecat2804.farmhousefurniture.block.chair;

import github.cutiecat2804.farmhousefurniture.enums.GrayChairColor;
import github.cutiecat2804.farmhousefurniture.init.BlockInit;
import github.cutiecat2804.farmhousefurniture.init.ItemInit;
import github.cutiecat2804.farmhousefurniture.utils.RotateShapeUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public abstract class ChairBlock<T extends Enum<T> & StringRepresentable> extends Block {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty IS_TOP = BooleanProperty.create("is_top");
    public EnumProperty<T> COLOR;
    private Class<T> enumClass;
    private final Boolean hasColors;

    public ChairBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.hasColors = false;
        this.registerDefaultState((T) null);
    }

    public ChairBlock(BlockBehaviour.Properties properties, Class<T> enumClass) {
        super(properties);
        this.hasColors = true;
        this.enumClass = enumClass;

    }

    public void registerDefaultState(T defaultColor) {
        BlockState defaultBlockState = this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(IS_TOP, false);
        if (defaultColor != null) {
            defaultBlockState.setValue(this.COLOR, defaultColor);
        }
        this.registerDefaultState(defaultBlockState);
    }

    @Override
    public void destroy(@NotNull LevelAccessor levelAccessor, @NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        if (!(levelAccessor instanceof Level level)) {
            return;
        }

        if (blockState.getValue(IS_TOP)) {
            level.removeBlock(blockPos.below(), true);
        } else {
            level.removeBlock(blockPos.above(), true);
        }
    }

    public BlockState getStateForPlacement(@NotNull BlockPlaceContext blockPlaceContext) {
        Level level = blockPlaceContext.getLevel();
        Direction direction = blockPlaceContext.getHorizontalDirection().getOpposite();
        BlockPos blockpos = blockPlaceContext.getClickedPos().above();

        level.setBlockAndUpdate(
                blockpos,
                this.defaultBlockState()
                        .setValue(FACING, direction)
                        .setValue(IS_TOP, true));

        return this.defaultBlockState().setValue(FACING, direction);
    }

    public @NotNull InteractionResult use(@NotNull BlockState blockState, @NotNull Level level, @NotNull BlockPos blockPos, @NotNull Player player, @NotNull InteractionHand interactionHand, @NotNull BlockHitResult blockHitResult) {
        if (this.hasColors && player.getItemInHand(interactionHand).getItem() == ItemInit.PAINTBRUSH.get()) {
            level.setBlockAndUpdate(
                    blockPos,
                    blockState.setValue(COLOR, enumClass.getEnumConstants()[((blockState.getValue(COLOR)).ordinal() + 1) % GrayChairColor.values().length])
            );
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        return InteractionResult.PASS;
    }

    public @NotNull VoxelShape getShape(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos, @NotNull CollisionContext collisionContext) {
        VoxelShape shape = Shapes.empty();

        if (this == BlockInit.GRAY_WOOD_CHAIR.get()) {
            if (blockState.getValue(IS_TOP)) {
                shape = Shapes.join(shape, Shapes.box(0.25, 0, 0.75, 0.75, 0.375, 0.875), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.125, 0, 0.75, 0.25, 0.4375, 0.875), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.75, 0, 0.75, 0.875, 0.4375, 0.875), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(-0.45625, -0.25, 0.8125, -0.39375, 0.5625, 0.8125), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(1.1, -1, 0.8125, 1.1625, -0.125, 0.8125), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.0625, 0.4375, 0.75, 0.9375, 0.625, 0.875), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.0625, 0.4375, 0.75, 0.9375, 0.625, 0.875), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.25, 0.375, 0.75, 0.75, 0.4375, 0.875), BooleanOp.OR);
            } else {
                shape = Shapes.join(shape, Shapes.box(0.1875, 0, 0.1875, 0.3125, 0.4375, 0.3125), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.6875, 0, 0.1875, 0.8125, 0.4375, 0.3125), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.1875, 0.4375, 0.1875, 0.8125, 0.5625, 0.8125), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.75, 0, 0.75, 0.875, 1, 0.875), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.25, 0.6875, 0.8125, 0.75, 0.8125, 0.8125), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.25, 0.5625, 0.25, 0.75, 0.625, 0.75), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.125, 0, 0.75, 0.25, 1, 0.875), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.25, 0.4375, 0.75, 0.75, 1, 0.875), BooleanOp.OR);
            }
        }
        if (this == BlockInit.BLUE_WOOD_CHAIR.get()) {
            if (blockState.getValue(IS_TOP)) {
                shape = Shapes.join(shape, Shapes.box(0.25, 0, 0.75, 0.75, 0.3125, 0.875), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.25, 0.3125, 0.75, 0.75, 0.5, 0.875), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.375, 0.5, 0.75, 0.625, 0.5625, 0.875), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.125, 0, 0.75, 0.25, 0.4375, 0.875), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.75, 0, 0.75, 0.875, 0.4375, 0.875), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.25, -0.375, 0.8125, 0.75, -0.25, 0.8125), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, -0.25, 0.8125, 0.5625, 0.3125, 0.8125), BooleanOp.OR);
            } else {
                shape = Shapes.join(shape, Shapes.box(0.1875, 0, 0.1875, 0.3125, 0.4375, 0.3125), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.125, 0, 0.75, 0.25, 1, 0.875), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.6875, 0, 0.1875, 0.8125, 0.4375, 0.3125), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.75, 0, 0.75, 0.875, 1, 0.875), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.1875, 0.4375, 0.1875, 0.8125, 0.5625, 0.75), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.25, 0.4375, 0.75, 0.75, 0.5625, 0.875), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.25, 0.5625, 0.75, 0.75, 1, 0.875), BooleanOp.OR);
            }
        }

        if (this == BlockInit.OAK_WOOD_CHAIR.get()) {
            if (blockState.getValue(IS_TOP)) {
                shape = Shapes.join(shape, Shapes.box(0.75, 0, 0.75, 0.875, 0.625, 0.875), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.125, 0, 0.75, 0.25, 0.625, 0.875), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.25, 0.375, 0.75, 0.75, 0.5, 0.8125), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.25, 0.0625, 0.75, 0.75, 0.1875, 0.8125), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.25, 0, 0.75, 0.75, 0.5, 0.875), BooleanOp.OR);
            } else {
                shape = Shapes.join(shape, Shapes.box(0.1875, 0.125, 0.3125, 0.25, 0.1875, 0.75), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.75, 0, 0.75, 0.875, 1, 0.875), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.125, 0, 0.75, 0.25, 1, 0.875), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.25, 0.75, 0.75, 0.75, 0.875, 0.8125), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.125, 0, 0.1875, 0.25, 0.4375, 0.3125), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.75, 0, 0.1875, 0.875, 0.4375, 0.3125), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.8125, 0.4375, 0.1875, 0.875, 0.5625, 0.3125), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.125, 0.4375, 0.1875, 0.1875, 0.5625, 0.3125), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.75, 0.125, 0.3125, 0.8125, 0.1875, 0.75), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.1875, 0.4375, 0.1875, 0.8125, 0.5625, 0.8125), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.25, 0.4375, 0.75, 0.75, 1, 0.875), BooleanOp.OR);
            }
        }

        if (this == BlockInit.DARK_WOOD_CHAIR.get()) {
            if (blockState.getValue(IS_TOP)) {
                shape = Shapes.join(shape, Shapes.box(0.125, 0, 0.75, 0.25, 0.4375, 0.875), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.75, 0, 0.75, 0.875, 0.4375, 0.875), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.0625, 0.4375, 0.75, 0.9375, 0.625, 0.875), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.25, -0.3125, 0.8125, 0.75, -0.1875, 0.8125), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.5625, -0.1875, 0.8125, 0.6875, 0.3125, 0.8125), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.3125, -0.1875, 0.8125, 0.4375, 0.3125, 0.8125), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.25, 0.3125, 0.8125, 0.75, 0.4375, 0.8125), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.25, 0, 0.75, 0.75, 0.4375, 0.875), BooleanOp.OR);
            } else {
                shape = Shapes.join(shape, Shapes.box(0.1875, 0, 0.1875, 0.3125, 0.4375, 0.3125), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.125, 0, 0.75, 0.25, 1, 0.875), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.6875, 0, 0.1875, 0.8125, 0.4375, 0.3125), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.75, 0, 0.75, 0.875, 1, 0.875), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.1875, 0.4375, 0.1875, 0.8125, 0.5625, 0.8125), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.25, 0.4375, 0.75, 0.75, 1, 0.875), BooleanOp.OR);
            }
        }

        if (blockState.getValue(FACING) == Direction.SOUTH) {
            shape = RotateShapeUtils.rotateShape(Direction.WEST, blockState.getValue(FACING), shape);
        } else if (blockState.getValue(FACING) == Direction.WEST) {
            shape = RotateShapeUtils.rotateShape(Direction.WEST, blockState.getValue(FACING), shape);
        } else if (blockState.getValue(FACING) == Direction.EAST) {
            shape = RotateShapeUtils.rotateShape(Direction.SOUTH, blockState.getValue(FACING), shape);
        }

        return shape;
    }
}
