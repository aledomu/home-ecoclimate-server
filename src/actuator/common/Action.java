package actuator.common;

import java.util.Objects;

/**
 * Clase abstracta que indica los métodos estándar mínimos de cualquier mensaje
 * para activar cualquier actuador de la placa.
 */
public abstract class Action {

	/**
	 * @return Identificador del sensor que recibe el mensaje de acción
	 */
	abstract public String id();
	
	final public int hashCode() {
		return Objects.hash(id());
	}
	
	final public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Action other = (Action) obj;
		return Objects.equals(id(), other.id());
	}
	
}
