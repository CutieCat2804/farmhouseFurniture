package github.cutiecat2804.farmhousefurniture.datagen.blockstateprovider;

import github.cutiecat2804.farmhousefurniture.block.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;

public class TableBlockStateProvider {
    public static void registerTable(BlockStateProvider blockStateProvider, ResourceLocation resLoc, Block block, String modelName) {
        blockStateProvider.simpleBlockItem(block, blockStateProvider.models().getExistingFile(resLoc.withPath("block/table/" + modelName + "/" + modelName)));

        String pathTop = "block/table/" + modelName + "/" + modelName + "_top";
        String pathLeg = "block/table/" + modelName + "/" + modelName + "_leg";
        String pathSide = "block/table/" + modelName + "/" + modelName + "_side";
        String pathCorner1 = "block/table/" + modelName + "/" + modelName + "_corner1";
        String pathCorner2 = "block/table/" + modelName + "/" + modelName + "_corner2";

        blockStateProvider.getMultipartBuilder(block)
                .part()
                .modelFile(blockStateProvider.models().getExistingFile(resLoc.withPath(pathTop)))
                .addModel()
                .end()

                .part()
                .modelFile(blockStateProvider.models().getExistingFile(resLoc.withPath(pathLeg)))
                .addModel()
                .nestedGroup()
                .condition(TableBlock.NORTH, false)
                .condition(TableBlock.EAST, false)
                .end()
                .end()

                .part()
                .modelFile(blockStateProvider.models().getExistingFile(resLoc.withPath(pathLeg)))
                .rotationY(90)
                .addModel()
                .nestedGroup()
                .condition(TableBlock.SOUTH, false)
                .condition(TableBlock.EAST, false)
                .end()
                .end()

                .part()
                .modelFile(blockStateProvider.models().getExistingFile(resLoc.withPath(pathLeg)))
                .rotationY(180)
                .addModel()
                .nestedGroup()
                .condition(TableBlock.SOUTH, false)
                .condition(TableBlock.WEST, false)
                .end()
                .end()

                .part()
                .modelFile(blockStateProvider.models().getExistingFile(resLoc.withPath(pathLeg)))
                .rotationY(270)
                .addModel()
                .nestedGroup()
                .condition(TableBlock.NORTH, false)
                .condition(TableBlock.WEST, false)
                .end()
                .end()

                .part()
                .modelFile(blockStateProvider.models().getExistingFile(resLoc.withPath(pathSide)))
                .addModel()
                .nestedGroup()
                .condition(TableBlock.NORTH, false)
                .end()
                .end()

                .part()
                .modelFile(blockStateProvider.models().getExistingFile(resLoc.withPath(pathSide)))
                .rotationY(90)
                .addModel()
                .nestedGroup()
                .condition(TableBlock.EAST, false)
                .end()
                .end()

                .part()
                .modelFile(blockStateProvider.models().getExistingFile(resLoc.withPath(pathSide)))
                .rotationY(180)
                .addModel()
                .nestedGroup()
                .condition(TableBlock.SOUTH, false)
                .end()
                .end()

                .part()
                .modelFile(blockStateProvider.models().getExistingFile(resLoc.withPath(pathSide)))
                .rotationY(270)
                .addModel()
                .nestedGroup()
                .condition(TableBlock.WEST, false)
                .end()
                .end()

                .part()
                .modelFile(blockStateProvider.models().getExistingFile(resLoc.withPath(pathCorner1)))
                .addModel()
                .nestedGroup()
                .condition(TableBlock.EAST, true)
                .condition(TableBlock.NORTH, false)
                .end()
                .end()

                .part()
                .modelFile(blockStateProvider.models().getExistingFile(resLoc.withPath(pathCorner1)))
                .rotationY(90)
                .addModel()
                .nestedGroup()
                .condition(TableBlock.SOUTH, true)
                .condition(TableBlock.EAST, false)
                .end()
                .end()

                .part()
                .modelFile(blockStateProvider.models().getExistingFile(resLoc.withPath(pathCorner1)))
                .rotationY(180)
                .addModel()
                .nestedGroup()
                .condition(TableBlock.WEST, true)
                .condition(TableBlock.SOUTH, false)
                .end()
                .end()

                .part()
                .modelFile(blockStateProvider.models().getExistingFile(resLoc.withPath(pathCorner1)))
                .rotationY(270)
                .addModel()
                .nestedGroup()
                .condition(TableBlock.NORTH, true)
                .condition(TableBlock.WEST, false)
                .end()
                .end()

                .part()
                .modelFile(blockStateProvider.models().getExistingFile(resLoc.withPath(pathCorner2)))
                .addModel()
                .nestedGroup()
                .condition(TableBlock.NORTH, true)
                .condition(TableBlock.EAST, false)
                .end()
                .end()

                .part()
                .modelFile(blockStateProvider.models().getExistingFile(resLoc.withPath(pathCorner2)))
                .rotationY(90)
                .addModel()
                .nestedGroup()
                .condition(TableBlock.EAST, true)
                .condition(TableBlock.SOUTH, false)
                .end()
                .end()

                .part()
                .modelFile(blockStateProvider.models().getExistingFile(resLoc.withPath(pathCorner2)))
                .rotationY(180)
                .addModel()
                .nestedGroup()
                .condition(TableBlock.SOUTH, true)
                .condition(TableBlock.WEST, false)
                .end()
                .end()

                .part()
                .modelFile(blockStateProvider.models().getExistingFile(resLoc.withPath(pathCorner2)))
                .rotationY(270)
                .addModel()
                .nestedGroup()
                .condition(TableBlock.WEST, true)
                .condition(TableBlock.NORTH, false)
                .end()
                .end();

    }


}
