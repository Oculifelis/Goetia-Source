package teamroots.goetia.client;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.client.event.*;

import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import teamroots.goetia.*;
@EventBusSubscriber(modid="goetia",bus=EventBusSubscriber.Bus.MOD,value=Dist.CLIENT)
public final class GoetiaClient {
 @SubscribeEvent public static void setup(FMLClientSetupEvent e){e.enqueueWork(()->{Goetia.tickVisual=GoetiaEffects::tick;Goetia.ritualVisual=GoetiaEffects::ritual;Goetia.attackVisual=GoetiaEffects::attack;Goetia.openScreen=(kind,hand)->Minecraft.getInstance().setScreen(new GoetiaScreen(kind,hand));for(var entry:GoetiaContent.BLOCK_MAP.entrySet())if(!entry.getKey().equals("altar"))ItemBlockRenderTypes.setRenderLayer(entry.getValue().get(),entry.getKey().contains("bowl")?RenderType.translucent():RenderType.cutout());ItemBlockRenderTypes.setRenderLayer(GoetiaFluids.SOURCE.get(),RenderType.translucent());ItemBlockRenderTypes.setRenderLayer(GoetiaFluids.FLOWING.get(),RenderType.translucent());});}
 @SubscribeEvent public static void renderers(EntityRenderersEvent.RegisterRenderers e){for(int i=0;i<3;i++){int kind=i;e.registerEntityRenderer(GoetiaEntities.DEMONS.get(i).get(),c->new GoetiaDemonRenderer(c,kind));}for(var h:GoetiaEntities.SYMBOLS)e.registerEntityRenderer(h.get(),GoetiaSymbolRenderer::new);e.registerEntityRenderer(GoetiaEntities.BLOOD.get(),net.minecraft.client.renderer.entity.NoopRenderer::new);}
 @SubscribeEvent public static void layers(EntityRenderersEvent.AddLayers e){for(var skin:e.getSkins()){var renderer=e.getSkin(skin);if(renderer instanceof net.minecraft.client.renderer.entity.player.PlayerRenderer playerRenderer)playerRenderer.addLayer(new GoetiaPlayerLayer(playerRenderer));}}

 @SubscribeEvent public static void items(RegisterColorHandlersEvent.Item e){e.register((s,t)->t==0&&GoetiaItem.tag(s).contains("color")?GoetiaItem.tag(s).getInt("color"):0xFFFFFF,GoetiaContent.item("liquid_bottle"));}
 @SubscribeEvent public static void blocks(RegisterColorHandlersEvent.Block e){e.register((state,level,pos,tint)->level!=null&&pos!=null&&tint==0&&level.getBlockEntity(pos) instanceof GoetiaBlockData d?d.color:0,GoetiaContent.block("full_bowl"),GoetiaContent.block("empty_bowl"));}
 @SubscribeEvent public static void hud(RegisterGuiOverlaysEvent e){e.registerAboveAll("alignment",(forgeGui,g,partial,width,height)->{var mc=Minecraft.getInstance();var p=mc.player;if(p==null)return;boolean demon=GoetiaPlayer.lean(p)==GoetiaPlayer.Alignment.DEMON;int x=g.guiWidth()-GoetiaConfig.OFFSET_X.get(),y=g.guiHeight()-GoetiaConfig.OFFSET_Y.get();g.blit(ResourceLocation.fromNamespaceAndPath("goetia","textures/gui/gui_overlay.png"),x,y,demon?0:21,0,20,20);int color=GoetiaPlayer.data(p).getBoolean("locked")?16775680:demon?16729156:3006972;g.drawString(mc.font,Integer.toString(GoetiaPlayer.data(p).getInt(demon?"impurity":"purity")),x+20,y+8,color,true);});}
}
