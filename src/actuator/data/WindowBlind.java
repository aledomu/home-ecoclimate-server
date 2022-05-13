package actuator.data;

import actuator.common.Action;

public final class WindowBlind extends Action {
	
	private String id;
	private int angle;
	
	public WindowBlind(String id, int angle) {
		super();
		this.id = id;
		this.angle = angle;
	}

	public String id() {
		return id;
	}

	public int angle() {
		return angle;
	}

	@Override
	public String toString() {
		return "WindowBlind [id=" + id + ", angle=" + angle + "]";
	}
	
}
