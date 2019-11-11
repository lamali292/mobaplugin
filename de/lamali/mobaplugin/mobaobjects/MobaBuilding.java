package de.lamali.mobaplugin.mobaobjects;

import org.bukkit.Location;
import org.bukkit.Sound;

public class MobaBuilding extends MobaObject{
	public Location buildingLocation;
	public int ns, ew;
	public boolean destroyed;
	public String direction;
	
	public MobaBuilding(String name, String team, int x, int y, int z, String direction, float maxhealth, float armor, float attackRange, float attackDamage, float attackSpeed,
			Sound attackSound, float attackSoundVolume, float attackSoundPitch) {
		super(name, team, maxhealth, armor, attackRange, attackDamage, attackSpeed, attackSound, attackSoundVolume, attackSoundPitch);
		Location Loc = new Location(world,x,y,z);
		buildingLocation = new Location(world, Loc.getBlockX() + 0.5F, Loc.getBlockY(), Loc.getBlockZ() + 0.5F, 0, 0);
		this.direction = direction;
		
		if (direction.contains("south")) {
			ns = -1;
			//this.buildingLocation.setZ(this.buildingLocation.getZ() - 0.5D);
		} else if (direction.contains("north")) {
			ns = 1;
			//this.buildingLocation.setZ(this.buildingLocation.getZ() + 0.5D);
		}
		if (direction.contains("west")) {
			ew = 1;
			//this.buildingLocation.setX(this.buildingLocation.getX() + 0.5D);
		} else if (direction.contains("east")) {
			ew = -1;
			//this.buildingLocation.setX(this.buildingLocation.getX() - 0.5D);
		}
	}
	
	public MobaBuilding(String name, String team, int x, int y, int z, String direction, float maxhealth, float armor) {
		super(name, team, maxhealth, armor, 0, 0, 1, null, 0, 1);
		Location Loc = new Location(world,x,y,z);
		buildingLocation = new Location(world, Loc.getBlockX() + 0.5F, Loc.getBlockY(), Loc.getBlockZ() + 0.5F, 0, 0);
		this.direction = direction;
		
		if (direction.contains("south")) {
			ns = -1;
			//this.buildingLocation.setZ(this.buildingLocation.getZ() - 0.5D);
		} else if (direction.contains("north")) {
			ns = 1;
			//this.buildingLocation.setZ(this.buildingLocation.getZ() + 0.5D);
		}
		if (direction.contains("west")) {
			ew = 1;
			//this.buildingLocation.setX(this.buildingLocation.getX() + 0.5D);
		} else if (direction.contains("east")) {
			ew = -1;
			//this.buildingLocation.setX(this.buildingLocation.getX() - 0.5D);
		}
	}

}
