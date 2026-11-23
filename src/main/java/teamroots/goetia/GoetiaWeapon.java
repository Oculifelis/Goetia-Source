package teamroots.goetia;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.enchantment.Repairable;
public final class GoetiaWeapon extends Item {
 public final String id;
 private static ToolMaterial material(String id){return new ToolMaterial(ToolMaterial.DIAMOND.incorrectBlocksForDrops(),id.equals("blood_knife")?50:749,3,id.equals("blood_knife")?-3:id.equals("abyssal_blade")?4:2.5f,3,ToolMaterial.DIAMOND.repairItems());}
 public GoetiaWeapon(String id){super(GoetiaContent.itemProperties(id).sword(material(id),3,id.equals("blood_knife")?0:-2.4f).component(DataComponents.REPAIRABLE,new Repairable(HolderSet.direct())));this.id=id;}
 @Override public boolean isCombineRepairable(ItemStack stack){return false;}
 @Override public boolean onLeftClickEntity(ItemStack stack,Player p,Entity target){if(!p.level().isClientSide()){if(!id.equals("blood_knife"))target.igniteForSeconds(3);if(id.equals("demon_horn_spear"))p.getFoodData().eat(1,.5f);else p.causeFoodExhaustion(1);}return false;}
}
