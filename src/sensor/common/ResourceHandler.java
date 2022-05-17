package sensor.common;

import java.util.Collection;
import java.util.Optional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

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
public class ResourceHandler<T extends Entry> {

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

	private <P> void setJSONResponse(HttpServerResponse response, Optional<P> payload) {
		payload.ifPresentOrElse(
			queryResult ->
				response
					.putHeader("content-type", "application/json; charset=utf-8")
					.setStatusCode(200)
					.end(gson.toJson(queryResult)),
			() -> {
				response
					.putHeader("content-type", "application/json; charset=utf-8")
					.setStatusCode(204)
					.end();
			}
		);
	}
	
	private <P extends Collection<T>> void setJSONCollectionResponse(HttpServerResponse response, P payload) {
		setJSONResponse(response, Optional.of(data.getAll()).filter(p -> !p.isEmpty()));
	}
	
	private void getAll(RoutingContext routingContext) {
		setJSONCollectionResponse(routingContext.response(), data.getAll());
	}

	private void getId(RoutingContext routingContext) {
		String id = routingContext.request().getParam("id");
		
		setJSONCollectionResponse(routingContext.response(), data.getById(id));
	}

	private void getIdTime(RoutingContext routingContext) {
		String id = routingContext.request().getParam("id");
		long time = Long.parseLong(routingContext.request().getParam("time"));
		
		setJSONResponse(routingContext.response(), data.getByIdAndTime(id, time));
	}

	private void addRecord(RoutingContext routingContext) {
		int statusCode = 201;
		
		try {
			String body = routingContext.getBodyAsString();
			
			if (body == null || body.equals(""))
				throw new JsonSyntaxException("The payload for adding an entry cannot be empty");
			
			final T newEntry = gson.fromJson(body, resource);
		
			data.add(newEntry);
		} catch (JsonSyntaxException ex) {
			statusCode = 400;
		}
		
		routingContext
			.response()
			.setStatusCode(statusCode)
			.end();
	}
	
}
