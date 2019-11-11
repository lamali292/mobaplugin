package de.lamali.mobaplugin.main.items.finished;

import de.lamali.mobaplugin.main.InventoryShop;
import de.lamali.mobaplugin.main.items.MobaItem;
import de.lamali.mobaplugin.mobaobjects.MobaPlayer;

public class TitanicHydra extends MobaItem{
	public TitanicHydra() {
		super("TitanicHydra", 575, InventoryShop.Tiamat, InventoryShop.RubyCrystal, InventoryShop.JaurimsFist);
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
		return 450;
	}

	@Override
	public float getHealthRegen(MobaPlayer p) {
		return 1;
	}

	@Override
	public float getAttackDamage(MobaPlayer p) {
		return 40;
	}

	

}
