package de.lamali.mobaplugin.main.items.basic;

import de.lamali.mobaplugin.main.items.MobaItem;
import de.lamali.mobaplugin.mobaobjects.MobaPlayer;

public class BlastingWand extends MobaItem{
	public BlastingWand() {
		super("BlastingWand", 850);
	}

	@Override
	public void getAktiv(MobaPlayer p) {
		
	}

	@Override
	public void getPassive(MobaPlayer p) {
		
	}


	@Override
	public float getAbilityPower(MobaPlayer p) {
		return 40;
	}



}
