package sensor.common;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Indica las operaciones necesarias sobre la base de datos sin depender
 * de ninguna implementación concreta.
 *
 * @param <T> Cualquier recurso que represente un tipo de sensor
 */
public interface DataPool<T extends Entry> {

	/**
	 * @return Todos los registros del recurso <b>T</b>
	 */
	public Set<T> getAll();
	
	/**
	 * @param id Identificador de las entradas
	 * @return Todos los registros del recurso <b>T</b> con el identificador indicado
	 */
	default public Set<T> getById(String id) {
		return getAll().stream().filter(e -> e.id().equals(id)).collect(Collectors.toSet());
	}
	
	/**
	 * @param id	Identificador de la entrada
	 * @param time	Marca de tiempo de la entrada
	 * @return Registro del recurso <b>T</b> con el identificador
	 * y la marca de tiempo indicados
	 */
	default public Optional<T> getByIdAndTime(String id, long time) {
		return getAll().stream()
				.filter(e -> e.id().equals(id) && e.time() == time)
				.findAny();
	}
	
	/**
	 * @param elem Entrada a añadir al registro
	 * @return <b>true</b> si se ha añadido el elemento, <b>false</b> si no
	 */
	public boolean add(T elem);

}
