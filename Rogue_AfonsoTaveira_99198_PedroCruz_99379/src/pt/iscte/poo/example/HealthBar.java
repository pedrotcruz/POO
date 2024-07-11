package pt.iscte.poo.example;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class HealthBar extends GameElements {

	private boolean isGreen;

	public HealthBar(Point2D position, boolean isGreen) {
		super(position);
		this.isGreen = isGreen;
	}

	@Override
	public String getName() {
		if (isGreen) {
			return "Green";
		}
		if(!isGreen) {
			return "Red";
		}
		return null;

	}

	public static void barraVidaVerde() {
		int vida = GameEngine.getInstance().getHero().getVida();
		HealthBar hb = null;
		for (int i = 0; i < 10; i++) {
			if (!GameEngine.getInstance().getGameElement(new Point2D(i, 11)).isEmpty()) {
				GameEngine.getInstance().removeGameElement((ImageTile) GameEngine.getInstance().getGameElement(new Point2D(i, 11)).get(0));
			}
		}
		for (int i = 0; i < 10; i++) {
			hb = new HealthBar(new Point2D(i, 11),true);
			GameEngine.getInstance().addGameElement(hb);
		}
		for (int i = 9; i >= vida; i--) {
			hb = new HealthBar(new Point2D(i, 11),false);
			GameEngine.getInstance().addGameElement(hb);
		}
	}

	@Override
	public int getLayer() {
		return 1;
	}

	@Override
	public String toString() {
		return getName();
	}

}
