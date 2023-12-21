package io.github.bedwarsrel.com.v1_20_R3;

import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.ai.goal.PathfinderGoalMeleeAttack;

public class PathfinderGoalBedwarsPlayer extends PathfinderGoalMeleeAttack {

  private EntityCreature creature = null;

  public PathfinderGoalBedwarsPlayer(EntityCreature name, double name3, boolean name4) {
    super(name, name3, name4);
    this.creature = name;
  }

  @Override
  public void e() {
    this.creature.N().a(this.creature.q(), 1);
  }

}
