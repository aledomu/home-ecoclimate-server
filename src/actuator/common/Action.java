package actuator.common;

/**
 * Clase abstracta que indica los métodos estándar mínimos de cualquier mensaje
 * para activar cualquier actuador de la placa.
 */
public abstract class Action {

	/**
	 * @return Identificador del sensor que recibe el mensaje de acción
	 */
	abstract public String id();
	
}
