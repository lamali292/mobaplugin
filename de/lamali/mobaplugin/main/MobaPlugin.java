package de.lamali.mobaplugin.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import de.lamali.mobaplugin.commands.MobaStartCommand;
import de.lamali.mobaplugin.commands.SetGoldCommand;
import de.lamali.mobaplugin.commands.SetTimeCommand;
import de.lamali.mobaplugin.mobaobjects.*;
import net.minecraft.server.v1_15_R1.Block;
import net.minecraft.server.v1_15_R1.BlockPosition;
import net.minecraft.server.v1_15_R1.Blocks;
import net.minecraft.server.v1_15_R1.Fluid;
import net.minecraft.server.v1_15_R1.IBlockAccess;
import net.minecraft.server.v1_15_R1.IBlockData;
import net.minecraft.server.v1_15_R1.PacketPlayOutBlockChange;
import net.minecraft.server.v1_15_R1.TileEntity;

public class MobaPlugin extends JavaPlugin {
	public static float time = 0;
	public static int second = 0;
	public static int min = 0;

	public static World world;

	public static float towerThickness = 1.5F;
	public static float nexusThickness = 4F;
	public static float inhibThickness = 2F;

	public static List<Lane> lane;
	public static List<Tower> towerList;
	public static List<Nexus> nexusList;
	public static List<Inhibitor> inhibitorList;
	public static List<MobaPlayer> playerList, redPlayerList, bluePlayerList;
	public static List<Camp> campList;

	public static Map<UUID, MobaObject> MobaObjects;

	public static Location[] redInvisBlocks, blueInvisBlocks;

	public static Location blueTeamSpawn, redTeamSpawn;

	public static MobaPlugin instance;
	public static Scoreboard board;
	public static String timeString = "";
	

	public void onEnable() {
		instance = this;
		loadConfig();
		String worldName = this.getConfig().getString("world");
		world = this.getServer().getWorld(worldName);

		this.getServer().getPluginManager().registerEvents(new EventHandler(), this);

		getCommand("mobastart").setExecutor(new MobaStartCommand());
		getCommand("mobatime").setExecutor(new SetTimeCommand());
		getCommand("mobagold").setExecutor(new SetGoldCommand());
	}

	public static void updatePlugin() {
		if (MobaObjects != null) {
			Object[] m = MobaObjects.values().toArray();
			for (Object mobaObj : m) {
				if (mobaObj instanceof MobaObject) {
					((MobaObject) mobaObj).update();
				}
			}
		}
		time += 0.05F;

	}

	public static void updateSecond() {
		updateTimeText();

		if (MobaObjects != null) {
			Object[] m = MobaObjects.values().toArray();
			for (Object mobaObj : m) {
				if (mobaObj instanceof MobaObject) {
					((MobaObject) mobaObj).updateEverySecond();
				}
			}
		}
		if (inhibitorList != null) {
			for (Inhibitor i : inhibitorList) {
				if (i.destroyed) {
					i.updateEverySecond();
				}
			}
		}
		if (playerList != null) {
			for (MobaPlayer p : playerList) {
				if (p.dead) {
					p.updateEverySecond();
				}
			}
		}
		for (Camp c : campList) {
			c.updateEverySecond();
		}
		second += 1;

	}

