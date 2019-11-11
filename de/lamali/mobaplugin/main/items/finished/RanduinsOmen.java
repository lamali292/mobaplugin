package de.lamali.mobaplugin.main.items.finished;

import de.lamali.mobaplugin.main.InventoryShop;
import de.lamali.mobaplugin.main.items.MobaItem;
import de.lamali.mobaplugin.mobaobjects.MobaPlayer;

public class RanduinsOmen extends MobaItem{
	public RanduinsOmen() {
		super("RanduinsOmen", 900, InventoryShop.WardensMail, InventoryShop.GiantsBelt);
	}


	@Override
	public void getAktiv(MobaPlayer p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getPassive(MobaPlayer p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getHealth(MobaPlayer p) {
		return 400;
	}

	@Override
	public float getArmor(MobaPlayer p) {
		return 70;
	}

	


}
