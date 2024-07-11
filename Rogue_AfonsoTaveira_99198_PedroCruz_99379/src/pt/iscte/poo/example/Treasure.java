package pt.iscte.poo.example;

import pt.iscte.poo.utils.Point2D;

public class Treasure extends GameElements {
	
	private int POINTS = 20;

	public Treasure(Point2D position) {
		super(position);
	}

	@Override
	public String getName() {
		return "Treasure";
	}

	@Override
	public int getLayer() {
		return 1;
	}
}
