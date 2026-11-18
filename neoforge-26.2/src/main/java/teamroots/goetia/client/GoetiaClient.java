package teamroots.goetia.client;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.renderer.block.FluidModel;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.entity.ClientAvatarEntity;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.fluid.FluidTintSources;
import net.neoforged.neoforge.client.renderstate.*;
import teamroots.goetia.*;
@EventBusSubscriber(modid="goetia",value=Dist.CLIENT)
public final class GoetiaClient {
 @SubscribeEvent public static void setup(FMLClientSetupEvent e){e.enqueueWork(()->{Goetia.tickVisual=GoetiaEffects::tick;Goetia.ritualVisual=GoetiaEffects::ritual;Goetia.attackVisual=GoetiaEffects::attack;Goetia.openScreen=(kind,hand)->Minecraft.getInstance().gui.setScreen(new GoetiaScreen(kind,hand));});}
 @SubscribeEvent public static void renderers(EntityRenderersEvent.RegisterRenderers e){for(int i=0;i<3;i++){int kind=i;e.registerEntityRenderer(GoetiaEntities.DEMONS.get(i).get(),c->new GoetiaDemonRenderer(c,kind));}for(var h:GoetiaEntities.SYMBOLS)e.registerEntityRenderer(h.get(),GoetiaSymbolRenderer::new);e.registerEntityRenderer(GoetiaEntities.BLOOD.get(),net.minecraft.client.renderer.entity.NoopRenderer::new);}
 @SubscribeEvent public static void layers(EntityRenderersEvent.AddLayers e){for(var skin:e.getSkins()){var renderer=e.getPlayerRenderer(skin);renderer.addLayer(new GoetiaPlayerLayer(renderer));}}
 @SubscribeEvent public static void states(RegisterRenderStateModifiersEvent e){e.registerAvatarEntityModifier(new AvatarRenderStateModifier(){@Override public <T extends Avatar & ClientAvatarEntity> void accept(T avatar,AvatarRenderState state){state.setRenderData(GoetiaPlayerLayer.ALIGNMENT_KEY,avatar instanceof Player p?GoetiaPlayer.alignment(p):GoetiaPlayer.Alignment.HUMAN);}});}
 @SubscribeEvent public static void pipelines(RegisterRenderPipelinesEvent e){e.registerPipeline(GoetiaPlayerLayer.AURA_PIPELINE);}
 @SubscribeEvent public static void fluid(RegisterFluidModelsEvent e){e.register(new FluidModel.Unbaked(new Material(Identifier.fromNamespaceAndPath("goetia","fluid/holy_still"),true),new Material(Identifier.fromNamespaceAndPath("goetia","fluid/holy_flowing"),true),null,FluidTintSources.constant(0xFFFFFFFF)),GoetiaFluids.SOURCE.get(),GoetiaFluids.FLOWING.get());}
 @SubscribeEvent public static void items(RegisterColorHandlersEvent.ItemTintSources e){e.register(Identifier.fromNamespaceAndPath("goetia","blood"),GoetiaBloodTint.CODEC);}
 @SubscribeEvent public static void blocks(RegisterColorHandlersEvent.BlockTintSources e){e.register(List.of(new BlockTintSource(){@Override public int color(BlockState state){return 0xFF000000;}@Override public int colorInWorld(BlockState state,BlockAndTintGetter level,BlockPos pos){return 0xFF000000|(level.getBlockEntity(pos) instanceof GoetiaBlockData d?d.color:0);}}),GoetiaContent.block("full_bowl"),GoetiaContent.block("empty_bowl"));}
 @SubscribeEvent public static void hud(RegisterGuiLayersEvent e){e.registerAboveAll(Identifier.fromNamespaceAndPath("goetia","alignment"),(g,delta)->{var mc=Minecraft.getInstance();var p=mc.player;if(p==null)return;boolean demon=GoetiaPlayer.lean(p)==GoetiaPlayer.Alignment.DEMON;int x=g.guiWidth()-GoetiaConfig.OFFSET_X.get(),y=g.guiHeight()-GoetiaConfig.OFFSET_Y.get();g.blit(RenderPipelines.GUI_TEXTURED,Identifier.fromNamespaceAndPath("goetia","textures/gui/gui_overlay.png"),x,y,demon?0:21,0,20,20,256,256);int color=GoetiaPlayer.data(p).getBooleanOr("locked",false)?16775680:demon?16729156:3006972;g.text(mc.font,Integer.toString(GoetiaPlayer.data(p).getIntOr(demon?"impurity":"purity",0)),x+20,y+8,0xFF000000|color,true);});}
}
