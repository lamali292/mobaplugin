package de.lamali.mobaplugin.main;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public class Lane {
	private Location[] path;
	private Vector[] vectorpath;

	public Lane(Location[] path) {
		this.path = path;
		this.vectorpath = new Vector[path.length - 1];
		for (int i = 0; i < path.length - 1; i++) {
			Location firstPoint = path[i].clone();
			Location secondPoint = path[i + 1].clone();
			vectorpath[i] = secondPoint.subtract(firstPoint).toVector();
		}
	}

	public Location getNearestLocation(Location minion) {
		double nearestDis = 10000;
		Location nearestLocation = null;
		for (int i = 0; i < path.length - 1; i++) {
			//minion.getWorld().spawnParticle(Particle.BARRIER, path[i + 1].getX(), path[i + 1].getY(), path[i + 1].getZ(),1);

			Vector supVector = path[i].toVector();
			Vector dirVector = vectorpath[i].clone();

			double e = dirVector.getX() * minion.toVector().getX() + dirVector.getY() * minion.toVector().getY()
					+ dirVector.getZ() * minion.toVector().getZ();
			double x = dirVector.getX();
			double y = dirVector.getY();
			double z = dirVector.getZ();
			double r = (e - (x * supVector.getX() + y * supVector.getY() + z * supVector.getZ()))
					/ (dirVector.getX() * x + dirVector.getY() * y + dirVector.getZ() * z);

			Location p;

			if(r<=1&&r>=0){
				p = supVector.add(dirVector.multiply(r)).toLocation(minion.getWorld());
			}else{
				p = supVector.toLocation(minion.getWorld());
			}

			if (p.distance(minion) < nearestDis) {
				nearestLocation = p.clone();
				nearestDis = p.distance(minion);
			}
		}

		return nearestLocation;
	}

	public Location getNextPath(Location minion,String team) {
		Location nearestPath = null;
		for (int i = 0; i < path.length - 1; i++) {

			Vector supVector = path[i].toVector();
			Vector dirVector = vectorpath[i].clone();

			double e = dirVector.getX() * minion.toVector().getX() + dirVector.getY() * minion.toVector().getY()
					+ dirVector.getZ() * minion.toVector().getZ();
			double x = dirVector.getX();
			double y = dirVector.getY();
			double z = dirVector.getZ();
			double r = (e - (x * supVector.getX() + y * supVector.getY() + z * supVector.getZ()))
					/ (dirVector.getX() * x + dirVector.getY() * y + dirVector.getZ() * z);

			if(r<=1&&r>=0){
				if(team.equals("blue")){
					nearestPath = path[i+1];
				}else if(team.equals("red")){
					nearestPath = path[i];
				}
			}

		}

		return nearestPath;
	}
}
