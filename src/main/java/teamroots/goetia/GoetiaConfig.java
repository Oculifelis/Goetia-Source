package teamroots.goetia;
import net.neoforged.neoforge.common.ModConfigSpec;
public final class GoetiaConfig {
 public static final ModConfigSpec SPEC;
 public static final ModConfigSpec.IntValue THRESHOLD,OFFSET_X,OFFSET_Y;
 public static final ModConfigSpec.BooleanValue ALIGNMENT_MODELS;
 static { var b=new ModConfigSpec.Builder();THRESHOLD=b.defineInRange("alignmentThreshold",1000,0,Integer.MAX_VALUE);OFFSET_X=b.defineInRange("alignmentIconOffsetX",425,0,32767);OFFSET_Y=b.defineInRange("alignmentIconOffsetY",20,0,32767);ALIGNMENT_MODELS=b.define("alignmentModels",true);SPEC=b.build(); }
 private GoetiaConfig(){}
}
