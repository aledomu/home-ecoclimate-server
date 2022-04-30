package sensor.common;

import java.util.Objects;

public abstract class Entry {
	
	abstract public String id();
	abstract public long time();
	
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
