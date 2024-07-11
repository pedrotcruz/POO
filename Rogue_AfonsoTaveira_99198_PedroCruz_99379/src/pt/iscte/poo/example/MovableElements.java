package pt.iscte.poo.example;

import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public abstract class MovableElements extends GameElements {

	private int HITPOINTS = 0;
	private int DAMAGE = 0;
	private int armor = 0;
	private int POINTS;

	public MovableElements(Point2D position) {
		super(position);
	}

	public int getPOINTS() {
		return POINTS;
	}

	public int getDano() {
		return DAMAGE;
	}

	public int getVida() {
		return HITPOINTS;
	}

	public void setVida(int hITPOINTS) {
		this.HITPOINTS = hITPOINTS;
	}

	public void setDano(int dAMAGE) {
		this.DAMAGE = dAMAGE;
	}
	
	public void setArmor(int armor) {
		this.armor = armor;
	}

	public void move() {
		Vector2D vec;
		boolean hasMoved = false;
		Point2D newPosition;
		if (hasMoved == false) {
			vec = Vector2D.movementVector(getPosition(), GameEngine.getInstance().getHero().getPosition());
			newPosition = getPosition().plus(vec);
			if (isWalkable(newPosition)) {
				this.setPosition(newPosition);
				hasMoved = true;
			} else {
				if (GameEngine.getInstance().getGameElement(newPosition).size() == 2
						&& GameEngine.getInstance().getGameElement(newPosition).get(0) instanceof Hero) {
					attack();
				}
				newPosition = getPosition();
				hasMoved = true;
			}
		}
	}

	public void attack() {
		Hero hero = GameEngine.getInstance().getHero();
		if(armor == 1) {
			if(Math.random() <= 0.5) {
				hero.setVida(hero.getVida() - this.DAMAGE);
			}
		}
		if(armor == 0) {
				hero.setVida(hero.getVida() - this.DAMAGE);
		}
		
	}
}
