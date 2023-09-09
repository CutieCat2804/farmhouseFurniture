package github.cutiecat2804.farmhousefurniture;

import github.cutiecat2804.farmhousefurniture.init.BlockInit;
import github.cutiecat2804.farmhousefurniture.init.CreativeTabInit;
import github.cutiecat2804.farmhousefurniture.init.ItemInit;
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
