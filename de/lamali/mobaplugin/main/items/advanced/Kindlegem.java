package de.lamali.mobaplugin.main.items.advanced;

import de.lamali.mobaplugin.main.InventoryShop;
import de.lamali.mobaplugin.main.items.MobaItem;
import de.lamali.mobaplugin.mobaobjects.MobaPlayer;

public class Kindlegem extends MobaItem{
	public Kindlegem() {
		super("Kindlegem", 400, InventoryShop.RubyCrystal);
	}

	@Override
	public void getAktiv(MobaPlayer p) {
		
	}

	@Override
	public void getPassive(MobaPlayer p) {
		
	}

	@Override
	public float getHealth(MobaPlayer p) {
		return 200;
	}

	@Override
	public float getCooldownReduction(MobaPlayer p) {
		return 0.1F;
	}

	

}
