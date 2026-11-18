package teamroots.goetia.client;
import com.google.gson.*;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.core.Direction;
public final class LegacyGeometry {
 private final Map<String,ModelPart> parts=new LinkedHashMap<>();
 public LegacyGeometry(String name){try(var in=LegacyGeometry.class.getResourceAsStream("/assets/goetia/legacy_models/"+name.toLowerCase()+".json")){if(in==null)throw new IllegalStateException(name);var root=JsonParser.parseReader(new InputStreamReader(in,StandardCharsets.UTF_8)).getAsJsonObject().getAsJsonObject("parts");for(String key:root.keySet())build(key,root);}catch(Exception e){throw new IllegalStateException("Cannot load original geometry "+name,e);}}
 private ModelPart build(String key,JsonObject all){if(parts.containsKey(key))return parts.get(key);var p=all.getAsJsonObject(key);var cubes=new ArrayList<ModelPart.Cube>();var uv=p.getAsJsonArray("uv");for(var b:p.getAsJsonArray("boxes")){var box=b.getAsJsonObject();var a=box.getAsJsonArray("args");var tex=box.getAsJsonArray("texture");float expand=a.size()>6?a.get(6).getAsFloat():0;cubes.add(new ModelPart.Cube(uv.get(0).getAsInt(),uv.get(1).getAsInt(),f(a,0),f(a,1),f(a,2),f(a,3),f(a,4),f(a,5),expand,expand,expand,box.get("mirror").getAsBoolean(),tex.get(0).getAsFloat(),tex.get(1).getAsFloat(),EnumSet.allOf(Direction.class)));}var children=new LinkedHashMap<String,ModelPart>();for(var c:p.getAsJsonArray("children"))children.put(c.getAsString(),build(c.getAsString(),all));var model=new ModelPart(cubes,children);var pivot=p.getAsJsonArray("pivot");model.setPos(f(pivot,0),f(pivot,1),f(pivot,2));var rot=p.getAsJsonArray("rotation");model.setRotation(f(rot,0),f(rot,1),f(rot,2));parts.put(key,model);return model;}
 private static float f(JsonArray a,int i){return a.get(i).getAsFloat();}
 public void draw(String name,PoseStack pose,VertexConsumer out,int light,int overlay,int color){var p=parts.get(name);if(p==null)throw new IllegalArgumentException(name);p.render(pose,out,light,overlay,((color>>>16)&255)/255F,((color>>>8)&255)/255F,(color&255)/255F,((color>>>24)&255)/255F);}
}
