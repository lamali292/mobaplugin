package de.lamali.mobaplugin.mobaobjects.monster;

import de.lamali.mobaplugin.mobaobjects.Camp;
import de.lamali.mobaplugin.mobaobjects.Monster;

public class Krug extends Monster{

	public Camp camp;

	public Krug(String subgroup, float x, float y, float z, float yaw, Camp camp) {
		super("krug", subgroup, x, y, z,yaw,camp);
		this.camp = camp;
		// TODO Auto-generated constructor stub
	}

}
