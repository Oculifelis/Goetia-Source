package teamroots.goetia;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
public final class GoetiaNetwork {
 public record Action(String kind,String value) implements CustomPacketPayload {
  public static final Type<Action> TYPE=new Type<>(Identifier.fromNamespaceAndPath("goetia","action"));
  public static final StreamCodec<RegistryFriendlyByteBuf,Action> CODEC=StreamCodec.of((b,v)->{b.writeUtf(v.kind,32);b.writeUtf(v.value,64);},b->new Action(b.readUtf(32),b.readUtf(64)));
  public Type<? extends CustomPacketPayload> type(){return TYPE;}
 }
 public record Sync(int entity,CompoundTag data) implements CustomPacketPayload {
  public static final Type<Sync> TYPE=new Type<>(Identifier.fromNamespaceAndPath("goetia","sync"));
  public static final StreamCodec<RegistryFriendlyByteBuf,Sync> CODEC=StreamCodec.of((b,v)->{b.writeVarInt(v.entity);b.writeNbt(v.data);},b->new Sync(b.readVarInt(),b.readNbt()));
  public Type<? extends CustomPacketPayload> type(){return TYPE;}
 }
 public static void register(IEventBus bus){bus.addListener(GoetiaNetwork::payloads);}
 private static void payloads(RegisterPayloadHandlersEvent e){var r=e.registrar("1");
  r.playToServer(Action.TYPE,Action.CODEC,(v,c)->c.enqueueWork(()->{if(!(c.player() instanceof ServerPlayer p))return;
   if(v.kind.equals("cast"))GoetiaSpells.cast(p,v.value);
   else if(v.kind.equals("chalk")&&GoetiaContent.is(p.getMainHandItem(),"demonic_chalk")&&GoetiaPlayer.knowledge(p).contains(v.value)&&GoetiaPlayer.SYMBOLS.contains(v.value)){GoetiaItem.edit(p.getMainHandItem(),d->d.putString("knowledge",v.value));}
  }));
  r.playToClient(Sync.TYPE,Sync.CODEC,(v,c)->c.enqueueWork(()->{var target=c.player().level().getEntity(v.entity);if(target instanceof Player p&&v.data!=null)p.getPersistentData().put("goetia",v.data);}));
 }
 public static void sync(Player player){if(player instanceof ServerPlayer p){var data=GoetiaPlayer.data(p).copy();PacketDistributor.sendToPlayer(p,new Sync(p.getId(),data.copy()));data=publicData(p);PacketDistributor.sendToPlayersTrackingEntity(p,new Sync(p.getId(),data));}}
 public static CompoundTag publicData(Player p){var source=GoetiaPlayer.data(p);var d=new CompoundTag();d.putInt("impurity",source.getIntOr("impurity",0));d.putInt("purity",source.getIntOr("purity",0));d.putBoolean("locked",source.getBooleanOr("locked",false));return d;}
 private GoetiaNetwork(){}
}
