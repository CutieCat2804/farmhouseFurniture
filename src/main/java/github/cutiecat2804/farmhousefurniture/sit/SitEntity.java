package github.cutiecat2804.farmhousefurniture.sit;

import github.cutiecat2804.farmhousefurniture.FarmhouseFurniture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class SitEntity extends Entity {
    public SitEntity(EntityType<SitEntity> type, Level world) {
        super(type, world);
    }

    public SitEntity(Level world, BlockPos pos) {
        super(FarmhouseFurniture.SIT_ENTITY_TYPE.get(), world);
        setPos(pos.getX() + 0.5D, pos.getY() + 0.25D, pos.getZ() + 0.5D);
        noPhysics = true;
    }

    @Override
    public @NotNull Vec3 getDismountLocationForPassenger(@NotNull LivingEntity passenger) {
        if (passenger instanceof Player player) {
            BlockPos pos = SitUtil.getPreviousPlayerPosition(player, this);

            if (pos != null) {
                Vec3 resetPosition = new Vec3(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);
                BlockPos belowResetPos = BlockPos.containing(resetPosition.x, resetPosition.y - 1, resetPosition.z);

                discard();

                if (!player.level().getBlockState(belowResetPos).isFaceSturdy(level(), belowResetPos, Direction.UP, SupportType.FULL))
                    return new Vec3(resetPosition.x, resetPosition.y + 1, resetPosition.z);
                else
                    return resetPosition;
            }
        }

        discard();
        return super.getDismountLocationForPassenger(passenger);
    }

    @Override
    public void remove(@NotNull RemovalReason reason) {
        super.remove(reason);

        SitUtil.removeSitEntity(level(), blockPosition());
    }

    @Override
    protected void defineSynchedData() {}

    @Override
    protected void readAdditionalSaveData(@NotNull CompoundTag tag) {}

    @Override
    protected void addAdditionalSaveData(@NotNull CompoundTag tag) {}

}