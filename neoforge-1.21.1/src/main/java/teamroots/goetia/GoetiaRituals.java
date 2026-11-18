package teamroots.goetia;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
public final class GoetiaRituals {
 public static boolean cleanseMatches(Level w,BlockPos altar){var p=altar.above();for(Direction a:new Direction[]{Direction.NORTH,Direction.WEST}){var one=w.getBlockState(p.relative(a));var two=w.getBlockState(p.relative(a.getOpposite()));if(one.is(GoetiaContent.block("angel_candle_stand"))&&two.is(GoetiaContent.block("demon_candle_stand"))||one.is(GoetiaContent.block("demon_candle_stand"))&&two.is(GoetiaContent.block("angel_candle_stand")))return true;}return false;}
 public static boolean perform(Level w,BlockPos pos,Player p){if(!(w instanceof ServerLevel level))return false;
  if(cleanseMatches(w,pos)){GoetiaPlayer.set(p,"impurity",0);GoetiaPlayer.set(p,"purity",0);return true;}
  if(w.getBlockState(pos.above()).is(GoetiaContent.block("full_bowl"))&&w.getBlockEntity(pos.above()) instanceof GoetiaBlockData bowl){var id=ResourceLocation.tryParse(bowl.entity);if(id==null||!BuiltInRegistries.ENTITY_TYPE.containsKey(id))return false;var entity=BuiltInRegistries.ENTITY_TYPE.get(id).create(w);if(entity==null)return false;entity.setPos(pos.getX()+.5,pos.getY()+1.3,pos.getZ()+.5);w.addFreshEntity(entity);w.setBlockAndUpdate(pos.above(),GoetiaContent.block("empty_bowl").defaultBlockState());return true;}return false;
 }
 private GoetiaRituals(){}
}
