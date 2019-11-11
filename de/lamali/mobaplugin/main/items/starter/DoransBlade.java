package de.lamali.mobaplugin.main.items.starter;

import de.lamali.mobaplugin.main.items.MobaItem;
import de.lamali.mobaplugin.mobaobjects.MobaPlayer;

public class DoransBlade extends MobaItem{
	public DoransBlade() {
		super("DoransBlade", 450);
	}

	@Override
	public void getAktiv(MobaPlayer p) {
		
	}

	@Override
	public void getPassive(MobaPlayer p) {
		
	}

	@Override
	public float getHealth(MobaPlayer p) {
		return 80;
	}

	@Override
	public float getAttackDamage(MobaPlayer p) {
		return 8;
	}

	@Override
	public float getLifeSteal(MobaPlayer p) {
		return 0.03F;
	}


}
