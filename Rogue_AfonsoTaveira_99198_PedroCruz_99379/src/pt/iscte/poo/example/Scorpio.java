package pt.iscte.poo.example;

import pt.iscte.poo.utils.Point2D;

public class Scorpio extends MovableElements {
	
	private int POINTS = 10;


	public Scorpio(Point2D position) {
		super(position);
		this.setVida(2);
		this.setDano(1);
	}

	@Override
	public String getName() {
		return "Scorpio";
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
		super.attack();
		GameEngine.getInstance().getHero().setPoisoned(true);
	}
	
	public int getPOINTS() {
		return POINTS;
	}
}
