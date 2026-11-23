package teamroots.goetia;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
public class GoetiaItem extends Item {
 public final String id;
 public GoetiaItem(String id){super(properties(id));this.id=id;}
 private static Properties properties(String id){var p=GoetiaContent.itemProperties(id);if(id.equals("holy_cross"))p.durability(20);else if(id.equals("demon_horn")||id.equals("demonic_chalk")||id.equals("soul_focus")||id.endsWith("_icon")||id.startsWith("lost_"))p.stacksTo(1);return p;}
 @Override public boolean isCombineRepairable(ItemStack stack){return false;}
 public static CompoundTag tag(ItemStack stack){var d=stack.get(DataComponents.CUSTOM_DATA);return d==null?new CompoundTag():d.copyTag();}
 public static void edit(ItemStack stack,Consumer<CompoundTag> change){var d=tag(stack);change.accept(d);stack.set(DataComponents.CUSTOM_DATA,CustomData.of(d));}
 @Override public InteractionResult use(Level level,Player p,InteractionHand hand){var stack=p.getItemInHand(hand);
  if(id.equals("demon_horn")){if(!level.isClientSide())GoetiaPlayer.set(p,"impurity",GoetiaPlayer.data(p).getIntOr("impurity",0)+15);return InteractionResult.SUCCESS;}
  if(id.equals("soul_focus")){if(level.isClientSide())Goetia.openScreen.accept("focus",hand);return InteractionResult.SUCCESS;}
  if(id.equals("demonic_chalk")&&p.isShiftKeyDown()&&hand==InteractionHand.MAIN_HAND){if(!level.isClientSide())GoetiaPlayer.sync(p);else Goetia.openScreen.accept("chalk",hand);return InteractionResult.SUCCESS;}
  if(id.startsWith("lost_demon_notes_")&&!p.isShiftKeyDown()){var d=tag(stack);String key=d.getStringOr("knowledge","");if(d.contains("knowledge")&&!GoetiaPlayer.knowledge(p).contains(key)&&!level.isClientSide()){GoetiaPlayer.learn(p,key);p.setItemInHand(hand,ItemStack.EMPTY);return InteractionResult.SUCCESS;}return InteractionResult.FAIL;}
  if(id.equals("holy_cross")){var hit=getPlayerPOVHitResult(level,p,ClipContext.Fluid.SOURCE_ONLY);if(hit.getType()!=HitResult.Type.BLOCK)return InteractionResult.PASS;var pos=hit.getBlockPos();if(!level.getBlockState(pos).is(Blocks.WATER))return InteractionResult.FAIL;for(Direction dir:Direction.values())if(level.getBlockState(pos.relative(dir)).is(Blocks.WATER)&&level.getFluidState(pos.relative(dir)).isSource())return InteractionResult.PASS;if(!level.isClientSide()){level.setBlockAndUpdate(pos,GoetiaFluids.BLOCK.get().defaultBlockState());stack.hurtAndBreak(1,p,hand==InteractionHand.MAIN_HAND?EquipmentSlot.MAINHAND:EquipmentSlot.OFFHAND);}return InteractionResult.SUCCESS;}
  return InteractionResult.FAIL;
 }
 @Override public InteractionResult useOn(UseOnContext c){var p=c.getPlayer();if(p==null)return InteractionResult.FAIL;var level=c.getLevel();var pos=c.getClickedPos();var stack=c.getItemInHand();
  if(id.equals("demonic_chalk")){String key=tag(stack).getStringOr("knowledge","");int index=GoetiaPlayer.SYMBOLS.indexOf(key);if(c.getClickedFace()==Direction.UP&&level.getBlockState(pos).isFaceSturdy(level,pos,Direction.UP)&&index>=0&&!level.isClientSide()){var symbol=GoetiaEntities.SYMBOLS.get(index).get().create(level,net.minecraft.world.entity.EntitySpawnReason.TRIGGERED);if(symbol!=null){symbol.setPos(pos.getX()+.5,pos.getY()+1,pos.getZ()+.5);level.addFreshEntity(symbol);}}return InteractionResult.FAIL;}
  if(id.startsWith("lost_")&&p.isShiftKeyDown()&&level.isEmptyBlock(pos.above())){if(!level.isClientSide()){String key=tag(stack).getStringOr("knowledge","");String block=id.startsWith("lost_demon")?"block_demon_lost_note":"block_angel_lost_note";level.setBlockAndUpdate(pos.above(),GoetiaContent.block(block).defaultBlockState().setValue(GoetiaBlock.FACING,p.getDirection().getOpposite()));if(level.getBlockEntity(pos.above()) instanceof GoetiaBlockData d){d.knowledge=key;d.changed();}stack.shrink(1);}return InteractionResult.PASS;}
  return super.useOn(c);
 }
 @Override public InteractionResult interactLivingEntity(ItemStack stack,Player p,LivingEntity target,InteractionHand hand){if(id.equals("bottle_holy_water")&&target instanceof GoetiaDemon d){if(!p.level().isClientSide()){stack.shrink(1);d.holyWater();if(!p.isCreative())p.getInventory().add(new ItemStack(Items.GLASS_BOTTLE));}return InteractionResult.SUCCESS;}return InteractionResult.PASS;}
 @Override public void inventoryTick(ItemStack stack,net.minecraft.server.level.ServerLevel level,Entity entity,EquipmentSlot slot){if(!(entity instanceof Player p)||level.isClientSide())return;
  if(id.startsWith("lost_")&&!stack.has(DataComponents.CUSTOM_DATA))edit(stack,d->d.putString("knowledge",id.startsWith("lost_demon")?GoetiaPlayer.SYMBOLS.get(level.getRandom().nextInt(6)):"WIP"));
  if(id.equals("holy_cross")){if(GoetiaPlayer.alignment(p)==GoetiaPlayer.Alignment.DEMON)p.hurt(level.damageSources().magic(),1);if(GoetiaContent.is(p.getMainHandItem(),"holy_cross"))for(var e:level.getEntitiesOfClass(LivingEntity.class,new AABB(p.getX()-5,p.getY(),p.getZ()-5,p.getX()+5,p.getY()+5,p.getZ()+5)))if(e instanceof GoetiaDemon||(e instanceof Player other&&GoetiaPlayer.alignment(other)==GoetiaPlayer.Alignment.DEMON))e.hurt(level.damageSources().magic(),1);}
 }
 @Override public void appendHoverText(ItemStack stack,TooltipContext context,net.minecraft.world.item.component.TooltipDisplay display,Consumer<Component> lines,TooltipFlag flag){var d=tag(stack);if(id.startsWith("lost_")){String key=d.contains("knowledge")?d.getStringOr("knowledge",""):id.startsWith("lost_demon")?GoetiaPlayer.SYMBOLS.get(Integer.parseInt(id.substring(id.length()-1))):"WIP";lines.accept(Component.literal("Knowledge: ").append(Component.translatable("goetia.knowledge."+key)));}else if(id.equals("liquid_bottle")&&d.contains("name"))lines.accept(Component.literal(d.getStringOr("name","")));}
 @Override public net.minecraft.world.item.ItemStackTemplate getCraftingRemainder(net.minecraft.world.item.ItemInstance stack){return id.equals("holy_cross")?new net.minecraft.world.item.ItemStackTemplate(this):null;}
}
