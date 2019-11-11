package de.lamali.mobaplugin.main.items.basic;

import de.lamali.mobaplugin.main.items.MobaItem;
import de.lamali.mobaplugin.mobaobjects.MobaPlayer;

public class NullMagicMantle extends MobaItem{
	public NullMagicMantle() {
		super("NullMagicMantle", 450);
	}

	@Override
	public void getAktiv(MobaPlayer p) {
		
	}

	@Override
	public void getPassive(MobaPlayer p) {
		
	}

	@Override
	public float getMagicRes(MobaPlayer p) {
		return 25;
	}

	
}
