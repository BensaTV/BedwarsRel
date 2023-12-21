package io.github.bedwarsrel.com.v1_20_R3;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.core.RegistryMaterials;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityTypes;

@SuppressWarnings("rawtypes")
public class CustomEntityRegistry extends RegistryMaterials {

  // Use this registry-hack
  // https://github.com/BillyGalbreath/Ridables/blob/master/src/main/java/net/pl3x/bukkit/ridables/util/RegistryHax.java

  private static CustomEntityRegistry instance = null;

  private final BiMap<MinecraftKey, Class<? extends Entity>> customEntities = HashBiMap.create();
  private final BiMap<Class<? extends Entity>, MinecraftKey> customEntityClasses =
      this.customEntities.inverse();
  private final Map<Class<? extends Entity>, Integer> customEntityIds = new HashMap<>();

  private final RegistryMaterials wrapped;

  private CustomEntityRegistry(RegistryMaterials original) {
    this.wrapped = original;
  }

  public static void addCustomEntity(int entityId, String entityName,
      Class<? extends Entity> entityClass) {
    getInstance().putCustomEntity(entityId, entityName, entityClass);
  }

  public static CustomEntityRegistry getInstance() {
    if (instance != null) {
      return instance;
    }

    //instance = new CustomEntityRegistry(EntityTypes);

    try {
      // TODO: Update name on version change (RegistryMaterials)
      Field registryMaterialsField = EntityTypes.class.getDeclaredField("b");
      registryMaterialsField.setAccessible(true);

      Field modifiersField = Field.class.getDeclaredField("modifiers");
      modifiersField.setAccessible(true);
      modifiersField.setInt(registryMaterialsField,
          registryMaterialsField.getModifiers() & ~Modifier.FINAL);

      registryMaterialsField.set(null, instance);
    } catch (Exception e) {
      instance = null;

      throw new RuntimeException("Unable to override the old entity RegistryMaterials", e);
    }

    return instance;
  }

  @SuppressWarnings("unchecked")
  public int a(Object key) { // TODO: Update name on version change (getId)
    if (this.customEntityIds.containsKey(key)) {
      return this.customEntityIds.get(key);
    }

    return this.a(key);
  }

  /*
  @SuppressWarnings("unchecked")
  @Override
  public MinecraftKey b(Object value) { // TODO: Update name on version change (getKey)
    if (this.customEntityClasses.containsKey(value)) {
      return this.customEntityClasses.get(value);
    }

    return (MinecraftKey) wrapped.b(value);
  }

  @SuppressWarnings("unchecked")
  @Override
  public Class<? extends Entity> get(Object key) {
    if (this.customEntities.containsKey(key)) {
      return this.customEntities.get(key);
    }

    return (Class<? extends Entity>) wrapped.get(key);
  }
  */

  public void putCustomEntity(int entityId, String entityName,
      Class<? extends Entity> entityClass) {
    MinecraftKey minecraftKey = new MinecraftKey(entityName);

    this.customEntities.put(minecraftKey, entityClass);
    this.customEntityIds.put(entityClass, entityId);
  }
}