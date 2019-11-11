package de.lamali.mobaplugin.main;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_14_R1.CraftServer;
import org.bukkit.craftbukkit.v1_14_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.server.MapInitializeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.map.MapView;
import org.bukkit.map.MapView.Scale;
import org.bukkit.util.Vector;

import com.mojang.authlib.GameProfile;

import de.lamali.mobaplugin.main.items.MobaItem;
import de.lamali.mobaplugin.mobaobjects.Minion;
import de.lamali.mobaplugin.mobaobjects.MobaPlayer;
import net.minecraft.server.v1_14_R1.EntityPlayer;
import net.minecraft.server.v1_14_R1.MinecraftServer;
import net.minecraft.server.v1_14_R1.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_14_R1.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_14_R1.PlayerConnection;
import net.minecraft.server.v1_14_R1.PlayerInteractManager;
import net.minecraft.server.v1_14_R1.WorldServer;

public class EventHandler implements Listener {
	public static boolean mirror = false;

	@org.bukkit.event.EventHandler
	public void onBlockPlayerEvent(BlockPlaceEvent event) {
		if (event.getPlayer() != null && mirror) {
			Location blockLoc = event.getBlock().getLocation();
			Location middle = new Location(event.getBlock().getWorld(), 230.5, blockLoc.getY(), 230.5);
			Vector vec = middle.subtract(blockLoc).toVector().multiply(2);
			Location newBlockLoc = blockLoc.add(vec);

			event.getBlock().getWorld().getBlockAt(newBlockLoc).setType(event.getBlock().getType());
			event.getBlock().getWorld().getBlockAt(newBlockLoc).setBlockData(event.getBlock().getBlockData());
		}
	}

	@org.bukkit.event.EventHandler
	public void onBlockBreakEvent(BlockBreakEvent event) {
		if (event.getPlayer() != null & mirror) {
			Location blockLoc = event.getBlock().getLocation();
			Location middle = new Location(event.getBlock().getWorld(), 230.5, blockLoc.getY(), 230.5);
			Vector vec = middle.subtract(blockLoc).toVector().multiply(2);
			Location newBlockLoc = blockLoc.add(vec);
			event.getBlock().getWorld().getBlockAt(newBlockLoc).setType(Material.AIR);
		}
	}

	@org.bukkit.event.EventHandler
	public void onPlayerMoveEvent(PlayerMoveEvent event) {
		float minDist = 0.4F;
		Player player = event.getPlayer();

		if (MobaPlugin.MobaObjects != null) {
			if (MobaPlugin.MobaObjects.containsKey(player.getUniqueId())) {
				MobaPlayer mobaplayer = (MobaPlayer) MobaPlugin.MobaObjects.get(player.getUniqueId());
				mobaplayer.to = event.getTo();
				mobaplayer.from = event.getFrom();
			}
		}

		MobaPlugin.setInvisBlocks(player);

		Location aLoc = event.getTo();
		List<Entity> nearEntities = player.getNearbyEntities(minDist, minDist, minDist);
		hey: for (Entity e : nearEntities) {
			if (MobaPlugin.MobaObjects != null) {
				if (MobaPlugin.MobaObjects.containsKey(e.getUniqueId())) {
					if (MobaPlugin.MobaObjects.get(e.getUniqueId()) instanceof Minion) {
						Location eLoc = e.getLocation();

						if (eLoc.distance(aLoc) < minDist) {
							Vector atoe = eLoc.subtract(aLoc).toVector();
							atoe.multiply(-minDist);
							// atoe.multiply(0.1F);
							aLoc.add(atoe);
							// player.teleport(aLoc.add(atoe));
							break hey;
						}
					}
				}
			}
		}
		event.setTo(aLoc);
	}

