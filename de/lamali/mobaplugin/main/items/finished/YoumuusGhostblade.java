package de.lamali.mobaplugin.main.items.finished;

import de.lamali.mobaplugin.main.InventoryShop;
import de.lamali.mobaplugin.main.items.MobaItem;
import de.lamali.mobaplugin.mobaobjects.MobaPlayer;

public class YoumuusGhostblade extends MobaItem{
	public YoumuusGhostblade() {
		super("YoumuusGhostblade", 700, InventoryShop.SerratedDirk,InventoryShop.CaulfieldsWarhammer);
	}

	@Override
	public void getAktiv(MobaPlayer p) {
		
	}

	@Override
	public void getPassive(MobaPlayer p) {
		
	}

	@Override
	public float getAttackDamage(MobaPlayer p) {
		return 55;
	}

	@Override
	public float getCooldownReduction(MobaPlayer p) {
		return 0.1F;
	}

	

}
