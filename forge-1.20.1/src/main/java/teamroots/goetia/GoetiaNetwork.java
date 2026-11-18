package teamroots.goetia;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
public final class GoetiaNetwork {
 public record Action(String kind,String value){}
 public record Sync(int entity,CompoundTag data){}
 private static final SimpleChannel CHANNEL=NetworkRegistry.ChannelBuilder.named(ResourceLocation.fromNamespaceAndPath("goetia","main")).networkProtocolVersion(()->"1").clientAcceptedVersions("1"::equals).serverAcceptedVersions("1"::equals).simpleChannel();
 public static void register(IEventBus bus){
  CHANNEL.messageBuilder(Action.class,0,NetworkDirection.PLAY_TO_SERVER).encoder((v,b)->{b.writeUtf(v.kind(),32);b.writeUtf(v.value(),64);}).decoder(b->new Action(b.readUtf(32),b.readUtf(64))).consumerMainThread((v,c)->{var p=c.get().getSender();if(p==null)return;if(v.kind().equals("cast"))GoetiaSpells.cast(p,v.value());else if(v.kind().equals("chalk")&&GoetiaContent.is(p.getMainHandItem(),"demonic_chalk")&&GoetiaPlayer.knowledge(p).contains(v.value())&&GoetiaPlayer.SYMBOLS.contains(v.value()))GoetiaItem.edit(p.getMainHandItem(),d->d.putString("knowledge",v.value()));}).add();
  CHANNEL.messageBuilder(Sync.class,1,NetworkDirection.PLAY_TO_CLIENT).encoder((v,b)->{b.writeVarInt(v.entity());b.writeNbt(v.data());}).decoder(b->new Sync(b.readVarInt(),b.readNbt())).consumerMainThread((v,c)->net.minecraftforge.fml.DistExecutor.unsafeRunWhenOn(net.minecraftforge.api.distmarker.Dist.CLIENT,()->()->teamroots.goetia.client.GoetiaSyncHandler.apply(v))).add();
 }
 public static void sendAction(Action action){CHANNEL.sendToServer(action);}
 public static void sendToPlayer(ServerPlayer p,Sync data){CHANNEL.send(PacketDistributor.PLAYER.with(()->p),data);}
 public static void sync(Player player){if(player instanceof ServerPlayer p){sendToPlayer(p,new Sync(p.getId(),GoetiaPlayer.data(p).copy()));CHANNEL.send(PacketDistributor.TRACKING_ENTITY.with(()->p),new Sync(p.getId(),publicData(p)));}}
 public static CompoundTag publicData(Player p){var source=GoetiaPlayer.data(p);var d=new CompoundTag();d.putInt("impurity",source.getInt("impurity"));d.putInt("purity",source.getInt("purity"));d.putBoolean("locked",source.getBoolean("locked"));return d;}
 private GoetiaNetwork(){}
}
