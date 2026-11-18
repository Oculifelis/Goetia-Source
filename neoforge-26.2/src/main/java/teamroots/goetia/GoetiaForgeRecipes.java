package teamroots.goetia;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import com.google.gson.JsonParser;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import teamroots.goetia.recipes.*;
public final class GoetiaForgeRecipes {
 private static boolean initialized;
 // Component bindings are available at the bound-data event, after common setup.
 public static synchronized void setup(){
  if(initialized)return;
  try(var in=GoetiaForgeRecipes.class.getResourceAsStream("/data/goetia/forge_recipes.json")){
   if(in==null)throw new IllegalStateException("Missing forge recipes");
   for(var value:JsonParser.parseReader(new InputStreamReader(in,StandardCharsets.UTF_8)).getAsJsonArray()){
    var r=value.getAsJsonObject();var input=Identifier.parse(r.get("input").getAsString());var output=Identifier.parse(r.get("output").getAsString());
    if(!BuiltInRegistries.ITEM.containsKey(input)||!BuiltInRegistries.ITEM.containsKey(output))throw new IllegalStateException("Unresolved forge recipe");
    var item=BuiltInRegistries.ITEM.getValue(input);
    if(!DemonForgeRecipeManager.recipes.containsKey(item))DemonForgeRecipeManager.instance.addDemonForgeRecipe(item,new DemonForgeRecipe(new ItemStack(BuiltInRegistries.ITEM.getValue(output),r.get("count").getAsInt()),r.get("cost").getAsInt()));
   }
  }catch(Exception e){throw new IllegalStateException("Cannot load Goetia forge recipes",e);}
  initialized=true;
 }
 private GoetiaForgeRecipes(){}
}
