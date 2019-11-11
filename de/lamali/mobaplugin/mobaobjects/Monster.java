package de.lamali.mobaplugin.mobaobjects;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import de.lamali.mobaplugin.main.MobaPlugin;
import de.lamali.mobaplugin.mobaobjects.monster.Krug;

public class Monster extends MobaObject {

	public String subgroup;
	public Location spawnLocation;
	public Player entityTarget = null;
	public float yaw;
	private double minDist = 0.7;
	private double see = 6;
	public Camp camp;
	public boolean krugClone = false;

	public Monster(String name, String subgroup, float x, float y, float z, float yaw, Camp camp) {
		super(name, "", 1000, 5F, 1, 0, 1, null, 0, 1);
		this.subgroup = subgroup;
		this.spawnLocation = new Location(MobaPlugin.world, x, y, z);
		this.yaw = yaw;
		this.camp = camp;
	}

	public void init() {
		if (name.equals("dragon")) {
			if (MobaPlugin.second > 2100) {
				subgroup = "elder";
			} else {
				int drakeGroup = (int) (Math.random() * 4);
				if (drakeGroup == 0) {
					subgroup = "mountain";
				} else if (drakeGroup == 1) {
					subgroup = "cloud";
				} else if (drakeGroup == 2) {
					subgroup = "ocean";
				} else if (drakeGroup == 3) {
					subgroup = "infernal";
				}
			}
		}
		dead = false;
		health = maxhealth;
		armorStand = (ArmorStand) world.spawnEntity(spawnLocation, EntityType.ARMOR_STAND);
		armorStand.setCustomName(subgroup + " " + name);
		armorStand.setVisible(true);
		armorStand.setGravity(false);
		armorStand.setCollidable(false);
		armorStand.setInvulnerable(true);
		armorStand.setBasePlate(false);
		armorStand.setArms(true);
		armorStand.setCustomNameVisible(true);

		MobaPlugin.MobaObjects.put(armorStand.getUniqueId(), this);
		switch (name) {
		case "raptor":
			if (subgroup.equals("crimson")) {
				initCrimsonRaptor();
			} else {
				initRaptor();
			}
			break;
		case "wolf":
			if (subgroup.equals("greater")) {
				initGreaterWolf();
			} else {
				initWolf();
			}
			break;
		case "krug":
			if (subgroup.equals("ancient")) {
				initAncientKrug();
			} else if (subgroup.equals("lesser")) {
				initLesserKrug();
			} else {
				initKrug();
			}
			break;
		case "gromp":
			initGromp();
			break;
		case "scutler":
			initScutler();
			break;
		case "blue":
			initBlue();
			break;
		case "red":
			initRed();
			break;
		case "herald":
			initHerald();
			break;
		case "dragon":
			switch (subgroup) {
			case "infernal":
				initDrake();
				break;
			case "cloud":
				initDrake();
				break;
			case "ocean":
				initDrake();
				break;
			case "mountain":
				initDrake();
				break;
			case "elder":
				initElderDragon();
				break;
			}
			break;
		case "baron":
			initBaronNashor();
			break;
		}
	}

	private void initBaronNashor() {
		goldReward = 325;
		expReward = 800;
		goldGlobalReward = 300;
		expGlobalReward = 600;
		attackDamage = 400F;
		attackSpeed = 0.625F;
		armor = 120F;
		maxhealth = 6400+MobaPlugin.min*180;
		health = maxhealth;
		attackRange = MobaPlugin.getMobaRange(955);
		movementSpeed = 0;
	}

	private void initElderDragon() {
		goldGlobalReward = 250;
		expReward = 650;
		attackDamage = 150F;
		attackSpeed = 0.5F;
		armor = 125F;
		maxhealth = 6400+MobaPlugin.min*180;
		health = maxhealth;
		attackRange = MobaPlugin.getMobaRange(500);
		movementSpeed = MobaPlugin.getMobaSpeed(330);
	}

	private void initDrake() {
		goldReward = 100;
		expReward = 150;
		attackDamage = 100F;
		attackSpeed = 0.5F;
		armor = 26F;
		maxhealth = 3500;
		health = maxhealth;
		attackRange = MobaPlugin.getMobaRange(500);
		movementSpeed = MobaPlugin.getMobaSpeed(330);
	}

