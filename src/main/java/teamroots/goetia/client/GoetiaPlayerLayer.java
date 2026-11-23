package teamroots.goetia.client;
import com.mojang.blaze3d.vertex.*;
import com.mojang.blaze3d.pipeline.*;
import com.mojang.blaze3d.platform.CompareOp;
import com.mojang.math.Axis;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.rendertype.*;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.context.ContextKey;
import teamroots.goetia.*;
public final class GoetiaPlayerLayer extends RenderLayer<AvatarRenderState,PlayerModel> {
 public static final ContextKey<GoetiaPlayer.Alignment> ALIGNMENT_KEY=new ContextKey<>(Identifier.fromNamespaceAndPath("goetia","alignment"));
 private static final Identifier HORN_TEXTURE=Identifier.fromNamespaceAndPath("goetia","textures/block/horns.png"),AURA=Identifier.fromNamespaceAndPath("goetia","textures/entity/angel_aura.png");
 public static final RenderPipeline AURA_PIPELINE=RenderPipeline.builder(RenderPipelines.ENTITY_EMISSIVE_SNIPPET).withLocation(Identifier.fromNamespaceAndPath("goetia","pipeline/angel_aura")).withShaderDefine("ALPHA_CUTOUT",.1F).withBindGroupLayout(net.minecraft.client.renderer.BindGroupLayouts.SAMPLER1).withColorTargetState(new ColorTargetState(BlendFunction.LIGHTNING)).withCull(false).withDepthStencilState(new DepthStencilState(CompareOp.GREATER_THAN_OR_EQUAL,false)).build();
 private static final RenderType AURA_TYPE=RenderType.create("goetia_aura",RenderSetup.builder(AURA_PIPELINE).withTexture("Sampler0",AURA).useOverlay().sortOnUpload().createRenderSetup());
 private final LegacyGeometry horns=new LegacyGeometry("ModelHorns");
 private final java.util.List<PlayerModel> shells=java.util.List.of(shell(.1f),shell(.6f),shell(1));
 private static PlayerModel shell(float expand){return new PlayerModel(LayerDefinition.create(PlayerModel.createMesh(new CubeDeformation(expand),false),64,64).bakeRoot(),false);}
 public GoetiaPlayerLayer(RenderLayerParent<AvatarRenderState,PlayerModel> parent){super(parent);}
 @Override public void submit(PoseStack pose,SubmitNodeCollector collector,int light,AvatarRenderState state,float yaw,float pitch){if(state.isInvisible||!GoetiaConfig.ALIGNMENT_MODELS.get())return;var alignment=state.getRenderData(ALIGNMENT_KEY);
  if(alignment==GoetiaPlayer.Alignment.DEMON)collector.submitCustomGeometry(pose,RenderTypes.entityCutout(HORN_TEXTURE),(snapshot,out)->{var local=new PoseStack();local.last().set(snapshot);local.mulPose(Axis.YP.rotationDegrees(yaw));local.mulPose(Axis.XP.rotationDegrees(pitch));local.translate(0,-2,0);for(String part:new String[]{"Elem11","Elem10","Elem9","Elem8","Elem7","Elem6","Elem5","Elem4"})horns.draw(part,local,out,light,OverlayTexture.NO_OVERLAY,0xFFFFFFFF);});
  else if(alignment==GoetiaPlayer.Alignment.ANGEL){var shellState=new AvatarRenderState();shellState.ageInTicks=state.ageInTicks;shellState.walkAnimationPos=state.walkAnimationPos;shellState.walkAnimationSpeed=state.walkAnimationSpeed;shellState.xRot=state.xRot;shellState.yRot=state.yRot;shellState.isFallFlying=state.isFallFlying;shellState.speedValue=state.speedValue;collector.submitCustomGeometry(pose,AURA_TYPE,(snapshot,buffer)->{var local=new PoseStack();local.last().set(snapshot);var out=new Scrolling(buffer,-shellState.ageInTicks*.01f);int[] alpha={204,102,51};for(int i=0;i<3;i++){var model=shells.get(i);model.setupAnim(shellState);model.renderToBuffer(local,out,light,OverlayTexture.NO_OVERLAY,(alpha[i]<<24)|0x7F7F7F);}});}
 }
 private record Scrolling(VertexConsumer target,float offset) implements VertexConsumer {
  @Override public void addVertex(float x,float y,float z,int color,float u,float v,int overlay,int light,float nx,float ny,float nz){target.addVertex(x,y,z,color,u+offset,v+offset,overlay,light,nx,ny,nz);}
  @Override public VertexConsumer addVertex(float x,float y,float z){target.addVertex(x,y,z);return this;}
  @Override public VertexConsumer setColor(int r,int g,int b,int a){target.setColor(r,g,b,a);return this;}
  @Override public VertexConsumer setColor(int color){target.setColor(color);return this;}
  @Override public VertexConsumer setUv(float u,float v){target.setUv(u+offset,v+offset);return this;}
  @Override public VertexConsumer setUv1(int u,int v){target.setUv1(u,v);return this;}
  @Override public VertexConsumer setUv2(int u,int v){target.setUv2(u,v);return this;}
  @Override public VertexConsumer setNormal(float x,float y,float z){target.setNormal(x,y,z);return this;}
  @Override public VertexConsumer setLineWidth(float width){target.setLineWidth(width);return this;}
 }
}
