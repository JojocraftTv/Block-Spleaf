package de.jojocrafttv;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class cmd_start implements CommandExecutor {

   
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage("§7Please wait...");
        App.start();
        sender.sendMessage("§aDone.");
        return true;
    }

}
