package teamroots.goetia;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import com.google.gson.JsonParser;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import teamroots.goetia.recipes.*;
public final class GoetiaForgeRecipes {
 public static void setup(){try(var in=GoetiaForgeRecipes.class.getResourceAsStream("/data/goetia/forge_recipes.json")){if(in==null)throw new IllegalStateException("Missing forge recipes");for(var value:JsonParser.parseReader(new InputStreamReader(in,StandardCharsets.UTF_8)).getAsJsonArray()){var r=value.getAsJsonObject();var input=ResourceLocation.parse(r.get("input").getAsString());var output=ResourceLocation.parse(r.get("output").getAsString());if(!BuiltInRegistries.ITEM.containsKey(input)||!BuiltInRegistries.ITEM.containsKey(output))throw new IllegalStateException("Unresolved forge recipe");DemonForgeRecipeManager.instance.addDemonForgeRecipe(BuiltInRegistries.ITEM.get(input),new DemonForgeRecipe(new ItemStack(BuiltInRegistries.ITEM.get(output),r.get("count").getAsInt()),r.get("cost").getAsInt()));}}catch(Exception e){throw new IllegalStateException("Cannot load Goetia forge recipes",e);}}
 private GoetiaForgeRecipes(){}
}
