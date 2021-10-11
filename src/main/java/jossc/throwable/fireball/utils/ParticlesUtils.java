package jossc.throwable.fireball.utils;

import cn.nukkit.level.Location;
import cn.nukkit.level.particle.GenericParticle;
import cn.nukkit.level.particle.Particle;
import cn.nukkit.math.Vector3;
import org.joml.Matrix3f;
import org.joml.Vector3f;

public class ParticlesUtils {

  public static void invokeAurora(Location location, Vector3 direction) {
    Matrix3f rotationMatrix = new Matrix3f()
      .setLookAlong(
        new Vector3f(
          (float) direction.getX(),
          (float) direction.getY(),
          (float) direction.getZ()
        )
          .negate(),
        new Vector3f(0, 1, 0)
      )
      .invert();

    Location aBitInFront = location
      .clone()
      .add(direction.clone().multiply(1.5));

    for (
      double angleRad = 0;
      angleRad < 4 * Math.PI;
      angleRad += Math.PI / 16
    ) {
      double x = Math.sin(angleRad) * (angleRad / 4);
      double y = Math.cos(angleRad) * (angleRad / 4);
      double z = angleRad / 3;

      Vector3f twisted = rotationMatrix.transform(
        new Vector3f((float) x, (float) y, (float) z)
      );

      Location particleLoc = aBitInFront
        .clone()
        .add(twisted.x, twisted.y, twisted.z);

      location
        .getLevel()
        .addParticle(
          new GenericParticle(particleLoc, Particle.TYPE_FIREWORKS_SPARK)
        );
    }
  }
}
