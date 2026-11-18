package teamroots.goetia.client;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import teamroots.goetia.GoetiaItem;
public record GoetiaBloodTint() implements ItemTintSource {
 public static final MapCodec<GoetiaBloodTint> CODEC=MapCodec.unit(new GoetiaBloodTint());
 @Override public int calculate(ItemStack stack,ClientLevel level,LivingEntity owner){var d=GoetiaItem.tag(stack);return d.contains("color")?0xFF000000|d.getIntOr("color",0):0xFFFFFFFF;}
 @Override public MapCodec<? extends ItemTintSource> type(){return CODEC;}
}
