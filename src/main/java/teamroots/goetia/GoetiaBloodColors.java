package teamroots.goetia;
import com.google.gson.JsonParser;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
/** Legacy primary spawn-egg colors, retained after vanilla removed the runtime color API. */
public final class GoetiaBloodColors {
 private static final Map<String,Integer> COLORS=load();
 private static Map<String,Integer> load(){try(var in=GoetiaBloodColors.class.getResourceAsStream("/data/goetia/blood_colors.json")){if(in==null)throw new IllegalStateException("Missing blood colors");var map=new HashMap<String,Integer>();JsonParser.parseReader(new InputStreamReader(in,StandardCharsets.UTF_8)).getAsJsonObject().entrySet().forEach(e->map.put(e.getKey(),e.getValue().getAsInt()));return map;}catch(Exception e){throw new IllegalStateException(e);}}
 public static int color(EntityType<?> entity){return COLORS.getOrDefault(BuiltInRegistries.ENTITY_TYPE.getKey(entity).toString(),0xFFFFFF);}
 private GoetiaBloodColors(){}
}
