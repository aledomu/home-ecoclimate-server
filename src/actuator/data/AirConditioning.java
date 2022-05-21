package actuator.data;

import actuator.common.Action;

/**
 * Acción que representa los ajustes del equipo climatizador.
 * Los índices son abstractos, por lo que el dispositivo cliente
 * los puede adaptar a la máquina que realmente controle.
 */
public final class AirConditioning extends Action {
	
	private String id;
	// El prototipo modelará esto con un servo.
	// El valor será positivo para calentar la estancia
	// y será negativo para enfriarla.
	private byte tempChangeIndex;
	// El prototipo modelará esto con un motor.
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
