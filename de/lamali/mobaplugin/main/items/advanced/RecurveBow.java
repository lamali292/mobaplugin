package de.lamali.mobaplugin.main.items.advanced;

import de.lamali.mobaplugin.main.InventoryShop;
import de.lamali.mobaplugin.main.items.MobaItem;
import de.lamali.mobaplugin.mobaobjects.MobaPlayer;

public class RecurveBow extends MobaItem{
	public RecurveBow() {
		super("RecurveBow", 400, InventoryShop.Dagger, InventoryShop.Dagger);
	}

	@Override
	public void getAktiv(MobaPlayer p) {
		
	}

	@Override
	public void getPassive(MobaPlayer p) {
		
	}

	@Override
	public float getAttackSpeed(MobaPlayer p) {
		return 0.25F;
	}



}
