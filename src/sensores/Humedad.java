package sensores;

public final class Humedad extends Entrada {
	
	private String id;
	private long tiempo;
	private double hectopascal;
	
	public Humedad(String id, long tiempo, double hectopascal) {
		super();
		this.id = id;
		this.tiempo = tiempo;
		this.hectopascal = hectopascal;
	}

	public String id() {
		return id;
	}

	public long tiempo() {
		return tiempo;
	}

	public double hectopascal() {
		return hectopascal;
	}

	@Override
	public String toString() {
		return "Humedad [id=" + id + ", tiempo=" + tiempo + ", hectopascal=" + hectopascal + "]";
	}
	
}
