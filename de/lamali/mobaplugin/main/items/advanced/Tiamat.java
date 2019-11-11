package de.lamali.mobaplugin.main.items.advanced;

import de.lamali.mobaplugin.main.InventoryShop;
import de.lamali.mobaplugin.main.items.MobaItem;
import de.lamali.mobaplugin.mobaobjects.MobaPlayer;

public class Tiamat extends MobaItem{
	public Tiamat() {
		super("Tiamat", 475, InventoryShop.LongSword,InventoryShop.RejuvenationBead,InventoryShop.LongSword);
	}

	@Override
	public void getAktiv(MobaPlayer p) {
		
	}

	@Override
	public void getPassive(MobaPlayer p) {
		
	}

	@Override
	public float getHealthRegen(MobaPlayer p) {
		return 0.5F;
	}

	@Override
	public float getAttackDamage(MobaPlayer p) {
		return 25;
	}



}
