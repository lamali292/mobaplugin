package de.lamali.mobaplugin.main.items.advanced;

import de.lamali.mobaplugin.main.InventoryShop;
import de.lamali.mobaplugin.main.items.MobaItem;
import de.lamali.mobaplugin.mobaobjects.MobaPlayer;

public class SpectresCowl extends MobaItem{
	public SpectresCowl() {
		super("SpectresCowl", 350, InventoryShop.NullMagicMantle,InventoryShop.RubyCrystal);
	}

	@Override
	public void getAktiv(MobaPlayer p) {
		
	}

	@Override
	public void getPassive(MobaPlayer p) {
		
	}

	@Override
	public float getHealth(MobaPlayer p) {
		return 250;
	}

	@Override
	public float getMagicRes(MobaPlayer p) {
		return 25;
	}

	@Override
	public float getMovement(MobaPlayer p) {
		if(p!=null){
			return 0.05F*p.baseMovementSpeed;
		}
		return 1;
	}



}
