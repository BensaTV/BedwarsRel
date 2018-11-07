package io.github.bedwarsrel.com.v1_13_r1;

import io.github.bedwarsrel.BedwarsRel;
import io.github.bedwarsrel.shop.Specials.ITNTSheep;
import java.lang.reflect.Field;
import java.util.Set;
import net.minecraft.server.v1_13_R1.EntityLiving;
import net.minecraft.server.v1_13_R1.EntitySheep;
import net.minecraft.server.v1_13_R1.EntityTNTPrimed;
import net.minecraft.server.v1_13_R1.GenericAttributes;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_13_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_13_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_13_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_13_R1.entity.CraftTNTPrimed;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

public class TNTSheep extends EntitySheep implements ITNTSheep {

  private TNTPrimed primedTnt = null;
  private World world = null;

  public TNTSheep(net.minecraft.server.v1_13_R1.World world) {
    super(world);
  }

  public TNTSheep(Location location, Player target) {
    super(((CraftWorld) location.getWorld()).getHandle());

    this.world = location.getWorld();

    this.locX = location.getX();
    this.locY = location.getY();
    this.locZ = location.getZ();

    try {
      Field b = this.goalSelector.getClass().getDeclaredField("b");
      b.setAccessible(true);

      Set<?> goals = (Set<?>) b.get(this.goalSelector);
      goals.clear(); // Clears the goals

      this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(128D);
      this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED)
          .setValue(
              BedwarsRel.getInstance().getConfig().getDouble("specials.tntsheep.speed", 0.4D));
    } catch (Exception e) {
      BedwarsRel.getInstance().getBugsnag().notify(e);
      e.printStackTrace();
    }

    this.goalSelector.a(0, new PathfinderGoalBedwarsPlayer(this, 1D, false)); // Add bedwars player
    // goal
    try {
      this.setGoalTarget((EntityLiving) (((CraftPlayer) target).getHandle()),
          TargetReason.TARGET_ATTACKED_ENTITY, false);
    } catch (Exception ex) {
      // newer spigot builds
      if (ex instanceof NoSuchMethodException) {
        BedwarsRel.getInstance().getBugsnag().notify(ex);
        this.setGoalTarget((EntityLiving) (((CraftPlayer) target).getHandle()));
      }
    }

    ((Creature) this.getBukkitEntity()).setTarget((LivingEntity) target);
  }

  @Override
  public Location getLocation() {
    return new Location(this.world, this.locX, this.locY, this.locZ);
  }

  @Override
  public TNTPrimed getTNT() {
    return this.primedTnt;
  }

  @Override
  public void setTNT(TNTPrimed tnt) {
    this.primedTnt = tnt;
  }

  @Override
  public void remove() {
    this.getBukkitEntity().remove();
  }

  @Override
  public void setPassenger(TNTPrimed tnt) {
    this.getBukkitEntity().setPassenger(tnt);
  }

  @Override
  public void setTNTSource(Entity source) {
    if (source == null) {
      return;
    }

    try {
      Field sourceField = EntityTNTPrimed.class.getDeclaredField("source");
      sourceField.setAccessible(true);
      sourceField.set(((CraftTNTPrimed) this.primedTnt).getHandle(),
          ((CraftEntity) source).getHandle());
    } catch (Exception ex) {
      BedwarsRel.getInstance().getBugsnag().notify(ex);
      // didn't work
    }
  }

}
