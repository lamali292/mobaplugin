package de.lamali.mobaplugin.main.items.advanced;

import de.lamali.mobaplugin.main.InventoryShop;
import de.lamali.mobaplugin.main.items.MobaItem;
import de.lamali.mobaplugin.mobaobjects.MobaPlayer;

public class Phage extends MobaItem{
	public Phage() {
		super("Phage", 500, InventoryShop.LongSword,InventoryShop.RubyCrystal);
	}

	@Override
	public void getAktiv(MobaPlayer p) {
		
	}

	@Override
	public void getPassive(MobaPlayer p) {
		
	}

	@Override
	public float getHealth(MobaPlayer p) {
		return 200;
	}

	@Override
	public float getAttackDamage(MobaPlayer p) {
		return 15;
	}

	
}
