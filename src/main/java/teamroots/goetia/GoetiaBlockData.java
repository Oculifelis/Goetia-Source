package teamroots.goetia;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
public final class GoetiaBlockData extends BlockEntity {
 public static final DeferredRegister<BlockEntityType<?>> TYPES=DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE,"goetia");
 public static final RegistryObject<BlockEntityType<GoetiaBlockData>> TYPE=TYPES.register("bowl_and_note",()->BlockEntityType.Builder.of(GoetiaBlockData::new,GoetiaContent.block("empty_bowl"),GoetiaContent.block("full_bowl"),GoetiaContent.block("block_demon_lost_note"),GoetiaContent.block("block_angel_lost_note")).build(null));
 public int color;public String entity="",knowledge="";
 public GoetiaBlockData(BlockPos pos,BlockState state){super(TYPE.get(),pos,state);}
 @Override protected void saveAdditional(CompoundTag d){super.saveAdditional(d);d.putInt("color",color);d.putString("entity",entity);d.putString("knowledge",knowledge);}
 @Override public void load(CompoundTag d){super.load(d);color=d.getInt("color");entity=d.getString("entity");knowledge=d.getString("knowledge");}
 @Override public CompoundTag getUpdateTag(){return saveWithoutMetadata();}
 @Override public ClientboundBlockEntityDataPacket getUpdatePacket(){return ClientboundBlockEntityDataPacket.create(this);}
 public void changed(){setChanged();if(level!=null)level.sendBlockUpdated(worldPosition,getBlockState(),getBlockState(),3);}
}
