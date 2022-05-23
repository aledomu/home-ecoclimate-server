package sensor.data;

import java.time.Instant;

import io.vertx.sqlclient.Row;
import sensor.common.Reading;

public final class Humidity extends Reading {

	private String id;
	private long time;
	private double hectopascal;
	
	public Humidity(String id, long time, double hectopascal) {
		super();
		this.id = id;
		this.time = time;
		this.hectopascal = hectopascal;
	}
	
	public Humidity(Row sqlRow) {
		new Humidity(
			sqlRow.getString("ID"),
			sqlRow.getLong("TIME"),
			sqlRow.getDouble("HECTOPASCAL")
		);
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
	public Humidity withCurrentTime() {
		return new Humidity(id(), Instant.now().getEpochSecond(), hectopascal());
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
