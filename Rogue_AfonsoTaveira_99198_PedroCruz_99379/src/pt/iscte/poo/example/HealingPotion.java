package pt.iscte.poo.example;

import pt.iscte.poo.utils.Point2D;

public class HealingPotion extends Catchables {

	public HealingPotion(Point2D position) {
		super(position);
	}

	@Override
	public String getName() {
		return "HealingPotion";
	}

	@Override
	public int getLayer() {
		return 1;
	}

	@Override
	public void apanhar() {
		super.apanhar();
		Hero hero = GameEngine.getInstance().getHero();
		GameEngine.getInstance().removeGameElement(this);
		if (hero.getVida() >= 5)
			hero.setVida(10);
		else
			hero.setVida(hero.getVida() + 5);
	}

	@Override
	public void drop(int key) {
		super.drop(key);
	}
}
