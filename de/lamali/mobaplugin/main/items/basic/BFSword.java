package de.lamali.mobaplugin.main.items.basic;

import de.lamali.mobaplugin.main.items.MobaItem;
import de.lamali.mobaplugin.mobaobjects.MobaPlayer;

public class BFSword extends MobaItem{
	public BFSword() {
		super("BFSword", 1300);
	}

	@Override
	public void getAktiv(MobaPlayer p) {
		
	}

	@Override
	public void getPassive(MobaPlayer p) {
		
	}


	@Override
	public float getAttackDamage(MobaPlayer p) {
		return 40;
	}




}
