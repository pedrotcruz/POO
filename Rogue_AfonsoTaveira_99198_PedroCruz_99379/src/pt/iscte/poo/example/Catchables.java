package pt.iscte.poo.example;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import pt.iscte.poo.utils.Point2D;

public abstract class Catchables extends GameElements {

	private Catchables[] slots = new Catchables[3];

	public Catchables(Point2D position) {
		super(position);
	}

	public void apanhar() {
		Point2D heroPos = GameEngine.getInstance().getHero().getPosition();
		if (heroPos.equals(this.getPosition())) {
			int slot = slotDisp();
			if (slot < 3) {
				slots[slot] = this;
				this.setPosition(new Point2D(slot, 10));
			} else {
				return;
			}
		}
	}

	@Override
	public String getName() {
		return this.getName();
	}

	public void drop(int key) {
		Point2D heroPos = GameEngine.getInstance().getHero().getPosition();
		int aux = 0;
		switch (key) {
		case KeyEvent.VK_Q:
			aux = 0;
			break;
		case KeyEvent.VK_W:
			aux = 1;
			break;
		case KeyEvent.VK_E:
			aux = 2;
			break;
		}
		List<GameElements> gameElementsHeroPosition = GameEngine.getInstance().getGameElement(heroPos);
		for(GameElements cat : gameElementsHeroPosition) {
			if(slots[aux] == null || cat instanceof Catchables) {
				return;
			}
		}
		slots[aux].setPosition(heroPos);
		slots[aux] = null;
	}
	
	public int slotDisp() {
		for (int i = 0; i < 3; i++) {
			if (GameEngine.getInstance().getGameElement(new Point2D(i, GameEngine.GRID_HEIGHT)).isEmpty()) {
				return i;
			}
		}
		return 4;
	}

	public static List<Catchables> getInventory() {
		List<Catchables> inv = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			if(!(GameEngine.getInstance().getGameElement(new Point2D(i, GameEngine.GRID_HEIGHT)).isEmpty())) {
				inv.add((Catchables) GameEngine.getInstance().getGameElement(new Point2D(i, GameEngine.GRID_HEIGHT)).get(0));
			}
		}
		return inv;
	}
	
	public Catchables[] getSlots() {
		return slots;
	}
	
	public void setSlots(Catchables[] slots) {
		this.slots = slots;
	}
}