	private void initHerald() {
		goldReward = 100;
		expReward = 200;
		attackDamage = 100F;
		attackSpeed = 0.5F;
		armor = 60F;
		maxhealth = 10000;
		health = maxhealth;
		attackRange = MobaPlugin.getMobaRange(250);
		movementSpeed = MobaPlugin.getMobaSpeed(325);
	}

	private void initRed() {
		goldReward = 100;
		expReward = 230;
		attackDamage = 80F;
		attackSpeed = 0.5F;
		armor = -15F;
		maxhealth = 2100;
		health = maxhealth;
		movementSpeed = MobaPlugin.getMobaSpeed(275);
	}

	private void initBlue() {
		goldReward = 100;
		expReward = 230;
		attackDamage = 82F;
		attackSpeed = 0.493F;
		armor = 10F;
		maxhealth = 2100;
		health = maxhealth;
		movementSpeed = MobaPlugin.getMobaSpeed(189);
	}

	private void initScutler() {
		goldReward = 70; // 140
		expReward = 115; // 230
		armor = 60F;
		maxhealth = 800;
		health = maxhealth;
		movementSpeed = MobaPlugin.getMobaSpeed(155);
	}

	private void initGromp() {
		goldReward = 86;
		expReward = 200;
		attackDamage = 70F;
		attackSpeed = 1.004F;
		armor = 0F;
		maxhealth = 1800;
		health = maxhealth;
		attackRange = MobaPlugin.getMobaRange(250);
		movementSpeed = MobaPlugin.getMobaSpeed(330);
	}

	private void initKrug() {
		goldReward = 10;
		expReward = 35;
		attackDamage = 25F;
		attackSpeed = 0.613F;
		armor = 0F;
		maxhealth = 500;
		health = maxhealth;
		attackRange = MobaPlugin.getMobaRange(110);
		movementSpeed = MobaPlugin.getMobaSpeed(285);
	}

	private void initLesserKrug() {
		goldReward = 10;
		expReward = 7;
		attackDamage = 17F;
		attackSpeed = 0.613F;
		armor = 0F;
		maxhealth = 60;
		health = maxhealth;
		attackRange = MobaPlugin.getMobaRange(110);
		movementSpeed = MobaPlugin.getMobaSpeed(335);
		armorStand.setSmall(true);
	}

	private void initAncientKrug() {
		goldReward = 70;
		expReward = 125;
		attackDamage = 80F;
		attackSpeed = 0.613F;
		armor = 10F;
		maxhealth = 1250;
		health = maxhealth;
		attackRange = MobaPlugin.getMobaRange(150);
		movementSpeed = MobaPlugin.getMobaSpeed(185);
	}

	private void initWolf() {
		goldReward = 16;
		expReward = 40;
		attackDamage = 16F;
		attackSpeed = 0.625F;
		armor = 0F;
		maxhealth = 380;
		health = maxhealth;
		attackRange = MobaPlugin.getMobaRange(175);
		movementSpeed = MobaPlugin.getMobaSpeed(450);
	}

	private void initGreaterWolf() {
		goldReward = 68;
		expReward = 100;
		attackDamage = 42F;
		attackSpeed = 0.625F;
		armor = 10F;
		maxhealth = 1300;
		health = maxhealth;
		attackRange = MobaPlugin.getMobaRange(175);
		movementSpeed = MobaPlugin.getMobaSpeed(450);
	}

	private void initCrimsonRaptor() {
		goldReward = 62;
		expReward = 15;
		attackDamage = 20F;
		attackSpeed = 0.667F;
		armor = 30F;
		maxhealth = 700;
		health = maxhealth;
		attackRange = MobaPlugin.getMobaRange(300);
		movementSpeed = MobaPlugin.getMobaSpeed(350);
	}

