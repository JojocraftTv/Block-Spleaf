package de.jojocrafttv;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class events implements Listener {

    @EventHandler
    public void ondamageby(EntityDamageByEntityEvent ev) {
        if(ev.getEntity() instanceof Player) {
            if(ev.getDamager() instanceof Arrow) {
                ev.setCancelled(true);
                return;
            }
            if(ev.getDamager() instanceof Player) {
                ev.setCancelled(true);
                return;
            }
        }
    }


    

    @EventHandler
    public void onswap(PlayerSwapHandItemsEvent ev) {
        if(ev.getPlayer().getGameMode().equals(GameMode.SURVIVAL))
        ev.setCancelled(true);
    }

    @EventHandler
    public void ond(PlayerDropItemEvent ev) {
        if(ev.getPlayer().getGameMode().equals(GameMode.SURVIVAL))
        ev.setCancelled(true);
    }

    @EventHandler
    public void onic(InventoryClickEvent  ev) {
        if(ev.getWhoClicked().getGameMode().equals(GameMode.SURVIVAL))
        ev.setCancelled(true);
    }

    
    /** Will replace item if its diffrent, but will add count, even if diffrent */
    private void ItemAdderHelper(Player p, ItemStack item, int slot) {
        ItemStack i = p.getInventory().getItem(slot);
        int add = item.getAmount();
        if(i != null) {
            add+=i.getAmount();
        }
        if(add > item.getMaxStackSize()) {
            add = item.getMaxStackSize();
        }
        item.setAmount(add);
        p.getInventory().setItem(slot, item);
    }
    @EventHandler
    public void onbb(BlockBreakEvent ev) {
        
        App.blocks_broken.put(ev.getPlayer().getUniqueId(), App.blocks_broken.get(ev.getPlayer().getUniqueId())+1);
        
        

        ev.setDropItems(false);
        //is lucky block
        if(ev.getBlock().getType().equals(Material.SPONGE)) {
            switch (Helper.random(0, 8)) {
                case 0:
                    //ev.getPlayer().getInventory().setItem(2, new ItemStack(Material.OAK_PLANKS, ev.getPlayer().getInventory().getItem(2).getAmount()+16));
                    ItemAdderHelper(ev.getPlayer(), new ItemStack(Material.OAK_PLANKS, 16), 2);
                    ev.getPlayer().sendMessage("§e[Lucky] §a+16 Planks.");
                    break;
                case 1:
                    ev.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 20*30, 4, false, false));
                    ev.getPlayer().sendMessage("§e[Lucky] §a+20 Health for 30 sec.");
                    break;
                case 2:
                    Location c = ev.getPlayer().getLocation();
                    c.add(0, 9, 0);
                    c.getBlock().setType(Material.OAK_PLANKS);
                    c.add(0, 1, 0);
                    ev.getPlayer().teleport(c);
                    ev.getPlayer().sendMessage("§e[Lucky] §aTeleported 10 Blocks up.");
                    break;
                case 3:
                    for(int x = ev.getPlayer().getLocation().getBlockX()-1; x <= ev.getPlayer().getLocation().getBlockX()+1; x++) 
                    for(int y = ev.getPlayer().getLocation().getBlockY()-1; y <= ev.getPlayer().getLocation().getBlockY()+1; y++)
                    for(int z = ev.getPlayer().getLocation().getBlockZ()-1; z <= ev.getPlayer().getLocation().getBlockZ()+1; z++) {
                        if(!ev.getPlayer().getLocation().getWorld().getBlockAt(x, y, z).getType().equals(Material.AIR)) {
                            ev.getPlayer().getLocation().getWorld().spawnParticle(Particle.BLOCK_DUST, new Location(ev.getPlayer().getWorld(), x+0.5, y+0.5, z+0.5), 50,  ev.getPlayer().getLocation().getWorld().getBlockAt(x, y, z).getBlockData());
                            ev.getPlayer().getLocation().getWorld().getBlockAt(x, y, z).setType(Material.AIR);
                        }
                        
                    
                        
                    }
                    ev.getPlayer().sendMessage("§e[Lucky] §cOh no, the floor broke.");
                    break;
                case 4:
                    for(int i = 0; i < 5; i++) {
                        TNTPrimed tnt = (TNTPrimed) ev.getBlock().getLocation().getWorld().spawnEntity(ev.getBlock().getLocation().add(0.5, 0.5, 0.5), EntityType.PRIMED_TNT);
                        tnt.setFuseTicks(20);
                    }
                    ev.getPlayer().sendMessage("§e[Lucky] §cOh no, a TNT trap");
                    break;
                case 5:
                    ev.getBlock().getWorld().getBlockAt(ev.getBlock().getLocation().add(3, 0, 0)).setType(Material.SPONGE);
                    ev.getBlock().getWorld().getBlockAt(ev.getBlock().getLocation().add(-3, 0, 0)).setType(Material.SPONGE);
                    ev.getBlock().getWorld().getBlockAt(ev.getBlock().getLocation().add(0, 0, 3)).setType(Material.SPONGE);
                    ev.getBlock().getWorld().getBlockAt(ev.getBlock().getLocation().add(0, 0, -3)).setType(Material.SPONGE);
                    ev.getPlayer().sendMessage("§e[Lucky] §aoh? new blocks appeared.");
                    break;
                case 6:
                    //ev.getPlayer().getInventory().setItem(3, new ItemStack(Material.ENDER_PEARL, ev.getPlayer().getInventory().getItem(3).getAmount()+1));
                    ItemAdderHelper(ev.getPlayer(), new ItemStack(Material.ENDER_PEARL, 1), 3);
                    ev.getPlayer().sendMessage("§e[Lucky] §a+1 Ender Pearl");
                    break;
                case 7:
                    ItemAdderHelper(ev.getPlayer(), new ItemStack(Material.TNT, 3), 4);
                    ev.getPlayer().sendMessage("§e[Lucky] §a+3 TNT");
                    break;
                //ev.getPlayer().getInventory().setItem(2, Helper.createItem(Material.EGG, 1, "§cDestoryer", new String[] {"§7Will destory everything that is in its way."}, false, null));
                //ev.getPlayer().sendMessage("§e+1 Destoryer");
    
                default:
                    //Something went wrong.
                    ev.setCancelled(true);
                    break;
            }
           
        }
        
    }

   /* @EventHandler
    public void onexplode(BlockExplodeEvent ev) {
        ev.setCancelled(true);
        for(Block b : ev.blockList()) {
            b.setType(Material.AIR);
        }
        
    }*/

    @EventHandler
    public void onexplode(EntityExplodeEvent ev) {
        //ev.setCancelled(true);
        for(Block b : ev.blockList()) {
            b.getWorld().spawnParticle(Particle.BLOCK_DUST, b.getLocation().add(0.5, 0.5, 0.5), 50,  b.getBlockData());
            b.setType(Material.AIR);
        }
    }

    @EventHandler
    public void onplace(BlockPlaceEvent ev) {
        if(ev.getBlock().getType().equals(Material.TNT)) {
            ev.getBlock().setType(Material.AIR);
            ev.getBlock().getWorld().spawnEntity( ev.getBlock().getLocation().add(0.5, 0, 0.5), EntityType.PRIMED_TNT);
        }
    }
 
    @EventHandler
    public void ondamage(EntityDamageEvent ev) {
        if (!(ev.getEntity() instanceof Player))  return;
        
        if((((LivingEntity)ev.getEntity()).getHealth() - ev.getFinalDamage()) <= 0) {
            if(!((Player)ev.getEntity()).getGameMode().equals(GameMode.SURVIVAL)) return;
            ev.setCancelled(true);
            ((LivingEntity)ev.getEntity()).setHealth(20);
            for(Player p : Bukkit.getOnlinePlayers()) {
                p.sendMessage("§5[GAME] §cPlayer §a" + ev.getEntity().getName() + " §cis out.");
            }
            ((Player)ev.getEntity()).setGameMode(GameMode.SPECTATOR);
        }
            
        
    }

    @EventHandler
    public void onmove(PlayerMoveEvent e) {
        if(!e.getPlayer().getGameMode().equals(GameMode.SURVIVAL)) return;
        if(e.getTo().getY() <= App.EndY-25) {
            e.getPlayer().setGameMode(GameMode.SPECTATOR);
            for(Player p : Bukkit.getOnlinePlayers()) {
                p.sendMessage("§5[GAME] §cPlayer §a" + e.getPlayer().getName() + " §cis out.");
            }
        }
    }

    @EventHandler
    public void hit(EntityPickupItemEvent e){
       
        if(e.getEntity() instanceof Player) {
            if(!((Player)e.getEntity()).getGameMode().equals(GameMode.SURVIVAL)) return;
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void hit(ProjectileHitEvent e){
        if(!(e.getEntity().getShooter() instanceof Player)) return;
        if(e.getEntity() instanceof Arrow) {
            int xp = e.getEntity().getLocation().getBlockX();
            int yp = e.getEntity().getLocation().getBlockY();
            int zp = e.getEntity().getLocation().getBlockZ();
            for(int x = xp-1; x <= xp+1; x++) 
            for(int y = yp-1; y <= yp+1; y++)
            for(int z = zp-1; z <= zp+1; z++) {
                if(!e.getEntity().getLocation().getWorld().getBlockAt(x, y, z).getType().equals(Material.AIR)) {
                    App.blocks_broken.put(((Player)e.getEntity().getShooter()).getUniqueId(), App.blocks_broken.get(((Player)e.getEntity().getShooter()).getUniqueId())+1);
                    e.getEntity().getLocation().getWorld().spawnParticle(Particle.BLOCK_DUST, new Location(e.getEntity().getWorld(), x+0.5, y+0.5, z+0.5), 50,  e.getEntity().getLocation().getWorld().getBlockAt(x, y, z).getBlockData());
                    e.getEntity().getLocation().getWorld().getBlockAt(x, y, z).setType(Material.AIR);
                }
                
              
                
            }
            e.getEntity().remove();
            /*for(Entity es : e.getEntity().getWorld().getEntities()) {
                if(es instanceof Item) {
                    es.remove();
                }
            }*/
            
        }
    }

}
