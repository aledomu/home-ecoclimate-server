package sensor.data;

import io.vertx.sqlclient.Row;
import sensor.common.Reading;

public final class Temperature extends Reading {
	
	private String id;
	private long time;
	private double celsius;
	
	public Temperature(Row sqlRow) {
		new Humidity(
			sqlRow.getString("ID"),
			sqlRow.getLong("TIME"),
			sqlRow.getDouble("CELSIUS")
		);
	}
	
	public Temperature(String id, long time, double celsius) {
		super();
		this.id = id;
		this.time = time;
		this.celsius = celsius;
	}
	
	public String id() {
		return id;
	}
	
	public long time() {
		return time;
	}
	
	public double celsius() {
		return celsius;
	}
	
	public String asSQLInsertQuery(String tableName) {
		return "INSERT INTO " + tableName
			+ " VALUES ('"
			+ id() + "', "
			+ time() + ", "
			+ celsius() + ");";
	}
	
	@Override
	public String toString() {
		return "Temperatura [id=" + id + ", time=" + time + ", celcius=" + celsius + "]";
	}
	
}
