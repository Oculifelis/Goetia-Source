package teamroots.goetia.client;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import teamroots.goetia.GoetiaDemon;
public final class GoetiaDemonRenderer extends MobRenderer<GoetiaDemon,GoetiaDemonRenderer.Model> {
 private final String name;
 public GoetiaDemonRenderer(EntityRendererProvider.Context c,int kind){super(c,new Model(kind),new float[]{.3f,.5f,.8f}[kind]);name=new String[]{"imp","fiend","demon"}[kind];}
 @Override public ResourceLocation getTextureLocation(GoetiaDemon e){return ResourceLocation.fromNamespaceAndPath("goetia","textures/entity/"+name+"_texture.png");}
 @Override protected boolean shouldShowName(GoetiaDemon e){return false;}
 public static final class Model extends EntityModel<GoetiaDemon> {
  private final LegacyGeometry geometry;private final int kind;private GoetiaDemon entity;private float age;
  public Model(int k){kind=k;geometry=new LegacyGeometry(new String[]{"ModelImp","ModelFiend","ModelDemon"}[k]);}
  @Override public void setupAnim(GoetiaDemon e,float limb,float amount,float age,float yaw,float pitch){entity=e;this.age=age;}
  @Override public void renderToBuffer(PoseStack pose,VertexConsumer buffer,int light,int overlay,int color){
   float speed=(float)(entity.getDeltaMovement().multiply(1,0,1).length()*(kind==0?2:3));
   pose.pushPose();
   switch(kind){
    case 0 -> {
     pose.translate(0.0, 0.65, 0.0);
     draw("head1",pose,buffer,light,overlay,color);
     draw("body1",pose,buffer,light,overlay,color);
     draw("horn1",pose,buffer,light,overlay,color);
     draw("horn2",pose,buffer,light,overlay,color);
     pose.mulPose(Axis.XP.rotationDegrees(speed * 60.0f * (float)Math.sin(Math.toRadians(age % 360.0f) * 24.0)));
     draw("armR",pose,buffer,light,overlay,color);
     pose.mulPose(Axis.XP.rotationDegrees(-120.0f * speed * (float)Math.sin(Math.toRadians(age % 360.0f) * 24.0)));
     draw("armL",pose,buffer,light,overlay,color);
     pose.mulPose(Axis.XP.rotationDegrees(speed * 60.0f * (float)Math.sin(Math.toRadians(age % 360.0f) * 24.0)));
     pose.mulPose(Axis.XP.rotationDegrees(speed * 60.0f * (float)Math.sin(Math.toRadians(age % 360.0f) * 24.0)));
     draw("legL1",pose,buffer,light,overlay,color);
     pose.mulPose(Axis.XP.rotationDegrees(-120.0f * speed * (float)Math.sin(Math.toRadians(age % 360.0f) * 24.0)));
     draw("legR1",pose,buffer,light,overlay,color);
     pose.mulPose(Axis.XP.rotationDegrees(speed * 60.0f * (float)Math.sin(Math.toRadians(age % 360.0f) * 24.0)));
     pose.mulPose(Axis.YP.rotationDegrees(30.0f * (float)Math.abs(Math.sin(Math.toRadians(age % 360.0f) * 15.0))));
     draw("wingL1",pose,buffer,light,overlay,color);
     draw("wingL2",pose,buffer,light,overlay,color);
     draw("wingL3",pose,buffer,light,overlay,color);
     draw("wingL4",pose,buffer,light,overlay,color);
     pose.mulPose(Axis.YP.rotationDegrees(-60.0f * (float)Math.abs(Math.sin(Math.toRadians(age % 360.0f) * 15.0))));
     draw("wingR1",pose,buffer,light,overlay,color);
     draw("wingR2",pose,buffer,light,overlay,color);
     draw("wingR3",pose,buffer,light,overlay,color);
     draw("wingR4",pose,buffer,light,overlay,color);
     pose.mulPose(Axis.YP.rotationDegrees(30.0f * (float)Math.abs(Math.sin(Math.toRadians(age % 360.0f) * 15.0))));
     pose.translate(0.0, -0.65, 0.0);
    }
    case 1 -> {
     pose.translate(0.0, 0.15, 0.0);
     draw("head1",pose,buffer,light,overlay,color);
     draw("body1",pose,buffer,light,overlay,color);
     draw("horn1",pose,buffer,light,overlay,color);
     draw("horn2",pose,buffer,light,overlay,color);
     pose.mulPose(Axis.XP.rotationDegrees(speed * 60.0f * (float)Math.sin(Math.toRadians(age % 360.0f) * 24.0)));
     draw("armR1",pose,buffer,light,overlay,color);
     pose.mulPose(Axis.XP.rotationDegrees(-120.0f * speed * (float)Math.sin(Math.toRadians(age % 360.0f) * 24.0)));
     draw("armL1",pose,buffer,light,overlay,color);
     pose.mulPose(Axis.XP.rotationDegrees(speed * 60.0f * (float)Math.sin(Math.toRadians(age % 360.0f) * 24.0)));
     pose.mulPose(Axis.XP.rotationDegrees(speed * 60.0f * (float)Math.sin(Math.toRadians(age % 360.0f) * 24.0)));
     draw("legL1",pose,buffer,light,overlay,color);
     pose.mulPose(Axis.XP.rotationDegrees(-120.0f * speed * (float)Math.sin(Math.toRadians(age % 360.0f) * 24.0)));
     draw("legR1",pose,buffer,light,overlay,color);
     pose.mulPose(Axis.XP.rotationDegrees(speed * 60.0f * (float)Math.sin(Math.toRadians(age % 360.0f) * 24.0)));
     pose.mulPose(Axis.YP.rotationDegrees(30.0f * (float)Math.abs(Math.sin(Math.toRadians(age % 360.0f) * 15.0))));
     draw("wingL1",pose,buffer,light,overlay,color);
     draw("wingL2",pose,buffer,light,overlay,color);
     draw("wingL3",pose,buffer,light,overlay,color);
     draw("wingL4",pose,buffer,light,overlay,color);
     pose.mulPose(Axis.YP.rotationDegrees(-60.0f * (float)Math.abs(Math.sin(Math.toRadians(age % 360.0f) * 15.0))));
     draw("wingR1",pose,buffer,light,overlay,color);
     draw("wingR2",pose,buffer,light,overlay,color);
     draw("wingR3",pose,buffer,light,overlay,color);
     draw("wingR4",pose,buffer,light,overlay,color);
     pose.mulPose(Axis.YP.rotationDegrees(30.0f * (float)Math.abs(Math.sin(Math.toRadians(age % 360.0f) * 15.0))));
     pose.translate(0.0, -0.15, 0.0);
    }
    case 2 -> {
     pose.translate(0.0, 0.15, 0.0);
     draw("head1",pose,buffer,light,overlay,color);
     draw("horn1",pose,buffer,light,overlay,color);
     draw("horn2",pose,buffer,light,overlay,color);
     draw("horn3",pose,buffer,light,overlay,color);
     draw("horn4",pose,buffer,light,overlay,color);
     draw("horn5",pose,buffer,light,overlay,color);
     draw("horn6",pose,buffer,light,overlay,color);
     draw("body1",pose,buffer,light,overlay,color);
     draw("body2",pose,buffer,light,overlay,color);
     draw("body3",pose,buffer,light,overlay,color);
     draw("body4",pose,buffer,light,overlay,color);
     draw("body5",pose,buffer,light,overlay,color);
     draw("body6",pose,buffer,light,overlay,color);
     pose.mulPose(Axis.XP.rotationDegrees(speed * 60.0f * (float)Math.sin(Math.toRadians(age % 360.0f) * 24.0)));
     draw("armR1",pose,buffer,light,overlay,color);
     pose.mulPose(Axis.XP.rotationDegrees(-120.0f * speed * (float)Math.sin(Math.toRadians(age % 360.0f) * 24.0)));
     draw("armL1",pose,buffer,light,overlay,color);
     pose.mulPose(Axis.XP.rotationDegrees(speed * 60.0f * (float)Math.sin(Math.toRadians(age % 360.0f) * 24.0)));
     pose.mulPose(Axis.XP.rotationDegrees(speed * 60.0f * (float)Math.sin(Math.toRadians(age % 360.0f) * 24.0)));
     draw("legL1",pose,buffer,light,overlay,color);
     pose.mulPose(Axis.XP.rotationDegrees(-120.0f * speed * (float)Math.sin(Math.toRadians(age % 360.0f) * 24.0)));
     draw("legR1",pose,buffer,light,overlay,color);
     pose.mulPose(Axis.XP.rotationDegrees(speed * 60.0f * (float)Math.sin(Math.toRadians(age % 360.0f) * 24.0)));
     pose.mulPose(Axis.YP.rotationDegrees(30.0f * (float)Math.abs(Math.sin(Math.toRadians(age % 360.0f) * 15.0))));
     draw("wingL1",pose,buffer,light,overlay,color);
     draw("wingL2",pose,buffer,light,overlay,color);
     draw("wingR3",pose,buffer,light,overlay,color);
     draw("wingL4",pose,buffer,light,overlay,color);
     pose.mulPose(Axis.YP.rotationDegrees(-60.0f * (float)Math.abs(Math.sin(Math.toRadians(age % 360.0f) * 15.0))));
     draw("wingR1",pose,buffer,light,overlay,color);
     draw("wingR2",pose,buffer,light,overlay,color);
     draw("wingL3",pose,buffer,light,overlay,color);
     draw("wingR4",pose,buffer,light,overlay,color);
     pose.mulPose(Axis.YP.rotationDegrees(30.0f * (float)Math.abs(Math.sin(Math.toRadians(age % 360.0f) * 15.0))));
     pose.translate(0.0, -0.15, 0.0);
    }
   }
   pose.popPose();
  }
  private void draw(String name,PoseStack p,VertexConsumer b,int l,int o,int c){geometry.draw(name,p,b,l,o,c);}
 }
}
