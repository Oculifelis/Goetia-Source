package teamroots.goetia.mixin;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.material.Fluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import teamroots.goetia.GoetiaFluids;
/** Restore the old Material.WATER queries for this single custom fluid. */
@Mixin(Entity.class)
public abstract class HolyWaterQueriesMixin {
 @Inject(method="updateFluidHeightAndDoFluidPushing(Lnet/minecraft/tags/TagKey;D)Z",at=@At("RETURN"),cancellable=true)
 private void goetia$wet(TagKey<Fluid> tag,double scale,CallbackInfoReturnable<Boolean> cir){if(tag==FluidTags.WATER&&GoetiaFluids.TYPE.isBound()&&((Entity)(Object)this).getFluidTypeHeight(GoetiaFluids.TYPE.get())>0)cir.setReturnValue(true);}
 @Inject(method="getFluidHeight",at=@At("RETURN"),cancellable=true)
 private void goetia$height(TagKey<Fluid> tag,CallbackInfoReturnable<Double> cir){if(tag==FluidTags.WATER&&GoetiaFluids.TYPE.isBound())cir.setReturnValue(Math.max(cir.getReturnValue(),((Entity)(Object)this).getFluidTypeHeight(GoetiaFluids.TYPE.get())));}
 @Inject(method="isEyeInFluid",at=@At("RETURN"),cancellable=true)
 private void goetia$eyes(TagKey<Fluid> tag,CallbackInfoReturnable<Boolean> cir){if(tag==FluidTags.WATER&&GoetiaFluids.TYPE.isBound()&&((Entity)(Object)this).getEyeInFluidType()==GoetiaFluids.TYPE.get())cir.setReturnValue(true);}
}
