package de.lamali.mobaplugin.main.items.advanced;

import de.lamali.mobaplugin.main.InventoryShop;
import de.lamali.mobaplugin.main.items.MobaItem;
import de.lamali.mobaplugin.mobaobjects.MobaPlayer;

public class TearOfTheGoddess extends MobaItem{
	public TearOfTheGoddess() {
		super("TearOfTheGoddess", 375,InventoryShop.SapphireCrystal,InventoryShop.FaerieCharm);
	}

	@Override
	public void getAktiv(MobaPlayer p) {
		
	}

	@Override
	public void getPassive(MobaPlayer p) {
		
	}

	@Override
	public float getMana(MobaPlayer p) {
		return 250;
	}

	


}
