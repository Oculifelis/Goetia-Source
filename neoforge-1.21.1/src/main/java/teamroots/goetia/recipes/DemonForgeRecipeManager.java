package teamroots.goetia.recipes;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.world.item.Item;
public class DemonForgeRecipeManager {
 public static Map<Item,DemonForgeRecipe> recipes=new HashMap<>();
 public static DemonForgeRecipeManager instance=new DemonForgeRecipeManager();
 public void addDemonForgeRecipe(Item input,DemonForgeRecipe recipe){recipes.put(input,recipe);}
}
