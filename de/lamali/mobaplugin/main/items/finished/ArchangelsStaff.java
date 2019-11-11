package de.lamali.mobaplugin.main.items.finished;

import de.lamali.mobaplugin.main.InventoryShop;
import de.lamali.mobaplugin.main.items.MobaItem;
import de.lamali.mobaplugin.mobaobjects.MobaPlayer;

public class ArchangelsStaff extends MobaItem{
	public ArchangelsStaff() {
		super("ArchangelsStaff", 1050, InventoryShop.TearOfTheGoddess, InventoryShop.LostChapter);
	}


	@Override
	public void getAktiv(MobaPlayer p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getPassive(MobaPlayer p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getAbilityPower(MobaPlayer p) {
		return 50;
	}

	@Override
	public float getCooldownReduction(MobaPlayer p) {
		return 0.1F;
	}

	@Override
	public float getMana(MobaPlayer p) {
		return 650;
	}



}
