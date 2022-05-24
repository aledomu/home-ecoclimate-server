package actuator.common;

/**
 * Clase abstracta que indica los m�todos est�ndar m�nimos de cualquier mensaje
 * para activar cualquier actuador de la placa.
 */
public abstract class Action {

	/**
	 * @return Identificador del sensor que recibe el mensaje de acci�n
	 */
	abstract public String id();
	
}
