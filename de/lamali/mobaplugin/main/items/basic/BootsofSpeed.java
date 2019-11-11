package de.lamali.mobaplugin.main.items.basic;

import de.lamali.mobaplugin.main.items.MobaItem;
import de.lamali.mobaplugin.mobaobjects.MobaPlayer;

public class BootsofSpeed extends MobaItem{
	public BootsofSpeed() {
		super("BootsofSpeed", 300);
	}

	@Override
	public void getAktiv(MobaPlayer p) {
		
	}

	@Override
	public void getPassive(MobaPlayer p) {
		
	}

	@Override
	public float getMovement(MobaPlayer p) {
		return 25;
	}



}
