package de.lamali.mobaplugin.main.items.advanced;

import de.lamali.mobaplugin.main.InventoryShop;
import de.lamali.mobaplugin.main.items.MobaItem;
import de.lamali.mobaplugin.mobaobjects.MobaPlayer;

public class BrambleVest extends MobaItem{
	public BrambleVest() {
		super("BrambleVest", 400, InventoryShop.ClothArmor,InventoryShop.ClothArmor);
	}

	@Override
	public void getAktiv(MobaPlayer p) {
		
	}

	@Override
	public void getPassive(MobaPlayer p) {
		
	}

	@Override
	public float getArmor(MobaPlayer p) {
		return 35;
	}


}
