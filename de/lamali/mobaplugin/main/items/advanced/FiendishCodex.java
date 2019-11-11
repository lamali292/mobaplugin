package de.lamali.mobaplugin.main.items.advanced;

import de.lamali.mobaplugin.main.InventoryShop;
import de.lamali.mobaplugin.main.items.MobaItem;
import de.lamali.mobaplugin.mobaobjects.MobaPlayer;

public class FiendishCodex extends MobaItem{
	public FiendishCodex() {
		super("FiendishCodex", 465, InventoryShop.AmplifyingTome);
	}

	@Override
	public void getAktiv(MobaPlayer p) {
		
	}

	@Override
	public void getPassive(MobaPlayer p) {
		
	}

	@Override
	public float getAbilityPower(MobaPlayer p) {
		return 35;
	}

	@Override
	public float getCooldownReduction(MobaPlayer p) {
		return 0.1F;
	}



}