	public static void init(int starttime) {
		/* Reset all Variables */
		time = starttime;
		second = starttime;
		lane = new ArrayList<Lane>();
		towerList = new ArrayList<Tower>();
		nexusList = new ArrayList<Nexus>();
		inhibitorList = new ArrayList<Inhibitor>();
		playerList = new ArrayList<MobaPlayer>();
		redPlayerList = new ArrayList<MobaPlayer>();
		bluePlayerList = new ArrayList<MobaPlayer>();
		campList = new ArrayList<Camp>();

		MobaObjects = new HashMap<UUID, MobaObject>();

		blueTeamSpawn = new Location(world, 165, 56, 165, 315, 0);
		redTeamSpawn = new Location(world, 297, 56, 297, 135, 0);

		InventoryShop.initShop();
		
		/* Clean up all threads */
		Bukkit.getScheduler().cancelTasks(MobaPlugin.instance);

		/* Do Player Stuff */
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		board = manager.getNewScoreboard();
		Team teamRed = board.registerNewTeam("red");
		Team teamBlue = board.registerNewTeam("blue");
		teamRed.setColor(ChatColor.RED);
		teamBlue.setColor(ChatColor.BLUE);

		List<Player> players = new ArrayList<Player>();
		for (int i = 0; i < Bukkit.getOnlinePlayers().size(); i++) {
			players.add((Player) Bukkit.getOnlinePlayers().toArray()[i]);
		}
		for (Player otherP : players) {
			for (Player anotherP : players) {
				if (!otherP.equals(anotherP)) {
					otherP.showPlayer(MobaPlugin.instance, anotherP);
				}
			}
		}
		for (int i = 0; i < Bukkit.getOnlinePlayers().size(); i++) {
			int randomPlayerIndex = (int) (Math.random() * players.size());
			Player p = players.get(randomPlayerIndex);
			Color color = Color.WHITE;
			if (i < Bukkit.getOnlinePlayers().size() / 2F) {
				MobaPlayer mobaPlayer = new MobaPlayer(p, "blue",600F, 5F, 300F, 5F, 550F, 60F, 0.625F, 30F, 30F, 340F, 90F,0.5F,40,0.5F,3F,0.02F,3F,0.5F);
				playerList.add(mobaPlayer);
				bluePlayerList.add(mobaPlayer);
				teamBlue.addEntry(p.getName());
				color = Color.BLUE;
			} else {
				MobaPlayer mobaPlayer = new MobaPlayer(p, "red", 600F, 5F, 300F, 5F, 550F, 60F, 0.625F, 30F, 30F, 340F, 90F,0.5F,40,0.5F,3F,0.02F,3F,0.5F);
				playerList.add(mobaPlayer);
				redPlayerList.add(mobaPlayer);
				teamRed.addEntry(p.getName());
				color = Color.RED;
			}
			p.setScoreboard(board);
			ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
			LeatherArmorMeta meta = (LeatherArmorMeta) chestplate.getItemMeta();
			meta.setColor(color);
			chestplate.setItemMeta(meta);
			p.getInventory().setChestplate(chestplate);
			players.remove(randomPlayerIndex);
		}

		for (int i = 0; i < bluePlayerList.size(); i++) {
			if (bluePlayerList.size() == 1) {
				bluePlayerList.get(i).player.teleport(blueTeamSpawn);
			} else {
				bluePlayerList.get(i).player.teleport(new Location(blueTeamSpawn.getWorld(), blueTeamSpawn.getX(),	blueTeamSpawn.getY(), blueTeamSpawn.getZ())
						.add(3* Math.sin(((float)i/(float)bluePlayerList.size()*2*Math.PI)+Math.PI/4),0, 3*Math.cos((i/(float)bluePlayerList.size()*2*Math.PI)+Math.PI/4)));
			}
		}
		for (int i = 0; i < redPlayerList.size(); i++) {
			if (redPlayerList.size() == 1) {
				redPlayerList.get(i).player.teleport(redTeamSpawn);
			} else {
				redPlayerList.get(i).player.teleport(new Location(redTeamSpawn.getWorld(), redTeamSpawn.getX(),
								redTeamSpawn.getY(), redTeamSpawn.getZ())
						.add(3* Math.sin(((float)i/(float)redPlayerList.size()*2*Math.PI)+Math.PI/4),0, 3*Math.cos((i/(float)redPlayerList.size()*2*Math.PI)+Math.PI/4)));
			}
		}

		/* Delete all armor stands */
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "kill @e[type=minecraft:armor_stand]");

