package github.cutiecat2804.farmhousefurniture.block;

import github.cutiecat2804.farmhousefurniture.enums.PlateColors;
import github.cutiecat2804.farmhousefurniture.init.ItemInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.NotNull;

public class DishBlockUtils {
    // Definiert in welche Richtung Block gesetzt werden kann
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final EnumProperty<PlateColors> COLOR = EnumProperty.create("color", PlateColors.class);

    public static BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext, Block block, IntegerProperty property) {
        BlockState blockstate = blockPlaceContext.getLevel().getBlockState(blockPlaceContext.getClickedPos());
        // Checkt, ob der BlockState schon der Type dieser Klasse ist (also schon eine Tasse dort steht)
        // fügt dann eine weitere hinzu
        if (blockstate.is(block)) {
            return blockstate
                    .setValue(property, Math.min(3, blockstate.getValue(property) + 1));
        } else {
            // Platziert die Tasse in der richtigen Richtung
            return block.defaultBlockState().setValue(FACING, blockPlaceContext.getHorizontalDirection().getOpposite());
        }
    }

    public static boolean removeDishWithShift(Block block, IntegerProperty property, @NotNull BlockState blockState, @NotNull Level level, @NotNull BlockPos blockPos, Player player, @NotNull InteractionHand interactionHand) {
        // Entfernt mit Shiftklick eine Tasse vom Block
        if (player.isShiftKeyDown() && player.getItemInHand(interactionHand).isEmpty() && blockState.getValue(property) > 1) {
            level.setBlockAndUpdate(
                    blockPos,
                    block.defaultBlockState()
                            .setValue(property, blockState.getValue(property) - 1)
                            .setValue(FACING, blockState.getValue(FACING))
                            .setValue(COLOR, blockState.getValue(COLOR))
            );

            // Gibt Spieler in Survival Tasse wieder ins Inventar
            if (!player.isCreative()) {
                player.addItem(ItemInit.CUP.get().getDefaultInstance());
            }
            return true;
        }
        return false;
    }

    public static boolean changeColor(@NotNull BlockState blockState, @NotNull Level level, @NotNull BlockPos blockPos, Player player, @NotNull InteractionHand interactionHand) {
        // Schaut ob der PaintBrush ausgewählt ist und wechselt die Farbe durch
        if (player.getItemInHand(interactionHand).getItem() == ItemInit.PAINTBRUSH.get()) {
            level.setBlockAndUpdate(
                    blockPos,
                    blockState.setValue(COLOR, PlateColors.values()[((blockState.getValue(COLOR)).ordinal() + 1) % PlateColors.values().length])
            );
            return true;
        }
        return false;
    }

    public static boolean addCupToPlate(Block block, @NotNull BlockState blockState, @NotNull Level level, @NotNull BlockPos blockPos, Player player, @NotNull InteractionHand interactionHand) {
        // Damit beim Klicken mit einer Tasse auf den Teller sich der Block ändert
        if (player.getItemInHand(interactionHand).getItem() == ItemInit.CUP.get() && blockState.getValue(PlateBlock.PLATES) == 1 && blockState.getValue(PlateBlock.CUPS) != 1) {
            // updated block state und setzt ihn neu. Wichtig, weil ihr ein anderer Block angezeigt werden soll
            level.setBlockAndUpdate(
                    blockPos,
                    block.defaultBlockState().setValue(PlateBlock.PLATES, 1)
                            .setValue(PlateBlock.CUPS, 1)
                            .setValue(DishBlockUtils.FACING, blockState.getValue(DishBlockUtils.FACING))
            );

            if (!player.isCreative()) {
                player.getItemInHand(interactionHand).setCount(player.getItemInHand(interactionHand).getCount() - 1);
            }

            return true;
        }

        return false;
    }
}
