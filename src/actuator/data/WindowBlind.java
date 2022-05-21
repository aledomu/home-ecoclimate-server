package actuator.data;

import actuator.common.Action;

/**
 * Acción que representa el ajuste de la persiana.
 * El índice es abstracto, por lo que el dispositivo cliente
 * los puede adaptar a la máquina que realmente controle.
 */
public final class WindowBlind extends Action {
	
	private String id;
	// El prototipo modelará esto con un servo.
	private byte angle;
	
	public WindowBlind(String id, byte angle) {
		super();
		this.id = id;
		this.angle = angle;
	}

	public String id() {
		return id;
	}

	public byte angle() {
		return angle;
	}

	@Override
	public String toString() {
		return "WindowBlind [id=" + id + ", angle=" + angle + "]";
	}
	
}
