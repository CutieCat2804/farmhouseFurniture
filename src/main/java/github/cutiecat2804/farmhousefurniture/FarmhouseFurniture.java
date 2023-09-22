package github.cutiecat2804.farmhousefurniture;

import github.cutiecat2804.farmhousefurniture.init.BlockInit;
import github.cutiecat2804.farmhousefurniture.init.CreativeTabInit;
import github.cutiecat2804.farmhousefurniture.init.ItemInit;
import github.cutiecat2804.farmhousefurniture.sit.SitEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod(FarmhouseFurniture.MODID)
public class FarmhouseFurniture {
    public static final String MODID = "farmhousefurniture";
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MODID);
    public static final RegistryObject<EntityType<SitEntity>> SIT_ENTITY_TYPE = ENTITY_TYPES.register("entity_sit", () -> EntityType.Builder.<SitEntity>of(SitEntity::new, MobCategory.MISC)
            .setTrackingRange(256)
            .setUpdateInterval(20)
            .sized(0.0001F, 0.0001F)
            .build(MODID + ":entity_sit"));

    public FarmhouseFurniture() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ItemInit.ITEMS.register(bus);
        BlockInit.BLOCKS.register(bus);
        CreativeTabInit.TABS.register(bus);

        ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
