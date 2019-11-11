package de.lamali.mobaplugin.main.items.advanced;

import de.lamali.mobaplugin.main.InventoryShop;
import de.lamali.mobaplugin.main.items.MobaItem;
import de.lamali.mobaplugin.mobaobjects.MobaPlayer;

public class LostChapter extends MobaItem{
	public LostChapter() {
		super("LostChapter", 80, InventoryShop.AmplifyingTome,InventoryShop.SapphireCrystal,InventoryShop.AmplifyingTome);
	}

	@Override
	public void getAktiv(MobaPlayer p) {
		
	}

	@Override
	public void getPassive(MobaPlayer p) {
		
	}

	@Override
	public float getAbilityPower(MobaPlayer p) {
		return 40;
	}

	@Override
	public float getMana(MobaPlayer p) {
		return 300;
	}



}
