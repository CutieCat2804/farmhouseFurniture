package github.cutiecat2804.farmhousefurniture.sit;

import github.cutiecat2804.farmhousefurniture.FarmhouseFurniture;
import github.cutiecat2804.farmhousefurniture.block.chair.ChairBlock;
import github.cutiecat2804.farmhousefurniture.block.BenchBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.event.level.BlockEvent.BreakEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = FarmhouseFurniture.MODID)
public class SitHandler {
    @SubscribeEvent
    public static void onRightClickBlock(RightClickBlock event) {
        Player player = event.getEntity();

        if (!event.getLevel().isClientSide && event.getFace() == Direction.UP && !SitUtil.isPlayerSitting(player) && !player.isShiftKeyDown()) {
            Level world = event.getLevel();
            BlockPos pos = event.getPos();
            Block block = world.getBlockState(pos).getBlock();

            if (isValidBlock(block) && isPlayerInRange(player, pos) && !SitUtil.isOccupied(world, pos) && player.getMainHandItem().isEmpty()) {
                // Bei Bank, die nur einen Block hoch ist extra if mit world.getBlockState(pos.above()).isAir()

                SitEntity sit = new SitEntity(world, pos);

                if (SitUtil.addSitEntity(world, pos, sit, player.blockPosition())) {
                    world.addFreshEntity(sit);
                    player.startRiding(sit);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onBreak(BreakEvent event) {
        if (!event.getLevel().isClientSide()) {
            // BreakEvent gets a World in its constructor, so the cast is safe
            SitEntity entity = SitUtil.getSitEntity((Level) event.getLevel(), event.getPos());

            if (entity != null) {
                SitUtil.removeSitEntity((Level) event.getLevel(), event.getPos());
                entity.ejectPassengers();
            }
        }
    }

    /**
     * Returns whether the given block can be sat on
     *
     * @param block The block to check
     * @return true if the given block can be sat one, false otherwise
     */
    private static boolean isValidBlock(Block block) {
        return block instanceof ChairBlock || block instanceof BenchBlock;
    }

    /**
     * Returns whether the player is close enough to the block to be able to sit on it
     *
     * @param player The player
     * @param pos The position of the block to sit on
     * @return true if the player is close enough, false otherwise
     */
    private static boolean isPlayerInRange(Player player, BlockPos pos) {
        BlockPos playerPos = player.blockPosition();
        int blockReachDistance = 4;

        pos = BlockPos.containing(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D);

        AABB range = new AABB(pos.getX() + blockReachDistance, pos.getY() + blockReachDistance, pos.getZ() + blockReachDistance, pos.getX() - blockReachDistance, pos.getY() - blockReachDistance, pos.getZ() - blockReachDistance);

        playerPos = BlockPos.containing(playerPos.getX() + 0.5D, playerPos.getY() + 0.5D, playerPos.getZ() + 0.5D);
        return range.minX <= playerPos.getX() && range.minY <= playerPos.getY() && range.minZ <= playerPos.getZ() && range.maxX >= playerPos.getX() && range.maxY >= playerPos.getY() && range.maxZ >= playerPos.getZ();
    }
}