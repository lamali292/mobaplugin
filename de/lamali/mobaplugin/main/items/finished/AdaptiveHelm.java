package de.lamali.mobaplugin.main.items.finished;

import de.lamali.mobaplugin.main.InventoryShop;
import de.lamali.mobaplugin.main.items.MobaItem;
import de.lamali.mobaplugin.mobaobjects.MobaPlayer;

public class AdaptiveHelm extends MobaItem{
	public AdaptiveHelm() {
		super("AdaptiveHelm", 1000, InventoryShop.NullMagicMantle, InventoryShop.SpectresCowl, InventoryShop.RejuvenationBead);
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
		return 350;
	}

	@Override
	public float getMagicRes(MobaPlayer p) {
		// TODO Auto-generated method stub
		return 55;
	}

	@Override
	public float getHealthRegen(MobaPlayer p) {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public float getCooldownReduction(MobaPlayer p) {
		// TODO Auto-generated method stub
		return 0.1F;
	}

	


}
