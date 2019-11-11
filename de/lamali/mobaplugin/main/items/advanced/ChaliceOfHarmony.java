package de.lamali.mobaplugin.main.items.advanced;

import de.lamali.mobaplugin.main.InventoryShop;
import de.lamali.mobaplugin.main.items.MobaItem;
import de.lamali.mobaplugin.mobaobjects.MobaPlayer;

public class ChaliceOfHarmony extends MobaItem{
	public ChaliceOfHarmony() {
		super("ChaliceOfHarmony", 100, InventoryShop.FaerieCharm,InventoryShop.NullMagicMantle, InventoryShop.FaerieCharm);
	}

	@Override
	public void getAktiv(MobaPlayer p) {
		
	}

	@Override
	public void getPassive(MobaPlayer p) {
		
	}

	@Override
	public float getMagicRes(MobaPlayer p) {
		return 30;
	}

	@Override
	public float getManaRegen(MobaPlayer p) {
		return 0.5F;
	}


}
