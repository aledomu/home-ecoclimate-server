package actuator.common;

import java.util.Objects;

public abstract class Action {

	abstract public String id();
	
	final public int hashCode() {
		return Objects.hash(id());
	}
	
	final public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Action other = (Action) obj;
		return Objects.equals(id(), other.id());
	}
	
}
