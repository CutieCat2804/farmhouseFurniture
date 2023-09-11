package github.cutiecat2804.farmhousefurniture.datagen;

import github.cutiecat2804.farmhousefurniture.FarmhouseFurniture;
import github.cutiecat2804.farmhousefurniture.block.CupBlock;
import github.cutiecat2804.farmhousefurniture.block.DishBlockUtils;
import github.cutiecat2804.farmhousefurniture.block.PlateBlock;
import github.cutiecat2804.farmhousefurniture.enums.PlateColors;
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
    }

    // Erstellt simple Models und BlockStates
    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));

    }

    private void registerCup() {
        var colors = Arrays.stream(PlateColors.values()).map(PlateColors::getSerializedName).collect(Collectors.toList());
        createColorVariants("block/cup/cup_one_cup", "block/plate/plate", colors);
        createColorVariants("block/cup/cup_two_cups", "block/plate/plate", colors);
        createColorVariants("block/cup/cup_three_cups", "block/plate/plate", colors);

        simpleBlockItem(BlockInit.CUP.get(), this.models().getExistingFile(resLoc.withPath("block/cup/cup_one_cup_white")));

        this.getVariantBuilder(BlockInit.CUP.get())
                .forAllStates(state ->
                        {
                            String path = switch (state.getValue(CupBlock.CUPS)) {
                                default ->
                                        "block/cup/cup_one_cup_" + state.getValue(DishBlockUtils.COLOR).getSerializedName();
                                case 2 ->
                                        "block/cup/cup_two_cups_" + state.getValue(DishBlockUtils.COLOR).getSerializedName();
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
        var colors = Arrays.stream(PlateColors.values()).map(PlateColors::getSerializedName).collect(Collectors.toList());
        createColorVariants("block/plate/plate_one_plate", "block/plate/plate", colors);
        createColorVariants("block/plate/plate_two_plates", "block/plate/plate", colors);
        createColorVariants("block/plate/plate_three_plates", "block/plate/plate", colors);
        createColorVariants("block/plate/plate_with_cup", "block/plate/plate", colors);

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
