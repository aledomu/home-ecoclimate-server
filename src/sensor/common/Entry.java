package sensor.common;

import java.util.Objects;

/**
 * Clase abstracta que indica los m�todos est�ndar m�nimos de cualquier registro
 * de un sensor de la placa.
 */
public abstract class Entry {
	
	/**
	 * @return identificador del sensor que env�a la entrada al registro.
	 */
	abstract public String id();
	/**
	 * @return marca de tiempo de la entrada del registro enviada por el sensor.
	 */
	abstract public long time();
	
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
		Entry other = (Entry) obj;
		return Objects.equals(id(), other.id()) && time() == other.time();
	}
	
}
