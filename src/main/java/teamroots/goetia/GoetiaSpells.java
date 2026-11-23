package teamroots.goetia;
import com.google.gson.Gson;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.hurtingprojectile.LargeFireball;
import net.minecraft.world.level.block.BonemealableBlock;
public final class GoetiaSpells {
 public record Effect(String type,String tag){}
 public record Spell(int index,String name,String alignment,int cost,int[] shape,String icon,Effect effect,Integer duration){}
 public static final List<Spell> ALL=load();
 private static List<Spell> load(){try(var in=GoetiaSpells.class.getResourceAsStream("/data/goetia/spells.json")){if(in==null)throw new IllegalStateException("Missing spell catalogue");return List.copyOf(Arrays.asList(new Gson().fromJson(new InputStreamReader(in,StandardCharsets.UTF_8),Spell[].class)));}catch(Exception e){throw new IllegalStateException("Cannot load Goetia spells",e);}}
 public static boolean eligible(Player p,Spell spell){String key=spell.alignment().equals("DEMON")?"impurity":"purity";return GoetiaPlayer.lean(p).name().equals(spell.alignment())&&GoetiaPlayer.data(p).getIntOr(key,0)>=spell.cost();}
 public static boolean cast(Player p,String name){
  Spell s=ALL.stream().filter(v->v.name().equals(name)).findFirst().orElse(null);
  if(s==null||!eligible(p,s)||!GoetiaContent.holding(p,"soul_focus")||!(p.level() instanceof ServerLevel level))return false;
  var d=GoetiaPlayer.data(p);
  switch(s.effect().type()){
   case "timed_tag"->d.putInt(s.effect().tag(),s.duration());
   case "flight"->{d.putInt("wings_tag",s.duration());if(!p.isCreative()){p.getAbilities().mayfly=true;p.onUpdateAbilities();}}
   case "string_tag"->d.putString("smite","ready");
   case "large_fireball"->{var look=p.getLookAngle();var fire=new LargeFireball(level,p,look.scale(0.01),1);fire.setOwner(null);GoetiaFireballs.start(fire,look.scale(.01));fire.setPos(p.getX()+look.x,p.getY()+p.getEyeHeight()+look.y,p.getZ()+look.z);level.addFreshEntity(fire);}
   case "growable_direct"->{for(int x=0;x<10;x++)for(int z=0;z<10;z++)for(int y=0;y<2;y++){var pos=p.blockPosition().offset(x,y,z);var state=level.getBlockState(pos);if(state.getBlock() instanceof BonemealableBlock grow)grow.performBonemeal(level,level.getRandom(),pos,state);}}
   case "lightning"->{for(int i=0;i<8;i++){var bolt=EntityType.LIGHTNING_BOLT.create(level,net.minecraft.world.entity.EntitySpawnReason.TRIGGERED);if(bolt!=null){bolt.snapTo(p.getX()+level.getRandom().nextInt(17)-5,p.getY(),p.getZ()+level.getRandom().nextInt(17)-5);level.addFreshEntity(bolt);}}}
   default->throw new IllegalStateException("Unknown spell effect "+s.effect().type());
  }
  GoetiaPlayer.sync(p);return true;
 }
 private GoetiaSpells(){}
}