	@org.bukkit.event.EventHandler
	public void onPlayerInteractEvent(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		MobaPlugin.setInvisBlocks(player);
		if (event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {

		}
		if (event.getClickedBlock() != null) {
			if (event.getClickedBlock().getType().equals(Material.BOOKSHELF)) {
				Inventory shop = InventoryShop.getShop();
				player.openInventory(shop);

				event.setCancelled(true);
			}
		}

	}

	@org.bukkit.event.EventHandler
	public void onPlayerDropItemEvent(PlayerDropItemEvent event) {
		event.setCancelled(true);
		Player player = event.getPlayer();
		if (MobaPlugin.MobaObjects != null) {
			if (MobaPlugin.MobaObjects.containsKey(player.getUniqueId())) {
				MobaPlayer mobaplayer = (MobaPlayer) MobaPlugin.MobaObjects.get(player.getUniqueId());
				if (event.getItemDrop().getItemStack().getType() == Material.APPLE) {
					mobaplayer.flash();
				} else if (event.getItemDrop().getItemStack().getType() == Material.DIAMOND_PICKAXE) {
					mobaplayer.Q();
				} else if (event.getItemDrop().getItemStack().getType() == Material.DIAMOND_AXE) {
					mobaplayer.W();
				} else if (event.getItemDrop().getItemStack().getType() == Material.DIAMOND_SHOVEL) {
					mobaplayer.E();
				} else if (event.getItemDrop().getItemStack().getType() == Material.DIAMOND_HOE) {
					mobaplayer.R();
				} else if (event.getItemDrop().getItemStack().getType() == Material.DIAMOND_SWORD) {
					// player.sendMessage(event.getItemDrop().getItemStack().getType()
					// + "");
					mobaplayer.action();
				} else if (event.getItemDrop().getItemStack().getType() == Material.COMPASS) {
					mobaplayer.recall();
				}

			}
		}
	}

	@org.bukkit.event.EventHandler
	public void onInventoryClickEvent(InventoryClickEvent event) {
		if (event.getClickedInventory() != null) {
			List<HumanEntity> player = event.getClickedInventory().getViewers();

			Bukkit.broadcastMessage(
					event.getInventory().getType() + " " + event.getInventory().getSize() + " " + event.getClick());
			if (player != null) {
				for (HumanEntity h : player) {
					if (!ClickType.CREATIVE.equals(event.getClick()) && MobaPlugin.MobaObjects != null) {
						if (MobaPlugin.MobaObjects.containsKey(h.getUniqueId())) {
							MobaPlayer p = (MobaPlayer) MobaPlugin.MobaObjects.get(h.getUniqueId());
							boolean foundItem = false;
							for (MobaItem item : InventoryShop.itemList) {
								if (item.getItem().equals(event.getCurrentItem())) {
									if (event.getClick().equals(ClickType.LEFT)) {
										p.buy(item);
									} else if (event.getClick().equals(ClickType.RIGHT)) {
										p.sell(item);
									}
									foundItem = true;
									p.updateStats();

								}
							}
							if (!foundItem && event.getInventory().getType().equals(InventoryType.CHEST)) {
								h.openInventory(InventoryShop.changePage());
							}
							event.setCancelled(true);
						}
					}
				}
			}
		}
	}

	@org.bukkit.event.EventHandler
	public void onEntityDamageEvent(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player) {
			event.setCancelled(true);
			event.getEntity().sendMessage("prevented " + (int) event.getDamage() + " Damage");
		}
	}

	@org.bukkit.event.EventHandler
	public void onMapInitializeEvent(MapInitializeEvent event) {
		MapView map = event.getMap();

		map.setCenterX(231);
		map.setCenterZ(231);
		map.setWorld(MobaPlugin.world);
		map.setScale(Scale.CLOSE);
		map.setLocked(false);

	}

	public void spawnFakePlayer(Player player, String displayname) {
		MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
		WorldServer world = ((CraftWorld) Bukkit.getWorlds().get(0)).getHandle();

		Player target = Bukkit.getServer().getPlayer(displayname);
		EntityPlayer npc;
		if (target != null) {
			npc = new EntityPlayer(server, world, new GameProfile(target.getUniqueId(), target.getName()),
					new PlayerInteractManager(world));
		} else {
			@SuppressWarnings("deprecation")
			OfflinePlayer op = Bukkit.getServer().getOfflinePlayer(displayname);
			npc = new EntityPlayer(server, world, new GameProfile(op.getUniqueId(), displayname),
					new PlayerInteractManager(world));
		}
		Location loc = player.getLocation();
		npc.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());

		for (Player all : Bukkit.getOnlinePlayers()) {
			PlayerConnection connection = ((CraftPlayer) all).getHandle().playerConnection;
			connection.sendPacket(
					new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));
			connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));

		}

	}
}
