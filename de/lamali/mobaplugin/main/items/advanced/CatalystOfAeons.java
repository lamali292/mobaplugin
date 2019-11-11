package de.lamali.mobaplugin.main.items.advanced;

import de.lamali.mobaplugin.main.InventoryShop;
import de.lamali.mobaplugin.main.items.MobaItem;
import de.lamali.mobaplugin.mobaobjects.MobaPlayer;

public class CatalystOfAeons extends MobaItem{
	public CatalystOfAeons() {
		super("CatalystOfAeons", 350, InventoryShop.RubyCrystal,InventoryShop.SapphireCrystal);
	}

	@Override
	public void getAktiv(MobaPlayer p) {
		
	}

	@Override
	public void getPassive(MobaPlayer p) {
		
	}

	@Override
	public float getHealth(MobaPlayer p) {
		return 225;
	}

	@Override
	public float getMana(MobaPlayer p) {
		return 300;
	}


}
