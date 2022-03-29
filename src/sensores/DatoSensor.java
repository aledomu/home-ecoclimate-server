package sensores;

import java.time.Instant;

public record DatoSensor<T>(String id, Instant tiempo, T medida) {

}
