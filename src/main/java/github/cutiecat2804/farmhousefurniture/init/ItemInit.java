package github.cutiecat2804.farmhousefurniture.init;

import github.cutiecat2804.farmhousefurniture.FarmhouseFurniture;
import github.cutiecat2804.farmhousefurniture.item.TwoBlockHighBlockItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static github.cutiecat2804.farmhousefurniture.init.CreativeTabInit.addToTab;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, FarmhouseFurniture.MODID);

    public static final RegistryObject<Item> EXAMPLE_ITEM = addToTab(ITEMS.register("example_item",
            () -> new Item(new Item.Properties()
                    .stacksTo(16)
                    .food(new FoodProperties.Builder()
                            .nutrition(5)
                            .saturationMod(0.2f)
                            .effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 200, 2), 1f)
                            .build())
            )));


    public static final RegistryObject<Item> PAINTBRUSH = addToTab(ITEMS.register("paintbrush",
            () -> new Item(new Item.Properties()
                    .stacksTo(1)
            )));

    public static final RegistryObject<BlockItem> EXAMPLE_BLOCK_ITEM = addToTab(ITEMS.register("example_block",
            () -> new BlockItem(BlockInit.EXAMPLE_BLOCK.get(), new Item.Properties())));

    public static final RegistryObject<BlockItem> CUP = addToTab(ITEMS.register("cup",
            () -> new BlockItem(BlockInit.CUP.get(), new Item.Properties())));

    public static final RegistryObject<BlockItem> PLATE = addToTab(ITEMS.register("plate",
            () -> new BlockItem(BlockInit.PLATE.get(), new Item.Properties())));

    public static final RegistryObject<BlockItem> NEWSPAPER = addToTab(ITEMS.register("newspaper",
            () -> new BlockItem(BlockInit.NEWSPAPER.get(), new Item.Properties())));

    public static final RegistryObject<BlockItem> NEWSPAPER_STAND = addToTab(ITEMS.register("newspaper_stand",
            () -> new BlockItem(BlockInit.NEWSPAPER_STAND.get(), new Item.Properties())));

    public static final RegistryObject<BlockItem> GRAY_WOOD_TABLE = addToTab(ITEMS.register("gray_wood_table",
            () -> new BlockItem(BlockInit.GRAY_WOOD_TABLE.get(), new Item.Properties())));

    public static final RegistryObject<BlockItem> BLUE_WOOD_TABLE = addToTab(ITEMS.register("blue_wood_table",
            () -> new BlockItem(BlockInit.BLUE_WOOD_TABLE.get(), new Item.Properties())));

    public static final RegistryObject<BlockItem> OAK_WOOD_TABLE = addToTab(ITEMS.register("oak_wood_table",
            () -> new BlockItem(BlockInit.OAK_WOOD_TABLE.get(), new Item.Properties())));

    public static final RegistryObject<BlockItem> DARK_WOOD_TABLE = addToTab(ITEMS.register("dark_wood_table",
            () -> new BlockItem(BlockInit.DARK_WOOD_TABLE.get(), new Item.Properties())));

    public static final RegistryObject<BlockItem> GRAY_WOOD_CHAIR = addToTab(ITEMS.register("gray_wood_chair",
            () -> new TwoBlockHighBlockItem(BlockInit.GRAY_WOOD_CHAIR.get(), new Item.Properties()))); // TwoBlockHighBlockItem :) Eigenes BlockItem, place Methode überschreiben, if mit above.isAir, wenn nicht dann fail sonst super aufrufen
}
