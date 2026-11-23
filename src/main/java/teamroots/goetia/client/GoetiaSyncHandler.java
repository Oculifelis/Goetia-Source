package teamroots.goetia.client;
public final class GoetiaSyncHandler {
 public static void apply(teamroots.goetia.GoetiaNetwork.Sync data){var level=net.minecraft.client.Minecraft.getInstance().level;if(level!=null&&level.getEntity(data.entity()) instanceof net.minecraft.world.entity.player.Player p&&data.data()!=null)p.getPersistentData().put("goetia",data.data());}
 private GoetiaSyncHandler(){}
}
