package sensor.data;

import java.time.Instant;

import io.vertx.sqlclient.Row;
import sensor.common.Reading;

public final class Humidity extends Reading {

	private double ratio;
	
	public Humidity(String id, long time, double ratio) {
		super();
		this.id = id;
		this.time = time;
		this.ratio = ratio;
	}
	
	public Humidity(Row sqlRow) {
		new Humidity(
			sqlRow.getString("ID"),
			sqlRow.getLong("TIME"),
			sqlRow.getDouble("RATIO")
		);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Humidity withCurrentTime() {
		return new Humidity(id(), Instant.now().getEpochSecond(), ratio());
	}

	public double ratio() {
		return ratio;
	}
	
	public String asSQLInsertQuery(String tableName) {
		return "INSERT INTO " + tableName
			+ " VALUES ('"
			+ id() + "', "
			+ time() + ", "
			+ ratio() + ");";
	}

	@Override
	public String toString() {
		return "Humedad [id=" + id + ", time=" + time + ", ratio=" + ratio + "]";
	}

}
