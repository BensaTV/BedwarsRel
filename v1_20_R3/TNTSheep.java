package io.github.bedwarsrel.com.v1_20_R3;

import io.github.bedwarsrel.BedwarsRel;
import io.github.bedwarsrel.shop.Specials.ITNTSheep;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.Sheep;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

public class TNTSheep extends Sheep implements ITNTSheep {

    private TNTPrimed primedTnt = null;
    private World world = null;
    private Entity tntSource = null; // Added field to track the TNT source

    public TNTSheep(World world) {
        super(world);
    }

    public TNTSheep(Location location, Player target) {
        super(location.getWorld());

        this.world = location.getWorld();

        this.teleport(location);

        // Set attributes
        this.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(128D);
        this.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)
                .setBaseValue(BedwarsRel.getInstance().getConfig().getDouble("specials.tntsheep.speed", 0.4D));

        // Add bedwars player goal
        this.setAI(false); // Disable default AI
        this.setTarget((LivingEntity) target);

        ((Creature) this).setTarget((LivingEntity) target);
    }

    public Location getLocation() {
        return this.getLocation();
    }

    public TNTPrimed getTNT() {
        return this.primedTnt;
    }

    public void setTNT(TNTPrimed tnt) {
        this.primedTnt = tnt;
    }

    public void remove() {
        this.remove();
    }

    public void setPassenger(TNTPrimed tnt) {
        this.setPassenger(tnt);
    }

    public void setTNTSource(Entity source) {
        if (source != null) {
            this.tntSource = source;
        }
    }

    // Method to get the TNT source
    public Entity getTNTSource() {
        return this.tntSource;
    }
}
