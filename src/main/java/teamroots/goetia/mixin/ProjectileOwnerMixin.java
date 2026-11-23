package teamroots.goetia.mixin;
@org.spongepowered.asm.mixin.Mixin(net.minecraft.world.entity.projectile.Projectile.class)
public abstract class ProjectileOwnerMixin implements teamroots.goetia.LegacyProjectileOwner {
 @org.spongepowered.asm.mixin.Shadow private java.util.UUID ownerUUID;
 @org.spongepowered.asm.mixin.Shadow private net.minecraft.world.entity.Entity cachedOwner;
 @Override public void goetia$clearOwner(){ownerUUID=null;cachedOwner=null;}
}
