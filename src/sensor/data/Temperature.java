package sensor.data;

import sensor.common.Entry;

public final class Temperature extends Entry {
	
	private String id;
	private long time;
	private double celcius;
	
	public Temperature(String id, long time, double celcius) {
		super();
		this.id = id;
		this.time = time;
		this.celcius = celcius;
	}
	
	public String id() {
		return id;
	}
	
	public long time() {
		return time;
	}
	
	public double celcius() {
		return celcius;
	}
	
	@Override
	public String toString() {
		return "Temperatura [id=" + id + ", time=" + time + ", celcius=" + celcius + "]";
	}
	
}
