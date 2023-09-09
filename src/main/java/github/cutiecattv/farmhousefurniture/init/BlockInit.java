package github.cutiecattv.farmhousefurniture.init;

import github.cutiecattv.farmhousefurniture.FarmhouseFurniture;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockInit {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, FarmhouseFurniture.MODID);

    public static final RegistryObject<Block> EXAMPLE_BLOCK = BLOCKS.register("example_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GREEN)
                    .strength(5.0f, 17f)
                    .instrument(NoteBlockInstrument.BELL)
                    .lightLevel(state -> 10)
                    .requiresCorrectToolForDrops()
            ));


    public static final RegistryObject<Block> CUP = BLOCKS.register("cup",
            () -> new CupBlock(BlockBehaviour.Properties.of()
                    .isViewBlocking((blockState, blockGetter, blockPos) -> false)
                    .noOcclusion().instabreak()
                    .pushReaction(PushReaction.DESTROY)
            ));
}