	private void initRaptor() {
		goldReward = 10;
		expReward = 35;
		attackDamage = 13.33F;
		attackSpeed = 1F;
		armor = 0F;
		maxhealth = 400;
		health = maxhealth;
		attackRange = MobaPlugin.getMobaRange(300);
		movementSpeed = MobaPlugin.getMobaSpeed(450);
		armorStand.setSmall(true);
	}

	public void update() {
		attackTime++;
		if (dead || armorStand.isDead()) {
			if (!armorStand.isDead()) {
				armorStand.remove();
			}
			return;
		}
		List<Entity> nearEntities = armorStand.getNearbyEntities(minDist, minDist, minDist);
		for (Entity e : nearEntities) {
			if (MobaPlugin.MobaObjects.containsKey(e.getUniqueId())) {
				if (MobaPlugin.MobaObjects.get(e.getUniqueId()) instanceof Monster) {
					Location eLoc = e.getLocation();
					Location aLoc = armorStand.getLocation();
					if (eLoc.distance(aLoc) < minDist) {
						Vector atoe = eLoc.subtract(aLoc).toVector();
						if (atoe.length() != 0) {
							atoe.multiply(-1 / atoe.length());
						}
						atoe.multiply(0.2F);
						atoe.setY(0);
						aLoc.setDirection(atoe);
						aLoc.add(atoe);
						if (world.getBlockAt(aLoc).isPassable()) {
							armorStand.teleport(aLoc);
						}
						break;
					}
				}
			}
		}

		entityTarget = getTarget(entityTarget);
		if (entityTarget != null) {
			target = entityTarget.getLocation();
		} else {
			target = null;
		}

		if (attackTime >= 20F / attackSpeed && target != null) {
			attack(entityTarget);
		}
		if (target != null && entityTarget != null && distanceTo(spawnLocation) < 10) {
			if (distanceTo(target) > attackRange) {
				goTo(target);
			} else {
				lookAt(target);
			}
		} else if (distanceTo(spawnLocation) > 0.2) {
			goTo(spawnLocation);
			camp.aggro = new ArrayList<MobaPlayer>();
			if (health < maxhealth) {
				health += maxhealth * 0.01;
			}
			if (health > maxhealth) {
				health = maxhealth;
			}
			armorStand.setCustomName(String.valueOf((int) (100 * health / maxhealth)) + "%");
		} else if (armorStand.getLocation().getYaw() != yaw) {
			Location loc = armorStand.getLocation();
			loc.setYaw(yaw);
			armorStand.teleport(loc);
		} else {
			if (health < maxhealth) {
				health += maxhealth * 0.01;
			}
			if (health > maxhealth) {
				health = maxhealth;
			}
			armorStand.setCustomName(String.valueOf((int) (100 * health / maxhealth)) + "%");
		}
	}

	public Player getTarget(Player previousTarget) {
		List<Entity> nearestPlayer = armorStand.getNearbyEntities(see, see, see);
		List<Player> player = new ArrayList<Player>();
		for (Entity e : nearestPlayer) {
			if (e instanceof Player) {
				Player p = (Player) e;
				if (MobaPlugin.MobaObjects.containsKey(p.getUniqueId())) {
					MobaPlayer mobaPlayer = (MobaPlayer) MobaPlugin.MobaObjects.get(p.getUniqueId());

					if (p.getLocation().distance(spawnLocation) < 10) {
						if (camp.aggro.contains(mobaPlayer)) {
							player.add(p);
							if (previousTarget != null) {
								if (p.getUniqueId().equals(previousTarget.getUniqueId())) {
									return previousTarget;
								}
							}
						}
					}
				}
			}
		}
		if (player.size() > 0) {
			return player.get((int) (Math.random() * player.size()));
		}
		return null;
	}

	public void distributeExp() {
		List<Entity> nearEntities = armorStand.getNearbyEntities(8, 8, 8);
		List<MobaPlayer> distributeTo = new ArrayList<MobaPlayer>();
		for (Entity e : nearEntities) {
			if (MobaPlugin.MobaObjects.containsKey(e.getUniqueId())
					&& e.getLocation().distance(armorStand.getLocation()) <= 8) {
				if (MobaPlugin.MobaObjects.get(e.getUniqueId()) instanceof MobaPlayer) {
					MobaPlayer player = (MobaPlayer) MobaPlugin.MobaObjects.get(e.getUniqueId());
					if (!player.team.equals(team)) {
						distributeTo.add(player);
					}
				}
			}
		}
		for (MobaPlayer player : distributeTo) {
			if (distributeTo.size() == 1) {
				player.addExperience(expReward);
			} else {
				player.addExperience(expReward * (1.304F / distributeTo.size()));
			}
		}
	}

