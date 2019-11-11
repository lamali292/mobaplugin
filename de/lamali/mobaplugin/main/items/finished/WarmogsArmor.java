package de.lamali.mobaplugin.main.items.finished;

import de.lamali.mobaplugin.main.InventoryShop;
import de.lamali.mobaplugin.main.items.MobaItem;
import de.lamali.mobaplugin.mobaobjects.MobaPlayer;

public class WarmogsArmor extends MobaItem{
	public WarmogsArmor() {
		super("WarmogsArmor", 400, InventoryShop.GiantsBelt, InventoryShop.Kindlegem, InventoryShop.CrystallineBracer);
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
		return 800;
	}

	@Override
	public float getHealthRegen(MobaPlayer p) {
		return 2;
	}

	@Override
	public float getCooldownReduction(MobaPlayer p) {
		return 0.1F;
	}

	

}
