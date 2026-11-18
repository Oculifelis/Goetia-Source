package teamroots.goetia;
import java.util.function.BiConsumer;
import net.minecraft.world.InteractionHand;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
@Mod(Goetia.ID)
public final class Goetia {
 public static final String ID="goetia";
 public static java.util.function.Consumer<net.minecraft.world.entity.player.Player> tickVisual=p->{};
 public static BiConsumer<net.minecraft.world.entity.LivingEntity,net.minecraft.world.damagesource.DamageSource> attackVisual=(p,s)->{};
 public static BiConsumer<net.minecraft.core.BlockPos,net.minecraft.world.entity.player.Player> ritualVisual=(pos,p)->{};
 public static BiConsumer<String,InteractionHand> openScreen=(kind,hand)->{};
 public Goetia(IEventBus bus,ModContainer container){
  GoetiaContent.register(bus);
  GoetiaNetwork.register(bus);
  GoetiaLoot.register(bus);
  bus.addListener((net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent e)->e.enqueueWork(GoetiaForgeRecipes::setup));
  container.registerConfig(ModConfig.Type.COMMON,GoetiaConfig.SPEC);
 }
}
