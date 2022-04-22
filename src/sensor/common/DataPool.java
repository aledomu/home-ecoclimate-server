package sensor.common;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public interface DataPool<T extends Entry> {

	public Set<T> getAll();
	
	default public Set<T> getById(String id) {
		return getAll().stream().filter(e -> e.id().equals(id)).collect(Collectors.toSet());
	}
	
	default public Optional<T> getByIdAndTime(String id, long time) {
		return getAll().stream()
				.filter(e -> e.id().equals(id) && e.time() == time)
				.findAny();
	}
	
	public boolean add(T elem);

}
