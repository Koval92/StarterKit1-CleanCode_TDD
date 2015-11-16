package com.capgemini.taxi;

public class Taxi {
	private int id;
	private double posX;
	private double posY;
	private boolean available;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getPosX() {
		return posX;
	}

	public void setPosX(double posX) {
		this.posX = posX;
	}

	public double getPosY() {
		return posY;
	}

	public void setPosY(double posY) {
		this.posY = posY;
	}

	public Taxi(int id, double posX, double posY, boolean availability) {
		this.id = id;
		this.posX = posX;
		this.posY = posY;
		this.available = availability;
	}

	@Override
	public String toString() {
		return "Taxi [id=" + id + ", posX=" + posX + ", posY=" + posY + ", availability=" + available + "]";
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

}
