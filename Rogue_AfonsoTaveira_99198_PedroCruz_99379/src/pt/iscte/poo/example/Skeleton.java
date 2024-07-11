package pt.iscte.poo.example;

import pt.iscte.poo.utils.Point2D;

public class Skeleton extends MovableElements {
	
	private int POINTS = 8;

	public Skeleton(Point2D position) {
		super(position);
		this.setVida(5);
		this.setDano(1);
	}

	@Override
	public String getName() {
		return "Skeleton";
	}

	@Override
	public int getLayer() {
		return 1;
	}

	@Override
	public void move() {
		if (GameEngine.getInstance().getTurns() % 2 != 0) {
			super.move();
		}
	}

	public int getPOINTS() {
		return POINTS;
	}
	
	
}
