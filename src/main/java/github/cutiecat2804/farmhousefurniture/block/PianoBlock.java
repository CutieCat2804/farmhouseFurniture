package github.cutiecat2804.farmhousefurniture.block;

import github.cutiecat2804.farmhousefurniture.enums.PianoPart;
import github.cutiecat2804.farmhousefurniture.utils.DishBlockUtils;
import github.cutiecat2804.farmhousefurniture.utils.RotateShapeUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class PianoBlock extends Block {
    public static final EnumProperty<PianoPart> PART = EnumProperty.create("part", PianoPart.class);
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public PianoBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(PART, PianoPart.BOTTOM_LEFT));
    }

    @Override
    public void destroy(@NotNull LevelAccessor levelAccessor, @NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        if (!(levelAccessor instanceof Level level)) {
            return;
        }

        if (blockState.getValue(PART) == PianoPart.BOTTOM_LEFT) {
            level.removeBlock(blockPos.above(), true);
            level.removeBlock(blockPos.relative(blockState.getValue(FACING).getCounterClockWise()), true);
            level.removeBlock(blockPos.relative(blockState.getValue(FACING).getCounterClockWise()).above(), true);
        }

        if (blockState.getValue(PART) == PianoPart.BOTTOM_RIGHT) {
            level.removeBlock(blockPos.above(), true);
            level.removeBlock(blockPos.relative(blockState.getValue(FACING).getClockWise()), true);
            level.removeBlock(blockPos.relative(blockState.getValue(FACING).getClockWise()).above(), true);
        }

        if (blockState.getValue(PART) == PianoPart.TOP_LEFT) {
            level.removeBlock(blockPos.below(), true);
            level.removeBlock(blockPos.relative(blockState.getValue(FACING).getCounterClockWise()), true);
            level.removeBlock(blockPos.relative(blockState.getValue(FACING).getCounterClockWise()).below(), true);
        }


        if (blockState.getValue(PART) == PianoPart.TOP_RIGHT) {
            level.removeBlock(blockPos.below(), true);
            level.removeBlock(blockPos.relative(blockState.getValue(FACING).getClockWise()), true);
            level.removeBlock(blockPos.relative(blockState.getValue(FACING).getClockWise()).below(), true);
        }
    }

    public BlockState getStateForPlacement(@NotNull BlockPlaceContext blockPlaceContext) {
        Direction direction = blockPlaceContext.getHorizontalDirection().getOpposite();

        BlockPos blockposTopLeft = blockPlaceContext.getClickedPos().above();
        BlockPos blockposBottomRight = blockPlaceContext.getClickedPos().relative(direction.getCounterClockWise());
        BlockPos blockposTopRight = blockPlaceContext.getClickedPos().relative(direction.getCounterClockWise()).above();

        blockPlaceContext.getLevel().setBlockAndUpdate(
                blockposTopLeft,
                this.defaultBlockState()
                        .setValue(FACING, direction)
                        .setValue(PART, PianoPart.TOP_LEFT));
        blockPlaceContext.getLevel().setBlockAndUpdate(
                blockposBottomRight,
                this.defaultBlockState()
                        .setValue(DishBlockUtils.FACING, direction)
                        .setValue(PART, PianoPart.BOTTOM_RIGHT));
        blockPlaceContext.getLevel().setBlockAndUpdate(
                blockposTopRight,
                this.defaultBlockState()
                        .setValue(DishBlockUtils.FACING, direction)
                        .setValue(PART, PianoPart.TOP_RIGHT));

        return this.defaultBlockState().setValue(FACING, direction);
    }

    public void attack(@NotNull BlockState blockState, Level level, @NotNull BlockPos blockPos, @NotNull Player player) {
        if (!level.isClientSide) {
            this.playNote(player, blockState, level, blockPos);
            player.awardStat(Stats.PLAY_NOTEBLOCK);
        }
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState blockState, Level level, @NotNull BlockPos blockPos, @NotNull Player player, @NotNull InteractionHand interactionHand, @NotNull BlockHitResult blockHitResult) {
        if (!level.isClientSide && player.getItemInHand(interactionHand).isEmpty()) {
            this.playNote(player, blockState, level, blockPos);
            player.awardStat(Stats.PLAY_NOTEBLOCK);
            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.PASS;
        }
    }

    private void playNote(@Nullable Entity entity, BlockState blockState, Level level, BlockPos blockPos) {
        if (blockState.getValue(PART) == PianoPart.BOTTOM_RIGHT || blockState.getValue(PART) == PianoPart.BOTTOM_LEFT) {
            level.blockEvent(blockPos, this, 0, 0);
            level.gameEvent(entity, GameEvent.NOTE_BLOCK_PLAY, blockPos);
        }
    }

    public boolean triggerEvent(@NotNull BlockState blockState, @NotNull Level level, @NotNull BlockPos blockPos, int p_55026_, int p_55027_) {
        NoteBlockInstrument noteblockinstrument = NoteBlockInstrument.HARP;
        int note = 0;
        float tune = 1.0F;

        net.minecraftforge.event.level.NoteBlockEvent.Play e = new net.minecraftforge.event.level.NoteBlockEvent.Play(level, blockPos, blockState, note, noteblockinstrument);
        if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(e)) return false;

        Holder<SoundEvent> holder = noteblockinstrument.getSoundEvent();

        level.playSeededSound(null, blockPos.getX() + 0.5D, blockPos.getY() + 0.5D, blockPos.getZ() + 0.5D, holder, SoundSource.RECORDS, 3.0F, tune, level.random.nextLong());
        return true;
    }

    public @NotNull VoxelShape getShape(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos, @NotNull CollisionContext collisionContext) {
        VoxelShape shape = Shapes.empty();

        if (blockState.getValue(PART) == PianoPart.BOTTOM_LEFT) {
            shape = Shapes.join(shape, Shapes.box(0.09375, 0.0625, 0.4375, 0.15625, 0.125, 0.5625), BooleanOp.OR);
            shape = Shapes.join(shape, Shapes.box(0, 0.0625, 0.4375, 0.03125, 0.125, 0.5625), BooleanOp.OR);
            shape = Shapes.join(shape, Shapes.box(0, 0.0625, 0.375, 0.03125, 0.09375, 0.4375), BooleanOp.OR);
            shape = Shapes.join(shape, Shapes.box(0.09375, 0.0625, 0.375, 0.15625, 0.09375, 0.4375), BooleanOp.OR);
            shape = Shapes.join(shape, Shapes.box(0.78125, 0.9375, 0.25, 0.84375, 0.96875, 0.4375), BooleanOp.OR);
            shape = Shapes.join(shape, Shapes.box(0.6875, 0.9375, 0.25, 0.75, 0.96875, 0.4375), BooleanOp.OR);
            shape = Shapes.join(shape, Shapes.box(0.34375, 0.9375, 0.25, 0.40625, 0.96875, 0.4375), BooleanOp.OR);
            shape = Shapes.join(shape, Shapes.box(0.1875, 0.9375, 0.25, 0.25, 0.96875, 0.4375), BooleanOp.OR);
            shape = Shapes.join(shape, Shapes.box(0.09375, 0.9375, 0.25, 0.15625, 0.96875, 0.4375), BooleanOp.OR);
            shape = Shapes.join(shape, Shapes.box(0.4375, 0.9375, 0.25, 0.5, 0.96875, 0.4375), BooleanOp.OR);
            shape = Shapes.join(shape, Shapes.box(0.53125, 0.9375, 0.25, 0.59375, 0.96875, 0.4375), BooleanOp.OR);
            shape = Shapes.join(shape, Shapes.box(0, 0.875, 0.1875, 0.875, 0.9375, 0.4375), BooleanOp.OR);
            shape = Shapes.join(shape, Shapes.box(0.875, 0, 0.125, 1, 0.125, 0.5625), BooleanOp.OR);
            shape = Shapes.join(shape, Shapes.box(0, 0, 0.5625, 1, 1, 1), BooleanOp.OR);
            shape = Shapes.join(shape, Shapes.box(0.875, 0.875, 0.125, 1, 1, 0.5625), BooleanOp.OR);
            shape = Shapes.join(shape, Shapes.box(0, 0.875, 0.4375, 0.875, 1, 0.5625), BooleanOp.OR);
            shape = Shapes.join(shape, Shapes.box(0.875, 0.125, 0.125, 1, 0.75, 0.25), BooleanOp.OR);
            shape = Shapes.join(shape, Shapes.box(0, 0.75, 0.125, 1, 0.875, 0.5625), BooleanOp.OR);
        }
        if (blockState.getValue(PART) == PianoPart.BOTTOM_RIGHT) {
            shape = Shapes.join(shape, Shapes.box(0, 0.875, 0.125, 0.125, 1, 0.5625), BooleanOp.OR);
            shape = Shapes.join(shape, Shapes.box(0.125, 0.875, 0.1875, 1, 0.9375, 0.4375), BooleanOp.OR);
            shape = Shapes.join(shape, Shapes.box(0.9375, 0.9375, 0.25, 1, 0.96875, 0.4375), BooleanOp.OR);
            shape = Shapes.join(shape, Shapes.box(0.84375, 0.9375, 0.25, 0.90625, 0.96875, 0.4375), BooleanOp.OR);
            shape = Shapes.join(shape, Shapes.box(0.75, 0.9375, 0.25, 0.8125, 0.96875, 0.4375), BooleanOp.OR);
            shape = Shapes.join(shape, Shapes.box(0.59375, 0.9375, 0.25, 0.65625, 0.96875, 0.4375), BooleanOp.OR);
            shape = Shapes.join(shape, Shapes.box(0.5, 0.9375, 0.25, 0.5625, 0.96875, 0.4375), BooleanOp.OR);
            shape = Shapes.join(shape, Shapes.box(0.34375, 0.9375, 0.25, 0.40625, 0.96875, 0.4375), BooleanOp.OR);
            shape = Shapes.join(shape, Shapes.box(0.25, 0.9375, 0.25, 0.3125, 0.96875, 0.4375), BooleanOp.OR);
            shape = Shapes.join(shape, Shapes.box(0.15625, 0.9375, 0.25, 0.21875, 0.96875, 0.4375), BooleanOp.OR);
            shape = Shapes.join(shape, Shapes.box(0, 0, 0.5625, 1, 1, 1), BooleanOp.OR);
            shape = Shapes.join(shape, Shapes.box(0, 0.125, 0.125, 0.125, 0.75, 0.25), BooleanOp.OR);
            shape = Shapes.join(shape, Shapes.box(0, 0, 0.125, 0.125, 0.125, 0.5625), BooleanOp.OR);
            shape = Shapes.join(shape, Shapes.box(0, 0.75, 0.125, 1, 0.875, 0.5625), BooleanOp.OR);
            shape = Shapes.join(shape, Shapes.box(0.125, 0.9375, 0.4375, 1, 1, 0.5625), BooleanOp.OR);
            shape = Shapes.join(shape, Shapes.box(0.96875, 0.0625, 0.4375, 1, 0.125, 0.5625), BooleanOp.OR);
            shape = Shapes.join(shape, Shapes.box(0.84375, 0.0625, 0.4375, 0.90625, 0.125, 0.5625), BooleanOp.OR);
            shape = Shapes.join(shape, Shapes.box(0.96875, 0.0625, 0.375, 1, 0.09375, 0.4375), BooleanOp.OR);
            shape = Shapes.join(shape, Shapes.box(0.84375, 0.0625, 0.375, 0.90625, 0.09375, 0.4375), BooleanOp.OR);
        }
        if (blockState.getValue(PART) == PianoPart.TOP_LEFT) {
            shape = Shapes.join(shape, Shapes.box(0.4375, 0.6875, 0.5, 0.75, 0.75, 0.9375), BooleanOp.OR);
            shape = Shapes.join(shape, Shapes.box(0, 0.0625, 0.5, 0.25, 0.375, 0.5625), BooleanOp.OR);
            shape = Shapes.join(shape, Shapes.box(0, 0, 0.5625, 1, 0.625, 1), BooleanOp.OR);
            shape = Shapes.join(shape, Shapes.box(0, 0.5625, 0.4375, 1, 0.625, 0.5625), BooleanOp.OR);
            shape = Shapes.join(shape, Shapes.box(0.875, 0, 0.5, 1, 0.5625, 0.5625), BooleanOp.OR);
            shape = Shapes.join(shape, Shapes.box(0, 0, 0.4375, 0.875, 0.0625, 0.5625), BooleanOp.OR);
            shape = Shapes.join(shape, Shapes.box(0.875, 0, 0.3125, 1, 0.0625, 0.5), BooleanOp.OR);
            shape = Shapes.join(shape, Shapes.box(0.4375, 0.625, 0.5, 0.75, 0.6875, 0.9375), BooleanOp.OR);
        }
        if (blockState.getValue(PART) == PianoPart.TOP_RIGHT) {
            shape = Shapes.join(shape, Shapes.box(0.75, 0.0625, 0.5, 1, 0.375, 0.5625), BooleanOp.OR);
            shape = Shapes.join(shape, Shapes.box(0, 0, 0.5625, 1, 0.625, 1), BooleanOp.OR);
            shape = Shapes.join(shape, Shapes.box(0, 0.5625, 0.4375, 1, 0.625, 0.5625), BooleanOp.OR);
            shape = Shapes.join(shape, Shapes.box(0, 0.0625, 0.5, 0.125, 0.5625, 0.5625), BooleanOp.OR);
            shape = Shapes.join(shape, Shapes.box(0, 0, 0.3125, 0.125, 0.0625, 0.5625), BooleanOp.OR);
            shape = Shapes.join(shape, Shapes.box(0.125, 0, 0.4375, 1, 0.0625, 0.5625), BooleanOp.OR);
            shape = Shapes.join(shape, Shapes.box(0.25, 0.625, 0.5625, 0.5625, 0.9375, 0.9375), BooleanOp.OR);
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


    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> blockBlockStateBuilder) {
        blockBlockStateBuilder.add(FACING, PART);
    }
}
