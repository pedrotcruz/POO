package pt.iscte.poo.example;

import java.awt.event.KeyEvent;
import java.util.List;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class Hero extends MovableElements {
	
	private boolean poisoned;
	private int POINTS;

	public Hero(Point2D position) {
		super(position);
		this.setVida(10);
		this.setDano(1);
	}

	@Override
	public String getName() {
		return "Hero";
	}

	public void move(int key) {
		Direction dir = null;
		Vector2D vec;
		boolean hasMoved = false;
		Point2D newPosition;
		while (hasMoved == false) {
			switch (key) {
			case KeyEvent.VK_DOWN:
				dir = Direction.DOWN;
				break;
			case KeyEvent.VK_UP:
				dir = Direction.UP;
				break;
			case KeyEvent.VK_LEFT:
				dir = Direction.LEFT;
				break;
			case KeyEvent.VK_RIGHT:
				dir = Direction.RIGHT;
				break;
			}
			vec = dir.asVector();
			newPosition = getPosition().plus(vec);
			List<GameElements> gameElementsNewPosition = GameEngine.getInstance().getGameElement(newPosition);
			if (gameElementsNewPosition.size() == 2 && gameElementsNewPosition.get(1) instanceof MovableElements) {
				((MovableElements) gameElementsNewPosition.get(1))
						.setVida(((MovableElements) gameElementsNewPosition.get(1)).getVida() - this.getDano());
				if (((MovableElements) gameElementsNewPosition.get(1)).getVida() <= 0) {
					POINTS += ((MovableElements) gameElementsNewPosition.get(1)).getPOINTS();
					((MovableElements) gameElementsNewPosition.get(1)).getPOINTS();
					GameEngine.getInstance().removeGameElement(gameElementsNewPosition.get(1));
				}
			}
			if (isWalkable(newPosition) || 
					(gameElementsNewPosition.size() == 2 && gameElementsNewPosition.get(1) instanceof Door && ((Door) gameElementsNewPosition.get(1)).truzTruz()) || 
					(gameElementsNewPosition.size() == 2 && gameElementsNewPosition.get(1) instanceof Treasure)) {
				this.setPosition(newPosition);
				hasMoved = true;
			}
			for(GameElements cat : gameElementsNewPosition) {
				if(cat instanceof Catchables) {
					this.setPosition(newPosition);
					hasMoved = true;
					List<GameElements> gameElementsHeroPosition = GameEngine.getInstance().getGameElement(getPosition());
					for(GameElements obj : gameElementsHeroPosition) {
						if(obj instanceof Catchables) {
							((Catchables) obj).apanhar();
						}
						if(obj instanceof HealingPotion) {
							poisoned = false;
						}
					}
				}
			}
			if(!isWalkable(newPosition)) {
				abrirDoor(newPosition);
				newPosition = getPosition();
				hasMoved = true;
			}
			if(poisoned == true) {
				setVida(getVida() - 1);
			}
		}
	}

	@Override
	public int getLayer() {
		return 2;
	}
	
	public void abrirDoor(Point2D newPosition) {
		List<GameElements> gameElementsPos = GameEngine.getInstance().getGameElement(newPosition);
		if(gameElementsPos.size() > 1 && gameElementsPos.get(1) instanceof Door) {
			for (int i = 0; i < 3; i++) {
				List<GameElements> gameElementsBar = GameEngine.getInstance().getGameElement(new Point2D(i,10));
				if(!(gameElementsBar.isEmpty()) && gameElementsBar.get(0) instanceof Key) {
					if(((Key) gameElementsBar.get(0)).getID().equals(((Door) gameElementsPos.get(1)).getIdKey())) {
						((Door) gameElementsPos.get(1)).openDoor(((Key) gameElementsBar.get(0)).getID());
						((Key) gameElementsBar.get(0)).setUsed(true);
						GameEngine.getInstance().removeGameElement(gameElementsBar.get(0));
						Catchables[] c = GameEngine.getInstance().getInventory();
						c[i] = null;
					}
				}
				if(((Door) gameElementsPos.get(1)).getIdKey() == null) {
					((Door) gameElementsPos.get(1)).noKeyDoor();;
					
				}
			}
		}
	}

	public void setPoisoned(boolean poisoned) {
		this.poisoned = poisoned;
	}

	public int getPOINTS() {
		return POINTS;
	}

	public void setPOINTS(int pOINTS) {
		POINTS = pOINTS;
	}
}
