package github.cutiecat2804.farmhousefurniture.init;

import github.cutiecat2804.farmhousefurniture.block.*;
import github.cutiecat2804.farmhousefurniture.FarmhouseFurniture;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
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
                    .noOcclusion()
                    .instabreak()
                    .pushReaction(PushReaction.DESTROY)
            ));

    public static final RegistryObject<Block> PLATE = BLOCKS.register("plate",
            () -> new PlateBlock(BlockBehaviour.Properties.of()
                    .isViewBlocking((blockState, blockGetter, blockPos) -> false)
                    .noOcclusion()
                    .instabreak()
                    .pushReaction(PushReaction.DESTROY)
            ));

    public static final RegistryObject<Block> NEWSPAPER = BLOCKS.register("newspaper",
            () -> new NewspaperBlock(BlockBehaviour.Properties.of()
                    .isViewBlocking((blockState, blockGetter, blockPos) -> false)
                    .noOcclusion()
                    .instabreak()
                    .pushReaction(PushReaction.DESTROY)
            ));

    public static final RegistryObject<Block> NEWSPAPER_STAND = BLOCKS.register("newspaper_stand",
            () -> new NewspaperStandBlock(BlockBehaviour.Properties.of()
                    .isViewBlocking((blockState, blockGetter, blockPos) -> false)
                    .noOcclusion()
                    .strength(1.0f, 17f)
            ));

    public static final RegistryObject<Block> GRAY_WOOD_TABLE = BLOCKS.register("gray_wood_table",
            () -> new TableBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noOcclusion(), false, false));

    public static final RegistryObject<Block> BLUE_WOOD_TABLE = BLOCKS.register("blue_wood_table",
            () -> new TableBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noOcclusion(), true, true));

    public static final RegistryObject<Block> OAK_WOOD_TABLE = BLOCKS.register("oak_wood_table",
            () -> new TableBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noOcclusion(), true, true));

    public static final RegistryObject<Block> DARK_WOOD_TABLE = BLOCKS.register("dark_wood_table",
            () -> new TableBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noOcclusion(), true, false));

    public static final RegistryObject<Block> GRAY_WOOD_CHAIR = BLOCKS.register("gray_wood_chair",
            () -> new ChairBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noOcclusion(), true));

    public static final RegistryObject<Block> BLUE_WOOD_CHAIR = BLOCKS.register("blue_wood_chair",
            () -> new ChairBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noOcclusion(), false));

    public static final RegistryObject<Block> OAK_WOOD_CHAIR = BLOCKS.register("oak_wood_chair",
            () -> new ChairBlock2(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noOcclusion(), true));
}
