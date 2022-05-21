package actuator.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.netty.handler.codec.mqtt.MqttQoS;
import io.vertx.core.Future;
import io.vertx.core.buffer.Buffer;
import io.vertx.mqtt.MqttClient;
import sensor.common.Reading;

/**
 * Clase abstracta a implementar por cada tipo de lectura de sensor
 * 
 * @param <T> Cualquier recurso que represente un tipo de sensor
 */
public abstract class CommandPublisher<T extends Reading> {

	protected MqttClient client;
	
	protected CommandPublisher(MqttClient client) {
		super();
		this.client = client;
	}
	
	/**
	 * @param series Serie de datos agrupados por el ID de sensor
	 * @return Índice general calculado a partir de la serie de datos
	 */
	abstract protected long getIndex(Stream<? extends List<T>> series);
	
	/**
	 * Método a implementar (pero no usar directamente) para realizar
	 * el envío del mensaje MQTT que representa el comando apropiado.
	 * Los valores no deben representar un valor numérico con un tamaño
	 * mayor de 8 bits, sea con o sin signo.
	 * 
	 * @param index Índice calculado por <b>getIndex</b> 
	 * @return Operación asíncrona tras enviar el comando según el índice
	 */
	abstract protected Future<Void> handleIndex(short index);

	/**
	 * Método de utilidad a ser usado solo por las clases que implementen
	 * esta clase abstracta.
	 * 
	 * @param cmdType Tipo de comando
	 * @param content Valor del comando
	 * @return Valor de retorno de la publicación del mensaje MQTT
	 */
	final protected Future<Integer> publish(String cmdType, String content) {
		return client.publish(
			"fanSpeed",
			Buffer.buffer(content),
			MqttQoS.AT_LEAST_ONCE,
			false,
			false
		);
	}
	
	
	/**
	 * @param series Serie de datos agrupados por el ID de sensor
	 * @return Operación asíncrona tras enviar el comando
	 */
	final public Future<Void> sendCmd(Set<T> series) {
		short index = saturatingCastToUnsignedByte(getIndex(
			series.stream()
				.collect(getSensorReadingMapByIdCollector())
				.entrySet()
				.stream()
				.map(Entry::getValue)
		));

		return handleIndex(index);
	}
	
	private static <T extends Reading> Collector<T, ?, Map<String, List<T>>> getSensorReadingMapByIdCollector() {
		return Collectors.toMap(
			Reading::id,
			t -> List.of(t),
			(c1, c2) -> {
				ArrayList<T> c = new ArrayList<>(c1);
				c.addAll(c2);
				return c;
			}
		);
	}
	
	protected static short saturatingCastToUnsignedByte(long value) {
		return value > 255 ? 255 : (short) value;
	}
	
}
