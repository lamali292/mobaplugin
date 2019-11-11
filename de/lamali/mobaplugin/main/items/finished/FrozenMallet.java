package de.lamali.mobaplugin.main.items.finished;

import de.lamali.mobaplugin.main.InventoryShop;
import de.lamali.mobaplugin.main.items.MobaItem;
import de.lamali.mobaplugin.mobaobjects.MobaPlayer;

public class FrozenMallet extends MobaItem{
	public FrozenMallet() {
		super("FrozenMallet", 900, InventoryShop.JaurimsFist, InventoryShop.GiantsBelt);
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
		return 700;
	}

	@Override
	public float getAttackDamage(MobaPlayer p) {
		return 30;
	}

	

}
