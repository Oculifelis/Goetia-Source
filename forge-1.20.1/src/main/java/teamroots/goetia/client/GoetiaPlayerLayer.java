package teamroots.goetia.client;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import teamroots.goetia.*;
public final class GoetiaPlayerLayer extends RenderLayer<AbstractClientPlayer,PlayerModel<AbstractClientPlayer>> {
 private final LegacyGeometry horns=new LegacyGeometry("ModelHorns");
 private final java.util.List<PlayerModel<AbstractClientPlayer>> shells=java.util.List.of(shell(.1f),shell(.6f),shell(1));
 private static final ResourceLocation HORN_TEXTURE=ResourceLocation.fromNamespaceAndPath("goetia","textures/block/horns.png"),AURA=ResourceLocation.fromNamespaceAndPath("goetia","textures/entity/angel_aura.png");
 private static final RenderType AURA_TYPE=AuraState.create();
 private static final class AuraState extends RenderStateShard {private AuraState(){super("goetia_aura",()->{},()->{});}private static RenderType create(){return RenderType.create("goetia_aura",DefaultVertexFormat.NEW_ENTITY,VertexFormat.Mode.QUADS,1536,false,true,RenderType.CompositeState.builder().setShaderState(RENDERTYPE_ENTITY_TRANSLUCENT_EMISSIVE_SHADER).setTextureState(new RenderStateShard.TextureStateShard(AURA,false,false)).setTransparencyState(LIGHTNING_TRANSPARENCY).setCullState(NO_CULL).setWriteMaskState(COLOR_WRITE).setOverlayState(OVERLAY).createCompositeState(false));}}
 private static PlayerModel<AbstractClientPlayer> shell(float expand){return new PlayerModel<>(net.minecraft.client.model.geom.builders.LayerDefinition.create(PlayerModel.createMesh(new CubeDeformation(expand),false),64,64).bakeRoot(),false);}
 public GoetiaPlayerLayer(RenderLayerParent<AbstractClientPlayer,PlayerModel<AbstractClientPlayer>> parent){super(parent);}
 @Override public void render(PoseStack pose,MultiBufferSource buffers,int light,AbstractClientPlayer p,float limb,float amount,float partial,float age,float yaw,float pitch){if(p.isInvisible()||!GoetiaConfig.ALIGNMENT_MODELS.get())return;var alignment=GoetiaPlayer.alignment(p);
  if(alignment==GoetiaPlayer.Alignment.DEMON){pose.pushPose();pose.mulPose(Axis.YP.rotationDegrees(yaw));pose.mulPose(Axis.XP.rotationDegrees(pitch));pose.translate(0,-2,0);var out=buffers.getBuffer(RenderType.entityCutoutNoCull(HORN_TEXTURE));for(String part:new String[]{"Elem11","Elem10","Elem9","Elem8","Elem7","Elem6","Elem5","Elem4"})horns.draw(part,pose,out,light,OverlayTexture.NO_OVERLAY,0xFFFFFFFF);pose.popPose();}
  else if(alignment==GoetiaPlayer.Alignment.ANGEL){var out=new Scrolling(buffers.getBuffer(AURA_TYPE),-(p.tickCount+partial)*.01f);int[] alpha={204,102,51};for(int i=0;i<3;i++){var model=shells.get(i);model.young=false;model.riding=false;model.crouching=false;model.setupAnim(p,limb,amount,age,yaw,pitch);model.renderToBuffer(pose,out,light,OverlayTexture.NO_OVERLAY,127/255F,127/255F,127/255F,alpha[i]/255F);}}
 }
 private record Scrolling(VertexConsumer target,float offset) implements VertexConsumer {
  public VertexConsumer vertex(double x,double y,double z){target.vertex(x,y,z);return this;}public VertexConsumer color(int r,int g,int b,int a){target.color(r,g,b,a);return this;}public VertexConsumer uv(float u,float v){target.uv(u+offset,v+offset);return this;}public VertexConsumer overlayCoords(int u,int v){target.overlayCoords(u,v);return this;}public VertexConsumer uv2(int u,int v){target.uv2(u,v);return this;}public VertexConsumer normal(float x,float y,float z){target.normal(x,y,z);return this;}public void endVertex(){target.endVertex();}public void defaultColor(int r,int g,int b,int a){target.defaultColor(r,g,b,a);}public void unsetDefaultColor(){target.unsetDefaultColor();}
 }
}
