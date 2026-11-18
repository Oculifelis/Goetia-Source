package teamroots.goetia;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
public final class GoetiaBlockData extends BlockEntity {
 public static final DeferredRegister<BlockEntityType<?>> TYPES=DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE,"goetia");
 public static final DeferredHolder<BlockEntityType<?>,BlockEntityType<GoetiaBlockData>> TYPE=TYPES.register("bowl_and_note",()->BlockEntityType.Builder.of(GoetiaBlockData::new,GoetiaContent.block("empty_bowl"),GoetiaContent.block("full_bowl"),GoetiaContent.block("block_demon_lost_note"),GoetiaContent.block("block_angel_lost_note")).build(null));
 public int color;public String entity="",knowledge="";
 public GoetiaBlockData(BlockPos pos,BlockState state){super(TYPE.get(),pos,state);}
 @Override protected void saveAdditional(CompoundTag d,HolderLookup.Provider p){super.saveAdditional(d,p);d.putInt("color",color);d.putString("entity",entity);d.putString("knowledge",knowledge);}
 @Override protected void loadAdditional(CompoundTag d,HolderLookup.Provider p){super.loadAdditional(d,p);color=d.getInt("color");entity=d.getString("entity");knowledge=d.getString("knowledge");}
 @Override public CompoundTag getUpdateTag(HolderLookup.Provider p){return saveWithoutMetadata(p);}
 @Override public ClientboundBlockEntityDataPacket getUpdatePacket(){return ClientboundBlockEntityDataPacket.create(this);}
 public void changed(){setChanged();if(level!=null)level.sendBlockUpdated(worldPosition,getBlockState(),getBlockState(),3);}
}