		/* Create all Objects for the game */
		campList.add(new Camp("raptor", 208, 56, 226, 45, 90, 150));
		campList.add(new Camp("red", 199, 56, 237, 135, 90, 300));
		campList.add(new Camp("blue", 237, 56, 197, -135, 90, 300));
		campList.add(new Camp("krug", 186, 56, 240, 90, 102, 150));
		campList.add(new Camp("gromp", 242, 56, 182, -45, 102, 150));
		campList.add(new Camp("wolf", 223, 56, 200, 135, 90, 150));

		campList.add(new Camp("raptor", 254, 56, 236, -135, 90, 150));
		campList.add(new Camp("red", 263, 56, 225, -45, 90, 300));
		campList.add(new Camp("blue", 225, 56, 265, 45, 90, 300));
		campList.add(new Camp("krug", 276, 56, 222, -90, 102, 150));
		campList.add(new Camp("gromp", 220, 56, 280, 135, 102, 150));
		campList.add(new Camp("wolf", 239, 56, 262, -45, 90, 150));

		campList.add(new Camp("dragon", 201, 56, 253, -45, 300, 300));
		campList.add(new Camp("baron", 261, 56, 209, 135, 1200, 420));
		campList.add(new Camp("herald", 261, 56, 209, 135, 570, 1200));

		Location[] redMinionSpawns = new Location[] { new Location(world, 284, 56, 288),
				new Location(world, 284, 56, 284), new Location(world, 288, 56, 284) };
		Location[] blueMinionSpawns = new Location[] { new Location(world, 173, 56, 177),
				new Location(world, 177, 56, 177), new Location(world, 177, 56, 173) };

		nexusList.add(new Nexus("blue", 173, 56, 173, "southeast", 5500, blueMinionSpawns));
		nexusList.add(new Nexus("red", 288, 56, 288, "northwest", 5500, redMinionSpawns));

		inhibitorList.add(new Inhibitor("red", 266, 56, 290, "northwest", 4000, 20, 0, blueMinionSpawns));
		inhibitorList.add(new Inhibitor("red", 272, 56, 272, "northwest", 4000, 20, 1, blueMinionSpawns));
		inhibitorList.add(new Inhibitor("red", 290, 56, 266, "northwest", 4000, 20, 2, blueMinionSpawns));

		inhibitorList.add(new Inhibitor("blue", 171, 56, 195, "southeast", 4000, 20, 0, redMinionSpawns));
		inhibitorList.add(new Inhibitor("blue", 189, 56, 189, "southeast", 4000, 20, 1, redMinionSpawns));
		inhibitorList.add(new Inhibitor("blue", 195, 56, 171, "southeast", 4000, 20, 2, redMinionSpawns));

		redInvisBlocks = new Location[] { new Location(world, 258, 56, 279), new Location(world, 258, 56, 278),
				new Location(world, 259, 56, 277), new Location(world, 259, 56, 276), new Location(world, 258, 57, 279),
				new Location(world, 258, 57, 278), new Location(world, 259, 57, 277), new Location(world, 259, 57, 276),
				new Location(world, 279, 56, 258), new Location(world, 278, 56, 258), new Location(world, 277, 56, 259),
				new Location(world, 276, 56, 259), new Location(world, 279, 57, 258), new Location(world, 278, 57, 258),
				new Location(world, 277, 57, 259), new Location(world, 276, 57, 259) };

		blueInvisBlocks = new Location[] { new Location(world, 182, 56, 203), new Location(world, 183, 56, 203),
				new Location(world, 184, 56, 202), new Location(world, 185, 56, 202), new Location(world, 182, 57, 203),
				new Location(world, 183, 57, 203), new Location(world, 184, 57, 202), new Location(world, 185, 57, 202),
				new Location(world, 203, 56, 182), new Location(world, 203, 56, 183), new Location(world, 202, 56, 184),
				new Location(world, 202, 56, 185), new Location(world, 203, 57, 182), new Location(world, 203, 57, 183),
				new Location(world, 202, 57, 184), new Location(world, 202, 57, 185) };

