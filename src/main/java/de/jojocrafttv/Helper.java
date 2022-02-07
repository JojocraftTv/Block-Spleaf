package de.jojocrafttv;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Helper {





    // static Functions 
    private static HashMap<Integer, ItemStack> itemmap = new HashMap<Integer, ItemStack>();
    static {
        ItemStack pickaxe, bow;
        HashMap<Enchantment, Integer> pickaxe_ench = new HashMap<Enchantment, Integer>();
        pickaxe_ench.put(Enchantment.DIG_SPEED, 10);
        pickaxe = createItem(Material.IRON_PICKAXE, 1, "§7Pickaxe", null, true, pickaxe_ench);

        HashMap<Enchantment, Integer> bow_ench = new HashMap<Enchantment, Integer>();
        bow_ench.put(Enchantment.ARROW_INFINITE, 1);
        bow = createItem(Material.BOW, 1, "§7Bow", null, true, bow_ench);

        itemmap.put(0, pickaxe);
        itemmap.put(1, bow);
        itemmap.put(8, new ItemStack(Material.ARROW, 1));
    }

    public static void setItems(Player p) {
        p.getInventory().clear();
        for(Entry<Integer, ItemStack> entry : itemmap.entrySet()) {
            p.getInventory().setItem(entry.getKey(), entry.getValue());
        }
    }

    public static ItemStack createItem(Material mat, int count, String displayname, String[] lore, boolean unbreakable, HashMap<Enchantment, Integer> enchants) {
        ItemStack s = new ItemStack(mat, count);
        ItemMeta m = s.getItemMeta();
        if(displayname != null) m.setDisplayName(displayname);
        if(lore != null) m.setLore(Arrays.asList(lore));
        if(enchants != null) {
            for(Entry<Enchantment, Integer> entry : enchants.entrySet()) {
                m.addEnchant(entry.getKey(), entry.getValue(), true);
            }
        }
        m.setUnbreakable(unbreakable);
        s.setItemMeta(m);
        return s;
    }

    // Gen code.


    @Deprecated
    public static void clear_async(World w, int x1, int y1, int z1, int x2, int y2, int z2) {
        Bukkit.getScheduler().runTaskAsynchronously(App.ins, new Runnable() {
            @Override
            public void run() {
                clear(w, x1, y1, z1, x2, y2, z2);
            }
        });
    }

    public static void clear(World w, int x1, int y1, int z1, int x2, int y2, int z2) {
        for(int x = x1; x <= x2; x++)
            for(int y = y1; y <= y2; y++)
                for(int z = z1; z <= z2; z++)
                    w.getBlockAt(x, y, z).setType(Material.AIR);
    }

    public static void addLuckyBlock(World w, int y, int offset_x, int offset_z, int maxrange) {
        w.getBlockAt(offset_x+random(-maxrange, maxrange), y, offset_z+random(-maxrange, maxrange)).setType(Material.SPONGE);
    }
    static Random random = new Random();
    public static int random(int min, int max) {
       
        return random.nextInt(max-min) + min;
        //return (int) (Math.random() * (max - min) + min);
    }

    @Deprecated
    public static void gen_platfroms_async(World w, int platform_size, int start_y, int end_y, int space, int x_offset, int z_offset, Material[] mats, int layer_random_x_offset, int layer_random_z_offset,  int luckyBlockRarit) {
        Bukkit.getScheduler().runTaskAsynchronously(App.ins, new Runnable() {
            @Override
            public void run() {
                gen_platfroms(w, platform_size, start_y, end_y, space, x_offset, z_offset, mats, layer_random_x_offset, layer_random_z_offset, luckyBlockRarit);
            }
        });
    }
    /**
     * @param w World where the Platforms should be made
     * @param platform_size the size of the platform
     * @param start_y Start Cord-Y
     * @param end_y End Cord-Y
     * @param space Space between layers
     * @param x_offset The X-offset if 0, at pos-x 0
     * @param z_offset The Z-offset if 0, at pos-z 0
     * @param mats The array of Materials each layer gets, randomly choosen
     * @param layer_random_x_offset The max x offset for each layer
     * @param layer_random_z_offset The max z offset for each layer
     * @param luckyBlockRarity The Higher the more unlikly the Block will spawn
     */
    public static void gen_platfroms(World w, int platform_size, int start_y, int end_y, int space, int x_offset, int z_offset, Material[] mats, int layer_random_x_offset, int layer_random_z_offset, int luckyBlockRarity) {
        if(end_y == start_y) return;

        //If wrongly used switch around.
        if(end_y > start_y) {
            int temp = end_y;
            end_y = start_y;
            start_y = temp;
        }
        // i = y-cord
        //int count = (start_y - end_y) / space;
        for(int i = start_y; i >= end_y; i-=space) {
            int xo = x_offset+random(-layer_random_x_offset, layer_random_x_offset);
            int zo = z_offset+random(-layer_random_z_offset, layer_random_z_offset);
            if(random(0, luckyBlockRarity) == 0) {
                for(int s = 0; s < 20; s++) addLuckyBlock(w, i+1, xo, zo, (platform_size/2)-1);
            }
            //Bukkit.getConsoleSender().sendMessage("[GEN-Platforms] Left: " + count);
            gen_platfrom(w, platform_size, i, xo, zo, mats[random.nextInt(mats.length)]);
            //count--;
        }
    }

    public static void gen_platfrom(World w, int platform_size, int y, int x_offset, int z_offset, Material mat) {
        for(int i = 0; i <= platform_size; i++) {
            for(int s = -(platform_size-i); s <= platform_size-i; s++) {
                w.getBlockAt(i+x_offset, y, s+z_offset).setType(mat);
                if(i != 0) w.getBlockAt(-i+x_offset, y, -s+z_offset).setType(mat);
            }
        }
       
        
    }

}
