package teamroots.goetia.client;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import teamroots.goetia.*;
public final class GoetiaScreen extends Screen {
 private static final Identifier SLOTS=Identifier.fromNamespaceAndPath("goetia","textures/gui/gui_slot.png");
 public final String mode;public final InteractionHand hand;private final List<Integer> steps=new ArrayList<>();private boolean tracking;private float lifetime;
 public GoetiaScreen(String mode,InteractionHand hand){super(Component.literal("Goetia"));this.mode=mode;this.hand=hand;}
 @Override public boolean isPauseScreen(){return mode.equals("altar");}
 private List<String> knowledge(){return GoetiaPlayer.knowledge(minecraft.player).stream().filter(GoetiaPlayer.SYMBOLS::contains).toList();}
 private List<GoetiaSpells.Spell> spells(){return GoetiaSpells.ALL.stream().filter(s->GoetiaSpells.eligible(minecraft.player,s)).toList();}
 private float x(int i,int n,float radius){return width/2f+radius*(float)Math.cos((float)i/n*Math.PI*2-Math.PI/2);}
 private float y(int i,int n,float radius){return height/2f+radius*(float)Math.sin((float)i/n*Math.PI*2-Math.PI/2);}
 private boolean over(double mx,double my,int i,int n,int size){return Math.abs(mx-x(i,n,80))<=size&&Math.abs(my-y(i,n,80))<=size;}
 @Override public boolean mouseClicked(MouseButtonEvent event,boolean doubleClick){double mx=event.x(),my=event.y();int button=event.button();if(mode.equals("focus")){for(int i=0;i<6;i++)if(over(mx,my,i,6,16)){tracking=true;steps.add(i);}return true;}if(mode.equals("chalk")){var known=knowledge();for(int i=0;i<known.size();i++)if(over(mx,my,i,known.size(),24)){String key=known.get(i);GoetiaItem.edit(minecraft.player.getMainHandItem(),d->d.putString("knowledge",key));ClientPacketDistributor.sendToServer(new GoetiaNetwork.Action("chalk",key));}return true;}return super.mouseClicked(event,doubleClick);}
 @Override public boolean mouseDragged(MouseButtonEvent event,double dx,double dy){double mx=event.x(),my=event.y();int button=event.button();if(mode.equals("focus")&&tracking&&!steps.isEmpty()){for(int i=0;i<6;i++)if(over(mx,my,i,6,16)&&steps.get(steps.size()-1)!=i)steps.add(i);return true;}return super.mouseDragged(event,dx,dy);}
 @Override public boolean mouseReleased(MouseButtonEvent event){double mx=event.x(),my=event.y();int button=event.button();if(mode.equals("focus")&&tracking){for(var s:spells()){if(s.shape().length!=steps.size())continue;boolean matches=true;for(int i=0;i<steps.size();i++)if(s.shape()[i]!=steps.get(i)+1)matches=false;if(matches){GoetiaEffects.spell(minecraft.player,s.name());ClientPacketDistributor.sendToServer(new GoetiaNetwork.Action("cast",s.name()));break;}}minecraft.setScreen(null);return true;}return super.mouseReleased(event);}
 private static void blit(GuiGraphicsExtractor g,Identifier tex,int x,int y,float u,float v,int w,int h){blit(g,tex,x,y,u,v,w,h,256,256,0xFFFFFFFF);}
 private static void blit(GuiGraphicsExtractor g,Identifier tex,int x,int y,float u,float v,int w,int h,int tw,int th,int color){g.blit(RenderPipelines.GUI_TEXTURED,tex,x,y,u,v,w,h,tw,th,color);}
 private void line(GuiGraphicsExtractor g,float x,float y,float x2,float y2,int width,int color){g.pose().pushMatrix();g.pose().translate(x,y);g.pose().rotate((float)Math.atan2(y2-y,x2-x));g.fill(0,-width/2,(int)Math.ceil(Math.hypot(x2-x,y2-y)),width/2,color);g.pose().popMatrix();}
 @Override public void extractRenderState(GuiGraphicsExtractor g,int mx,int my,float partial){if(minecraft.player==null)return;lifetime+=10;
  if(mode.equals("focus")){if(!tracking){boolean demon=GoetiaPlayer.lean(minecraft.player)==GoetiaPlayer.Alignment.DEMON;String label=Component.translatable(demon?"goetia.tooltip.impurity":"goetia.tooltip.purity").getString()+GoetiaPlayer.data(minecraft.player).getIntOr(demon?"impurity":"purity",0);g.centeredText(font,label,width/2,height/2-font.lineHeight/2+24,demon?0xFFFF4444:0xFF2DE1FC);}for(int i=0;i<6;i++){blit(g,SLOTS,(int)x(i,6,80)-16,(int)y(i,6,80)-16,96,0,32,32);g.centeredText(font,Integer.toString(i+1),(int)x(i,6,105),(int)y(i,6,105)-font.lineHeight/2,0xFFFFFFFF);}if(tracking&&!steps.isEmpty()){for(int i=0;i<6;i++)if(over(mx,my,i,6,16)&&steps.get(steps.size()-1)!=i)steps.add(i);boolean demon=GoetiaPlayer.lean(minecraft.player)==GoetiaPlayer.Alignment.DEMON;int rgb=demon?0xFF0000:0x0000FF;for(int pass=0;pass<2;pass++){for(int i=0;i<steps.size();i++){int a=steps.get(i);float bx=i+1<steps.size()?x(steps.get(i+1),6,80):mx,by=i+1<steps.size()?y(steps.get(i+1),6,80):my;line(g,x(a,6,80),y(a,6,80),bx,by,pass==0?6:18,(pass==0?0xFF000000:0x80000000)|rgb);}}for(int a:steps){g.pose().pushMatrix();g.pose().translate(x(a,6,80),y(a,6,80));g.pose().rotate((float)Math.toRadians(lifetime));blit(g,SLOTS,-16,-16,128,demon?0:32,32,32);g.pose().popMatrix();}}return;}
  if(mode.equals("chalk")){var known=knowledge();String selected=GoetiaItem.tag(minecraft.player.getMainHandItem()).getStringOr("knowledge","");if(known.isEmpty())g.centeredText(font,"You do not know any symbols!",width/2,height/2+24,0xFFFFFFFF);for(int i=0;i<known.size();i++){int x=(int)x(i,known.size(),80),y=(int)y(i,known.size(),80);blit(g,SLOTS,x-24,y-24,0,0,48,48);if(known.get(i).equals(selected))blit(g,SLOTS,x-32,y-32,0,48,64,64);int k=GoetiaPlayer.SYMBOLS.indexOf(known.get(i));var tex=Identifier.fromNamespaceAndPath("goetia","textures/entity/"+GoetiaSymbolRenderer.TEXTURES[k]+".png");blit(g,tex,x-16,y-16,32,0,32,32,128,64,0xFFFFEDC7);if(over(mx,my,i,known.size(),24))g.setComponentTooltipForNextFrame(font,List.of(Component.translatable("goetia.knowledge."+known.get(i)),Component.translatable("goetia.knowledge."+known.get(i)+".desc"),Component.translatable("goetia.tooltip.levelcost").append(Integer.toString(GoetiaSymbol.COSTS[k])).append(Component.translatable("goetia.tooltip.levelcost2"))),mx,my);}return;}
  var spells=spells();if(spells.isEmpty())g.centeredText(font,"You cannot use any spells!",width/2,height/2+24,0xFFFFFFFF);for(int i=0;i<spells.size();i++){var s=spells.get(i);int x=(int)x(i,spells.size(),80),y=(int)y(i,spells.size(),80);blit(g,SLOTS,x-16,y-16,160,0,32,32);g.item(new ItemStack(BuiltInRegistries.ITEM.getValue(Identifier.parse(s.icon()))),x-8,y-8);if(over(mx,my,i,spells.size(),16))g.setComponentTooltipForNextFrame(font,List.of(Component.translatable("goetia.spell."+s.name()),Component.translatable("goetia.spell."+s.name()+".desc"),Component.translatable("goetia.tooltip.impurity").append(Integer.toString(s.cost())),Component.translatable("goetia.tooltip.spellSteps").append(java.util.Arrays.toString(s.shape()).replace("[","").replace("]",""))),mx,my);}
 }
}
