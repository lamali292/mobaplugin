package de.lamali.mobaplugin.mobaobjects;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_15_R1.CraftParticle;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import de.lamali.mobaplugin.main.AABB;
import de.lamali.mobaplugin.main.MobaPlugin;
import de.lamali.mobaplugin.main.AABB.Ray3D;
import de.lamali.mobaplugin.main.AABB.Vec3D;
import de.lamali.mobaplugin.main.items.MobaItem;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_15_R1.PacketPlayOutWorldParticles;

public class MobaPlayer extends MobaObject {

	public Player player;
	private UUID uuid;

	public float baseAttackSpeed, baseAttackDamage, baseHealthRegen, baseMaxmana, baseManaRegen, baseMaxhealth,
			baseAttackRange, baseArmor, baseMovementSpeed, baseMagicRes;

	public float levelMaxhealth, levelHealthRegen, levelMaxmana, levelManaRegen, levelAttackDamage, levelAttackSpeed,
			levelArmor, levelMagicRes;

	private float healthRegen;
	private float maxmana;
	private float mana;
	private float manaRegen;

	public int recallTime = 160;
	public boolean recall = false;
	public Location recallLocation;
	public int gold = 500;
	public int goldPerSecond = 2;
	private int respawnTime = 0;
	public int level = 1;
	public float xp = 0;
	private int timeUntilRespawn = 0;
	public Location to, from;
	public boolean visibleByOpponent = true;
	public int kills = 0;
	public int deaths = 0;
	public int assists = 0;
	public int secondsUntilLastHit = 0;
	public int consecutiveKills = 0;
	public int consecutiveDeaths = 0;
	private List<MobaPlayer> assistPlayers = new ArrayList<MobaPlayer>();
	public List<MobaItem> playerItems = new ArrayList<MobaItem>();
	private float cooldownReduction;

	public MobaPlayer(Player player, String team, float maxhealth, float healthRegen, float maxmana, float manaRegen,
			float attackRange, float attackDamage, float attackSpeed, float armor, float magicRes, float movementSpeed,
			float levelMaxhealth, float levelHealthRegen, float levelMaxmana, float levelManaRegen,
			float levelAttackDamage, float levelAttackSpeed, float levelArmor, float levelMagicRes) {
		super("Player", team, maxhealth, armor, 1, attackDamage, attackSpeed, null, 0, 1);
		MobaPlugin.MobaObjects.put(player.getUniqueId(), this);
		this.player = player;
		this.uuid = player.getUniqueId();

		this.magicRes = magicRes;
		this.movementSpeed = MobaPlugin.getMobaSpeed((int) movementSpeed);
		this.healthRegen = healthRegen;
		this.maxmana = maxmana;
		this.mana = maxmana;
		this.manaRegen = manaRegen;
		this.attackRange = MobaPlugin.getMobaRange((int) attackRange);

		this.levelMaxhealth = levelMaxhealth;
		this.levelHealthRegen = levelHealthRegen;
		this.levelMaxmana = levelMaxmana;
		this.levelManaRegen = levelManaRegen;
		this.levelAttackDamage = levelAttackDamage;
		this.levelAttackSpeed = levelAttackSpeed;
		this.levelArmor = levelArmor;
		this.levelMagicRes = levelMagicRes;

		this.baseMagicRes = magicRes;
		this.baseMovementSpeed = MobaPlugin.getMobaSpeed((int) movementSpeed);
		this.baseArmor = armor;
		this.baseMaxhealth = maxhealth;
		this.baseHealthRegen = healthRegen;
		this.baseMaxmana = maxmana;
		this.baseManaRegen = manaRegen;
		this.baseAttackSpeed = attackSpeed;
		this.baseAttackDamage = attackDamage;
		this.baseAttackRange = MobaPlugin.getMobaRange((int) attackRange);

		player.setExp(0);
		player.setHealth(20);
		player.setLevel(level);

	}

