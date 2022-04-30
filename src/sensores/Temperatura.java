package sensores;

public final class Temperatura extends Entrada {
	
	String id;
	long tiempo;
	double celcius;
	
	public Temperatura(String id, long tiempo, double celcius) {
		super();
		this.id = id;
		this.tiempo = tiempo;
		this.celcius = celcius;
	}
	
	public String id() {
		return id;
	}
	
	public long tiempo() {
		return tiempo;
	}
	
	public double celcius() {
		return celcius;
	}
	
	@Override
	public String toString() {
		return "Temperatura [id=" + id + ", tiempo=" + tiempo + ", celcius=" + celcius + "]";
	}
	
}
