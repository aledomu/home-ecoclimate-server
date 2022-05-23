package sensor.data;

import java.time.Instant;

import com.google.gson.annotations.Expose;

import io.vertx.sqlclient.Row;
import sensor.common.Reading;

public final class Temperature extends Reading {
	
	private String id;
	@Expose(deserialize = false)
	private long time = Instant.now().getEpochSecond();
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
	
	@Override
	public String id() {
		return id;
	}

	@Override
	public long time() {
		return time;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Temperature withCurrentTime() {
		return new Temperature(id(), Instant.now().getEpochSecond(), celsius());
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
