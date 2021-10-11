package jossc.throwable.fireball.event;

import cn.nukkit.event.HandlerList;
import cn.nukkit.event.entity.EntityEvent;
import jossc.throwable.fireball.entity.EntityFireBall;

public class FireballMoveEvent extends EntityEvent {

  private static final HandlerList handlers = new HandlerList();

  private boolean spawnAuroraParticle = false;

  public FireballMoveEvent(EntityFireBall entityFireBall) {
    entity = entityFireBall;
  }

  public boolean isSpawnAuroraParticle() {
    return spawnAuroraParticle;
  }

  public void setSpawnAuroraParticle() {
    setSpawnAuroraParticle(true);
  }

  public void setSpawnAuroraParticle(boolean spawnAuroraParticle) {
    this.spawnAuroraParticle = spawnAuroraParticle;
  }

  public static HandlerList getHandlers() {
    return handlers;
  }
}
