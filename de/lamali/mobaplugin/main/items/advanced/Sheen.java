package de.lamali.mobaplugin.main.items.advanced;

import de.lamali.mobaplugin.main.InventoryShop;
import de.lamali.mobaplugin.main.items.MobaItem;
import de.lamali.mobaplugin.mobaobjects.MobaPlayer;

public class Sheen extends MobaItem{
	public Sheen() {
		super("Sheen", 700, InventoryShop.SapphireCrystal);
	}

	@Override
	public void getAktiv(MobaPlayer p) {
		
	}

	@Override
	public void getPassive(MobaPlayer p) {
		
	}

	@Override
	public float getMana(MobaPlayer p) {
		return 250;
	}

	@Override
	public float getCooldownReduction(MobaPlayer p) {
		return 0.1F;
	}




}
