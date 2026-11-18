package teamroots.goetia.client;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
import teamroots.goetia.GoetiaDemon;
public final class GoetiaDemonRenderer extends MobRenderer<GoetiaDemon,GoetiaDemonRenderer.State,GoetiaDemonRenderer.Model> {
 public static final class State extends LivingEntityRenderState {public float speed;}
 public static final class Model extends EntityModel<State>{public Model(){super(new ModelPart(java.util.List.of(),java.util.Map.of()));}}
 private final Identifier texture;private final LegacyMesh mesh;
 public GoetiaDemonRenderer(EntityRendererProvider.Context c,int kind){super(c,new Model(),new float[]{.3f,.5f,.8f}[kind]);texture=Identifier.fromNamespaceAndPath("goetia","textures/entity/"+new String[]{"imp","fiend","demon"}[kind]+"_texture.png");mesh=new LegacyMesh(kind);addLayer(new RenderLayer<State,Model>(this){
  @Override public void submit(PoseStack pose,SubmitNodeCollector collector,int light,State state,float yaw,float pitch){boolean ghost=state.isInvisible&&!state.isInvisibleToPlayer;int overlay=LivingEntityRenderer.getOverlayCoords(state,0);if(!state.isInvisible||ghost)submitGeometry(pose,collector,state,light,overlay,ghost?RenderTypes.entityTranslucentCull(texture):RenderTypes.entityCutout(texture),ghost?0x26FFFFFF:0xFFFFFFFF);if(state.appearsGlowing())submitGeometry(pose,collector,state,light,overlay,RenderTypes.outline(texture),0xFF000000|state.outlineColor);}
 });}
 private void submitGeometry(PoseStack pose,SubmitNodeCollector collector,State state,int light,int overlay,RenderType type,int color){collector.submitCustomGeometry(pose,type,(snapshot,out)->{var local=new PoseStack();local.last().set(snapshot);mesh.draw(state,local,out,light,overlay,color);});}
 @Override public State createRenderState(){return new State();}
 @Override public void extractRenderState(GoetiaDemon entity,State state,float partial){super.extractRenderState(entity,state,partial);state.speed=(float)(entity.getDeltaMovement().multiply(1,0,1).length()*(entity.kind==0?2:3));}
 @Override public Identifier getTextureLocation(State state){return texture;}
 @Override protected boolean shouldShowName(GoetiaDemon entity,double distance){return false;}
 private static final class LegacyMesh {
  private final LegacyGeometry geometry;private final int kind;
  LegacyMesh(int kind){this.kind=kind;geometry=new LegacyGeometry(new String[]{"ModelImp","ModelFiend","ModelDemon"}[kind]);}
  void draw(State state,PoseStack pose,VertexConsumer buffer,int light,int overlay,int color){
   float speed=state.speed;float age=state.ageInTicks;
   pose.pushPose();
   switch(kind){
    case 0 -> {
     pose.translate(0.0, 0.65, 0.0);
     draw("head1",pose,buffer,light,overlay,color);
     draw("body1",pose,buffer,light,overlay,color);
     draw("horn1",pose,buffer,light,overlay,color);
     draw("horn2",pose,buffer,light,overlay,color);
     pose.rotate(Axis.XP.rotationDegrees(speed * 60.0f * (float)Math.sin(Math.toRadians(age % 360.0f) * 24.0)));
     draw("armR",pose,buffer,light,overlay,color);
     pose.rotate(Axis.XP.rotationDegrees(-120.0f * speed * (float)Math.sin(Math.toRadians(age % 360.0f) * 24.0)));
     draw("armL",pose,buffer,light,overlay,color);
     pose.rotate(Axis.XP.rotationDegrees(speed * 60.0f * (float)Math.sin(Math.toRadians(age % 360.0f) * 24.0)));
     pose.rotate(Axis.XP.rotationDegrees(speed * 60.0f * (float)Math.sin(Math.toRadians(age % 360.0f) * 24.0)));
     draw("legL1",pose,buffer,light,overlay,color);
     pose.rotate(Axis.XP.rotationDegrees(-120.0f * speed * (float)Math.sin(Math.toRadians(age % 360.0f) * 24.0)));
     draw("legR1",pose,buffer,light,overlay,color);
     pose.rotate(Axis.XP.rotationDegrees(speed * 60.0f * (float)Math.sin(Math.toRadians(age % 360.0f) * 24.0)));
     pose.rotate(Axis.YP.rotationDegrees(30.0f * (float)Math.abs(Math.sin(Math.toRadians(age % 360.0f) * 15.0))));
     draw("wingL1",pose,buffer,light,overlay,color);
     draw("wingL2",pose,buffer,light,overlay,color);
     draw("wingL3",pose,buffer,light,overlay,color);
     draw("wingL4",pose,buffer,light,overlay,color);
     pose.rotate(Axis.YP.rotationDegrees(-60.0f * (float)Math.abs(Math.sin(Math.toRadians(age % 360.0f) * 15.0))));
     draw("wingR1",pose,buffer,light,overlay,color);
     draw("wingR2",pose,buffer,light,overlay,color);
     draw("wingR3",pose,buffer,light,overlay,color);
     draw("wingR4",pose,buffer,light,overlay,color);
     pose.rotate(Axis.YP.rotationDegrees(30.0f * (float)Math.abs(Math.sin(Math.toRadians(age % 360.0f) * 15.0))));
     pose.translate(0.0, -0.65, 0.0);
    }
    case 1 -> {
     pose.translate(0.0, 0.15, 0.0);
     draw("head1",pose,buffer,light,overlay,color);
     draw("body1",pose,buffer,light,overlay,color);
     draw("horn1",pose,buffer,light,overlay,color);
     draw("horn2",pose,buffer,light,overlay,color);
     pose.rotate(Axis.XP.rotationDegrees(speed * 60.0f * (float)Math.sin(Math.toRadians(age % 360.0f) * 24.0)));
     draw("armR1",pose,buffer,light,overlay,color);
     pose.rotate(Axis.XP.rotationDegrees(-120.0f * speed * (float)Math.sin(Math.toRadians(age % 360.0f) * 24.0)));
     draw("armL1",pose,buffer,light,overlay,color);
     pose.rotate(Axis.XP.rotationDegrees(speed * 60.0f * (float)Math.sin(Math.toRadians(age % 360.0f) * 24.0)));
     pose.rotate(Axis.XP.rotationDegrees(speed * 60.0f * (float)Math.sin(Math.toRadians(age % 360.0f) * 24.0)));
     draw("legL1",pose,buffer,light,overlay,color);
     pose.rotate(Axis.XP.rotationDegrees(-120.0f * speed * (float)Math.sin(Math.toRadians(age % 360.0f) * 24.0)));
     draw("legR1",pose,buffer,light,overlay,color);
     pose.rotate(Axis.XP.rotationDegrees(speed * 60.0f * (float)Math.sin(Math.toRadians(age % 360.0f) * 24.0)));
     pose.rotate(Axis.YP.rotationDegrees(30.0f * (float)Math.abs(Math.sin(Math.toRadians(age % 360.0f) * 15.0))));
     draw("wingL1",pose,buffer,light,overlay,color);
     draw("wingL2",pose,buffer,light,overlay,color);
     draw("wingL3",pose,buffer,light,overlay,color);
     draw("wingL4",pose,buffer,light,overlay,color);
     pose.rotate(Axis.YP.rotationDegrees(-60.0f * (float)Math.abs(Math.sin(Math.toRadians(age % 360.0f) * 15.0))));
     draw("wingR1",pose,buffer,light,overlay,color);
     draw("wingR2",pose,buffer,light,overlay,color);
     draw("wingR3",pose,buffer,light,overlay,color);
     draw("wingR4",pose,buffer,light,overlay,color);
     pose.rotate(Axis.YP.rotationDegrees(30.0f * (float)Math.abs(Math.sin(Math.toRadians(age % 360.0f) * 15.0))));
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
     pose.rotate(Axis.XP.rotationDegrees(speed * 60.0f * (float)Math.sin(Math.toRadians(age % 360.0f) * 24.0)));
     draw("armR1",pose,buffer,light,overlay,color);
     pose.rotate(Axis.XP.rotationDegrees(-120.0f * speed * (float)Math.sin(Math.toRadians(age % 360.0f) * 24.0)));
     draw("armL1",pose,buffer,light,overlay,color);
     pose.rotate(Axis.XP.rotationDegrees(speed * 60.0f * (float)Math.sin(Math.toRadians(age % 360.0f) * 24.0)));
     pose.rotate(Axis.XP.rotationDegrees(speed * 60.0f * (float)Math.sin(Math.toRadians(age % 360.0f) * 24.0)));
     draw("legL1",pose,buffer,light,overlay,color);
     pose.rotate(Axis.XP.rotationDegrees(-120.0f * speed * (float)Math.sin(Math.toRadians(age % 360.0f) * 24.0)));
     draw("legR1",pose,buffer,light,overlay,color);
     pose.rotate(Axis.XP.rotationDegrees(speed * 60.0f * (float)Math.sin(Math.toRadians(age % 360.0f) * 24.0)));
     pose.rotate(Axis.YP.rotationDegrees(30.0f * (float)Math.abs(Math.sin(Math.toRadians(age % 360.0f) * 15.0))));
     draw("wingL1",pose,buffer,light,overlay,color);
     draw("wingL2",pose,buffer,light,overlay,color);
     draw("wingR3",pose,buffer,light,overlay,color);
     draw("wingL4",pose,buffer,light,overlay,color);
     pose.rotate(Axis.YP.rotationDegrees(-60.0f * (float)Math.abs(Math.sin(Math.toRadians(age % 360.0f) * 15.0))));
     draw("wingR1",pose,buffer,light,overlay,color);
     draw("wingR2",pose,buffer,light,overlay,color);
     draw("wingL3",pose,buffer,light,overlay,color);
     draw("wingR4",pose,buffer,light,overlay,color);
     pose.rotate(Axis.YP.rotationDegrees(30.0f * (float)Math.abs(Math.sin(Math.toRadians(age % 360.0f) * 15.0))));
     pose.translate(0.0, -0.15, 0.0);
    }
   }
   pose.popPose();
  }
  private void draw(String name,PoseStack pose,VertexConsumer buffer,int light,int overlay,int color){geometry.draw(name,pose,buffer,light,overlay,color);}
 }
}
