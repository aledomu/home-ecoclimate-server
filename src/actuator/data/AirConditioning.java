package actuator.data;

import actuator.common.Action;

public final class AirConditioning extends Action {
	
	private final String id;
	private final int tempChangeIndex;  // puede ser negativo
	private final int ventilationSpeed;
	
	public AirConditioning(String id, int tempChangeIndex, int ventilationSpeed) {
		super();
		this.id = id;
		this.tempChangeIndex = tempChangeIndex;
		this.ventilationSpeed = ventilationSpeed;
	}

	public String id() {
		return id;
	}

	public int tempChangeIndex() {
		return tempChangeIndex;
	}

	public int ventilationSpeed() {
		return ventilationSpeed;
	}

	@Override
	public String toString() {
		return "AirConditioning [id=" + id + ", tempChangeIndex=" + tempChangeIndex
				+ ", ventilationSpeed=" + ventilationSpeed + "]";
	}

}
