package actuator.data;

import java.util.Objects;

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
	public int hashCode() {
		return Objects.hash(id, tempChangeIndex, ventilationSpeed);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AirConditioning other = (AirConditioning) obj;
		return Objects.equals(id, other.id) && tempChangeIndex == other.tempChangeIndex
				&& ventilationSpeed == other.ventilationSpeed;
	}

	@Override
	public String toString() {
		return "AirConditioning [id=" + id + ", tempChangeIndex=" + tempChangeIndex
				+ ", ventilationSpeed=" + ventilationSpeed + "]";
	}

}