		// Blue
		towerList.add(new Tower("blue", 159, 56, 159, "southeast", 100000, 3F, 500, 100, 15));
		// Base
		towerList.add(new Tower("blue", 177, 56, 181, "southeast", 2700, 0.833F, 150, 70, 8.5F));
		towerList.add(new Tower("blue", 181, 56, 177, "southeast", 2700, 0.833F, 150, 70, 8.5F));
		// Botlane
		towerList.add(new Tower("blue", 171, 56, 201, "southeast", 3300, 0.833F, 170, 70, 8.5F));
		towerList.add(new Tower("blue", 172, 56, 227, "southwest", 3600, 0.833F, 170, 55, 8.5F));
		towerList.add(new Tower("blue", 169, 56, 259, "southeast", 5000, 0.833F, 152, 40, 8.5F));
		// Midlane
		towerList.add(new Tower("blue", 193, 56, 193, "southeast", 3300, 0.833F, 170, 70, 8.5F));
		towerList.add(new Tower("blue", 204, 56, 206, "southeast", 3600, 0.833F, 170, 55, 8.5F));
		towerList.add(new Tower("blue", 218, 56, 216, "southeast", 5000, 0.833F, 152, 40, 8.5F));
		// Toplane
		towerList.add(new Tower("blue", 201, 56, 171, "southeast", 3300, 0.833F, 170, 70, 8.5F));
		towerList.add(new Tower("blue", 227, 56, 172, "northeast", 3600, 0.833F, 170, 55, 8.5F));
		towerList.add(new Tower("blue", 259, 56, 169, "southeast", 5000, 0.833F, 152, 40, 8.5F));

		// Red
		towerList.add(new Tower("red", 302, 56, 302, "northwest", 100000, 3F, 500, 100, 15));
		// Base
		towerList.add(new Tower("red", 284, 56, 280, "northwest", 2700, 0.833F, 150, 70, 8.5F));
		towerList.add(new Tower("red", 280, 56, 284, "northwest", 2700, 0.833F, 150, 70, 8.5F));
		// Botlane
		towerList.add(new Tower("red", 260, 56, 290, "northwest", 3300, 0.833F, 170, 70, 8.5F));
		towerList.add(new Tower("red", 234, 56, 289, "southwest", 3600, 0.833F, 170, 55, 8.5F));
		towerList.add(new Tower("red", 202, 56, 292, "northwest", 5000, 0.833F, 152, 40, 8.5F));
		// Midlane
		towerList.add(new Tower("red", 268, 56, 268, "northwest", 3300, 0.833F, 170, 70, 8.5F));
		towerList.add(new Tower("red", 257, 56, 255, "northwest", 3600, 0.833F, 170, 55, 8.5F));
		towerList.add(new Tower("red", 243, 56, 245, "northwest", 5000, 0.833F, 152, 40, 8.5F));
		// Toplane
		towerList.add(new Tower("red", 290, 56, 260, "northwest", 3300, 0.833F, 170, 70, 8.5F));
		towerList.add(new Tower("red", 289, 56, 234, "northeast", 3600, 0.833F, 170, 55, 8.5F));
		towerList.add(new Tower("red", 292, 56, 202, "northwest", 5000, 0.833F, 152, 40, 8.5F));

		// Lanes
		// Botlane
		lane.add(new Lane(new Location[] { new Location(world, 173, 56, 173), new Location(world, 174, 56, 193),
				new Location(world, 173, 56, 215), new Location(world, 171, 56, 225), new Location(world, 171, 56, 265),
				new Location(world, 179, 56, 282), new Location(world, 197, 56, 291), new Location(world, 237, 56, 291),
				new Location(world, 246, 56, 289), new Location(world, 269, 56, 288),
				new Location(world, 288, 56, 288) }));

		// Midlane
		lane.add(new Lane(new Location[] { new Location(world, 173, 56, 173), new Location(world, 191, 56, 187),
				new Location(world, 206, 56, 204), new Location(world, 216, 56, 218), new Location(world, 230, 56, 230),
				new Location(world, 232, 56, 232), new Location(world, 246, 56, 244), new Location(world, 256, 56, 258),
				new Location(world, 271, 56, 275), new Location(world, 288, 56, 288) }));

