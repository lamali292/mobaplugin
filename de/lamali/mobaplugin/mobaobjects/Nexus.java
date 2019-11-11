package de.lamali.mobaplugin.mobaobjects;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import de.lamali.mobaplugin.main.MobaPlugin;

public class Nexus extends MobaBuilding {

	private Location[] minionSpawns;

	public Nexus(String team, int x, int y, int z, String direction, float maxhealth, Location[] minionSpawns) {
		super("Nexus", team, x, y, z, direction, maxhealth, 0);
		this.minionSpawns = minionSpawns;

		init();
	}

	public void init() {

		armorStand = (ArmorStand) world.spawnEntity(buildingLocation, EntityType.ARMOR_STAND);
		MobaPlugin.MobaObjects.put(armorStand.getUniqueId(), this);

		armorStand.setCustomName(maxhealth + "");
		armorStand.setVisible(false);
		armorStand.setGravity(false);
		armorStand.setCollidable(false);
		armorStand.setInvulnerable(true);
		armorStand.setCustomNameVisible(true);

		startNexus();
	}
	
	public void startNexus() {
		int x = buildingLocation.getBlockX();
		int y = buildingLocation.getBlockY();
		int z = buildingLocation.getBlockZ();
		Material material = Material.STONE;
		Material material2 = Material.GLASS;
		if (team.equals("blue")) {
			material = Material.LAPIS_BLOCK;
			material2 = Material.BLUE_STAINED_GLASS;
		} else if (team.equals("red")) {
			material = Material.REDSTONE_BLOCK;
			material2 = Material.RED_STAINED_GLASS;
		}

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				world.getBlockAt(x + (i - 2) * ew, y, z + (j - 2) * ns).setType(Material.STONE_BRICKS);
				if ((i == 0 || i == 4) && (j == 0 || j == 4)) {
					world.getBlockAt(x + (i - 2) * ew, y + 1, z + (j - 2) * ns).setType(Material.STONE_BRICKS);
					world.getBlockAt(x + (i - 2) * ew, y + 2, z + (j - 2) * ns).setType(material2);
				} else {
					world.getBlockAt(x + (i - 2) * ew, y + 1, z + (j - 2) * ns).setType(Material.STONE_BRICK_SLAB);
				}
				if ((i >= 1 && i <= 3) && (j >= 1 && j <= 3)) {
					world.getBlockAt(x + (i - 2) * ew, y + 1, z + (j - 2) * ns).setType(Material.AIR);
				}

			}
		}
		world.getBlockAt(x, y + 2, z).setType(material2);
		world.getBlockAt(x, y + 3, z).setType(material);
	}

	public void updateEverySecond() {
		if (health < maxhealth) {
			health += 20;
			if (health > maxhealth) {
				health = maxhealth;
			}
			armorStand.setCustomName(health + "");

		}

		int time = (int) MobaPlugin.time;

		if (time >= 65) {
			for (int i = 0; i < MobaPlugin.lane.size(); i++) {
				if ((time - 65) % 30 == 0) {
					new Minion(team, "melee",  MobaPlugin.lane.get(i), minionSpawns[i]);
				} else if ((time - 65) % 30 == 1) {
					new Minion(team, "melee",  MobaPlugin.lane.get(i), minionSpawns[i]);
				} else if ((time - 65) % 30 == 2) {
					new Minion(team, "melee",  MobaPlugin.lane.get(i), minionSpawns[i]);
				} else if ((time - 65) % 30 == 4) {
					new Minion(team, "magic",  MobaPlugin.lane.get(i), minionSpawns[i]);
				} else if ((time - 65) % 30 == 5) {
					new Minion(team, "magic",  MobaPlugin.lane.get(i), minionSpawns[i]);
				} else if ((time - 65) % 30 == 6) {
					new Minion(team, "magic",  MobaPlugin.lane.get(i), minionSpawns[i]);
				}
				if (time >= 65 + 63 && (time - (65 + 63)) % 90 == 0) {
					new Minion(team, "cannon",  MobaPlugin.lane.get(i), minionSpawns[i]);
				}
			}
		}
	}

	public void addDamage(float attackDamage,MobaObject damager) {
		health -= attackDamage;
		armorStand.setCustomName(health + "");
		if (health <= 0) {
			MobaPlugin.MobaObjects.remove(armorStand.getUniqueId());
			armorStand.remove();
			destroyNexus();
		}

	}

	private void destroyNexus() {
		destroyed = true;
		int x = buildingLocation.getBlockX();
		int y = buildingLocation.getBlockY();
		int z = buildingLocation.getBlockZ();
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (i != 0 && j != 0 && i != 4 && j != 4) {
					world.getBlockAt(x + (i - 2) * ew, y, z + (j - 2) * ns).setType(Material.AIR);
				}
				world.getBlockAt(x + (i - 2) * ew, y + 1, z + (j - 2) * ns).setType(Material.AIR);
				world.getBlockAt(x + (i - 2) * ew, y + 2, z + (j - 2) * ns).setType(Material.AIR);
				world.getBlockAt(x + (i - 2) * ew, y + 3, z + (j - 2) * ns).setType(Material.AIR);
			}
		}
		Bukkit.getScheduler().cancelTasks(MobaPlugin.instance);
	}
}
