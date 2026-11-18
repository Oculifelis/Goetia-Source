package teamroots.goetia;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
public final class GoetiaFireballData {
 public static final DeferredRegister<AttachmentType<?>> TYPES=DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES,"goetia");
 public static final DeferredHolder<AttachmentType<?>,AttachmentType<CompoundTag>> POWER=TYPES.register("legacy_fireball_power",()->AttachmentType.builder(() -> new CompoundTag()).sync(ByteBufCodecs.COMPOUND_TAG).build());
 public static void register(IEventBus bus){TYPES.register(bus);}
 private GoetiaFireballData(){}
}
