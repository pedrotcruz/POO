package pt.iscte.poo.example;

import java.util.List;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public abstract class GameElements implements ImageTile {
	private Point2D position;

	public GameElements(Point2D position) {
		this.position = position;
	}

	public static GameElements create(String tipo, Point2D position, String st1, Point2D newPosition, String st2) {
		switch (tipo) {
		case " ":
			return new Floor(position);
		case "#":
			return new Wall(position);
		case "Skeleton":
			return new Skeleton(position);
		case "Bat":
			return new Bat(position);
		case "Key":
			return new Key(position, st1);
		case "Door":
			return new Door(position, st1, newPosition, st2);
		case "Sword":
			return new Sword(position);
		case "Thug":
			return new Thug(position);
		case "Armor":
			return new Armor(position);
		case "HealingPotion":
			return new HealingPotion(position);
		case "Treasure":
			return new Treasure(position);
		case "Scorpio":
			return new Scorpio(position);

		}
		return null;
	}

	public Point2D getPosition() {
		return position;
	}

	public void setPosition(Point2D position) {
		this.position = position;
	}

	public boolean isWalkable(Point2D position) {
		List<GameElements> gameElementsPos = GameEngine.getInstance().getGameElement(position);
		if(position.getX() < 0 || position.getX() > 9 || position.getY() < 0 || position.getY() > 9) {
			return false;
		}
		if (gameElementsPos.get(0).getLayer() == 0
				&& (gameElementsPos.size() == 1)) {
			return true;
		}
		return false;
	}

}
