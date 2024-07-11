package pt.iscte.poo.example;

import pt.iscte.poo.utils.Point2D;

public class Key extends Catchables {

	private String id;
	private boolean used;

	public Key(Point2D position, String id) {
		super(position);
		this.id = id;
	}

	@Override
	public String getName() {
		return "Key";
	}

	@Override
	public int getLayer() {
		return 1;
	}

	public String getID() {
		return id;
	}
	
	public void setUsed(boolean used) {
		this.used = used;
	}

	public boolean isUsed() {
		return used;
	}
}
