package de.lamali.mobaplugin.main.items.basic;

import de.lamali.mobaplugin.main.items.MobaItem;
import de.lamali.mobaplugin.mobaobjects.MobaPlayer;

public class SpellthiefsEdge extends MobaItem{
	public SpellthiefsEdge() {
		super("SpellthiefsEdge", 400);
	}

	@Override
	public void getAktiv(MobaPlayer p) {
		
	}

	@Override
	public void getPassive(MobaPlayer p) {
		
	}

	@Override
	public float getAbilityPower(MobaPlayer p) {
		return 10;
	}
	
	@Override
	public float getManaRegen(MobaPlayer p) {
		return 0.25F;
	}

	

}
