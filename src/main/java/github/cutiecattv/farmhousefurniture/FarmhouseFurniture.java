package github.cutiecattv.farmhousefurniture;

import github.cutiecattv.farmhousefurniture.init.BlockInit;
import github.cutiecattv.farmhousefurniture.init.CreativeTabInit;
import github.cutiecattv.farmhousefurniture.init.ItemInit;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(FarmhouseFurniture.MODID)
public class FarmhouseFurniture {
    public static final String MODID = "farmhousefurniture";

    public FarmhouseFurniture() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ItemInit.ITEMS.register(bus);
        BlockInit.BLOCKS.register(bus);
        CreativeTabInit.TABS.register(bus);
    }
}
