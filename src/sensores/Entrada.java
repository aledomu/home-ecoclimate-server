package sensores;

import java.util.Objects;

public abstract class Entrada {
	
	abstract public String id();
	abstract public long tiempo();
	
	final public int hashCode() {
		return Objects.hash(id(), tiempo());
	}
	
	final public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Entrada other = (Entrada) obj;
		return Objects.equals(id(), other.id()) && tiempo() == other.tiempo();
	}
	
}
