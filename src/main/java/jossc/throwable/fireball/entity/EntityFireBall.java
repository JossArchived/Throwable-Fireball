package jossc.throwable.fireball.entity;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.projectile.EntityProjectile;
import cn.nukkit.event.entity.EntityExplosionPrimeEvent;
import cn.nukkit.level.Explosion;
import cn.nukkit.level.Sound;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.network.protocol.AddEntityPacket;
import jossc.throwable.fireball.utils.Utils;

public class EntityFireBall extends EntityProjectile {

  public static int NETWORK_ID = 94;

  public EntityFireBall(
    FullChunk chunk,
    CompoundTag nbt,
    Entity shootingEntity
  ) {
    super(chunk, nbt, shootingEntity);
  }

  @Override
  public int getNetworkId() {
    return NETWORK_ID;
  }

  @Override
  public float getWidth() {
    return 0.31f;
  }

  @Override
  public float getLength() {
    return 0.25f;
  }

  @Override
  public float getHeight() {
    return 0.31f;
  }

  @Override
  protected float getGravity() {
    return 0.03f;
  }

  @Override
  protected float getDrag() {
    return 0.01f;
  }

  @Override
  public boolean onUpdate(int currentTick) {
    if (isClosed()) {
      return false;
    }

    timing.startTiming();

    Utils.coolFx(getLocation(), getDirectionVector());

    updateMovement();

    boolean hasUpdated = super.onUpdate(currentTick);

    int lg = 1200;
    if (age > lg || isCollided) {
      getLevel().addSound(asVector3f().asVector3(), Sound.RANDOM_EXPLODE);

      EntityExplosionPrimeEvent event = new EntityExplosionPrimeEvent(this, 2);
      server.getPluginManager().callEvent(event);

      if (!event.isCancelled()) {
        new Explosion(this, event.getForce(), this).explodeB();
      }

      close();
      kill();

      hasUpdated = true;
    }

    timing.stopTiming();

    return hasUpdated;
  }

  @Override
  public void spawnTo(Player player) {
    AddEntityPacket pk = new AddEntityPacket();

    pk.type = NETWORK_ID;
    pk.entityRuntimeId = id;
    pk.entityUniqueId = id;
    pk.x = (float) x;
    pk.y = (float) y;
    pk.z = (float) z;
    pk.speedX = (float) motionX;
    pk.speedY = (float) motionY;
    pk.speedZ = (float) motionZ;
    pk.metadata = dataProperties;

    super.spawnTo(player);
  }
}
