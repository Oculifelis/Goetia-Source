package teamroots.goetia.client;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import teamroots.goetia.GoetiaSymbol;
public final class GoetiaSymbolRenderer extends MobRenderer<GoetiaSymbol,GoetiaSymbolRenderer.Model> {
 public static final String[] TEXTURES={"imp_symbol","fiend_symbol","demon_symbol","devil_trap","forge_symbol","open_soul_symbol"};
 public GoetiaSymbolRenderer(EntityRendererProvider.Context c){super(c,new Model(),0);}
 @Override public ResourceLocation getTextureLocation(GoetiaSymbol e){return ResourceLocation.fromNamespaceAndPath("goetia","textures/entity/"+TEXTURES[e.kind]+".png");}
 @Override protected boolean shouldShowName(GoetiaSymbol e){return false;}
 public static final class Model extends EntityModel<GoetiaSymbol> {
  private final LegacyGeometry geometry=new LegacyGeometry("ModelSymbol");private GoetiaSymbol entity;private float partial;
  public Model(){super(RenderType::entityTranslucent);}
  @Override public void setupAnim(GoetiaSymbol e,float a,float b,float age,float y,float p){entity=e;partial=age-(int)age;}
  @Override public void renderToBuffer(PoseStack pose,VertexConsumer out,int light,int overlay,float red,float green,float blue,float alpha){int color=((int)(alpha*255)<<24)|((int)(red*255)<<16)|((int)(green*255)<<8)|(int)(blue*255);float fade=entity.ready()>=1?entity.fade()-partial*.05f:1;float bright=entity.active()?1:.6f;int rgb=((int)(255*bright)<<16)|((int)(237*bright)<<8)|(int)(199*bright);int tint=(Math.max(0,Math.min(255,(int)(fade*255)))<<24)|rgb;float angle=entity.angle+partial*entity.renderSpin()*entity.ready();pose.pushPose();pose.translate(0,1.35f-.5f*entity.ready(),0);pose.mulPose(Axis.YP.rotationDegrees(angle));geometry.draw("symbol",pose,out,entity.active()?255:light,overlay,tint);pose.popPose();}
 }
}
