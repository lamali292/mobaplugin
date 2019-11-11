package de.lamali.mobaplugin.mobaobjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.util.Vector;

import de.lamali.mobaplugin.main.Lane;
import de.lamali.mobaplugin.main.MobaPlugin;

public class Minion extends MobaObject {
	private Lane lane;
	private float see, minDist;
	String type;
	private Location spawnLocation;

	public Minion(String team, String type, Lane lane, Location spawnLocation, float maxhealth, float armor, float see,
			float attackRange, float attackDamage, float attackSpeed, Sound attackSound, float attackSoundVolume,
			float attackSoundPitch) {
		super("Minion", team, maxhealth, armor, attackRange, attackDamage, attackSpeed, attackSound, attackSoundVolume,
				attackSoundPitch);
		this.lane = lane;
		this.see = see;
		this.type = type;
		this.spawnLocation = spawnLocation;
	}

	public Minion(String team, String type, Lane lane, Location spawnLocation) {
		super("Minion", team, 0, 0, 0, 0, 1, null, 0, 1);
		this.type = type;
		this.lane = lane;
		this.spawnLocation = spawnLocation;
		if (type.equals("melee")) {
			maxhealth = 477;
			attackSpeed = 1.25F;
			attackDamage = 12F;
			attackRange = 1.1F;
			see = 4F;
			minDist = 0.7F;
			attackSound = Sound.ENTITY_PLAYER_ATTACK_NODAMAGE;
			attackSoundVolume = 0.1f;
			attackSoundPitch = 1;
			goldReward = 21;
			expReward = 59;
		} else if (type.equals("magic")) {
			maxhealth = 296;
			attackSpeed = 0.667F;
			attackDamage = 24F;
			attackRange = 5.5F;
			see = 6F;
			minDist = 0.7F;
			attackSound = Sound.ENTITY_WITCH_THROW;
			attackSoundVolume = 0.1f;
			attackSoundPitch = 1;
			goldReward = 14;
			expReward = 29;
		} else if (type.equals("cannon")) {
			maxhealth = 912;
			attackSpeed = 1F;
			attackDamage = 41F;
			attackRange = 3.0F;
			see = 5F;
			minDist = 0.8F;
			attackSound = Sound.ENTITY_BLAZE_SHOOT;
			attackSoundVolume = 0.1f;
			attackSoundPitch = 2;
			goldReward = 60;
			expReward = 90;
		} else if (type.equals("super")) {
			maxhealth = 1600;
			attackSpeed = 0.85F;
			attackDamage = 230F;
			attackRange = 1.1F;
			see = 4F;
			minDist = 0.7F;
			armor = 100;
			attackSound = Sound.ENTITY_WITCH_THROW;
			attackSoundVolume = 0.1f;
			attackSoundPitch = 1;
			goldReward = 60;
			expReward = 97;
		}
		health = maxhealth;
		init();
	}

	@Override
	public void init() {
		Location armorLoc = new Location(world, spawnLocation.getBlockX() + 0.5F, spawnLocation.getBlockY(),
				spawnLocation.getBlockZ() + 0.5F, 0, 0);

		target = armorLoc;

		armorStand = (ArmorStand) world.spawnEntity(armorLoc, EntityType.ARMOR_STAND);

		entityTarget = armorStand;

		armorStand.setCustomName("100%");
		armorStand.setVisible(true);
		armorStand.setGravity(false);
		armorStand.setCollidable(false);
		armorStand.setInvulnerable(true);
		if (maxhealth < 900) {
			armorStand.setSmall(true);
		}
		armorStand.addScoreboardTag(team);
		armorStand.setBasePlate(false);
		armorStand.setArms(true);
		armorStand.setCustomNameVisible(true);

		ItemStack helm = new ItemStack(Material.LEATHER_HELMET);
		ItemStack plate = new ItemStack(Material.LEATHER_CHESTPLATE);
		ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
		ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
		LeatherArmorMeta meta = (LeatherArmorMeta) helm.getItemMeta();
		if (team.equals("red")) {
			meta.setColor(Color.RED);
		} else if (team.equals("blue")) {
			meta.setColor(Color.BLUE);
		} else {
			meta.setColor(Color.GREEN);
		}

		helm.setItemMeta(meta);
		plate.setItemMeta(meta);
		leggings.setItemMeta(meta);
		boots.setItemMeta(meta);
		armorStand.setHelmet(helm);
		armorStand.setLeggings(leggings);
		armorStand.setBoots(boots);

		if (type.equals("melee")) {
			armorStand.setChestplate(plate);
			armorStand.setItemInHand(new ItemStack(Material.IRON_SWORD));
		} else if (type.equals("magic")) {
			armorStand.setChestplate(plate);
			armorStand.setItemInHand(new ItemStack(Material.STICK));
		} else if (type.equals("cannon")) {
			armorStand.setChestplate(plate);
			armorStand.setItemInHand(new ItemStack(Material.COAL));
		} else if (type.equals("super")) {
			armorStand.setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
			armorStand.setItemInHand(new ItemStack(Material.SHIELD));
		}

		MobaPlugin.MobaObjects.put(armorStand.getUniqueId(), this);
	}

