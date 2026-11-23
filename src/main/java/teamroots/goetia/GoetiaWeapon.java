package teamroots.goetia;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
public final class GoetiaWeapon extends SwordItem {
 public final String id;
 public record LegacyTier(int getUses,float getAttackDamageBonus) implements Tier {
  public float getSpeed(){return 3;}public int getEnchantmentValue(){return 3;}public Ingredient getRepairIngredient(){return Ingredient.EMPTY;}public TagKey<Block> getIncorrectBlocksForDrops(){return Tiers.DIAMOND.getIncorrectBlocksForDrops();}
 }
 private static Tier tier(String id){return new LegacyTier(id.equals("blood_knife")?50:749,id.equals("blood_knife")?-3:id.equals("abyssal_blade")?4:2.5f);}
 public GoetiaWeapon(String id){super(tier(id),new Properties().setNoRepair().attributes(createAttributes(tier(id),3,id.equals("blood_knife")?0:-2.4f)));this.id=id;}
 @Override public boolean isValidRepairItem(ItemStack a,ItemStack b){return false;}
 @Override public boolean onLeftClickEntity(ItemStack stack,Player p,Entity target){if(!p.level().isClientSide){if(!id.equals("blood_knife"))target.igniteForSeconds(3);if(id.equals("demon_horn_spear"))p.getFoodData().eat(1,.5f);else p.causeFoodExhaustion(1);}return false;}
}
