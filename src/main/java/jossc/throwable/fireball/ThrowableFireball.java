package jossc.throwable.fireball;

import cn.nukkit.entity.Entity;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.TextFormat;
import jossc.throwable.fireball.entity.EntityFireBall;
import jossc.throwable.fireball.listener.EventListener;
import lombok.Getter;

public class ThrowableFireball extends PluginBase {

  @Getter
  private static ThrowableFireball instance;

  @Override
  public void onEnable() {
    super.onEnable();

    instance = this;

    Entity.registerEntity("EntityFireBall", EntityFireBall.class, true);

    getServer().getPluginManager().registerEvents(new EventListener(), this);

    getLogger().info(TextFormat.GREEN + "This plugin has been enabled!");
  }

  @Override
  public void onDisable() {
    super.onDisable();

    getLogger().info(TextFormat.RED + "This plugin has been disabled!");
  }
}
