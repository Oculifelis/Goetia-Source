package teamroots.goetia;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
public final class GoetiaBlock extends Block implements EntityBlock {
 public static final DirectionProperty FACING=BlockStateProperties.HORIZONTAL_FACING;
 public final String id;
 public GoetiaBlock(String id,BlockBehaviour.Properties p){super(p.mapColor(id.equals("altar")||id.contains("bowl")?net.minecraft.world.level.material.MapColor.STONE:net.minecraft.world.level.material.MapColor.WOOL));this.id=id;registerDefaultState(stateDefinition.any().setValue(FACING,Direction.NORTH));}
 @Override protected void createBlockStateDefinition(StateDefinition.Builder<Block,BlockState> b){b.add(FACING);}
 @Override public BlockState getStateForPlacement(BlockPlaceContext c){return defaultBlockState().setValue(FACING,c.getHorizontalDirection().getOpposite());}
 @Override public BlockEntity newBlockEntity(BlockPos p,BlockState s){return id.contains("bowl")||id.startsWith("block_")?new GoetiaBlockData(p,s):null;}
 @Override protected VoxelShape getShape(BlockState s,BlockGetter w,BlockPos p,CollisionContext c){if(id.contains("candle"))return box(6,0,6,10,id.endsWith("_stand")?10:6,10);if(id.contains("bowl"))return box(4,0,4,12,3.2,12);if(id.startsWith("block_"))return box(0,0,0,16,1.6,16);return super.getShape(s,w,p,c);}
 @Override protected boolean canSurvive(BlockState s,net.minecraft.world.level.LevelReader w,BlockPos p){return !id.contains("candle")||w.getBlockState(p.below()).isFaceSturdy(w,p.below(),Direction.UP);}
 @Override protected void neighborChanged(BlockState s,Level w,BlockPos p,Block b,BlockPos from,boolean moving){if(id.contains("candle")&&!s.canSurvive(w,p))w.destroyBlock(p,true);}
 @Override public void animateTick(BlockState s,Level w,BlockPos p,RandomSource r){if(!id.contains("candle"))return;int n=r.nextInt(3);double y=p.getY()+(id.endsWith("_stand")?.75:.5);if(n==0)w.addParticle(ParticleTypes.FLAME,p.getX()+.5,y,p.getZ()+.5,0,0,0);if(n<=1)w.addParticle(ParticleTypes.SMOKE,p.getX()+.5,y,p.getZ()+.5,0,r.nextFloat()*.015,0);w.addParticle(ParticleTypes.SMOKE,p.getX()+.5,y,p.getZ()+.5,0,r.nextFloat()*.015,0);}
 private InteractionResult interact(BlockState state,Level w,BlockPos pos,Player p,InteractionHand hand){if(w.isClientSide){if(id.equals("altar")&&p.getMainHandItem().isEmpty())Goetia.openScreen.accept("altar",hand);else if(id.equals("altar"))Goetia.ritualVisual.accept(pos,p);return InteractionResult.SUCCESS;}
  if(id.equals("altar")){if(!p.getMainHandItem().isEmpty())GoetiaRituals.perform(w,pos,p);}
  else if(id.contains("bowl")){var stack=p.getItemInHand(hand);if(GoetiaContent.is(stack,"liquid_bottle")){if(id.equals("empty_bowl")){var tag=GoetiaItem.tag(stack);w.setBlockAndUpdate(pos,GoetiaContent.block("full_bowl").defaultBlockState());if(w.getBlockEntity(pos) instanceof GoetiaBlockData d){d.color=tag.getInt("color");d.entity=tag.getString("entityName");d.changed();}stack.shrink(1);}else w.setBlockAndUpdate(pos,GoetiaContent.block("empty_bowl").defaultBlockState());}}
  else if(id.startsWith("block_")&&w.getBlockEntity(pos) instanceof GoetiaBlockData d){var stack=new ItemStack(GoetiaContent.item(id.equals("block_demon_lost_note")?"lost_demon_notes_0":"lost_angel_notes_0"));GoetiaItem.edit(stack,t->t.putString("knowledge",d.knowledge));p.getInventory().add(stack);w.destroyBlock(pos,false);}
  return InteractionResult.SUCCESS;
 }
 @Override protected ItemInteractionResult useItemOn(ItemStack held,BlockState state,Level w,BlockPos pos,Player p,InteractionHand hand,BlockHitResult hit){interact(state,w,pos,p,hand);return ItemInteractionResult.SUCCESS;}
 @Override protected InteractionResult useWithoutItem(BlockState s,Level w,BlockPos pos,Player p,BlockHitResult hit){return interact(s,w,pos,p,InteractionHand.MAIN_HAND);}
}
