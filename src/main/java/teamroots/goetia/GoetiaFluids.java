package teamroots.goetia;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FlowingFluid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
public final class GoetiaFluids {
 public static final DeferredRegister<FluidType> TYPES=DeferredRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES,"goetia");
 public static final DeferredRegister<Fluid> FLUIDS=DeferredRegister.create(Registries.FLUID,"goetia");
 public static final DeferredHolder<FluidType,FluidType> TYPE=TYPES.register("holy_water",()->new FluidType(FluidType.Properties.create().canExtinguish(true).canHydrate(true).fallDistanceModifier(0F).supportsBoating(true).sound(net.neoforged.neoforge.common.SoundActions.BUCKET_FILL,net.minecraft.sounds.SoundEvents.BUCKET_FILL).sound(net.neoforged.neoforge.common.SoundActions.BUCKET_EMPTY,net.minecraft.sounds.SoundEvents.BUCKET_EMPTY)){@Override public boolean canDrownIn(net.minecraft.world.entity.LivingEntity entity){return !entity.canBreatheUnderwater();}});
 public static final DeferredHolder<Fluid,BaseFlowingFluid.Source> SOURCE=FLUIDS.register("holy_water",()->new BaseFlowingFluid.Source(properties()));
 public static final DeferredHolder<Fluid,BaseFlowingFluid.Flowing> FLOWING=FLUIDS.register("flowing_holy_water",()->new BaseFlowingFluid.Flowing(properties()));
 public static final DeferredHolder<net.minecraft.world.level.block.Block,LiquidBlock> BLOCK=GoetiaContent.BLOCKS.register("holy_water",()->new LiquidBlock(SOURCE.get(),BlockBehaviour.Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.WATER)){
  @Override protected void entityInside(BlockState state,Level level,BlockPos pos,Entity entity){super.entityInside(state,level,pos,entity);if(!level.isClientSide&&entity instanceof GoetiaDemon d)d.holyWater();}
 });
 public static final DeferredHolder<Item,BucketItem> BUCKET=GoetiaContent.ITEMS.register("holy_water_bucket",()->new BucketItem(SOURCE.get(),new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
 private static BaseFlowingFluid.Properties properties(){return new BaseFlowingFluid.Properties(TYPE,SOURCE,FLOWING).bucket(BUCKET).block(BLOCK).slopeFindDistance(4).levelDecreasePerBlock(1).tickRate(5).explosionResistance(100);}
 public static void register(IEventBus bus){TYPES.register(bus);FLUIDS.register(bus);}
 private GoetiaFluids(){}
}
