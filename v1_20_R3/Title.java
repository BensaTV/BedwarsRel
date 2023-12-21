package io.github.bedwarsrel.com.v1_20_R3;

import org.bukkit.entity.Player;

public class Title {

  public static void showSubTitle(Player player, String subTitle, int fadeIn, int stay, int fadeOut) {
    player.sendTitle("", subTitle, fadeIn, stay, fadeOut);
  }

  public static void showTitle(Player player, String title, int fadeIn, int stay, int fadeOut) {
    player.sendTitle(title, "", fadeIn, stay, fadeOut);
  }

}