	public void addDamage(float damage, MobaObject damager) {
		health -= (100 / (100 + armor)) * damage;
		if (!camp.aggro.contains(damager) && damager instanceof MobaPlayer) {
			camp.aggro.add((MobaPlayer) damager);
		}
		if (health <= 0) {
			MobaPlugin.MobaObjects.remove(armorStand.getUniqueId());
			armorStand.remove();
			dead = true;
			distributeExp();
			onKrug();
			if (damager instanceof MobaPlayer) {
				rewardGold((MobaPlayer) damager);
			}
			if (this.goldGlobalReward > 0) {
				for (MobaPlayer p : MobaPlugin.playerList) {
					if (p.team.equals(damager.team)) {
						rewardGlobalGold(p);
					}
				}
			}
			if (expGlobalReward > 0) {
				for (MobaPlayer p : MobaPlugin.playerList) {
					if (p.team.equals(damager.team)) {
						p.addExperience(expGlobalReward);
					}
				}

			}

		} else {
			armorStand.setCustomName(String.valueOf((int) (100 * health / maxhealth)) + "%");
		}
	}
	
	public void onKrug(){
		if (name.equals("krug")) {
			if (subgroup.equals("ancient")) {
				Krug k1 = new Krug("", (float) (spawnLocation.getX() + Math.random() - 0.5), (float) spawnLocation.getY(),
						(float) (spawnLocation.getZ() + Math.random() - 0.5), spawnLocation.getYaw(), camp);
				Krug k2 = new Krug("", (float) (spawnLocation.getX() + Math.random() - 0.5), (float) spawnLocation.getY(),
						(float) (spawnLocation.getZ() + Math.random() - 0.5), spawnLocation.getYaw(), camp);
				k1.krugClone = true;
				k2.krugClone = true;
				k1.init();
				k2.init();
				camp.monster.add(k1);
				camp.monster.add(k2);
			} else if (!subgroup.equals("lesser")) {
				Krug k1 = new Krug("lesser", (float) (spawnLocation.getX() + Math.random() - 0.5),
						(float) spawnLocation.getY(), (float) (spawnLocation.getZ() + Math.random() - 0.5),
						spawnLocation.getYaw(), camp);
				Krug k2 = new Krug("lesser", (float) (spawnLocation.getX() + Math.random() - 0.5),
						(float) spawnLocation.getY(), (float) (spawnLocation.getZ() + Math.random() - 0.5),
						spawnLocation.getYaw(), camp);
				k1.krugClone = true;
				k2.krugClone = true;
				k1.init();
				k2.init();
				camp.monster.add(k1);
				camp.monster.add(k2);
			}
			if(camp.monster.contains(this)&&krugClone){
				camp.monster.remove(this);
			}
		}
	}

	public void goTo(Location localtarget) {
		if (localtarget != null) {
			Location loc = armorStand.getLocation();
			Vector vec = localtarget.subtract(loc).toVector();
			localtarget.add(loc);
			if (vec.length() > 0) {
				vec.multiply(1 / vec.length());
				vec.multiply(movementSpeed);
				vec.setY(0);

				loc.setDirection(vec);
				loc.add(vec);
				if (world.getBlockAt(loc).isPassable()) {
					armorStand.teleport(loc);
				}
			}
		}

	}

	public void lookAt(Location target) {
		Location minionLoc = armorStand.getLocation();
		Vector vec = target.subtract(minionLoc).toVector();
		target.add(minionLoc);
		vec.setY(0);
		minionLoc.setDirection(vec);
		armorStand.teleport(minionLoc);
	}

	public double distanceTo(Location target) {
		if (target != null) {
			return armorStand.getLocation().distance(target);
		}
		return 0;
	}

}
