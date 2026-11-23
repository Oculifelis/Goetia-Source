package teamroots.goetia;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.world.entity.player.Player;
public final class GoetiaPlayer {
 public enum Alignment { HUMAN,DEMON,ANGEL }
 public static final List<String> SYMBOLS=List.of("impSymbol","fiendSymbol","demonSymbol","devilsTrap","forgeSymbol","openSoulSymbol");
 public static final List<String> TIMERS=List.of("burning_touch","rebuke","fallen_armor","chained_strikes","wings_tag","voracious_strikes","inner_fire_grace");
 public static CompoundTag data(Player p){var root=p.getPersistentData();if(!root.contains("goetia"))root.put("goetia",new CompoundTag());return root.getCompoundOrEmpty("goetia");}
 public static Alignment lean(Player p){var d=data(p);return d.getIntOr("impurity",0)>d.getIntOr("purity",0)?Alignment.DEMON:Alignment.ANGEL;}
 public static Alignment alignment(Player p){var d=data(p);int t=((LegacyPlayerState)p).goetia$threshold();return d.getIntOr("purity",0)>=t?Alignment.ANGEL:d.getIntOr("impurity",0)>=t?Alignment.DEMON:Alignment.HUMAN;}
 public static void set(Player p,String key,int value){data(p).putInt(key,Math.max(0,value));sync(p);}
 public static void add(Player p,String key,int value,boolean regardless){var d=data(p);String other=key.equals("purity")?"impurity":"purity";if(regardless||(!d.getBooleanOr("locked",false)&&d.getIntOr(other,0)<((LegacyPlayerState)p).goetia$threshold())){d.putInt(key,d.getIntOr(key,0)+value);sync(p);}}
 public static List<String> knowledge(Player p){var list=data(p).getListOrEmpty("knowledge");var out=new ArrayList<String>();for(var tag:list)tag.asString().ifPresent(out::add);return out;}
 public static void learn(Player p,String key){var d=data(p);var list=d.getListOrEmpty("knowledge");list.add(StringTag.valueOf(key));d.put("knowledge",list);sync(p);}
 public static void tick(Player p){var d=data(p);for(String key:TIMERS)if(d.contains(key)){int n=d.getIntOr(key,0)-1;if(n<=0)d.remove(key);else d.putInt(key,n);}}
 public static void sync(Player p){GoetiaNetwork.sync(p);}
 public static void cloneDeath(Player old,Player replacement){var prior=data(old);var fresh=new CompoundTag();fresh.putInt("impurity",Math.max(0,prior.getIntOr("impurity",0)));fresh.put("knowledge",prior.getListOrEmpty("knowledge").copy());replacement.getPersistentData().put("goetia",fresh);}
 private GoetiaPlayer(){}
}
