package pt.iscte.poo.example;

import pt.iscte.poo.utils.Point2D;

public class Sword extends Catchables {

	public Sword(Point2D position) {
		super(position);
	}

	@Override
	public String getName() {
		return "Sword";
	}

	@Override
	public int getLayer() {
		return 1;
	}

	@Override
	public void apanhar() {
		super.apanhar();
		GameEngine.getInstance().getHero().setDano(2);
	}

	@Override
	public void drop(int key) {
		super.drop(key);
		GameEngine.getInstance().getHero().setDano(1);
	}
}
