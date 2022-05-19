package sensor.data;

import sensor.common.Entry;

public final class Humidity extends Entry {
	
	private String id;
	private long time;
	private double hectopascal;
	
	public Humidity(String id, long time, double hectopascal) {
		super();
		this.id = id;
		this.time = time;
		this.hectopascal = hectopascal;
	}

	public String id() {
		return id;
	}

	public long time() {
		return time;
	}

	public double hectopascal() {
		return hectopascal;
	}
	
	public String asSQLInsertQuery(String tableName) {
		return "INSERT INTO " + tableName
			+ " VALUES ('"
			+ id() + "', "
			+ time() + ", "
			+ hectopascal() + ");";
	}

	@Override
	public String toString() {
		return "Humedad [id=" + id + ", time=" + time + ", hectopascal=" + hectopascal + "]";
	}
	
}
