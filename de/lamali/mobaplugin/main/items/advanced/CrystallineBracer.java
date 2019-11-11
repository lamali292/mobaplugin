package de.lamali.mobaplugin.main.items.advanced;

import de.lamali.mobaplugin.main.InventoryShop;
import de.lamali.mobaplugin.main.items.MobaItem;
import de.lamali.mobaplugin.mobaobjects.MobaPlayer;

public class CrystallineBracer extends MobaItem{
	public CrystallineBracer() {
		super("CrystallineBracer", 100, InventoryShop.RubyCrystal,InventoryShop.RejuvenationBead);
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
	public float getHealthRegen(MobaPlayer p) {
		return 0.5F;
	}

}
