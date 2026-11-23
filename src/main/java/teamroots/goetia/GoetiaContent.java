package teamroots.goetia;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
public final class GoetiaContent {
 public static final DeferredRegister<Block> BLOCKS=DeferredRegister.create(Registries.BLOCK,"goetia");
 public static final DeferredRegister<Item> ITEMS=DeferredRegister.create(Registries.ITEM,"goetia");
 public static final DeferredRegister<CreativeModeTab> TABS=DeferredRegister.create(Registries.CREATIVE_MODE_TAB,"goetia");
 public static final Map<String,RegistryObject<Block>> BLOCK_MAP=new LinkedHashMap<>();
 public static final Map<String,RegistryObject<? extends Item>> ITEM_MAP=new LinkedHashMap<>();
 static {
  block("altar",()->new GoetiaBlock("altar",BlockBehaviour.Properties.of().strength(1).requiresCorrectToolForDrops()),true);
  for(String id:new String[]{"demon_candle","demon_candle_stand","angel_candle","angel_candle_stand","candle","candle_stand"})block(id,()->new GoetiaBlock(id,BlockBehaviour.Properties.of().strength(.4f).noOcclusion().lightLevel(s->15)),true);
  block("full_bowl",()->new GoetiaBlock("full_bowl",BlockBehaviour.Properties.of().noOcclusion().requiresCorrectToolForDrops()),false);
  block("empty_bowl",()->new GoetiaBlock("empty_bowl",BlockBehaviour.Properties.of().noOcclusion().requiresCorrectToolForDrops()),true);
  for(String id:new String[]{"block_angel_lost_note","block_demon_lost_note"})block(id,()->new GoetiaBlock(id,BlockBehaviour.Properties.of().strength(-1,0).noCollission().noOcclusion()),false);
  for(String id:new String[]{"demon_horn","imp_tallow","tallow","demon_hide","demonic_chalk","soul_focus","liquid_bottle","holy_cross","bottle_holy_water","symbol_icon","spell_icon"})item(id,()->new GoetiaItem(id));
  for(String prefix:new String[]{"lost_demon_notes_","lost_angel_notes_"})for(int i=0;i<6;i++){String id=prefix+i;item(id,()->new GoetiaItem(id));}
  for(String id:new String[]{"demon_horn_spear","abyssal_blade","blood_knife"})item(id,()->new GoetiaWeapon(id));
  TABS.register("main",()->CreativeModeTab.builder().title(Component.translatable("itemGroup.goetia")).icon(()->new ItemStack(item("demon_horn"))).displayItems((p,o)->ITEM_MAP.forEach((id,v)->{if(!id.equals("full_bowl")&&!id.startsWith("block_")&&!id.endsWith("_icon"))o.accept(v.get());})).build());
 }
 private static void block(String id,Supplier<Block> factory,boolean creative){var h=BLOCKS.register(id,factory);BLOCK_MAP.put(id,h);item(id,()->new BlockItem(h.get(),new Item.Properties()));}
 private static void item(String id,Supplier<? extends Item> factory){ITEM_MAP.put(id,ITEMS.register(id,factory));}
 public static Item item(String id){var h=ITEM_MAP.get(id);if(h==null)throw new IllegalArgumentException(id);return h.get();}
 public static Block block(String id){var h=BLOCK_MAP.get(id);if(h==null)throw new IllegalArgumentException(id);return h.get();}
 public static boolean is(ItemStack stack,String id){return !stack.isEmpty()&&stack.getItem()==item(id);}
 public static boolean holding(Player p,String id){return is(p.getMainHandItem(),id)||is(p.getOffhandItem(),id);}
 public static void register(IEventBus bus){BLOCKS.register(bus);ITEMS.register(bus);TABS.register(bus);GoetiaEntities.register(bus);GoetiaBlockData.TYPES.register(bus);GoetiaFluids.register(bus);}
 private GoetiaContent(){}
}
