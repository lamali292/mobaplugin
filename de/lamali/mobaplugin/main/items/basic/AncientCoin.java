package de.lamali.mobaplugin.main.items.basic;

import de.lamali.mobaplugin.main.items.MobaItem;
import de.lamali.mobaplugin.mobaobjects.MobaPlayer;

public class AncientCoin extends MobaItem{
	public AncientCoin() {
		super("AncientCoin", 400);
	}

	@Override
	public void getAktiv(MobaPlayer p) {
		
	}

	@Override
	public void getPassive(MobaPlayer p) {
		
	}

	public float getCooldownReduction(MobaPlayer p){
		return 0.05F;
	}
	
	public float getMovement(MobaPlayer p){
		return 5;
	}


}
