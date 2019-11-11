package de.lamali.mobaplugin.main.items.advanced;

import de.lamali.mobaplugin.main.InventoryShop;
import de.lamali.mobaplugin.main.items.MobaItem;
import de.lamali.mobaplugin.mobaobjects.MobaPlayer;

public class VampiricScepter extends MobaItem{
	public VampiricScepter() {
		super("SpaceVampiricScepter", 550, InventoryShop.LongSword);
	}

	@Override
	public void getAktiv(MobaPlayer p) {
		
	}

	@Override
	public void getPassive(MobaPlayer p) {
		
	}

	@Override
	public float getAttackDamage(MobaPlayer p) {
		return 15;
	}

	@Override
	public float getLifeSteal(MobaPlayer p) {
		return 0.1F;
	}



}
