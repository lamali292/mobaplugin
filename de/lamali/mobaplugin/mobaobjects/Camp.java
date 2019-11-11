package de.lamali.mobaplugin.mobaobjects;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;

import de.lamali.mobaplugin.main.MobaPlugin;
import de.lamali.mobaplugin.mobaobjects.monster.Baron;
import de.lamali.mobaplugin.mobaobjects.monster.BlueBuff;
import de.lamali.mobaplugin.mobaobjects.monster.Dragon;
import de.lamali.mobaplugin.mobaobjects.monster.Gromp;
import de.lamali.mobaplugin.mobaobjects.monster.Herald;
import de.lamali.mobaplugin.mobaobjects.monster.Krug;
import de.lamali.mobaplugin.mobaobjects.monster.Raptor;
import de.lamali.mobaplugin.mobaobjects.monster.RedBuff;
import de.lamali.mobaplugin.mobaobjects.monster.Wolf;

public class Camp {
	public List<MobaPlayer> aggro = new ArrayList<MobaPlayer>();
	public List<Monster> monster = new ArrayList<Monster>();
	public Location loc;
	private int firstSpawn;
	public int nextSpawn;
	public int respawnTime = 0;

	public Camp(String name, float x, float y, float z, float yaw, int firstSpawn, int nextSpawn) {
		this.loc = new Location(MobaPlugin.world, x, y, z, yaw, 0);
		this.firstSpawn = firstSpawn;
		this.nextSpawn = nextSpawn;
		switch (name) {
		case "raptor":
			initRaptorCamp();
			break;
		case "wolf":
			initWolfCamp();
			break;
		case "krug":
			initKrugCamp();
			break;
		case "gromp":
			initGrompCamp();
			break;
		case "red":
			initRedBuffCamp();
			break;
		case "blue":
			initBlueBuffCamp();
			break;
		case "baron":
			initBaronCamp();
			break;
		case "dragon":
			initDragonCamp();
			break;
		case "herald":
			initHeraldCamp();
			break;
		}
	}

	private void initHeraldCamp() {
		monster.add(new Herald(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), loc.getYaw(), this));
	}

	private void initDragonCamp() {
		monster.add(new Dragon(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), loc.getYaw(), this));
	}

	private void initBaronCamp() {
		monster.add(new Baron(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), loc.getYaw(), this));
	}

	private void initBlueBuffCamp() {
		monster.add(new BlueBuff(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), loc.getYaw(), this));
	}

	private void initRedBuffCamp() {
		monster.add(new RedBuff(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), loc.getYaw(), this));
	}

	private void initGrompCamp() {
		monster.add(new Gromp(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), loc.getYaw(), this));
	}

	private void initKrugCamp() {
		monster.add(new Krug("ancient", (float) (loc.getX() + Math.cos(getRadian())), loc.getBlockY(),
				(float) (loc.getZ() + Math.sin(getRadian())), loc.getYaw(), this));
		monster.add(new Krug("", (float) (loc.getX() - Math.cos(getRadian())), loc.getBlockY(),
				(float) (loc.getZ() - Math.sin(getRadian())), loc.getYaw(), this));
	}

	private void initWolfCamp() {
		monster.add(new Wolf("", (float) (loc.getX() + Math.sin(getRadian())), loc.getBlockY(),
				(float) (loc.getZ() + Math.cos(getRadian())), loc.getYaw(), this));
		monster.add(new Wolf("", (float) (loc.getX() - Math.sin(getRadian())), loc.getBlockY(),
				(float) (loc.getZ() - Math.cos(getRadian())), loc.getYaw(), this));
		monster.add(new Wolf("greater", (float) (loc.getX() + Math.sin(getRadian() + Math.PI / 2)), loc.getBlockY(),
				(float) (loc.getZ() + Math.cos(getRadian() + Math.PI / 2)), loc.getYaw(), this));
	}

	private void initRaptorCamp() {
		monster.add(new Raptor("", (float) (loc.getX() + Math.sin(getRadian())), loc.getBlockY(), (float) (loc.getZ() + Math.cos(getRadian())), loc.getYaw(), this));
		monster.add(new Raptor("", (float) (loc.getX() - Math.sin(getRadian())), loc.getBlockY(), (float) (loc.getZ() - Math.cos(getRadian())), loc.getYaw(), this));
		
		monster.add(new Raptor("", (float) (loc.getX() - Math.cos(getRadian() - Math.PI / 2)), loc.getBlockY(), (float) (loc.getZ() - Math.sin(getRadian() - Math.PI / 2)), loc.getYaw(), this));
		monster.add(new Raptor("", (float) (loc.getX()), loc.getBlockY(), (float) (loc.getZ()), loc.getYaw(), this));
		monster.add(new Raptor("", (float) (loc.getX() + Math.cos(getRadian() -Math.PI / 4)), loc.getBlockY(), (float) (loc.getZ() + Math.sin(getRadian() - Math.PI / 4)), loc.getYaw(), this));
		
		monster.add(new Raptor("crimson", (float) (loc.getX() + Math.cos(getRadian() - Math.PI / 2)), loc.getBlockY(), (float) (loc.getZ() + Math.sin(getRadian() - Math.PI / 2)), loc.getYaw(), this));
	}

	public void updateEverySecond() {
		boolean alive = false;

		if (MobaPlugin.second == firstSpawn) {
			initMonster();
		} else {
			for (Monster m : monster) {
				if (m.name.equals("herald")) {
					if (MobaPlugin.second == nextSpawn) {
						m.kill();
					}
					return;
				}
				if (!m.dead) {
					alive = true;
				}
			}
			if (alive == false) {
				if (respawnTime < nextSpawn) {
					respawnTime++;
				} else {
					respawnTime = 0;
					initMonster();
				}
			}
		}
	}

	public void initMonster() {
		aggro = new ArrayList<MobaPlayer>();
		for (Monster m : monster) {
			m.init();
		}
	}

	public float getRadian() {
		return (float) (loc.getYaw() * Math.PI / 180);
	}
}
