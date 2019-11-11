package de.lamali.mobaplugin.main.items.basic;

import de.lamali.mobaplugin.main.items.MobaItem;
import de.lamali.mobaplugin.mobaobjects.MobaPlayer;

public class RejuvenationBead extends MobaItem{
	public RejuvenationBead() {
		super("RejuvenationBead", 150);
	}

	@Override
	public void getAktiv(MobaPlayer p) {
		
	}

	@Override
	public void getPassive(MobaPlayer p) {
		
	}

	@Override
	public float getHealthRegen(MobaPlayer p) {
		return 0.5F;
	}

	

}
