package de.teamlapen.vampirismaddonexample;

import de.teamlapen.vampirism.api.VReference;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod.EventBusSubscriber
@Mod(value = VampirismAddonExampleMod.MODID)
public class VampirismAddonExampleMod {

    public static final String MODID = "vampirism-addon-example";
    private final static Logger LOGGER = LogManager.getLogger();
    public static VampirismAddonExampleMod instance;
    private EntityType<EntityStrongVampire> strongVampireEntityType;

    public VampirismAddonExampleMod() {
        instance = this;
        FMLJavaModLoadingContext.get().getModEventBus().register(this);

    }

    @SubscribeEvent
    public void init(FMLClientSetupEvent event) {

        //Should probably be handled in in a separate class to allow this to run on dedicated servers
        RenderingRegistry.registerEntityRenderingHandler(strongVampireEntityType, manager -> new BipedRenderer<>(manager, new BipedModel<>(1), 1F));

    }

    @SubscribeEvent
    public void setup(FMLCommonSetupEvent event) {
        GlobalEntityTypeAttributes.put(strongVampireEntityType, EntityStrongVampire.getAttributeBuilder().create());
    }

    @SubscribeEvent
    public void onEntityRegistration(RegistryEvent.Register<EntityType<?>> event) {
        strongVampireEntityType = EntityType.Builder.create(EntityStrongVampire::new, VReference.VAMPIRE_CREATURE_TYPE).size(0.6f, 1.8f).setTrackingRange(80).setUpdateInterval(1).setShouldReceiveVelocityUpdates(true).build(MODID + ":strong_vampire");
        strongVampireEntityType.setRegistryName(MODID, "strong_vampire");
        event.getRegistry().register(strongVampireEntityType);

        LOGGER.debug("Registered entity");
    }


}
