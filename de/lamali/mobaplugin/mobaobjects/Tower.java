package de.lamali.mobaplugin.mobaobjects;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_14_R1.CraftParticle;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftArmorStand;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import de.lamali.mobaplugin.main.MobaPlugin;
import net.minecraft.server.v1_14_R1.PacketPlayOutWorldParticles;

public class Tower extends MobaBuilding {

	private int loaded = 0;
	private int loadedTimer = 0;

	public Tower(String team, int x, int y, int z, String direction, float maxhealth, float attackTime,
			float attackDamage, float armor, float attackRange) {
		super("Tower", team, x, y, z, direction, maxhealth, armor, attackRange, attackDamage, attackTime,
				Sound.ITEM_TRIDENT_RETURN, 0.5F, 2);
		init();
	}

	public void init() {

		armorStand = (ArmorStand) world.spawnEntity(buildingLocation, EntityType.ARMOR_STAND);
		MobaPlugin.MobaObjects.put(armorStand.getUniqueId(), this);
		armorStand.setVisible(false);
		armorStand.setCustomName(maxhealth + "");
		armorStand.setCustomNameVisible(true);
		armorStand.setGravity(false);
		armorStand.setCollidable(false);
		armorStand.setInvulnerable(true);

		startTurret();
	}

	public void update() {
		attackTime++;
		List<Entity> nearEntities = armorStand.getNearbyEntities(attackRange + 2, attackRange + 2, attackRange + 2);
		if (loaded > 0) {
			loadedTimer++;
			if (loadedTimer >= 60) {
				loadedTimer = 0;
				loaded = 0;
			}
		}
		entityTarget = getTarget(entityTarget);

		if (entityTarget != null) {
			Location pLoc = entityTarget.getLocation().add(0, 1, 0);
			if (attackTime >= 20F / attackSpeed) {
				Particle par = Particle.SMOKE_LARGE;
				if (loaded > 0) {
					par = Particle.EXPLOSION_NORMAL;
				} 
				attack(entityTarget);
				attackTime = 0;
				Location aLoc = armorStand.getLocation().add(0, 5.5, 0);
				Vector newLoc = pLoc.subtract(aLoc).toVector().divide(new Vector(20, 20, 20));
				for (int i = 0; i < 20; i++) {
					aLoc.add(newLoc);
					
					entityTarget.getWorld().spawnParticle(par, aLoc, 1, 0.05D, 0.05D, 0.05D, 0D);
				}

			}
		}
		for (Entity e : nearEntities) {
			if (e instanceof Player) {
				Player player = (Player) e;
				Particle par = Particle.CRIT;
				if (team.equals("red")) {
					par = Particle.LANDING_LAVA;
				} else if (team.equals("blue")) {
					par = Particle.DRIP_WATER;
				}

				for (int i = 0; i < 10; i++) {
					double ran = Math.random();

					float x = (float) (armorStand.getLocation().getX() + attackRange * Math.sin(2 * Math.PI * ran));
					float y = (float) armorStand.getLocation().getY();
					float z = (float) (armorStand.getLocation().getZ() + attackRange * Math.cos(2 * Math.PI * ran));

					PacketPlayOutWorldParticles particles = new PacketPlayOutWorldParticles(CraftParticle.toNMS(par),
							true, x, y, z, 0F, 0F, 0F, 0F, 1);
					((CraftPlayer) player).getHandle().playerConnection.sendPacket(particles);
					// e.getWorld().spawnParticle(par,x,y,z, 1);

				}

			}
		}

	}

