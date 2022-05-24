package actuator.data;

import actuator.common.Action;

/**
 * Acci�n que representa los ajustes del equipo climatizador.
 * Los �ndices son abstractos, por lo que el dispositivo cliente
 * los puede adaptar a la m�quina que realmente controle.
 */
public final class AirConditioning extends Action {
	
	private String id;
	// El prototipo modelar� esto con un servo.
	// El valor ser� positivo para calentar la estancia
	// y ser� negativo para enfriarla.
	private byte tempChangeIndex;
	// El prototipo modelar� esto con un motor.
	private byte ventilationSpeed;
	
	public AirConditioning(String id, byte tempChangeIndex, byte ventilationSpeed) {
		super();
		this.id = id;
		this.tempChangeIndex = tempChangeIndex;
		this.ventilationSpeed = ventilationSpeed;
	}

	public String id() {
		return id;
	}

	public byte tempChangeIndex() {
		return tempChangeIndex;
	}

	public byte ventilationSpeed() {
		return ventilationSpeed;
	}

	@Override
	public String toString() {
		return "AirConditioning [id=" + id + ", tempChangeIndex=" + tempChangeIndex
				+ ", ventilationSpeed=" + ventilationSpeed + "]";
	}

}
