package sensor.common;

import java.util.Objects;

/**
 * Clase abstracta que indica los m�todos est�ndar m�nimos de cualquier registro
 * de un sensor de la placa.
 */
public abstract class Reading {
	
	protected String id;
	protected long time;
	
	/**
	 * @return Identificador del sensor que env�a la entrada al registro
	 */
	final public String id() {
		return id;
	}
	
	/**
	 * @return Marca de tiempo de la entrada del registro enviada por el sensor
	 */
	final public long time() {
		return time;
	}
	
	/**
	 * @param <T> Subclase de esta clase abstracta
	 * @return Lectura con el tiempo modificado a la hora actual
	 */
	abstract public <T extends Reading> T withCurrentTime();
	
	/**
	 * @return Consulta de MySQL para insertar este registro en la base de datos.
	 */
	abstract public String asSQLInsertQuery(String tableName);
	
	final public int hashCode() {
		return Objects.hash(id(), time());
	}
	
	final public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reading other = (Reading) obj;
		return Objects.equals(id(), other.id()) && time() == other.time();
	}
	
}
