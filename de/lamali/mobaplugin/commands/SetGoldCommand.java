package de.lamali.mobaplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.lamali.mobaplugin.main.MobaPlugin;
import de.lamali.mobaplugin.mobaobjects.MobaPlayer;

public class SetGoldCommand implements CommandExecutor{
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length>0&&sender instanceof Player&&MobaPlugin.MobaObjects!=null){
			Player p = (Player) sender;
			if(MobaPlugin.MobaObjects.containsKey(p.getUniqueId())){
				MobaPlayer player = (MobaPlayer) MobaPlugin.MobaObjects.get(p.getUniqueId());
				player.addGold(Integer.parseInt(args[0]));
			}
		}
		return false;
	}
}
