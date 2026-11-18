package teamroots.goetia;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
public final class GoetiaEntities {
 public static final DeferredRegister<EntityType<?>> ENTITIES=DeferredRegister.create(Registries.ENTITY_TYPE,"goetia");
 public static final List<DeferredHolder<EntityType<?>,EntityType<GoetiaDemon>>> DEMONS=new ArrayList<>();
 public static final List<DeferredHolder<EntityType<?>,EntityType<GoetiaSymbol>>> SYMBOLS=new ArrayList<>();
 static {
  for(int i=0;i<3;i++){int kind=i;String id=new String[]{"imp","fiend","demon"}[i];var h=ENTITIES.register(id,()->EntityType.Builder.<GoetiaDemon>of((t,w)->new GoetiaDemon(t,w,kind),MobCategory.MONSTER).sized(new float[]{.6f,.8f,1.4f}[kind],new float[]{.8f,2,2.8f}[kind]).clientTrackingRange(4).updateInterval(3).build("goetia:"+id));DEMONS.add(h);var egg=GoetiaContent.ITEMS.register(id+"_spawn_egg",()->new DeferredSpawnEggItem(h,0x6A1024,0xA06774,new Item.Properties()));GoetiaContent.ITEM_MAP.put(id+"_spawn_egg",egg);}
  for(int i=0;i<6;i++){int kind=i;String id=new String[]{"symbol_imp","symbol_fiend","symbol_demon","symbol_devils_trap","symbol_forge","symbol_open_soul"}[i];SYMBOLS.add(ENTITIES.register(id,()->EntityType.Builder.<GoetiaSymbol>of((t,w)->new GoetiaSymbol(t,w,kind),MobCategory.MISC).sized(2,.5f).clientTrackingRange(4).updateInterval(3).build("goetia:"+id)));}
 }
 public static final DeferredHolder<EntityType<?>,EntityType<GoetiaBloodProjectile>> BLOOD=ENTITIES.register("blood_projectile",()->EntityType.Builder.<GoetiaBloodProjectile>of(GoetiaBloodProjectile::new,MobCategory.MISC).sized(.5f,.5f).clientTrackingRange(4).updateInterval(3).build("goetia:blood_projectile"));
 public static void register(IEventBus bus){ENTITIES.register(bus);bus.addListener(GoetiaEntities::attributes);}
 private static void attributes(EntityAttributeCreationEvent e){for(int i=0;i<3;i++)e.put(DEMONS.get(i).get(),GoetiaDemon.attributes(i).build());for(var h:SYMBOLS)e.put(h.get(),GoetiaSymbol.attributes().build());}
 private GoetiaEntities(){}
}
