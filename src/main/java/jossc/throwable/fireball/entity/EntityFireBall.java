package jossc.throwable.fireball.entity;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.projectile.EntityProjectile;
import cn.nukkit.event.Event;
import cn.nukkit.level.ParticleEffect;
import cn.nukkit.level.Sound;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.network.protocol.AddEntityPacket;
import jossc.throwable.fireball.event.FireballCollideEvent;
import jossc.throwable.fireball.event.FireballMoveEvent;
import jossc.throwable.fireball.utils.ParticlesUtils;

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

    FireballMoveEvent moveEvent = new FireballMoveEvent(this);
    callEvent(moveEvent);

    if (moveEvent.isSpawnAuroraParticle()) {
      ParticlesUtils.invokeAurora(this, getDirectionVector());
    }

    updateMovement();

    boolean hasUpdated = super.onUpdate(currentTick);

    int length = 1200;
    if (
      age > length ||
      isCollided ||
      isInsideOfFire() ||
      !isAlive() ||
      isInsideOfWater() ||
      isOnGround()
    ) {
      close();

      FireballCollideEvent collideEvent = new FireballCollideEvent(this);
      callEvent(collideEvent);

      if (!collideEvent.isCancelled()) {
        Vector3 vector3 = asVector3f().asVector3();

        getLevel().addSound(vector3, Sound.RANDOM_EXPLODE);
        getLevel()
          .addParticleEffect(vector3, ParticleEffect.LARGE_EXPLOSION_LEVEL);
      }

      hasUpdated = true;
    }

    timing.stopTiming();

    return hasUpdated;
  }

  private void callEvent(Event event) {
    server.getPluginManager().callEvent(event);
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
