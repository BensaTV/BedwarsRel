package io.github.bedwarsrel.com.v1_20_R3;

import io.github.bedwarsrel.BedwarsRel;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.Particles;
import net.minecraft.network.protocol.game.PacketPlayOutWorldParticles;

import org.bukkit.Particle;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ParticleSpawner {


  public static void spawnParticle(List<Player> players, String particle, float x, float y,
                                   float z) {

    // Default particletype (fireworks)
    // Source: https://www.spigotmc.org/threads/enumparticle-replacement-in-1-13.330756/
    ParticleType particleType = Particles.w;

    if (isValidParticleType(particle)) {
      ParticleType particleTypeFound = getParticleTypeForName(particle);
      if (particleTypeFound != null) {
        particleType = particleTypeFound;
      }
    } else {
      BedwarsRel.getInstance().getLogger().warning("Particletype " + particle + " is not supported in your server version! Using default (fireworks).");
    }

    PacketPlayOutWorldParticles packet =
            new PacketPlayOutWorldParticles(particleType, false, x, y, z, 0.0F, 0.0F, 0.0F, 0.0F, 1);
    for (Player player : players) {
      CraftPlayer craftPlayer = (CraftPlayer) player;
      craftPlayer.getHandle().c.a(packet);

    }
  }

  private static boolean isValidParticleType(String particleTypeName) {
    ArrayList<String> validParticleTypeNames = getAllValidParticleTypeNames();
    return validParticleTypeNames.contains(particleTypeName);
  }

  private static ArrayList<String> getAllValidParticleTypeNames() {
    ArrayList<String> validParticleTypeNames = new ArrayList<>();

    // Receive all valid particle names by the declared fields minecrafts particleclass
    for (Field f : Particles.class.getDeclaredFields()) {
      try {
        Object returnedObject = f.get(null);

        if (returnedObject instanceof ParticleType) {
          ParticleType particleType = (ParticleType) returnedObject;
          // a string starting with "minecraft:"
          String particleTypeName = particleType.a();
          particleTypeName = particleTypeName.replaceFirst("^minecraft:", "");
          validParticleTypeNames.add(particleTypeName);
        }
      } catch (Exception ex) {
        // IllegalArgumentException / IllegalAccessException
        ex.printStackTrace();
      }
    }

    return validParticleTypeNames;
  }

  private static ParticleType getParticleTypeForName(String name) {
    for (Field f : Particles.class.getDeclaredFields()) {
      try {
        Object returnedObject = f.get(null);

        if (returnedObject instanceof ParticleType) {
          ParticleType particleType = (ParticleType) returnedObject;
          // a string starting with "minecraft:"
          String particleTypeName = particleType.a();
          particleTypeName = particleTypeName.replaceFirst("^minecraft:", "");
          if (particleTypeName.equalsIgnoreCase(name)) {
            return particleType;
          }
        }
      } catch (Exception ex) {
        // IllegalArgumentException / IllegalAccessException
        ex.printStackTrace();
      }
    }

    return null;
  }

}
