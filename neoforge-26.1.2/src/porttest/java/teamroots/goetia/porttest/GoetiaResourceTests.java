package teamroots.goetia.porttest;
import teamroots.goetia.*;
import net.minecraft.gametest.framework.*;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.*;
import net.minecraft.world.level.storage.loot.parameters.*;
public final class GoetiaResourceTests {
 public static void allRecipesLoadAndCrossRequiresPristine(GameTestHelper h){
  var manager=h.getLevel().getServer().getRecipeManager();for(String name:new String[]{"altar","angel_candle","angel_candle_stand","blood_knife","bottle_holy_water","candle","candle_stand","demonic_chalk","demon_candle","demon_candle_stand","holy_cross","leather","soul_focus","tallow"})h.assertTrue(manager.byKey(ResourceKey.create(Registries.RECIPE,Identifier.fromNamespaceAndPath("goetia",name))).isPresent(),"recipe loaded: "+name);
  var recipe=manager.byKey(ResourceKey.create(Registries.RECIPE,Identifier.fromNamespaceAndPath("goetia","bottle_holy_water"))).orElseThrow().value();var ingredient=recipe.placementInfo().ingredients().get(1);var fresh=new ItemStack(GoetiaContent.item("holy_cross"));var worn=fresh.copy();worn.setDamageValue(1);h.assertTrue(ingredient.test(fresh)&&!ingredient.test(worn),"holy water recipe accepts pristine cross and rejects worn cross");h.assertTrue(fresh.getItem().getCraftingRemainder(fresh)!=null&&fresh.getItem().getCraftingRemainder(fresh).create().getItem()==fresh.getItem(),"cross survives crafting as original container item");h.succeed();
 }
 public static void dungeonLootActuallyGeneratesBothNoteFamilies(GameTestHelper h){
  var w=h.getLevel();var table=w.getServer().reloadableRegistries().getLootTable(ResourceKey.create(Registries.LOOT_TABLE,Identifier.withDefaultNamespace("chests/simple_dungeon")));var params=new LootParams.Builder(w).withParameter(LootContextParams.ORIGIN,net.minecraft.world.phys.Vec3.atCenterOf(h.absolutePos(new net.minecraft.core.BlockPos(3,2,3)))).withLuck(0).create(LootContextParamSets.CHEST);boolean angel=false,demon=false;
  for(long seed=1;seed<=32;seed++){int count=0;for(var s:table.getRandomItems(params,seed)){var id=net.minecraft.core.registries.BuiltInRegistries.ITEM.getKey(s.getItem()).toString();if(id.startsWith("goetia:lost_angel_notes_")){angel=true;count+=s.getCount();}if(id.startsWith("goetia:lost_demon_notes_")){demon=true;count+=s.getCount();}}h.assertTrue(count>=1&&count<=3,"dungeon note count follows released pool: "+count);}
  h.assertTrue(angel&&demon,"actual dungeon rolls include both note families");h.succeed();
 }
}
