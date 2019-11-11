package de.lamali.mobaplugin.main.items.finished;

import de.lamali.mobaplugin.main.InventoryShop;
import de.lamali.mobaplugin.main.items.MobaItem;
import de.lamali.mobaplugin.mobaobjects.MobaPlayer;

public class BlackCleaver extends MobaItem{
	public BlackCleaver() {
		super("BlackCleaver", 950, InventoryShop.Phage, InventoryShop.Kindlegem);
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
		return 400;
	}

	@Override
	public float getAttackDamage(MobaPlayer p) {
		return 40;
	}

	@Override
	public float getCooldownReduction(MobaPlayer p) {
		return 0.2F;
	}

	

}
