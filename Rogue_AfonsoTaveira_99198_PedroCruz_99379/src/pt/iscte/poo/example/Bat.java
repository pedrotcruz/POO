package pt.iscte.poo.example;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class Bat extends MovableElements {
	
	private int POINTS = 5;

	public Bat(Point2D position) {
		super(position);
		this.setVida(3);
		this.setDano(1);
	}

	@Override
	public String getName() {
		return "Bat";
	}

	@Override
	public int getLayer() {
		return 1;
	}

	@Override
	public void move() {
		Point2D newPosition = null;
		if (Math.random() <= 0.5) {
			super.move();
		} else {
			Direction dir = Direction.random();
			Vector2D vec = dir.asVector();
			newPosition = getPosition().plus(vec);
			if (isWalkable(newPosition)) {
				this.setPosition(newPosition);
			} else {
				newPosition = getPosition();
			}
		}
	}

	@Override
	public void attack() {
		if (Math.random() <= 0.5) {
			super.attack();
			if (this.getVida() < 3) {
				this.setVida(getVida() + 1);
			}
		}
	}

	public int getPOINTS() {
		return POINTS;
	}
	
	
}
