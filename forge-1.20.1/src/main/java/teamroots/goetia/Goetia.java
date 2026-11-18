package teamroots.goetia;
import java.util.function.BiConsumer;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.fml.common.Mod;
@Mod(Goetia.ID)
public final class Goetia {
 public static final String ID="goetia";
 public static java.util.function.Consumer<net.minecraft.world.entity.player.Player> tickVisual=p->{};
 public static BiConsumer<net.minecraft.world.entity.LivingEntity,net.minecraft.world.damagesource.DamageSource> attackVisual=(p,s)->{};
 public static BiConsumer<net.minecraft.core.BlockPos,net.minecraft.world.entity.player.Player> ritualVisual=(pos,p)->{};
 public static BiConsumer<String,InteractionHand> openScreen=(kind,hand)->{};
 public Goetia(){var bus=net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext.get().getModEventBus();GoetiaContent.register(bus);GoetiaNetwork.register(bus);GoetiaLoot.register(bus);bus.addListener((net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent e)->e.enqueueWork(GoetiaForgeRecipes::setup));net.minecraftforge.fml.ModLoadingContext.get().registerConfig(net.minecraftforge.fml.config.ModConfig.Type.COMMON,GoetiaConfig.SPEC);}
}