	public Entity getTarget(Entity target) {
		if (!destroyed) {
			List<Entity> nearEntities = armorStand.getNearbyEntities(attackRange + 2, attackRange + 2, attackRange + 2);
			HashMap<UUID, Integer> prio = new HashMap<UUID, Integer>();
			int highestPrio = 0;
			UUID highestUUID = null;
			for (Entity e : nearEntities) {
				if (MobaPlugin.MobaObjects.containsKey(e.getUniqueId())) {
					MobaObject obj = MobaPlugin.MobaObjects.get(e.getUniqueId());
					if (obj instanceof MobaPlayer || obj instanceof Minion) {
						if (obj.team != null && team != null) {
							if (!team.equals(obj.team)) {

								if (e.getLocation().distance(armorStand.getLocation()) < attackRange) {
									if (target != null) {
										if (e instanceof Player && target.getUniqueId() == e.getUniqueId()
												&& MobaPlugin.MobaObjects.get(e.getUniqueId()) instanceof MobaPlayer) {
											return target;
										}
									}
									if (e instanceof Player && obj.isAttackingPlayer()
											&& MobaPlugin.MobaObjects.get(e.getUniqueId()) instanceof MobaPlayer) {
										prio.put(e.getUniqueId(), 5);

									} else {
										if (e instanceof CraftArmorStand
												&& MobaPlugin.MobaObjects.containsKey(e.getUniqueId())) {
											if (MobaPlugin.MobaObjects.get(e.getUniqueId()) instanceof Minion) {
												Minion m = (Minion) MobaPlugin.MobaObjects.get(e.getUniqueId());
												if (m.type.equals("super")) {
													prio.put(e.getUniqueId(), 5);
												} else if (m.type.equals("cannon")) {
													prio.put(e.getUniqueId(), 4);
												} else if (m.type.equals("melee")) {
													prio.put(e.getUniqueId(), 3);
												} else if (m.type.equals("magic")) {
													prio.put(e.getUniqueId(), 2);
												}
											}
										} else if (MobaPlugin.MobaObjects.get(e.getUniqueId()) instanceof MobaPlayer) {
											prio.put(e.getUniqueId(), 1);
										}

									}
								}
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
					}
				}
			}
			if (highestUUID != null) {
				return Bukkit.getEntity(highestUUID);
			}
		}
		return null;
	}

	public void attack(Entity entity) {
		if (MobaPlugin.MobaObjects.containsKey(entity.getUniqueId())) {
			if (MobaPlugin.MobaObjects.get(entity.getUniqueId()) instanceof Minion) {
				Minion m = (Minion) MobaPlugin.MobaObjects.get(entity.getUniqueId());
				if (m.type.equals("melee")) {
					m.addDirectDamage(m.maxhealth * 0.45F,this);
				} else if (m.type.equals("magic")) {
					m.addDirectDamage(m.maxhealth * 0.7F,this);
				} else if (m.type.equals("cannon")) {
					m.addDirectDamage(m.maxhealth * 0.14F,this);
				} else if (m.type.equals("super")) {
					m.addDirectDamage(m.maxhealth * 0.05F,this);
				} else {
					m.addDamage(attackDamage,this);
				}

			} else if (MobaPlugin.MobaObjects.get(entity.getUniqueId()) instanceof MobaPlayer) {
				MobaPlayer l = (MobaPlayer) MobaPlugin.MobaObjects.get(entity.getUniqueId());
				l.addDamage(attackDamage + attackDamage * 0.4F * loaded,this);
				if (loaded < 3) {
					loaded++;
					loadedTimer = 0;
				}
			}
		}
		attackTime = 0F;
		entity.getWorld().playSound(entity.getLocation(), attackSound, attackSoundVolume, attackSoundPitch);

	}

	public void destroyTurret() {
		int x = buildingLocation.getBlockX();
		int y = buildingLocation.getBlockY() + 4;
		int z = buildingLocation.getBlockZ();
		for (int i = -1; i < 5; i++) {
			world.getBlockAt(x + ew, y - i, z).setType(Material.AIR);
			world.getBlockAt(x, y - i, z + ns).setType(Material.AIR);
			world.getBlockAt(x, y - i, z).setType(Material.AIR);
			world.getBlockAt(x + ew, y - i + 1, z + ns).setType(Material.AIR);
		}
		destroyed = true;
	}

	public void startTurret() {
		int x = buildingLocation.getBlockX();
		int y = buildingLocation.getBlockY() + 4;
		int z = buildingLocation.getBlockZ();
		if (team.equals("blue")) {
			world.getBlockAt(x, y + 1, z).setType(Material.LAPIS_BLOCK);
		} else if (team.equals("red")) {
			world.getBlockAt(x, y + 1, z).setType(Material.REDSTONE_BLOCK);
		} else {
			world.getBlockAt(x, y + 1, z).setType(Material.STONE);
		}
		world.getBlockAt(x, y, z).setType(Material.STONE_BRICK_WALL);
		world.getBlockAt(x, y - 1, z).setType(Material.STONE);
		world.getBlockAt(x, y - 2, z).setType(Material.STONE);

		world.getBlockAt(x, y - 1, z + ns).setType(Material.STONE_BRICK_WALL);
		world.getBlockAt(x, y - 2, z + ns).setType(Material.STONE);
		world.getBlockAt(x, y - 3, z + ns).setType(Material.STONE);
		world.getBlockAt(x, y - 4, z + ns).setType(Material.STONE);
		world.getBlockAt(x + ew, y - 1, z).setType(Material.STONE_BRICK_WALL);
		world.getBlockAt(x + ew, y - 2, z).setType(Material.STONE);
		world.getBlockAt(x + ew, y - 3, z).setType(Material.STONE);
		world.getBlockAt(x + ew, y - 4, z).setType(Material.STONE);
		world.getBlockAt(x + ew, y - 2, z + ns).setType(Material.STONE_BRICK_WALL);
		world.getBlockAt(x + ew, y - 3, z + ns).setType(Material.STONE);
		world.getBlockAt(x + ew, y - 4, z + ns).setType(Material.STONE);

		world.getBlockAt(x, y - 5, z).setType(Material.STONE);
		world.getBlockAt(x + ew, y - 5, z).setType(Material.STONE);
		world.getBlockAt(x, y - 5, z + ns).setType(Material.STONE);
		world.getBlockAt(x + ew, y - 5, z + ns).setType(Material.STONE);

		destroyed = false;

	}

	public void addDamage(float attackDamage, MobaObject damager) {
		health -= (100 / (100 + armor)) * attackDamage;
		armorStand.setCustomName(String.valueOf((int) health));
		if (health <= 0) {
			MobaPlugin.MobaObjects.remove(armorStand.getUniqueId());
			armorStand.remove();
			destroyTurret();
		}

	}

}
