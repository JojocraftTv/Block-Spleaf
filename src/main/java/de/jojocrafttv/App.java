package de.jojocrafttv;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public final class App extends JavaPlugin {


    public static int offsetX = 0;
    public static int offsetZ = 0;
    public static int layerRandomOffsetX = 5;
    public static int layerRandomOffsetZ = 5;
    public static int startY = 200;
    public static int EndY = 0;
    public static int layerSpace = 5;
    public static int platformSize = 25;
    public static int luckyBlockRarity = 1;
    public static Material[] list = {Material.GRAY_CONCRETE, Material.GREEN_CONCRETE, Material.YELLOW_CONCRETE, Material.RED_CONCRETE, Material.CYAN_CONCRETE, Material.BLUE_CONCRETE, Material.LIME_CONCRETE, Material.PINK_CONCRETE, Material.BROWN_CONCRETE, Material.WHITE_CONCRETE, Material.ORANGE_CONCRETE, Material.PURPLE_CONCRETE, Material.MAGENTA_CONCRETE, Material.LIGHT_BLUE_CONCRETE, Material.LIGHT_GRAY_CONCRETE};
    //excluded Material.BLACK_CONCRETE, becouse its too dark
    
    //Stats - Just for Fun
    public static HashMap<UUID, Integer> blocks_broken = new HashMap<UUID, Integer>();

    public static Plugin ins;
    @Override
    public void onEnable() {
        ins = this;
        Bukkit.getPluginCommand("start").setExecutor(new cmd_start());
        Bukkit.getPluginCommand("settings").setExecutor(new cmd_settings());
        Bukkit.getPluginManager().registerEvents(new events(), this);

        new BukkitRunnable() {
            @Override
            public void run() {
                for(Player p : Bukkit.getOnlinePlayers()) {
                    if(!p.getGameMode().equals(GameMode.SURVIVAL)) return;
                    int layer = p.getLocation().getBlockY() / (layerSpace) +1;
                    if(layer <= 0) {
                        return;
                    }
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§aLayer: §e" + layer + " §7- §aBlocks: §e" + blocks_broken.get(p.getUniqueId())));
                }
            }
        }.runTaskTimer(this, 20, 1);
    }

    public static void start() {
        blocks_broken.clear();
        World w = Bukkit.getWorld("world");
        for(Entity e : w.getEntities()) {
            if(e instanceof Item || e instanceof Arrow) {
                e.remove();
            }
        }

        Helper.clear(w, -platformSize-layerRandomOffsetX-30+offsetX, EndY-50, -platformSize-layerRandomOffsetZ-30+offsetZ, platformSize+layerRandomOffsetX+30+offsetX, startY+50,  platformSize+layerRandomOffsetZ+30+offsetZ);
        Helper.gen_platfroms(w, platformSize, startY, EndY, layerSpace, offsetX, offsetZ, list, layerRandomOffsetX, layerRandomOffsetZ, luckyBlockRarity);
        w.setDifficulty(Difficulty.PEACEFUL);
        for(Player p : Bukkit.getOnlinePlayers()) {
            App.blocks_broken.put(p.getUniqueId(), 0);
            Helper.setItems(p);
            p.teleport(new Location(w, offsetX, startY + 1, offsetZ));
            for (PotionEffect effect : p.getActivePotionEffects())
            p.removePotionEffect(effect.getType());
            p.setGameMode(GameMode.SURVIVAL);
            p.setHealth(20);
        }


        /*

        // These are the dev values

        World w = Bukkit.getWorld("world");
        for(Entity e : w.getEntities()) {
            if(e instanceof Item || e instanceof Arrow) {
                e.remove();
            }
        }
        Helper.clear(w, -30, 0, -30, 30, 310, 30);
        Helper.gen_platfroms(w, 25, 300, 0, 5, 0, 0, list, 5, 5);
        w.setDifficulty(Difficulty.PEACEFUL);
        for(Player p : Bukkit.getOnlinePlayers()) {
            Helper.setItems(p);
            p.teleport(new Location(w, 0, 301, 0));
            for (PotionEffect effect : p.getActivePotionEffects())
            p.removePotionEffect(effect.getType());
            
            p.setGameMode(GameMode.SURVIVAL);
            p.setHealth(20);
            //p.setInvulnerable(true);
        }
        */
        
    }
   
}