	public void update() {
		attackTime++;
		if (attacksMinion > 0) {
			attacksMinion--;
		}
		if (attacksPlayer > 0) {
			attacksPlayer--;
		}
		if (dead || armorStand.isDead()) {
			if (!armorStand.isDead()) {
				armorStand.remove();
			}
			return;
		}

		List<Entity> nearEntities = armorStand.getNearbyEntities(minDist, minDist, minDist);
		for (Entity e : nearEntities) {
			if (MobaPlugin.MobaObjects.containsKey(e.getUniqueId())) {
				if (MobaPlugin.MobaObjects.get(e.getUniqueId()) instanceof Minion) {
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

		Entity entityTarget = getTarget();
		if (entityTarget != null) {
			target = entityTarget.getLocation();
		} else {
			target = null;
		}

		if (attackTime >= 20F / attackSpeed && target != null) {
			attack(entityTarget);

		}
		if (target != null && entityTarget != null) {
			if (MobaPlugin.MobaObjects.get(entityTarget.getUniqueId()) instanceof Nexus) {
				if (distanceTo(target) - MobaPlugin.nexusThickness > attackRange) {
					goTo(target);
				} else {
					lookAt(target);
				}
			} else if (MobaPlugin.MobaObjects.get(entityTarget.getUniqueId()) instanceof Tower
					&& (type.equals("melee") || type.equals("super"))) {
				if (distanceTo(target) - MobaPlugin.towerThickness > attackRange) {
					goTo(target);
				} else {
					lookAt(target);
				}
			} else if (MobaPlugin.MobaObjects.get(entityTarget.getUniqueId()) instanceof Inhibitor) {
				if (distanceTo(target) - MobaPlugin.inhibThickness > attackRange) {
					goTo(target);
				} else {
					lookAt(target);
				}
			} else {
				if (distanceTo(target) > attackRange) {
					goTo(target);
				} else {
					lookAt(target);
				}
			}
		} else {
			followLane();
		}

	}

	public void updateEverySecond() {
		if (MobaPlugin.second > 120 && (MobaPlugin.second - 135) % 90 == 0 && MobaPlugin.second < 1050) {
			goldReward += 3;
		}
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

	public void attack(Entity entity) {
		if (MobaPlugin.MobaObjects.containsKey(entity.getUniqueId())) {
			if (MobaPlugin.MobaObjects.get(entity.getUniqueId()) instanceof Tower) {
				Tower t = (Tower) MobaPlugin.MobaObjects.get(entity.getUniqueId());
				t.addDamage(0.5F * attackDamage,this);
			} else if (MobaPlugin.MobaObjects.get(entity.getUniqueId()) instanceof Minion) {
				Minion m = (Minion) MobaPlugin.MobaObjects.get(entity.getUniqueId());
				m.addDamage(attackDamage,this);
				attacksMinion = 20;
			} else if (MobaPlugin.MobaObjects.get(entity.getUniqueId()) instanceof Nexus) {
				Nexus n = (Nexus) MobaPlugin.MobaObjects.get(entity.getUniqueId());
				if (name.equals("super")) {
					n.addDamage(0.4F * attackDamage,this);
				} else {
					n.addDamage(0.5F * attackDamage,this);
				}
			} else if (MobaPlugin.MobaObjects.get(entity.getUniqueId()) instanceof Inhibitor) {
				Inhibitor i = (Inhibitor) MobaPlugin.MobaObjects.get(entity.getUniqueId());
				i.addDamage(attackDamage,this);
			} else if (MobaPlugin.MobaObjects.get(entity.getUniqueId()) instanceof MobaPlayer) {
				MobaPlayer l = (MobaPlayer) MobaPlugin.MobaObjects.get(entity.getUniqueId());
				l.addDamage(0.5F * attackDamage,this);
				attacksPlayer = 20;
			}

			world.playSound(armorStand.getLocation(), attackSound, attackSoundVolume, attackSoundPitch);
		}

		attackTime = 0;

		Location pLoc = armorStand.getLocation().add(0, 0.5F, 0);
		Location aLoc = entity.getLocation().add(0, 0.5F, 0);
		Vector newLoc = pLoc.subtract(aLoc).toVector().divide(new Vector(5, 5, 5));
		if (type.equals("magic")) {
			for (int i = 0; i < 5; i++) {
				aLoc.add(newLoc);
				world.spawnParticle(Particle.SPELL_WITCH, aLoc, 1, 0.05D, 0.05D, 0.05D, 0D);
			}
		} else if (type.equals("cannon")) {
			for (int i = 0; i < 5; i++) {
				aLoc.add(newLoc);
				world.spawnParticle(Particle.CLOUD, aLoc, 1, 0.05D, 0.05D, 0.05D, 0D);
			}
		} else {
			world.spawnParticle(Particle.SWEEP_ATTACK, aLoc, 1, 0D, 0D, 0D, 0D);
		}
	}

	protected Entity getTarget() {
		List<Entity> nearEntities = armorStand.getNearbyEntities(see, see, see);

		HashMap<UUID, Integer> prio = new HashMap<UUID, Integer>();
		int highestPrio = 0;
		UUID highestUUID = null;

		for (Tower t : MobaPlugin.towerList) {
			if (t.buildingLocation.distance(armorStand.getLocation()) < see + MobaPlugin.towerThickness) {
				if (!t.team.equals(team)) {
					prio.put(t.armorStand.getUniqueId(), 6);
				}
			}
		}
		for (Inhibitor i : MobaPlugin.inhibitorList) {
			if (i.buildingLocation.distance(armorStand.getLocation()) < see + MobaPlugin.inhibThickness) {
				if (!i.team.equals(team)) {
					prio.put(i.armorStand.getUniqueId(), 5);
				}
			}
		}
		for (Nexus n : MobaPlugin.nexusList) {
			if (n.buildingLocation.distance(armorStand.getLocation()) < see + MobaPlugin.nexusThickness) {
				if (!n.team.equals(team)) {
					prio.put(n.armorStand.getUniqueId(), 4);
				}
			}
		}
		for (Entity e : nearEntities) {
			if (MobaPlugin.MobaObjects.containsKey(e.getUniqueId())) {
				if (!MobaPlugin.MobaObjects.get(e.getUniqueId()).team.equals(team)) {
					MobaObject mobaObject = MobaPlugin.MobaObjects.get(e.getUniqueId());
					if (e.getLocation().distance(armorStand.getLocation()) < see) {
						if (mobaObject.isAttackingPlayer() && mobaObject instanceof MobaPlayer) {
							prio.put(e.getUniqueId(), 9);
						} else if (e instanceof ArmorStand && mobaObject.isAttackingPlayer()) {
							prio.put(e.getUniqueId(), 8);
						} else if (e instanceof ArmorStand && mobaObject.isAttackingMinion()) {
							prio.put(e.getUniqueId(), 7);
						} else if (mobaObject.isAttackingMinion() && mobaObject instanceof MobaPlayer) {
							prio.put(e.getUniqueId(), 3);
						} else if (e instanceof ArmorStand && !(mobaObject instanceof Monster)) {
							prio.put(e.getUniqueId(), 2);
						} else if (mobaObject instanceof MobaPlayer) {
							prio.put(e.getUniqueId(), 1);
						}
					}
				}
			}
		}

		for (Entity e : nearEntities) {
			if (prio.containsKey(e.getUniqueId())) {
				if (prio.get(e.getUniqueId()) > highestPrio) {
					highestUUID = e.getUniqueId();
					highestPrio = prio.get(e.getUniqueId());
				} else if (prio.get(e.getUniqueId()) == highestPrio && Bukkit.getEntity(highestUUID) != null) {
					if (e.getLocation().distance(armorStand.getLocation()) < Bukkit.getEntity(highestUUID).getLocation()
							.distance(armorStand.getLocation())) {
						highestUUID = e.getUniqueId();
						highestPrio = prio.get(e.getUniqueId());
					}
				}
			}
		}
		if (highestUUID != null) {
			entityTarget = Bukkit.getEntity(highestUUID);
			return Bukkit.getEntity(highestUUID);
		}
		return null;
	}

	protected void followLane() {
		Location lanePoint = lane.getNearestLocation(armorStand.getLocation());
		if (lanePoint != null) {
			if (lanePoint.distance(armorStand.getLocation()) > 0.2) {
				goTo(lanePoint);
			} else {
				goTo(lane.getNextPath(armorStand.getLocation(), team));
			}
		}
	}

	public void goTo(Location localtarget) {
		if (localtarget != null) {
			Location minionLoc = armorStand.getLocation();
			Vector vec = localtarget.subtract(minionLoc).toVector();
			localtarget.add(minionLoc);
			vec.multiply(1 / vec.length());
			vec.multiply(movementSpeed);
			vec.setY(0);

			minionLoc.setDirection(vec);
			minionLoc.add(vec);
			if (world.getBlockAt(minionLoc).isPassable()) {
				armorStand.teleport(minionLoc);
			} else {
				findOtherWay(minionLoc, localtarget);
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

	public void findOtherWay(Location from, Location to) {
		Bukkit.broadcastMessage("I'm stuck! "+from.getBlockX()+"/"+from.getBlockY()+"/"+from.getBlockZ());
	}
}
