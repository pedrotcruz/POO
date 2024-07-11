package pt.iscte.poo.example;

import pt.iscte.poo.utils.Point2D;

public class Armor extends Catchables {

	public Armor(Point2D position) {
		super(position);
	}

	@Override
	public String getName() {
		return "Armor";
	}

	@Override
	public int getLayer() {
		return 1;
	}
	
	@Override
	public void apanhar() {
		super.apanhar();
		for(GameElements ge : GameEngine.getInstance().getGameElement(GameEngine.getInstance().getHero().getPosition())) {
			if(ge instanceof MovableElements) {
				((MovableElements) ge).setArmor(1);
			}
		}
	}
	
	@Override
	public void drop(int key) {
		super.drop(key);
		for(GameElements ge : GameEngine.getInstance().getGameElement(GameEngine.getInstance().getHero().getPosition())) {
			if(ge instanceof MovableElements) { 
				((MovableElements) ge).setArmor(0);
			}
		}
	}
}
