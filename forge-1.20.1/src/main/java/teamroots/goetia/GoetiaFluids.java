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
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
public final class GoetiaFluids {
 public static final DeferredRegister<FluidType> TYPES=DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES,"goetia");
 public static final DeferredRegister<Fluid> FLUIDS=DeferredRegister.create(Registries.FLUID,"goetia");
 public static final RegistryObject<FluidType> TYPE=TYPES.register("holy_water",()->new FluidType(FluidType.Properties.create().canExtinguish(true).canHydrate(true).fallDistanceModifier(0F).supportsBoating(true).sound(net.minecraftforge.common.SoundActions.BUCKET_FILL,net.minecraft.sounds.SoundEvents.BUCKET_FILL).sound(net.minecraftforge.common.SoundActions.BUCKET_EMPTY,net.minecraft.sounds.SoundEvents.BUCKET_EMPTY)){@Override public boolean canDrownIn(net.minecraft.world.entity.LivingEntity entity){return !entity.canBreatheUnderwater();}
  @Override public void initializeClient(java.util.function.Consumer<net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions> consumer){consumer.accept(new net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions(){@Override public net.minecraft.resources.ResourceLocation getStillTexture(){return net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("goetia","fluid/holy_still");}@Override public net.minecraft.resources.ResourceLocation getFlowingTexture(){return net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("goetia","fluid/holy_flowing");}});}
 });
 public static final RegistryObject<ForgeFlowingFluid.Source> SOURCE=FLUIDS.register("holy_water",()->new ForgeFlowingFluid.Source(properties()));
 public static final RegistryObject<ForgeFlowingFluid.Flowing> FLOWING=FLUIDS.register("flowing_holy_water",()->new ForgeFlowingFluid.Flowing(properties()));
 public static final RegistryObject<LiquidBlock> BLOCK=GoetiaContent.BLOCKS.register("holy_water",()->new LiquidBlock(SOURCE,BlockBehaviour.Properties.copy(net.minecraft.world.level.block.Blocks.WATER)){
  @Override public void entityInside(BlockState state,Level level,BlockPos pos,Entity entity){super.entityInside(state,level,pos,entity);if(!level.isClientSide&&entity instanceof GoetiaDemon d)d.holyWater();}
 });
 public static final RegistryObject<BucketItem> BUCKET=GoetiaContent.ITEMS.register("holy_water_bucket",()->new BucketItem(SOURCE,new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
 private static ForgeFlowingFluid.Properties properties(){return new ForgeFlowingFluid.Properties(TYPE,SOURCE,FLOWING).bucket(BUCKET).block(BLOCK).slopeFindDistance(4).levelDecreasePerBlock(1).tickRate(5).explosionResistance(100);}
 public static void register(IEventBus bus){TYPES.register(bus);FLUIDS.register(bus);}
 private GoetiaFluids(){}
}
