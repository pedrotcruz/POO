package pt.iscte.poo.example;

import pt.iscte.poo.utils.Point2D;

public class Thug extends MovableElements {
	
	private int POINTS = 10;


	public Thug(Point2D position) {
		super(position);
		this.setVida(10);
		this.setDano(3);
	}

	@Override
	public String getName() {
		return "Thug";
	}

	@Override
	public int getLayer() {
		return 1;
	}
	
	@Override
	public void move() {
		super.move();
	}

	@Override
	public void attack() {
		if (Math.random() <= 0.3) {
			super.attack();
		}
	}
	
	public int getPOINTS() {
		return POINTS;
	}
}
