package de.lamali.mobaplugin.main.items.basic;

import de.lamali.mobaplugin.main.items.MobaItem;
import de.lamali.mobaplugin.mobaobjects.MobaPlayer;

public class DarkSeal extends MobaItem{
	public DarkSeal() {
		super("DarkSeal", 300);
	}

	@Override
	public void getAktiv(MobaPlayer p) {
		
	}

	@Override
	public void getPassive(MobaPlayer p) {
		
	}

	public float getAbilityPower(MobaPlayer p){
		return 10;
	}

	public float getMana(MobaPlayer p){
		return 100;
	}
}
