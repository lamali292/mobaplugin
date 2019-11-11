package de.lamali.mobaplugin.main.items.finished;

import de.lamali.mobaplugin.main.InventoryShop;
import de.lamali.mobaplugin.main.items.MobaItem;
import de.lamali.mobaplugin.mobaobjects.MobaPlayer;

public class Thornmail extends MobaItem{
	public Thornmail() {
		super("Thornmail", 500, InventoryShop.BrambleVest, InventoryShop.RubyCrystal, InventoryShop.WardensMail);
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
		return 250;
	}

	@Override
	public float getArmor(MobaPlayer p) {
		return 80;
	}

	


}
