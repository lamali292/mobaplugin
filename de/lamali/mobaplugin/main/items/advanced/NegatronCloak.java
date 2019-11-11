package de.lamali.mobaplugin.main.items.advanced;

import de.lamali.mobaplugin.main.InventoryShop;
import de.lamali.mobaplugin.main.items.MobaItem;
import de.lamali.mobaplugin.mobaobjects.MobaPlayer;

public class NegatronCloak extends MobaItem{
	public NegatronCloak() {
		super("NegatronCloak", 270, InventoryShop.NullMagicMantle);
	}

	@Override
	public void getAktiv(MobaPlayer p) {
		
	}

	@Override
	public void getPassive(MobaPlayer p) {
		
	}

	@Override
	public float getMagicRes(MobaPlayer p) {
		return 40;
	}


}
