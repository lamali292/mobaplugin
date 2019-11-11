package de.lamali.mobaplugin.main.items.finished;

import de.lamali.mobaplugin.main.InventoryShop;
import de.lamali.mobaplugin.main.items.MobaItem;
import de.lamali.mobaplugin.mobaobjects.MobaPlayer;

public class RabadonsDeathcap extends MobaItem{
	public RabadonsDeathcap() {
		super("RabadonsDeathcap", 1100, InventoryShop.NeedlesslyLargeRod,InventoryShop.NeedlesslyLargeRod);
	}

	@Override
	public void getAktiv(MobaPlayer p) {
		
	}

	@Override
	public void getPassive(MobaPlayer p) {
		
	}

	@Override
	public float getAbilityPower(MobaPlayer p) {
		return 120;
	}



	

}
