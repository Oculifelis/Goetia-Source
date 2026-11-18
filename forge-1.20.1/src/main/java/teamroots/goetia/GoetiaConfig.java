package teamroots.goetia;
import net.minecraftforge.common.ForgeConfigSpec;
public final class GoetiaConfig {
 public static final ForgeConfigSpec SPEC;
 public static final ForgeConfigSpec.IntValue THRESHOLD,OFFSET_X,OFFSET_Y;
 public static final ForgeConfigSpec.BooleanValue ALIGNMENT_MODELS;
 static { var b=new ForgeConfigSpec.Builder();THRESHOLD=b.defineInRange("alignmentThreshold",1000,0,Integer.MAX_VALUE);OFFSET_X=b.defineInRange("alignmentIconOffsetX",425,0,32767);OFFSET_Y=b.defineInRange("alignmentIconOffsetY",20,0,32767);ALIGNMENT_MODELS=b.define("alignmentModels",true);SPEC=b.build(); }
 private GoetiaConfig(){}
}