		// Toplane
		lane.add(new Lane(new Location[] { new Location(world, 173, 56, 173), new Location(world, 193, 56, 174),
				new Location(world, 215, 56, 173), new Location(world, 225, 56, 171), new Location(world, 265, 56, 171),
				new Location(world, 282, 56, 179), new Location(world, 291, 56, 197), new Location(world, 291, 56, 237),
				new Location(world, 289, 56, 246), new Location(world, 288, 56, 269),
				new Location(world, 288, 56, 288) }));

		/* Create update functions */
		Bukkit.getScheduler().scheduleSyncRepeatingTask(MobaPlugin.instance, new Runnable() {
			@Override
			public void run() {
				updatePlugin();
			}
		}, 0, 1);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(MobaPlugin.instance, new Runnable() {
			@Override
			public void run() {
				updateSecond();
			}
		}, 0, 20);
	}

	public static void setInvisBlocks(Player player) {
		if (blueInvisBlocks != null && redInvisBlocks != null) {
			if (MobaObjects.containsKey(player.getUniqueId())) {
				MobaObject mobaObj = MobaObjects.get(player.getUniqueId());
				if (mobaObj.team.equals("red")) {
					for (int i = 0; i < blueInvisBlocks.length; i++) {
						if (player.getWorld().getBlockAt(blueInvisBlocks[i]).getType().equals(Material.AIR)) {
							setBlockForPlayer(player, blueInvisBlocks[i], Blocks.BLUE_STAINED_GLASS);
							setBlockForPlayer(player, redInvisBlocks[i], Blocks.AIR);
						}
					}
				} else if (mobaObj.team.equals("blue")) {
					for (int i = 0; i < redInvisBlocks.length; i++) {
						if (player.getWorld().getBlockAt(redInvisBlocks[i]).getType().equals(Material.AIR)) {
							setBlockForPlayer(player, redInvisBlocks[i], Blocks.RED_STAINED_GLASS);
							setBlockForPlayer(player, blueInvisBlocks[i], Blocks.AIR);
						}
					}
				}
			}
		}
	}

	public static void updateTimeText() {
		int sec = MobaPlugin.second % 60;
		min = (MobaPlugin.second - sec) / 60;
		String ssec = String.valueOf(sec);
		String smin = String.valueOf(min);
		if (ssec.length() < 2) {
			ssec = "0" + ssec;
		}
		if (smin.length() < 2) {
			smin = "0" + smin;
		}
		if (min < 60) {
			timeString = "§f" + smin + ":" + ssec;
		} else {
			int extramin = min % 60;
			int hou = (min - extramin) / 60;
			smin = String.valueOf(extramin);
			String shou = String.valueOf(hou);
			if (shou.length() < 2) {
				shou = "0" + shou;
			}
			if (smin.length() < 2) {
				smin = "0" + smin;
			}
			timeString = "§f" + shou + ":" + smin + ":" + ssec;
		}

	}

	public static void setBlockForPlayer(Player player, Location location, Block data) {
		BlockPosition bp = new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ());
		IBlockAccess var = new IBlockAccess() {

			@Override
			public IBlockData getType(BlockPosition arg0) {
				return data.getBlockData();
			}

			@Override
			public TileEntity getTileEntity(BlockPosition arg0) {
				return null;
			}

			@Override
			public Fluid getFluid(BlockPosition arg0) {
				return null;
			}
		};
		PacketPlayOutBlockChange packet = new PacketPlayOutBlockChange(var, bp);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);

	}
	
	public static float getMobaRange(int range){
		float mobaTowerRange = 775;
		float pluginTowerRange = 8.5F;
		return (pluginTowerRange*range)/mobaTowerRange;
	}
	
	public static float getMobaSpeed(int speed){
		float mobaMinionSpeed = 325;
		float pluginMinionSpeed = 0.15F;
		return (pluginMinionSpeed*speed)/mobaMinionSpeed;
	}

	public void loadConfig() {
		getConfig().options().copyDefaults(true);
		saveConfig();
	}

}
