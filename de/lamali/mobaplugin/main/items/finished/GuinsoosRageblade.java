package de.lamali.mobaplugin.main.items.finished;

import de.lamali.mobaplugin.main.InventoryShop;
import de.lamali.mobaplugin.main.items.MobaItem;
import de.lamali.mobaplugin.mobaobjects.MobaPlayer;

public class GuinsoosRageblade extends MobaItem{
	public GuinsoosRageblade() {
		super("GuinsoosRageblade", 790, InventoryShop.AmplifyingTome,InventoryShop.RecurveBow,InventoryShop.Pickaxe);
	}

	@Override
	public void getAktiv(MobaPlayer p) {
		
	}

	@Override
	public void getPassive(MobaPlayer p) {
		
	}

	@Override
	public float getAttackDamage(MobaPlayer p) {
		return 25;
	}

	@Override
	public float getAttackSpeed(MobaPlayer p) {
		return 0.25F;
	}

	@Override
	public float getAbilityPower(MobaPlayer p) {
		return 25;
	}


}
