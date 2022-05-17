package sensor.data.pools;

import java.util.HashSet;
import java.util.Set;

import sensor.common.DataPool;
import sensor.common.Entry;

/**
 * Implementación de una base de datos local genérica en memoria (sin persistencia).
 *
 * @param <T> Cualquier recurso que represente un tipo de sensor
 */
public class LocalNonPersistent<T extends Entry> implements DataPool<T> {
	
	private Set<T> pool = new HashSet<T>();

	@Override
	public Set<T> getAll() {
		return Set.copyOf(pool);
	}

	@Override
	public boolean add(T e) {
		return e != null ? pool.add(e) : false;
	}

}
