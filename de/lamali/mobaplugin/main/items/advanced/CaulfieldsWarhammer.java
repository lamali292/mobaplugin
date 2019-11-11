package de.lamali.mobaplugin.main.items.advanced;

import de.lamali.mobaplugin.main.InventoryShop;
import de.lamali.mobaplugin.main.items.MobaItem;
import de.lamali.mobaplugin.mobaobjects.MobaPlayer;

public class CaulfieldsWarhammer extends MobaItem{
	public CaulfieldsWarhammer() {
		super("CaulfieldsWarhammer", 400, InventoryShop.LongSword,InventoryShop.LongSword);
	}

	@Override
	public void getAktiv(MobaPlayer p) {
		
	}

	@Override
	public void getPassive(MobaPlayer p) {
		
	}

	@Override
	public float getAttackDamage(MobaPlayer p) {
		return 25;
	}

	@Override
	public float getCooldownReduction(MobaPlayer p) {
		return 0.1F;
	}

	

}
