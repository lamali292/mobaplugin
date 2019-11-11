package de.lamali.mobaplugin.mobaobjects.monster;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import de.lamali.mobaplugin.mobaobjects.Camp;
import de.lamali.mobaplugin.mobaobjects.Monster;

public class Raptor extends Monster{

	public List<ArmorStand> armorList = new ArrayList<ArmorStand>();

	public Raptor(String subgroup, float x, float y, float z, float yaw, Camp camp) {
		super("raptor", subgroup, x, y, z, yaw,camp);	
	}

	public void inits(){
		//modelHead(armorStand.getLocation(),Material.BARREL,1,2.1F,0);
		//modelHead(armorStand.getLocation(),Material.BARREL,0,3,1);
		//modelHead(armorStand.getLocation(),Material.BARREL,-1,3.1F,1);
		//modelHead(armorStand.getLocation(),Material.BARREL,0,2.5F,-1);
		//modelHead(armorStand.getLocation(),Material.BARREL,1,2,2);
	}
	
	public void updatseEverySecond(){
		Player p = (Player) Bukkit.getOnlinePlayers().toArray()[0];
		Location playLoc = p.getLocation();
		playLoc.setY(armorStand.getLocation().getY());
		if(armorStand.getLocation().distance(playLoc)>0.4F){
			goTo(playLoc);
		}
	}
	
	public ArmorStand modelHead(Location loc, Material head,float x,float y,float z){
		ArmorStand mod1 = (ArmorStand) world.spawnEntity(loc.add(x,-1.35+y,z), EntityType.ARMOR_STAND);
		mod1.setMarker(true);
		mod1.setGravity(false);
		mod1.setVisible(false);
		mod1.setAI(false);
		mod1.setInvulnerable(false);
		mod1.setHelmet(new ItemStack(head));
		armorList.add(mod1);
		
		return mod1;
	}
	
	public void rotateTo(Vector dir){
		Location armorloc = armorStand.getLocation();
		Vector previousDir = armorloc.getDirection();
		Vector e = new Vector(0,0,1);
		
		double ang1;
		if(previousDir.getX()<0){
			ang1 = -e.angle(previousDir);
		}else{
			ang1 = e.angle(previousDir);
		}
		double ang2;
		if(dir.getX()<0){
			ang2 = -e.angle(dir);
		}else{
			ang2 = e.angle(dir);
		}
		float angle;
		if(ang1<ang2){
			angle = previousDir.angle(dir);
		}else{
			angle = -previousDir.angle(dir);
		}
		
		
		for(ArmorStand a : armorList){
			Location aLoc = a.getLocation();
			Vector head = aLoc.getDirection();
			Vector toModel = aLoc.subtract(armorloc).toVector();
			
			Vector newLoc = toModel.rotateAroundY(angle);
			a.teleport(armorloc.add(newLoc).setDirection(head.rotateAroundY(angle)));
		
			armorloc = armorStand.getLocation();
		}
	}
	
	public void gosTo(Location loc){
		Location armorloc = armorStand.getLocation();
		Vector toNewLoc = loc.subtract(armorloc).toVector().normalize().multiply(0.1F);
		rotateTo(toNewLoc);
		armorStand.teleport(armorloc.add(toNewLoc).setDirection(toNewLoc));
		
		for(ArmorStand a : armorList){
			a.teleport(a.getLocation().add(toNewLoc));
		}
	}
}
