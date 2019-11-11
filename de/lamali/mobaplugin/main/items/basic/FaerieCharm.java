package de.lamali.mobaplugin.main.items.basic;

import de.lamali.mobaplugin.main.items.MobaItem;
import de.lamali.mobaplugin.mobaobjects.MobaPlayer;

public class FaerieCharm extends MobaItem{
	public FaerieCharm() {
		super("FaerieCharm", 125);
	}

	@Override
	public void getAktiv(MobaPlayer p) {
		
	}

	@Override
	public void getPassive(MobaPlayer p) {
		
	}

	@Override
	public float getManaRegen(MobaPlayer p) {
		return 0.25F;
	}

	
}
