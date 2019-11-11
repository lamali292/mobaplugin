package de.lamali.mobaplugin.main.items.basic;

import de.lamali.mobaplugin.main.items.MobaItem;
import de.lamali.mobaplugin.mobaobjects.MobaPlayer;

public class SapphireCrystal extends MobaItem{
	public SapphireCrystal() {
		super("SapphireCrystal", 350);
	}

	@Override
	public void getAktiv(MobaPlayer p) {
		
	}

	@Override
	public void getPassive(MobaPlayer p) {
		
	}

	@Override
	public float getMana(MobaPlayer p) {
		return 250;
	}

	

}
