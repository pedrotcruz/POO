package pt.iscte.poo.example;

import pt.iscte.poo.utils.Point2D;

public class Door extends GameElements {

	private String newRoom;
	private Point2D newRoomPos;
	private String idKey;
	private boolean isOpen;
	protected Point2D saida;

	public Door(Point2D position, String newRoom, Point2D newRoomPos, String idKey) {
		super(position);
		this.newRoom = newRoom;
		this.newRoomPos = newRoomPos;
		this.idKey = idKey;
	}

	public String getNewRoom() {
		return newRoom;
	}

	public Point2D getNewRoomPos() {
		return newRoomPos;
	}

	public String getIdKey() {
		return idKey;
	}

	@Override
	public String getName() {
		if(getIdKey() == null) {
			return "DoorWay";
		}
		if(isOpen) {
			return "DoorOpen";
		}
		if(!isOpen) {
			return "DoorClosed";
		}
		return null;
	}

	@Override
	public int getLayer() {
		return 1;
	}

	public void openDoor(String st) {
		if(st.equals(getIdKey())) {
			isOpen = true;
		}
	}
	
	public void noKeyDoor() {
		if(getIdKey() == null) {
			isOpen = true;
		}
	}

	public boolean truzTruz() {
		return isOpen;
	}
	
	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}
}
