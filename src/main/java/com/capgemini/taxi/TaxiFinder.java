package com.capgemini.taxi;

import java.util.ArrayList;
import java.util.List;

public class TaxiFinder {
	private static final String ID_ALREADY_EXISTS = "Id already exists";
	private static final String TAXI_NOT_EXISTS = "Taxi not exists";
	
	private List<Taxi> taxis = new ArrayList<>();

	public void displayTaxis() {
		System.out.println("All taxis:");
		for (Taxi taxi : taxis) {
			System.out.println(taxi);
		}
	}
	
	public void displayAvailableTaxis() {
		System.out.println("Available taxis:");
		for (Taxi taxi : getAvailableTaxis()) {
			System.out.println(taxi);
		}
	}

	public void addNewTaxi(int id, double posX, double posY, boolean available) throws Exception {
		for (Taxi taxi : taxis) {
			if(taxi.getId() == id) {
				throw new Exception(ID_ALREADY_EXISTS + ": " + id);
			}
		}
		taxis.add(new Taxi(id, posX, posY, available));
	}

	public void update(int id, double newPosX, double newPosY, boolean isAvailable) throws Exception {
		Taxi taxi = findTaxi(id);
		taxi.setPosX(newPosX);
		taxi.setPosY(newPosY);
		taxi.setAvailable(isAvailable);
	}
	
	public void displayAvailableAndNearbyTaxis(double posX, double posY, double maxDistance) {
		List<Taxi> nearbyTaxis = getNearbyTaxis(posX, posY, maxDistance);
		
		System.out.println("Nearby taxis: ");
		for (Taxi taxi : nearbyTaxis) {
			System.out.println(taxi);
		}
		if(nearbyTaxis == null || nearbyTaxis.isEmpty()) {
			System.out.println("\tThere is no available taxis in given radius");
		}
	}
	
	private List<Taxi> getAvailableTaxis() {
		List<Taxi> availableTaxis = new ArrayList<>();
		for (Taxi taxi : taxis) {
			if (taxi.isAvailable())
				availableTaxis.add(taxi);
		}
		return availableTaxis;
	}

	private Taxi findTaxi(int id) throws Exception {
		for (Taxi taxi : taxis) {
			if(taxi.getId() == id) {
				return taxi;
			}
		}
		throw new Exception(TAXI_NOT_EXISTS + ": " + id);
	}

	private List<Taxi> getNearbyTaxis(double posX, double posY, double maxDistance) {
		List<Taxi> nearbyTaxis = new ArrayList<>();
		for (Taxi taxi : getAvailableTaxis()) {
			double distanceX = taxi.getPosX() - posX;
			double distanceY = taxi.getPosY() - posY;
			
			if(isCloseEnough(distanceX, distanceY, maxDistance)) {
				nearbyTaxis.add(taxi);
			}
		}
		return nearbyTaxis;
	}

	private boolean isCloseEnough(double distanceX, double distanceY, double maxDistance) {
		return Math.pow(distanceX, 2) + Math.pow(distanceY, 2) < Math.pow(maxDistance, 2);
	}
}
