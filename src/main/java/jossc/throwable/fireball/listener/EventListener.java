package jossc.throwable.fireball.listener;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemID;
import cn.nukkit.level.Sound;
import cn.nukkit.nbt.tag.CompoundTag;
import jossc.throwable.fireball.entity.EntityFireBall;
import jossc.throwable.fireball.utils.NBTUtils;

public class EventListener implements Listener {

  @EventHandler(priority = EventPriority.NORMAL)
  public void onInteract(PlayerInteractEvent event) {
    Player player = event.getPlayer();
    Item item = event.getItem();

    if (!item.equals(Item.get(ItemID.FIRE_CHARGE))) {
      return;
    }

    CompoundTag compoundTag = NBTUtils.generateFireBallNBTTo(player);

    EntityFireBall fireBall = new EntityFireBall(
      player.getChunk(),
      compoundTag,
      player
    );
    fireBall.spawnToAll();
    fireBall.setMotion(fireBall.getMotion().multiply(2));

    player
      .getLevel()
      .addSound(player.asVector3f().asVector3(), Sound.MOB_GHAST_FIREBALL);

    event.setCancelled();
  }
}
