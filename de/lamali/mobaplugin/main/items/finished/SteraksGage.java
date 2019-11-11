package de.lamali.mobaplugin.main.items.finished;

import de.lamali.mobaplugin.main.InventoryShop;
import de.lamali.mobaplugin.main.items.MobaItem;
import de.lamali.mobaplugin.mobaobjects.MobaPlayer;

public class SteraksGage extends MobaItem{
	public SteraksGage() {
		super("SteraksGage", 725, InventoryShop.JaurimsFist, InventoryShop.Pickaxe, InventoryShop.RubyCrystal);
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

	

}
