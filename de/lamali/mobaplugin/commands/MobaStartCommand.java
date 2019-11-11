package de.lamali.mobaplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.lamali.mobaplugin.main.MobaPlugin;

public class MobaStartCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length>0){
			MobaPlugin.init(Integer.parseInt(args[0]));
		}else{
			MobaPlugin.init(0);
		}
		return false;
	}

}
