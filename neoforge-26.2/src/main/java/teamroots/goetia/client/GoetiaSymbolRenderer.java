package teamroots.goetia.client;
import com.mojang.blaze3d.vertex.PoseStack;
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
import teamroots.goetia.GoetiaSymbol;
public final class GoetiaSymbolRenderer extends MobRenderer<GoetiaSymbol,GoetiaSymbolRenderer.State,GoetiaSymbolRenderer.Model> {
 public static final String[] TEXTURES={"imp_symbol","fiend_symbol","demon_symbol","devil_trap","forge_symbol","open_soul_symbol"};
 public static final class State extends LivingEntityRenderState {public int kind;public float ready,fade,angle;public boolean active;}
 public static final class Model extends EntityModel<State>{public Model(){super(new ModelPart(java.util.List.of(),java.util.Map.of()),RenderTypes::entityTranslucent);}}
 private final LegacyGeometry geometry=new LegacyGeometry("ModelSymbol");
 public GoetiaSymbolRenderer(EntityRendererProvider.Context context){super(context,new Model(),0);addLayer(new RenderLayer<State,Model>(this){
  @Override public void submit(PoseStack pose,SubmitNodeCollector collector,int light,State state,float yaw,float pitch){boolean ghost=state.isInvisible&&!state.isInvisibleToPlayer;int overlay=LivingEntityRenderer.getOverlayCoords(state,0);var texture=getTextureLocation(state);if(!state.isInvisible||ghost)submitGeometry(pose,collector,state,light,overlay,ghost?RenderTypes.entityTranslucentCullItemTarget(texture):RenderTypes.entityTranslucent(texture),ghost?0x26FFFFFF:0xFFFFFFFF,false);if(state.appearsGlowing())submitGeometry(pose,collector,state,light,overlay,RenderTypes.outline(texture),0xFF000000|state.outlineColor,true);}
 });}
 private void submitGeometry(PoseStack pose,SubmitNodeCollector collector,State state,int light,int overlay,RenderType type,int baseColor,boolean outline){collector.submitCustomGeometry(pose,type,(snapshot,out)->{var local=new PoseStack();local.last().set(snapshot);float bright=state.active?1:.6f;int rgb=outline?(baseColor&0xFFFFFF):((int)(255*bright)<<16)|((int)(237*bright)<<8)|(int)(199*bright);int alpha=outline?255:Math.max(0,Math.min(255,(int)(state.fade*(baseColor>>>24))));local.translate(0,1.35f-.5f*state.ready,0);local.mulPose(Axis.YP.rotationDegrees(state.angle));geometry.draw("symbol",local,out,state.active?255:light,overlay,(alpha<<24)|rgb);});}
 @Override public State createRenderState(){return new State();}
 @Override public void extractRenderState(GoetiaSymbol entity,State state,float partial){super.extractRenderState(entity,state,partial);state.kind=entity.kind;state.active=entity.active();state.ready=entity.ready();state.fade=state.ready>=1?entity.fade()-partial*.05f:1;state.angle=entity.angle+partial*entity.renderSpin()*state.ready;}
 @Override public Identifier getTextureLocation(State state){return Identifier.fromNamespaceAndPath("goetia","textures/entity/"+TEXTURES[state.kind]+".png");}
 @Override protected boolean shouldShowName(GoetiaSymbol entity,double distance){return false;}
}
