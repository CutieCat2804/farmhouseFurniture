package github.cutiecat2804.farmhousefurniture.datagen;

import github.cutiecat2804.farmhousefurniture.FarmhouseFurniture;
import github.cutiecat2804.farmhousefurniture.block.*;
import github.cutiecat2804.farmhousefurniture.datagen.blockstateprovider.TableBlockStateProvider;
import github.cutiecat2804.farmhousefurniture.enums.DishColor;
import github.cutiecat2804.farmhousefurniture.init.BlockInit;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ModBlockStateProvider extends BlockStateProvider {

    private final ResourceLocation resLoc = this.modLoc(FarmhouseFurniture.MODID);

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, FarmhouseFurniture.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(BlockInit.EXAMPLE_BLOCK);
        registerCup();
        registerPlate();
        registerNewspaper();
        registerNewspaperStand();
        TableBlockStateProvider.registerTable(this, resLoc, BlockInit.GREY_WOOD_TABLE.get(), "grey_wood_table");
        TableBlockStateProvider.registerTable(this, resLoc, BlockInit.BLUE_WOOD_TABLE.get(), "blue_wood_table");
        TableBlockStateProvider.registerTable(this, resLoc, BlockInit.OAK_WOOD_TABLE.get(), "oak_wood_table");
        TableBlockStateProvider.registerTable(this, resLoc, BlockInit.DARK_WOOD_TABLE.get(), "dark_wood_table");
    }

    // Erstellt simple Models und BlockStates
    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));

    }

    private void registerNewspaperStand() {
        simpleBlockItem(BlockInit.NEWSPAPER_STAND.get(), this.models().getExistingFile(resLoc.withPath("block/newspaper/newspaper_stand")));

        this.getVariantBuilder(BlockInit.NEWSPAPER_STAND.get())
                .forAllStates(state ->
                        ConfiguredModel.builder()
                                .modelFile(this.models().getExistingFile(resLoc.withPath("block/newspaper/newspaper_stand")))
                                .rotationY(((int) state.getValue(DishBlockUtils.FACING).toYRot() + 180) % 360)
                                .build()
                );
    }

    private void registerNewspaper() {
        simpleBlockItem(BlockInit.NEWSPAPER.get(), this.models().getExistingFile(resLoc.withPath("block/newspaper/newspaper_one_newspaper")));

        this.getVariantBuilder(BlockInit.NEWSPAPER.get())
                .forAllStates(state ->
                        {
                            String path = switch (state.getValue(NewspaperBlock.NEWSPAPERS)) {
                                default -> "block/newspaper/newspaper_one_newspaper";
                                case 2 -> "block/newspaper/newspaper_two_newspapers";
                                case 3 -> "block/newspaper/newspaper_three_newspapers";
                            };

                            return ConfiguredModel.builder()
                                    .modelFile(this.models().getExistingFile(resLoc.withPath(path)))
                                    .rotationY(((int) state.getValue(DishBlockUtils.FACING).toYRot() + 180) % 360)
                                    .build();
                        }
                );
    }

    private void registerCup() {
        var colors = Arrays.stream(DishColor.values()).map(DishColor::getSerializedName).collect(Collectors.toList());
        createColorVariants("block/cup/cup_one_cup", "block/dish/dish", colors);
        createColorVariants("block/cup/cup_two_cups", "block/dish/dish", colors);
        createColorVariants("block/cup/cup_three_cups", "block/dish/dish", colors);

        simpleBlockItem(BlockInit.CUP.get(), this.models().getExistingFile(resLoc.withPath("block/cup/cup_one_cup_white")));

        this.getVariantBuilder(BlockInit.CUP.get())
                .forAllStates(state ->
                        {
                            String path = switch (state.getValue(CupBlock.CUPS)) {
                                default -> "block/cup/cup_one_cup_" + state.getValue(DishBlockUtils.COLOR).getSerializedName();
                                case 2 -> "block/cup/cup_two_cups_" + state.getValue(DishBlockUtils.COLOR).getSerializedName();
                                case 3 ->
                                        "block/cup/cup_three_cups_" + state.getValue(DishBlockUtils.COLOR).getSerializedName();
                            };

                            return ConfiguredModel.builder()
                                    .modelFile(this.models().getExistingFile(resLoc.withPath(path)))
                                    .rotationY(((int) state.getValue(DishBlockUtils.FACING).toYRot() + 270) % 360)
                                    .build();
                        }
                );


    }

    private void registerPlate() {
        var colors = Arrays.stream(DishColor.values()).map(DishColor::getSerializedName).collect(Collectors.toList());
        createColorVariants("block/plate/plate_one_plate", "block/dish/dish", colors);
        createColorVariants("block/plate/plate_two_plates", "block/dish/dish", colors);
        createColorVariants("block/plate/plate_three_plates", "block/dish/dish", colors);
        createColorVariants("block/plate/plate_with_cup", "block/dish/dish", colors);

        // Erstellt das item
        simpleBlockItem(BlockInit.PLATE.get(), this.models().getExistingFile(resLoc.withPath("block/plate/plate_one_plate_white")));

        // Loopt durch alle BlockState Props durch und erstellt daraus den BlockState
        this.getVariantBuilder(BlockInit.PLATE.get())
                .forAllStates(state ->
                        {
                            String path = switch (state.getValue(PlateBlock.PLATES)) {
                                default ->
                                        "block/plate/plate_one_plate_" + state.getValue(DishBlockUtils.COLOR).getSerializedName();
                                case 2 ->
                                        "block/plate/plate_two_plates_" + state.getValue(DishBlockUtils.COLOR).getSerializedName();
                                case 3 ->
                                        "block/plate/plate_three_plates_" + state.getValue(DishBlockUtils.COLOR).getSerializedName();
                            };

                            if (state.getValue(PlateBlock.PLATES) == 1 && state.getValue(PlateBlock.CUPS) == 1) {
                                path = "block/plate/plate_with_cup_" + state.getValue(DishBlockUtils.COLOR).getSerializedName();
                            }

                            return ConfiguredModel.builder()
                                    .modelFile(this.models().getExistingFile(resLoc.withPath(path)))
                                    // Fügt die benötigte Rotation hinzu
                                    .rotationY(((int) state.getValue(DishBlockUtils.FACING).toYRot() + 180) % 360)
                                    .build();
                        }
                );

    }

    // Erstellt für alle definierten Farben ein Model mit der gegebenen Texture
    private void createColorVariants(String modelPath, String texturePath, List<String> colors) {
        var model = this.models().getExistingFile(resLoc.withPath(modelPath));

        for (var color : colors) {
            this.models().getBuilder(modelPath + "_" + color).parent(model)
                    .texture("1", FarmhouseFurniture.MODID + ":" + texturePath + "_" + color)
                    .texture("particle", FarmhouseFurniture.MODID + ":" + texturePath + "_" + color);
        }

    }
}
