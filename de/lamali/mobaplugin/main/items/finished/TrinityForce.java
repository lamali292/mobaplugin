package de.lamali.mobaplugin.main.items.finished;

import de.lamali.mobaplugin.main.InventoryShop;
import de.lamali.mobaplugin.main.items.MobaItem;
import de.lamali.mobaplugin.mobaobjects.MobaPlayer;

public class TrinityForce extends MobaItem{
	public TrinityForce() {
		super("TrinityForce", 333, InventoryShop.Stinger, InventoryShop.Sheen, InventoryShop.Phage);
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
	public float getHealth(MobaPlayer p) {
		return 250;
	}

	@Override
	public float getMana(MobaPlayer p) {
		return 250;
	}
	
	@Override
	public float getMovement(MobaPlayer p) {
		if(p!=null){
		return 0.05F*p.baseMovementSpeed;
		}
		return 1;
	}
	
	@Override
	public float getCooldownReduction(MobaPlayer p) {
		return 0.2F;
	}

	@Override
	public float getAttackSpeed(MobaPlayer p) {
		return 0.4F;
	}
	
	@Override
	public float getAttackDamage(MobaPlayer p) {
		return 25;
	}
	


}
