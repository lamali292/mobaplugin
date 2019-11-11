package de.lamali.mobaplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.lamali.mobaplugin.main.MobaPlugin;

public class SetTimeCommand implements CommandExecutor{
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length>0){
			int dif = Integer.valueOf(args[0])-MobaPlugin.second;
			if(dif>0){
				for(int i = 0;i<dif;i++){
					MobaPlugin.updateSecond();
					for(int j = 0;j<20;j++){
						MobaPlugin.updatePlugin();
					}
				}
			}
		}else{
			for(int i = 0;i<30;i++){
				MobaPlugin.updateSecond();
				for(int j = 0;j<20;j++){
					MobaPlugin.updatePlugin();
				}
			}
		}
		return false;
	}
}
