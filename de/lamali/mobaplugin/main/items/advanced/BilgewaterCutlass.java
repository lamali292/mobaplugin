package de.lamali.mobaplugin.main.items.advanced;

import de.lamali.mobaplugin.main.InventoryShop;
import de.lamali.mobaplugin.main.items.MobaItem;
import de.lamali.mobaplugin.mobaobjects.MobaPlayer;

public class BilgewaterCutlass extends MobaItem{
	public BilgewaterCutlass() {
		super("BilgewaterCutlass", 350, InventoryShop.VampiricScepter,InventoryShop.LongSword);
	}

	@Override
	public void getAktiv(MobaPlayer p) {
		
	}

	@Override
	public void getPassive(MobaPlayer p) {
		
	}

	@Override
	public float getAttackDamage(MobaPlayer p) {
		return 25;
	}
	
	@Override
	public float getLifeSteal(MobaPlayer p) {
		return 0.1F;
	}


}
