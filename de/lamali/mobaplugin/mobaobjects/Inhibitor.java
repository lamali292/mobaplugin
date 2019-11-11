package de.lamali.mobaplugin.mobaobjects;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

import de.lamali.mobaplugin.main.MobaPlugin;

public class Inhibitor extends MobaBuilding {

	private Location[] superMinionSpawns;
	private int lane;
	public int respawnTime = 300;
	public int timeUntilRespawn = 0;

	public Inhibitor(String team, int x, int y, int z, String direction, float maxhealth, float armor,
			int lane, Location[] superMinionSpawns) {
		super("Inhibitor", team, x, y, z, direction, maxhealth, armor);
		this.superMinionSpawns = superMinionSpawns;
		this.lane = lane;
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

		startInhibitor();
	}

	public void startInhibitor() {
		int x = buildingLocation.getBlockX();
		int y = buildingLocation.getBlockY();
		int z = buildingLocation.getBlockZ();
		Material material = Material.STONE;
		if (team.equals("blue")) {
			material = Material.LAPIS_BLOCK;
		} else if (team.equals("red")) {
			material = Material.REDSTONE_BLOCK;
		}

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				world.getBlockAt(x + (i - 1) * ew, y, z + (j - 1) * ns).setType(Material.STONE_BRICKS);

			}
		}
		world.getBlockAt(x, y + 1, z).setType(material);
		world.getBlockAt(x + ew, y, z).setType(Material.IRON_BLOCK);
		world.getBlockAt(x - ew, y, z).setType(Material.IRON_BLOCK);
		world.getBlockAt(x, y, z + ns).setType(Material.IRON_BLOCK);
		world.getBlockAt(x, y, z - ns).setType(Material.IRON_BLOCK);

		destroyed = false;

	}

	public void updateEverySecond() {
		if (armorStand.isDead()) {
			destroyed = true;
		}
		if (destroyed) {
			int time = (int) MobaPlugin.time;

			if ((time + 1 - 65) % 30 == 0 && respawnTime - timeUntilRespawn > 60) {
				if (team.equals("red")) {
					new Minion("blue", "super", MobaPlugin.lane.get(lane), superMinionSpawns[lane]);
				} else if (team.equals("blue")) {
					new Minion("red", "super", MobaPlugin.lane.get(lane), superMinionSpawns[lane]);
				}

			}

			timeUntilRespawn++;
			int sek = (respawnTime - timeUntilRespawn) % 60;
			int min = (respawnTime - timeUntilRespawn - sek) / 60;
			// world.getPlayers().get(0).sendMessage(min + ":" + sek + " " +
			// team + " Inhib respawn");
			if (sek == 15 && min == 0) {
				Bukkit.getServer().broadcastMessage(team + "'s Inhibitor will respawn soon");
			}
			if (timeUntilRespawn >= respawnTime) {
				health = maxhealth;
				timeUntilRespawn = 0;
				init();
			}
		} else {
			if (health < maxhealth) {
				health += 15;
				if (health > maxhealth) {
					health = maxhealth;
				}
				armorStand.setCustomName(health + "");

			}
		}

	}

	public void addDamage(float attackDamage,MobaObject damager) {
		health -= (100 / (100 + armor)) * attackDamage;
		armorStand.setCustomName(health + "");
		if (health <= 0) {
			MobaPlugin.MobaObjects.remove(armorStand.getUniqueId());
			armorStand.remove();
			destroyInhibitor();
		}

	}

	private void destroyInhibitor() {
		destroyed = true;
		int x = buildingLocation.getBlockX();
		int y = buildingLocation.getBlockY();
		int z = buildingLocation.getBlockZ();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (i == 1 && j == 1) {

				} else {
					world.getBlockAt(x + (i - 1) * ew, y, z + (j - 1) * ns).setType(Material.AIR);
				}
				world.getBlockAt(x + (i - 1) * ew, y + 1, z + (j - 1) * ns).setType(Material.AIR);
			}
		}
	}
}
