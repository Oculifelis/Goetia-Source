package teamroots.goetia.compat.jei;
import java.util.List;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import teamroots.goetia.GoetiaContent;
import teamroots.goetia.recipes.DemonForgeRecipeManager;
@JeiPlugin
public final class GoetiaJei implements IModPlugin {
 public record ForgeDisplay(ItemStack input,ItemStack output,int cost){}
 public static final RecipeType<ForgeDisplay> TYPE=RecipeType.create("goetia","demon_forge",ForgeDisplay.class);
 public static volatile IJeiRuntime runtime;
 @Override public ResourceLocation getPluginUid(){return ResourceLocation.fromNamespaceAndPath("goetia","jei");}
 @Override public void onRuntimeAvailable(IJeiRuntime value){runtime=value;}
 @Override public void onRuntimeUnavailable(){runtime=null;}
 @Override public void registerCategories(IRecipeCategoryRegistration r){r.addRecipeCategories(new Category(r.getJeiHelpers().getGuiHelper()));}
 @Override public void registerRecipes(IRecipeRegistration r){var rows=DemonForgeRecipeManager.recipes.entrySet().stream().map(e->new ForgeDisplay(new ItemStack(e.getKey()),e.getValue().result.copy(),e.getValue().cost)).toList();r.addRecipes(TYPE,rows);}
 @Override public void registerRecipeCatalysts(IRecipeCatalystRegistration r){r.addRecipeCatalyst(new ItemStack(GoetiaContent.item("altar")),TYPE);}
 public static final class Category implements IRecipeCategory<ForgeDisplay> {
  private final IDrawable background,icon;
  public Category(IGuiHelper gui){background=gui.createDrawable(ResourceLocation.fromNamespaceAndPath("goetia","textures/gui/jei_demon_forge.png"),30,16,110,60);icon=gui.createDrawableIngredient(mezz.jei.api.constants.VanillaTypes.ITEM_STACK,new ItemStack(GoetiaContent.item("altar")));}
  @Override public RecipeType<ForgeDisplay> getRecipeType(){return TYPE;}
  @Override public Component getTitle(){return Component.translatable("gui.jei.category.demon_forge");}
  @Override public IDrawable getBackground(){return background;}
  @Override public IDrawable getIcon(){return icon;}
  @Override public void setRecipe(IRecipeLayoutBuilder b,ForgeDisplay r,IFocusGroup f){b.addSlot(RecipeIngredientRole.INPUT,13,17).addItemStack(r.input());b.addSlot(RecipeIngredientRole.OUTPUT,85,17).addItemStack(r.output());}
  @Override public void draw(ForgeDisplay r,IRecipeSlotsView slots,GuiGraphics g,double mouseX,double mouseY){g.drawString(Minecraft.getInstance().font,Integer.toString(r.cost()),55,10,0xFFFF0000,false);}
 }
}
