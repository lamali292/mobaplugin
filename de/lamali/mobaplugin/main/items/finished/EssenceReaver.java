package de.lamali.mobaplugin.main.items.finished;

import de.lamali.mobaplugin.main.InventoryShop;
import de.lamali.mobaplugin.main.items.MobaItem;
import de.lamali.mobaplugin.mobaobjects.MobaPlayer;

public class EssenceReaver extends MobaItem{
	public EssenceReaver() {
		super("EssenceReaver", 100, InventoryShop.BFSword, InventoryShop.CaulfieldsWarhammer, InventoryShop.CloakOfAgility);
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
	public float getAttackDamage(MobaPlayer p) {
		return 70;
	}

	@Override
	public float getCriticalStrike(MobaPlayer p) {
		return 0.25F;
	}

	@Override
	public float getCooldownReduction(MobaPlayer p) {
		return 0.2F;
	}

	

}
