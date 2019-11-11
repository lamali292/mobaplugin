package de.lamali.mobaplugin.main.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.lamali.mobaplugin.main.InventoryShop;
import de.lamali.mobaplugin.mobaobjects.MobaPlayer;

public abstract class MobaItem {
	public String name;
	public MobaItem[] build;
	public int price;
	public int buildPrice;
	public int tier = 0;
	public List<String> tags = new ArrayList<String>();

	public MobaItem(String name, int price, MobaItem ... build) {
		this.name = name;
		this.build = build;
		this.price = price;
		this.buildPrice=price;
		if(build!=null&&build.length>0){
			int highestTier = tier;
			for(MobaItem item : build){
				buildPrice+=item.buildPrice;
				if(item.tier>highestTier){
					highestTier=item.tier;
				}
			}
			this.tier = highestTier+1;
		}
		InventoryShop.itemList.add(this);
		
		if (getHealth(null) > 0) {
			tags.add("health");
		}
		if (getMagicRes(null) > 0) {
			tags.add("magicResist");
		}
		if (getHealthRegen(null) > 0) {
			tags.add("healthRegen");
		}
		if (getArmor(null) > 0) {
			tags.add("armor");
		}
		if (getAttackDamage(null) > 0) {
			tags.add("attackDamage");
		}
		if (getCriticalStrike(null) > 0) {
			tags.add("criticalStrike");
		}
		if (getAttackSpeed(null) > 0) {
			tags.add("attackSpeed");
		}
		if (getLifeSteal(null) > 0) {
			tags.add("lifeSteal");
		}
		if (getAbilityPower(null) > 0) {
			tags.add("abilityPower");
		}
		if (getCooldownReduction(null) > 0) {
			tags.add("cooldownReduction");
		}
		if (getMana(null) > 0) {
			tags.add("mana");
		}
		if (getManaRegen(null) > 0) {
			tags.add("manaRegen");
		}
		if (getMovement(null) > 0) {
			tags.add("movement");
		}
		if (getConsumable(null)) {
			tags.add("consumable");
		}

	}

	public ItemStack getItem() {
		String epicColor = "§a";
		switch(tier){
		case 1:epicColor = "§c";break;
		case 2:epicColor = "§d";break;
		case 3:epicColor = "§e";break;
		}
		
		Material mat = Material.STONE;
		ItemStack stack = new ItemStack(mat);
		stack.setAmount(1);

		ItemMeta meta = stack.getItemMeta();
				
		meta.setDisplayName(epicColor+name);
		List<String> lore = new ArrayList<String>();
		lore.add("§6"+price);
		if (build != null && build.length>0) {
		    String buildPath = "§3";
			for (MobaItem item : build) {
				buildPath+=item.name+" ";
			}	
			lore.set(0,"§6"+ buildPrice+" ("+lore.get(0)+")");
			lore.add(buildPath);
		}
		meta.setLore(lore);
		stack.setItemMeta(meta);
		return stack;
	}

	public abstract void getAktiv(MobaPlayer p);

	public abstract void getPassive(MobaPlayer p);

	public float getHealth(MobaPlayer p){
		return 0;
	}

	public float getMagicRes(MobaPlayer p){
		return 0;
	}

	public float getHealthRegen(MobaPlayer p){
		return 0;
	}

	public float getArmor(MobaPlayer p){
		return 0;
	}

	public float getAttackDamage(MobaPlayer p){
		return 0;
	}

	public float getCriticalStrike(MobaPlayer p){
		return 0;
	}

	public float getAttackSpeed(MobaPlayer p){
		return 0;
	}

	public float getLifeSteal(MobaPlayer p){
		return 0;
	}

	public float getAbilityPower(MobaPlayer p){
		return 0;
	}

	public float getCooldownReduction(MobaPlayer p){
		return 0;
	}

	public float getMana(MobaPlayer p){
		return 0;
	}

	public float getManaRegen(MobaPlayer p){
		return 0;
	}

	public float getMovement(MobaPlayer p){
		return 0;
	}

	public boolean getConsumable(MobaPlayer p){
		return false;
	}
}
