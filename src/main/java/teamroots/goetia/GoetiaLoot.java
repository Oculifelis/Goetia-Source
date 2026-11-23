package teamroots.goetia;
import com.mojang.serialization.MapCodec;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
public final class GoetiaLoot extends LootModifier {
 public static final MapCodec<GoetiaLoot> CODEC=com.mojang.serialization.codecs.RecordCodecBuilder.mapCodec(i->codecStart(i).apply(i,GoetiaLoot::new));
 public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> TYPES=DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS,"goetia");
 static {TYPES.register("ancient_notes",()->CODEC);}
 private GoetiaLoot(LootItemCondition[] conditions){super(conditions);}
 @Override public MapCodec<? extends IGlobalLootModifier> codec(){return CODEC;}
 @Override protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> loot,LootContext context){var random=context.getRandom();int outer=1+Mth.floor(random.nextFloat()*context.getLuck());for(int j=0;j<outer;j++){int rolls=1+random.nextInt(3);for(int i=0;i<rolls;i++){String family=random.nextInt(20)<10?"lost_demon_notes_":"lost_angel_notes_";loot.add(new ItemStack(GoetiaContent.item(family+random.nextInt(6))));}}return loot;}
 public static void register(IEventBus bus){TYPES.register(bus);}
}
