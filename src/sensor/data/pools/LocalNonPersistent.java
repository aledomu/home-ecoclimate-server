package sensor.data.pools;

import java.util.HashSet;
import java.util.Set;

import io.vertx.core.Future;
import sensor.common.DataPool;
import sensor.common.Reading;

/**
 * Implementación de una base de datos local genérica en memoria (sin persistencia).
 *
 * @param <T> Cualquier recurso que represente un tipo de sensor
 */
public class LocalNonPersistent<T extends Reading> implements DataPool<T> {
	
	private Set<T> pool = new HashSet<T>();

	@Override
	public Future<Set<T>> getAll() {
		return Future.succeededFuture(Set.copyOf(pool));
	}

	@Override
	public Future<Boolean> add(T e) {
		return Future.succeededFuture(e != null ? pool.add(e) : false);
	}

}
