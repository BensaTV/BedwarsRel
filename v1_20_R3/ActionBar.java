package io.github.bedwarsrel.com.v1_20_R3;


import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class ActionBar {

  public static void sendActionBar(Player player, String message) {
    String s = ChatColor.translateAlternateColorCodes('&', message.replace("_", " "));
    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(s + message));
  }

}