	public void updateEverySecond() {
		if (player != null) {
			if (MobaPlugin.second >= 65) {
				gold += goldPerSecond;
			}
			if (dead) {
				if (timeUntilRespawn >= respawnTime) {
					this.health = maxhealth;
					this.mana = maxmana;
					player.setHealth(20);
					MobaPlugin.MobaObjects.put(player.getUniqueId(), this);
					dead = false;
					timeUntilRespawn = 0;
					if (team.equals("red")) {
						player.teleport(MobaPlugin.redTeamSpawn);
					} else if (team.equals("blue")) {
						player.teleport(MobaPlugin.blueTeamSpawn);
					}

					player.setGameMode(GameMode.ADVENTURE);
				} else {
					player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
							TextComponent.fromLegacyText(respawnTime - timeUntilRespawn + " seconds until respawn"));
				}
				timeUntilRespawn++;
			} else {

				if (recall) {

					if (recallLocation.getBlock().equals(player.getLocation().getBlock())) {
						player.sendMessage("recall in " + recallTime / 20);
						if (recallTime <= 0) {
							recall = false;
							recallTime = 160;
							if (team.equals("red")) {
								player.teleport(MobaPlugin.redTeamSpawn);
							} else if (team.equals("blue")) {
								player.teleport(MobaPlugin.blueTeamSpawn);
							}
						}
					} else {
						recall = false;
						recallTime = 160;
						player.sendMessage("recall failed");
					}
					recallTime -= 20;
				}

				if (secondsUntilLastHit >= 8) {
					assistPlayers = new ArrayList<MobaPlayer>();
					secondsUntilLastHit++;
				} else {
					secondsUntilLastHit++;
				}
				player.setLevel(level);
				if (mana < maxmana) {
					mana += manaRegen;
					if (mana > maxmana) {
						mana = maxmana;
					}
				}
				if (health < maxhealth) {
					health += healthRegen;
					if (health > maxhealth) {
						health = maxhealth;
					}
					player.setHealth(20 * health / maxhealth);
				}
			}
		}
	}

	public void update() {
		player = Bukkit.getPlayer(uuid);
		if (attacksMinion > 0) {
			attacksMinion--;
		}
		if (attacksPlayer > 0) {
			attacksPlayer--;
		}
		if (player != null) {
			if (!dead) {
				if (team.equals("blue")) {
					if (MobaPlugin.blueTeamSpawn.distance(player.getLocation()) < 7
							&& (health < maxhealth || mana < maxmana)) {
						health += maxhealth * 0.005;
						mana += maxmana * 0.005;
						if (health > maxhealth) {
							health = maxhealth;
						}
						if (mana > maxmana) {
							mana = maxmana;
						}
					}
				} else if (team.equals("red")) {
					if (MobaPlugin.redTeamSpawn.distance(player.getLocation()) < 7
							&& (health < maxhealth || mana < maxmana)) {
						health += maxhealth * 0.005;
						mana += maxmana * 0.005;
						if (health > maxhealth) {
							health = maxhealth;
						}
						if (mana > maxmana) {
							mana = maxmana;
						}
					}
				}
			}
			attackTime++;
			updateTabList(player);
			player.setScoreboard(MobaPlugin.board);
			Location playerLoc = player.getLocation();

			if (player.getWorld().getBlockAt(playerLoc).getType().equals(Material.TALL_GRASS)) {
				visibleByOpponent = false;
			} else {
				visibleByOpponent = true;
			}

			if (visibleByOpponent) {
				for (MobaPlayer m : MobaPlugin.playerList) {
					if (m.player != null) {
						if (!team.equals(m.team) && !m.player.canSee(player)) {
							m.player.showPlayer(MobaPlugin.instance, player);
						}
					}
				}
			} else {
				for (MobaPlayer m : MobaPlugin.playerList) {
					if (m.player != null) {
						if (!team.equals(m.team) && m.player.canSee(player)) {
							m.player.hidePlayer(MobaPlugin.instance, player);
						}
					}
				}
			}
			updateActionBar();
		}
	}

	public void addDamage(float damage, MobaObject damager) {
		health -= (100 / (100 + armor)) * damage;
		if (health <= 0) {
			MobaPlugin.MobaObjects.remove(player.getUniqueId());
			player.setGameMode(GameMode.SPECTATOR);
			setRespawnTime();
			this.dead = true;
			deaths++;
			if (damager instanceof MobaPlayer) {
				MobaPlayer damagerPlayer = (MobaPlayer) damager;
				secondsUntilLastHit = 0;
				damagerPlayer.announceKill(this);
				damagerPlayer.addBounty(consecutiveKills - consecutiveDeaths);
				damagerPlayer.consecutiveDeaths = 0;
				damagerPlayer.consecutiveKills += 1;

				if (!assistPlayers.contains(damagerPlayer)) {
					assistPlayers.add(damagerPlayer);
				}
				if (assistPlayers.size() > 0) {
					for (MobaPlayer m : assistPlayers) {
						int xpFromPlayer = (90 + 50 * level);
						if (0.07 * (level - m.level) > -0.8) {
							xpFromPlayer += xpFromPlayer * 0.07 * (level - m.level);
						} else {
							xpFromPlayer += xpFromPlayer * -0.8;
						}
						m.addExperience(xpFromPlayer / assistPlayers.size());
						if (!m.equals(damagerPlayer)) {
							m.assists++;
							m.addAssistBounty(consecutiveKills - consecutiveDeaths);
						}
					}
				}

				damagerPlayer.kills++;

			} else if (assistPlayers.size() > 0) {
				MobaPlayer recentDamager = assistPlayers.get(assistPlayers.size() - 1);
				recentDamager.consecutiveDeaths = 0;
				recentDamager.consecutiveKills += 1;
				recentDamager.announceKill(this);
				recentDamager.addBounty(consecutiveKills - consecutiveDeaths);
				for (MobaPlayer m : assistPlayers) {
					int xpFromPlayer = (90 + 50 * level);
					if (0.07 * (level - m.level) > -0.8) {
						xpFromPlayer += xpFromPlayer * 0.07 * (level - m.level);
					} else {
						xpFromPlayer += xpFromPlayer * -0.8;
					}
					m.addExperience(xpFromPlayer / assistPlayers.size());
					if (!m.equals(recentDamager)) {
						m.assists++;
						m.addAssistBounty(consecutiveKills - consecutiveDeaths);
					}
				}
				recentDamager.kills++;
			}
			consecutiveDeaths += 1;
			consecutiveKills = 0;
			updateTabList(player);
		} else {
			if (damager instanceof MobaPlayer) {
				MobaPlayer damagerPlayer = (MobaPlayer) damager;
				secondsUntilLastHit = 0;
				if (!assistPlayers.contains(damagerPlayer)) {
					assistPlayers.add(damagerPlayer);
				}
			}

			player.setHealth(Math.min(20 * health / maxhealth, 20));
		}
	}

	private void addBounty(int tier) {
		if (tier >= 3) {
			addGold((tier + 3) * 100);
		} else if (tier == 2) {
			addGold(0);
		} else if (tier == 1) {
			addGold(300);
		} else if (tier == 0) {
			addGold(300);
		} else if (tier == -1) {
			addGold(274);
		} else if (tier == -2) {
			addGold(220);
		} else if (tier == -3) {
			addGold(176);
		} else if (tier == -4) {
			addGold(140);
		} else if (tier == -5) {
			addGold(112);
		} else if (tier <= -6) {
			addGold(100);
		}
	}

	private void addAssistBounty(int tier) {
		if (tier >= 0) {
			addGold(150);
		} else if (tier == -1) {
			addGold(137);
		} else if (tier == -2) {
			addGold(110);
		} else if (tier == -3) {
			addGold(88);
		} else if (tier == -4) {
			addGold(70);
		} else if (tier == -5) {
			addGold(56);
		} else if (tier <= -6) {
			addGold(50);
		}
	}

	public void addGold(int amount) {
		gold += amount;
	}

	private void announceKill(MobaPlayer killedPlayer) {
		String killAnnouncement = getTeamColor(team) + player.getDisplayName() + "§f killed "
				+ getTeamColor(killedPlayer.team) + killedPlayer.player.getDisplayName();
		Bukkit.broadcastMessage(killAnnouncement);
		String extraAnnouncement = player.getDisplayName();
		switch (consecutiveKills - consecutiveDeaths) {
		case 8:
			extraAnnouncement += " is Legendary";
			break;
		case 7:
			extraAnnouncement += " is Godlike";
			break;
		case 6:
			extraAnnouncement += " is Dominating";
			break;
		case 5:
			extraAnnouncement += " is Unstoppable";
			break;
		case 4:
			extraAnnouncement += " is on a Rampage";
			break;
		case 3:
			extraAnnouncement += " is on a Killing spree";
			break;
		}
		if (!extraAnnouncement.equals(player.getDisplayName())) {
			Bukkit.broadcastMessage("§6" + extraAnnouncement);
		}

	}

	private void setRespawnTime() {
		float bRT = 0;
		float rt = 0;
		if (level <= 6) {
			bRT = level * 2 + 4;
		} else if (level == 7) {
			bRT = 21;
		} else if (level > 7) {
			bRT = level * 2.5F + 7.5F;
		}
		int time = (int) MobaPlugin.time;
		float min = (float) Math.floor((time - time % 60D) / 60D);
		rt = bRT;
		float kbrt = bRT / 100F;
		if (min >= 15) {
			rt += kbrt * (min - 15F) * 2 * 0.425F;
		}
		if (min >= 30) {
			rt += kbrt * (min - 30F) * 2 * 0.3F;
		}
		if (min >= 45) {
			rt += kbrt * (min - 45F) * 2 * 0.145F;
		}
		if (time >= 3210) {
			rt = 1.5F * bRT;
		}
		this.respawnTime = (int) rt;

	}

	public void addExperience(float exp) {
		xp += exp;
		while (xp >= getExpFromLevel()) {
			xp -= getExpFromLevel();
			addLevel();
			player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
			player.setLevel(level);
		}
		player.setExp(xp / getExpFromLevel());
	}

	public void addLevel() {
		level++;
		baseMaxhealth += levelMaxhealth;
		baseHealthRegen += levelHealthRegen;
		baseMaxmana += levelMaxmana;
		baseManaRegen += levelManaRegen;
		baseAttackDamage += levelAttackDamage;
		baseAttackSpeed += levelAttackSpeed;
		baseArmor += levelArmor;
		baseMagicRes += levelMagicRes;
		updateStats();
	}

	public int getExpFromLevel() {
		if (level < 18) {
			return 100 * (level + 1) + 80;
		}
		return 10000000;
	}

	public void updateStats() {
		// TODO Items list/test

		maxhealth = baseMaxhealth;
		healthRegen = baseHealthRegen;
		maxmana = baseMaxmana;
		manaRegen = baseManaRegen;
		attackRange = baseAttackRange;
		attackDamage = baseAttackDamage;
		attackSpeed = baseAttackSpeed;
		armor = baseArmor;
		magicRes = baseMagicRes;
		movementSpeed = baseMovementSpeed;

		for (MobaItem item : playerItems) {
			maxhealth += item.getHealth(this);
			healthRegen += baseHealthRegen * item.getHealthRegen(this);
			maxmana += item.getMana(this);
			manaRegen += baseManaRegen * item.getManaRegen(this);
			attackDamage += item.getAttackDamage(this);
			attackSpeed += baseAttackSpeed * item.getAttackSpeed(this);
			armor += item.getArmor(this);
			magicRes += item.getMagicRes(this);
			movementSpeed += item.getMovement(this);
			cooldownReduction += item.getCooldownReduction(this);
			if (cooldownReduction > 0.6F) {
				cooldownReduction = 0.6F;
			}
		}
		if (maxhealth < health) {
			health = maxhealth;
		}
		if (maxmana < mana) {
			mana = maxmana;
		}
	}

	public void flash() {
		float flashRange = 5;

		Vector lookLocation = new Vector(0, 0, 0);

		if (to != null && from != null) {
			lookLocation = to.clone().subtract(from).toVector().setY(0);
			to = null;
			from = null;
		}
		if (lookLocation.length() == 0) {
			lookLocation = player.getLocation().getDirection().setY(0);
		}

		lookLocation.multiply(flashRange / lookLocation.length());

		Location flashLoc = player.getLocation().add(lookLocation);
		flashLoc.setY(player.getLocation().getY());
		if (flashLoc.getBlock().isEmpty() || flashLoc.getBlock().isPassable()) {
			Location aLoc = player.getLocation().add(0, 1, 0);
			player.teleport(flashLoc);
			Vector newLoc = flashLoc.subtract(aLoc).add(0, 1, 0).toVector().divide(new Vector(20, 20, 20));
			for (int i = 0; i < 20; i++) {
				aLoc.add(newLoc);
				player.getWorld().spawnParticle(Particle.SPELL_INSTANT, aLoc, 1, 0.1D, 0.1D, 0.1D, 0D);
			}
		}
	}

	public void updateTabList(Player player) {
		int spaceCount = 16 - player.getDisplayName().length();
		String spaces = "";
		for (int i = 0; i < spaceCount; i++) {
			spaces += " ";
		}
		player.setPlayerListName(
				getTeamColor(team) + player.getDisplayName() + spaces + "§f" + kills + "/" + deaths + "/" + assists);

	}

	public void updateActionBar() {
		String sHealth = "§4" + (int) health + " / " + (int) maxhealth;
		String sMana = "§1" + (int) mana + " / " + (int) maxmana;
		String sGold = "§6" + gold;

		player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent
				.fromLegacyText(sHealth + "     " + MobaPlugin.timeString + "    " + sGold + "     " + sMana));
	}

	public void Q() {

	}

	public void W() {

	}

	public void E() {

	}

	public void R() {

	}

	public void recall() {
		recall = true;
		recallLocation = player.getLocation();
		player.sendMessage("recall started");

	}

	public void action() {
		Bukkit.broadcastMessage(20 / attackTime + " " + attackSpeed);
		// if (attackTime >= 20F / attackSpeed) {
		attackTime = 0;
		float range = attackRange;

		Particle par = Particle.SMOKE_NORMAL;
		for (int i = 0; i < 100; i++) {
			double ran = Math.random();

			float x = (float) (player.getLocation().getX() + range * Math.sin(2 * Math.PI * ran));
			float y = (float) (player.getLocation().getY());
			float z = (float) (player.getLocation().getZ() + range * Math.cos(2 * Math.PI * ran));

			PacketPlayOutWorldParticles particles = new PacketPlayOutWorldParticles(CraftParticle.toNMS(par), true, x,
					y, z, 0F, 0F, 0F, 0F, 1);
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(particles);
		}

		RayTraceResult ray = player.rayTraceBlocks(range);
		Vector hit;
		if (ray != null) {
			hit = ray.getHitPosition();
		} else {
			hit = player.getEyeLocation().add(player.getLocation().getDirection().multiply(range)).toVector();
		}

		player.spawnParticle(Particle.CRIT_MAGIC, hit.toLocation(player.getWorld()), 10, 0, 0, 0, 0);

		List<Entity> markedEntities = new ArrayList<Entity>();

		List<Entity> nearEntities = player.getNearbyEntities(range, range, range);
		for (Entity e : nearEntities) {
			if (player.getLocation().distance(e.getLocation()) < range) {
				float distance = (float) player.getEyeLocation().distance(hit.toLocation(player.getWorld()));
				AABB EntityBoundingBox = new AABB(
						Vec3D.fromVector(e.getBoundingBox().getMin()).add(new Vec3D(-0.1, 0.0, -0.1)),
						Vec3D.fromVector(e.getBoundingBox().getMax()).add(new Vec3D(0.1, 0.3, 0.1)));
				if (EntityBoundingBox.intersectsRay(new Ray3D(player.getEyeLocation()), 0F, distance) != null) {
					markedEntities.add(e);
					if (e instanceof Player) {
						Player p = (Player) e;
						if (p.getGameMode().equals(GameMode.SPECTATOR)) {
							markedEntities.remove(e);
						}
					}
					if (MobaPlugin.MobaObjects.containsKey(e.getUniqueId())
							&& MobaPlugin.MobaObjects.containsKey(player.getUniqueId())) {
						MobaPlayer mobaplayer = (MobaPlayer) MobaPlugin.MobaObjects.get(player.getUniqueId());
						MobaObject mobaobject = (MobaObject) MobaPlugin.MobaObjects.get(e.getUniqueId());
						if (mobaplayer.team.equals(mobaobject.team)) {
							markedEntities.remove(e);
						}

					}
				}

			}
		}
		Entity nearestEntity = null;
		for (Entity e : markedEntities) {
			if (nearestEntity == null) {
				nearestEntity = e;
			} else if (e.getLocation().distance(player.getEyeLocation()) < nearestEntity.getLocation()
					.distance(player.getEyeLocation())) {
				nearestEntity = e;
			}
		}
		if (markedEntities.size() > 0 && nearestEntity != null && MobaPlugin.MobaObjects != null) {
			if (MobaPlugin.MobaObjects.containsKey(nearestEntity.getUniqueId())
					&& MobaPlugin.MobaObjects.containsKey(player.getUniqueId())) {
				MobaPlayer mobaplayer = (MobaPlayer) MobaPlugin.MobaObjects.get(player.getUniqueId());
				if (MobaPlugin.MobaObjects.get(nearestEntity.getUniqueId()) instanceof Minion) {
					Minion min = (Minion) MobaPlugin.MobaObjects.get(nearestEntity.getUniqueId());
					min.addDamage(attackDamage, mobaplayer);
					mobaplayer.attacksMinion = 3;
				} else if (MobaPlugin.MobaObjects.get(nearestEntity.getUniqueId()) instanceof Tower) {
					Tower tur = (Tower) MobaPlugin.MobaObjects.get(nearestEntity.getUniqueId());
					tur.addDamage(attackDamage, mobaplayer);
				} else if (MobaPlugin.MobaObjects.get(nearestEntity.getUniqueId()) instanceof Nexus) {
					Nexus nex = (Nexus) MobaPlugin.MobaObjects.get(nearestEntity.getUniqueId());
					nex.addDamage(attackDamage, mobaplayer);
				} else if (MobaPlugin.MobaObjects.get(nearestEntity.getUniqueId()) instanceof Inhibitor) {
					Inhibitor inh = (Inhibitor) MobaPlugin.MobaObjects.get(nearestEntity.getUniqueId());
					inh.addDamage(attackDamage, mobaplayer);
				} else if (MobaPlugin.MobaObjects.get(nearestEntity.getUniqueId()) instanceof MobaPlayer) {
					MobaPlayer mob = (MobaPlayer) MobaPlugin.MobaObjects.get(nearestEntity.getUniqueId());
					mob.addDamage(attackDamage, mobaplayer);
					if (MobaPlugin.MobaObjects.containsKey(player.getUniqueId())) {
						mobaplayer.attacksPlayer = 3;
					}
				} else if (MobaPlugin.MobaObjects.get(nearestEntity.getUniqueId()) instanceof Monster) {
					Monster mon = (Monster) MobaPlugin.MobaObjects.get(nearestEntity.getUniqueId());
					mon.addDamage(attackDamage, mobaplayer);
				}
				// }

			}
		}
	}

	public void buy(MobaItem item) {
		boolean foundBuild = true;
		List<MobaItem> playerrItemsclone = new ArrayList<MobaItem>();
		for (MobaItem build : playerItems) {
			playerrItemsclone.add(build);
		}
		for (MobaItem build : item.build) {
			if (foundBuild) {
				MobaItem playItem = null;
				for (int i = playerrItemsclone.size() - 1; i >= 0; i--) {
					MobaItem pItems = playerrItemsclone.get(i);
					if (build.name.equals(pItems.name)) {
						playItem = pItems;
						playerrItemsclone.remove(pItems);
					}
				}
				if (playItem == null) {
					buy(build);
				}else{
					if (gold < item.price) {
						foundBuild=false;
					}
					
				}
			}
		}
		if (gold >= item.price && foundBuild) {
			for (MobaItem build : item.build) {
				MobaItem playItem = null;
				for (MobaItem pItems : playerItems) {
					if (build.name.equals(pItems.name)) {
						playItem = pItems;
					}
				}
				if (playItem != null) {
					playerItems.remove(playItem);
					for (int i = 0; i < playerItems.size() + 1; i++) {
						player.getInventory().setItem(i + 9, new ItemStack(Material.AIR));
					}
					for (int i = 0; i < playerItems.size(); i++) {
						player.getInventory().setItem(i + 9, playerItems.get(i).getItem());
					}
				}
			}
			
			gold -= item.price;
			playerItems.add(item);
			

		} 
		for (int i = 0; i < playerItems.size() + 1; i++) {
			player.getInventory().setItem(i + 9, new ItemStack(Material.AIR));
		}
		for (int i = 0; i < playerItems.size(); i++) {
			player.getInventory().setItem(i + 9, playerItems.get(i).getItem());
		}
		for (int i = 6; i < playerItems.size(); i++) {
			playerItems.remove(i);
			player.getInventory().setItem(i + 9, new ItemStack(Material.AIR));
			gold += item.price;
		}
		String itemnames = "";
		for (MobaItem items : playerItems) {
			itemnames += items.name + ", ";
		}
		Bukkit.broadcastMessage(itemnames);
	}

	public void sell(MobaItem item) {
		lol: for (int i = 0; i < playerItems.size(); i++) {
			if (playerItems.get(i).name.equals(item.name)) {
				playerItems.remove(i);
				player.getInventory().setItem(i + 9, new ItemStack(Material.AIR));
				gold += item.price * 0.7F;
				break lol;
			}
		}
		for (int i = 0; i < playerItems.size() + 1; i++) {
			player.getInventory().setItem(i + 9, new ItemStack(Material.AIR));
		}
		for (int i = 0; i < playerItems.size(); i++) {
			player.getInventory().setItem(i + 9, playerItems.get(i).getItem());
		}
		String itemnames = "";
		for (MobaItem items : playerItems) {
			itemnames += items.name + ", ";
		}
		Bukkit.broadcastMessage(itemnames);
	}

}
