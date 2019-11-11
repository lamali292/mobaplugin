package de.lamali.mobaplugin.main.items.finished;

import de.lamali.mobaplugin.main.InventoryShop;
import de.lamali.mobaplugin.main.items.MobaItem;
import de.lamali.mobaplugin.mobaobjects.MobaPlayer;

public class AbyssalMask extends MobaItem{
	public AbyssalMask() {
		super("AbyssalMask", 1180, InventoryShop.CatalystOfAeons, InventoryShop.NegatronCloak);
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
		return 350;
	}

	@Override
	public float getMagicRes(MobaPlayer p) {
		return 55;
	}

	@Override
	public float getCooldownReduction(MobaPlayer p) {
		return 0.1F;
	}

	@Override
	public float getMana(MobaPlayer p) {
		return 300;
	}



	


}
