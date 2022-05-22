package sensor.common;

import java.util.Collection;
import java.util.Optional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

/**
 * Administrador de la API REST junto con la base de datos para cualquier recurso
 * que represente la serie de datos de un sensor.
 * 
 * @param <T> Cualquier recurso que represente un tipo de sensor
 */
public class ResourceHandler<T extends Reading> {

	final private Class<T> resource;
	final private DataPool<T> data;

	final private Gson gson = new GsonBuilder().create();

	public ResourceHandler(Class<T> resource, DataPool<T> data) {
		super();
		this.data = data;
		this.resource = resource;
	}
	
	/**
	 * Configura una estructura de routing para el recurso <b>T</b>
	 * con la siguiente estructura:
	 * <ul>
	 * <li>
	 * <b>GET /</b> => Recolectar todos los registros de este recurso.
	 * </li>
	 * <li>
	 * <b>GET /:id</b> => Recolectar todos los registros de este recurso
	 * con el identificador indicado.
	 * </li>
	 * <li>
	 * <b>GET /:id/:time</b> => Devolver el registro de este recurso
	 * con el identificador y la marca de tiempo indicados.
	 * Debe ser único.
	 * </li>
	 * <li>
	 * <b>POST /</b> => Añadir un registro del tipo <b>T</b>.
	 * </li>
	 * </ul>
	 * 
	 * @param vertx Instancia de Vert.X desde la que configurar el router
	 * @return Estructura de routing con los handlers ya configurados
	 */
	public Router getRouter(Vertx vertx) {
		Router router = Router.router(vertx);
		router.route("/*").handler(BodyHandler.create());
		router.get("/").handler(this::getAll);
		router.get("/:id").handler(this::getId);
		router.get("/:id/:time").handler(this::getIdTime);
		router.post().handler(this::addRecord);
		
		return router;
	}
	
	/**
	 * @return El nombre del tipo de recurso <b>T</b>
	 */
	public String resourceName() {
		return resource.getSimpleName();
	}

	private <P> Future<Void> sendJSONResponse(HttpServerResponse response, Optional<P> payload) {
		return payload
			.map(queryResult ->
				response
					.putHeader("content-type", "application/json; charset=utf-8")
					.setStatusCode(200)
					.end(gson.toJson(queryResult))
			)
			.orElseGet(() ->
				response
					.putHeader("content-type", "application/json; charset=utf-8")
					.setStatusCode(204)
					.end()
			);
	}
	
	private <P extends Collection<T>> Future<Void> sendJSONCollectionResponse(HttpServerResponse response, P payload) {
		return sendJSONResponse(response, Optional.of(payload).filter(p -> !p.isEmpty()));
	}
	
	private Future<Void> getAll(RoutingContext routingContext) {
		return data.getAll().flatMap(
			r -> sendJSONCollectionResponse(routingContext.response(), r)
		);
	}

	private Future<Void> getId(RoutingContext routingContext) {
		String id = routingContext.request().getParam("id");
		
		return data.getById(id).flatMap(
			r -> sendJSONCollectionResponse(routingContext.response(), r)
		);
	}

	private Future<Void> getIdTime(RoutingContext routingContext) {
		String id = routingContext.request().getParam("id");
		long time = Long.parseLong(routingContext.request().getParam("time"));
		
		return data.getByIdAndTime(id, time).flatMap(
			r -> sendJSONResponse(routingContext.response(), r)
		);
	}

	private Future<Void> addRecord(RoutingContext routingContext) {
		Future<T> start;
		try {
			start = Future.succeededFuture(bodyToJSON(routingContext.getBodyAsString()));
		} catch (JsonSyntaxException ex) {
			start = Future.failedFuture(ex);
		}
		
		return start
			.flatMap(data::add)
			.map(201)
			.otherwise(ex -> ex instanceof JsonSyntaxException ? 400 : 500)
			.flatMap(statusCode ->
				routingContext
					.response()
					.setStatusCode(statusCode)
					.end()
			);
	}
	
	private T bodyToJSON(String body) throws JsonSyntaxException {
		if (body == null || body.equals(""))
			throw new JsonSyntaxException("The payload for adding an entry cannot be empty");
		
		return gson.fromJson(body, resource);
	}
	
}
