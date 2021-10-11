package jossc.throwable.fireball.event;

import cn.nukkit.event.Cancellable;
import cn.nukkit.event.HandlerList;
import cn.nukkit.event.entity.EntityEvent;
import jossc.throwable.fireball.entity.EntityFireBall;

public class FireballCollideEvent extends EntityEvent implements Cancellable {

  private static final HandlerList handlers = new HandlerList();

  public FireballCollideEvent(EntityFireBall entityFireBall) {
    entity = entityFireBall;
  }

  public static HandlerList getHandlers() {
    return handlers;
  }
}
