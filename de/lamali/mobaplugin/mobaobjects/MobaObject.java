package de.lamali.mobaplugin.mobaobjects;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;

import de.lamali.mobaplugin.main.MobaPlugin;

public class MobaObject {

	public String name;
	public String team;
	protected float maxhealth;
	protected float health;
	protected float armor;
	protected float magicRes = 0F;
	protected float movementSpeed;
	protected float attackDamage;
	protected float attackTime = 0F;
	protected float attackRange;
	protected float attackSoundVolume;
	protected float attackSoundPitch;
	protected Sound attackSound;
	public boolean dead;
	protected ArmorStand armorStand;
	public Location target;
	protected Entity entityTarget = null;
	protected World world = MobaPlugin.world;
	protected float attackSpeed;
	public int attacksMinion = 0;
	public int attacksPlayer = 0;
	public int goldReward = 0;
	public int expReward = 0;
	public int goldGlobalReward = 0;
	public int expGlobalReward = 0;

	public MobaObject(String name, String team, float maxhealth, float armor, float attackRange, float attackDamage,
			float attackSpeed, Sound attackSound, float attackSoundVolume, float attackSoundPitch) {
		this.name = name;
		this.team = team;

		this.maxhealth = maxhealth;
		this.health = maxhealth;

		this.armor = armor;

		this.dead = false;

		this.movementSpeed = MobaPlugin.getMobaSpeed(325);
		
		this.attackRange = attackRange;
		
		this.attackDamage = attackDamage;
		this.attackSpeed = attackSpeed;
		this.attackSound = attackSound;
		this.attackSoundVolume = attackSoundVolume;
		this.attackSoundPitch = attackSoundPitch;
	}

	public void attack(Entity entity) {
		if (MobaPlugin.MobaObjects.containsKey(entity.getUniqueId())) {
			if (MobaPlugin.MobaObjects.get(entity.getUniqueId()) instanceof MobaObject) {
				MobaObject p = (MobaObject) MobaPlugin.MobaObjects.get(entity.getUniqueId());
				p.addDamage(attackDamage, this);
				attackTime = 0;
				entity.getWorld().playSound(entity.getLocation(), attackSound, attackSoundVolume, attackSoundPitch);
			}
		}
	}

	public Entity getTarget(Entity previousTarget) {
		return previousTarget;
	}

	public void update() {

	}

	public void addDamage(float damage, MobaObject damager) {
		health -= (100 / (100 + armor)) * damage;
		if (health <= 0) {
			MobaPlugin.MobaObjects.remove(armorStand.getUniqueId());
			armorStand.remove();
			dead = true;
			distributeExp();
			if(damager instanceof MobaPlayer){
				rewardGold((MobaPlayer) damager);
			}
		}else {
			armorStand.setCustomName(String.valueOf((int) (100 * health / maxhealth)) + "%");
		}
	}

	public void addDirectDamage(float damage, MobaObject damager) {
		health -= damage;
		if (health <= 0) {
			MobaPlugin.MobaObjects.remove(armorStand.getUniqueId());
			armorStand.remove();
			dead = true;
			distributeExp();
			if(damager instanceof MobaPlayer){
				rewardGold((MobaPlayer) damager);
			}
		}else {
			armorStand.setCustomName(String.valueOf((int) (100 * health / maxhealth)) + "%");
		}

	}
	
	public void kill(){
		if(armorStand!=null){
			if(MobaPlugin.MobaObjects.containsKey(armorStand.getUniqueId())){
				MobaPlugin.MobaObjects.remove(armorStand.getUniqueId());
				armorStand.remove();
			}
		}
		dead = true;
	}
	
	public void rewardGold(MobaPlayer moba){
		moba.addGold(goldReward);
	}
	
	public void rewardGlobalGold(MobaPlayer moba){
		moba.addGold(goldGlobalReward);
	}
	
	public void distributeExp(){
		
	}

	public void spawn() {

	}

	public void init() {

	}

	public void updateEverySecond() {

	}
	
	public boolean isAttackingPlayer(){
		if(attacksPlayer>0){
			return true;
		}
		return false;
	}
	
	public boolean isAttackingMinion(){
		if(attacksMinion>0){
			return true;
		}
		return false;
	}
	
	public String getTeamColor(String team){
		return team.equals("blue")?"§9":team.equals("red")?"§c":"§7";
	}
}
