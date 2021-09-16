package jossc.throwable.fireball.utils;

import cn.nukkit.Player;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.DoubleTag;
import cn.nukkit.nbt.tag.FloatTag;
import cn.nukkit.nbt.tag.ListTag;

public class NBTUtils {

  public static CompoundTag generateFireBallNBTTo(Player player) {
    return new CompoundTag()
      .putList(
        new ListTag<>("Pos")
          .add(new DoubleTag("", player.x))
          .add(new DoubleTag("", player.y + 2))
          .add(new DoubleTag("", player.z))
      )
      .putList(
        new ListTag<DoubleTag>("Motion")
          .add(
            new DoubleTag(
              "",
              -Math.sin(player.yaw / 180 * Math.PI) *
              Math.cos(player.pitch / 180 * Math.PI)
            )
          )
          .add(new DoubleTag("", -Math.sin(player.pitch / 180 * Math.PI)))
          .add(
            new DoubleTag(
              "",
              Math.cos(player.yaw / 180 * Math.PI) *
              Math.cos(player.pitch / 180 * Math.PI)
            )
          )
      )
      .putList(
        new ListTag<FloatTag>("Rotation")
          .add(new FloatTag("", (float) player.yaw))
          .add(new FloatTag("", (float) player.pitch))
      );
  }
}
