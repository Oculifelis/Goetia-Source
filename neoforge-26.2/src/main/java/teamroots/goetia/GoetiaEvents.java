package teamroots.goetia;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import static net.minecraft.commands.Commands.literal;
import static net.minecraft.commands.Commands.argument;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.commands.arguments.EntityArgument;
@EventBusSubscriber(modid="goetia")
public final class GoetiaEvents {
 public static void livingTick(Player p){GoetiaPlayer.tick(p);if(p.level().isClientSide())Goetia.tickVisual.accept(p);if(GoetiaPlayer.data(p).contains("inner_fire_grace")&&p.onGround())p.setDeltaMovement(p.getDeltaMovement().multiply(1.4,1,1.4));}
 @SubscribeEvent public static void tick(PlayerTickEvent.Post event){var p=event.getEntity();if(!p.level().isClientSide()&&p.tickCount%20==0)GoetiaPlayer.sync(p);}
 @SubscribeEvent public static void clone(PlayerEvent.Clone e){if(e.isWasDeath())GoetiaPlayer.cloneDeath(e.getOriginal(),e.getEntity());else e.getEntity().getPersistentData().put("goetia",GoetiaPlayer.data(e.getOriginal()).copy());}
 @SubscribeEvent public static void login(PlayerEvent.PlayerLoggedInEvent e){GoetiaPlayer.sync(e.getEntity());}
 @SubscribeEvent public static void changed(PlayerEvent.PlayerChangedDimensionEvent e){GoetiaPlayer.sync(e.getEntity());}
 @SubscribeEvent public static void tracking(PlayerEvent.StartTracking e){if(e.getEntity() instanceof ServerPlayer viewer&&e.getTarget() instanceof Player target){var tag=GoetiaNetwork.publicData(target);net.neoforged.neoforge.network.PacketDistributor.sendToPlayer(viewer,new GoetiaNetwork.Sync(target.getId(),tag));}}

 @SubscribeEvent public static void death(LivingDeathEvent e){if(e.getEntity().level().isClientSide()||!e.getSource().is(DamageTypes.PLAYER_ATTACK)||!(e.getSource().getEntity() instanceof Player p))return;if(e.getEntity() instanceof Monster)GoetiaPlayer.add(p,"purity",2,false);if(e.getEntity() instanceof Animal)GoetiaPlayer.add(p,"impurity",1,false);
  if(GoetiaContent.is(p.getMainHandItem(),"blood_knife")){for(int i=0;i<p.getInventory().getContainerSize();i++){var bottle=p.getInventory().getItem(i);if(bottle.is(Items.GLASS_BOTTLE)){bottle.shrink(1);var out=new ItemStack(GoetiaContent.item("liquid_bottle"));var entity=e.getEntity();GoetiaItem.edit(out,d->{d.putString("entityName",BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).toString());d.putString("name",entity.getName().getString());d.putInt("color",GoetiaBloodColors.color(entity.getType()));});if(!p.getInventory().add(out))p.drop(out,false);break;}}}
 }
 @SubscribeEvent public static void commands(RegisterCommandsEvent e){var root=literal("goetia").executes(c->{c.getSource().sendSuccess(()->Component.literal("/goetia setpurity <amount> [player]; /goetia setimpurity <amount> [player]"),false);return 1;});root.then(literal("help").executes(c->{c.getSource().sendSuccess(()->Component.literal("Set purity or impurity with /goetia setpurity or setimpurity."),false);return 1;}));for(String key:new String[]{"purity","impurity"})root.then(literal("set"+key).requires(s->s.permissions().hasPermission(net.minecraft.server.permissions.Permissions.COMMANDS_GAMEMASTER)||s.getServer().isSingleplayer()).then(argument("amount",IntegerArgumentType.integer()).executes(c->{GoetiaPlayer.set(c.getSource().getPlayerOrException(),key,IntegerArgumentType.getInteger(c,"amount"));return 1;}).then(argument("player",EntityArgument.player()).executes(c->{GoetiaPlayer.set(EntityArgument.getPlayer(c,"player"),key,IntegerArgumentType.getInteger(c,"amount"));return 1;}))));e.getDispatcher().register(root);}
}
