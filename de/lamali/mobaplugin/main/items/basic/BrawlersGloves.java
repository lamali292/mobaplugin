package de.lamali.mobaplugin.main.items.basic;

import de.lamali.mobaplugin.main.items.MobaItem;
import de.lamali.mobaplugin.mobaobjects.MobaPlayer;

public class BrawlersGloves extends MobaItem{
	public BrawlersGloves() {
		super("BrawlersGloves", 400);
	}

	@Override
	public void getAktiv(MobaPlayer p) {
		
	}

	@Override
	public void getPassive(MobaPlayer p) {
		
	}

	@Override
	public float getCriticalStrike(MobaPlayer p) {
		return 0.1F;
	}



}
