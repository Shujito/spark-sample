package org.shujito.sparksample;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.eclipse.jetty.http.HttpHeader;
import org.shujito.sparksample.notes.NoteController;
import org.shujito.sparksample.root.RootController;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

import spark.Filter;
import spark.Spark;

@SuppressWarnings("NewApi")
public class Application {
	public static void main(String[] args) {
		Spark.port(getPort());
		DBI dbi = new DBI("jdbc:sqlite:notes.db3");
		try (Handle handle = dbi.open()) {
			handle.begin();
			handle.execute("create table if not exists notes(id integer primary key on conflict fail, title text not null, content text not null)");
			handle.execute("create view if not exists unixtime as select cast(((julianday('now') - julianday('1970-01-01')) * 86400000) as integer) as unixtime");
			//handle.execute("insert into notes(title,content) values('A title','Some text'),('Another title','Read here, you!');");
			handle.commit();
		}
		Gson gson = new GsonBuilder().create();
		Spark.exception(Exception.class, (exception, request, response) -> {
			response.status(500);
			response.header(HttpHeader.CONTENT_TYPE.asString(), "application/json");
			ApiResponse apiResponse = ApiResponse.builder()
				.status(500)
				.success(false)
				.message(exception.getMessage())
				.build();
			response.body(gson.toJson(apiResponse));
		});
		NoteController noteController = new NoteController(dbi, gson);
		RootController rootController = new RootController(dbi);
		Spark.get("/", rootController::getRoot, gson::toJson);
		// notes
		Spark.get("/notes", noteController::getAll, gson::toJson);
		Spark.get("/note/:id", noteController::getOne, gson::toJson);
		Spark.post("/note", "application/json", noteController::postNote, gson::toJson);
		Spark.delete("/note/:id", noteController::deleteNote, gson::toJson);
		// filter
		Spark.after((Filter) (request, response) -> {
			response.header(HttpHeader.CONTENT_TYPE.asString(), "application/json");
			response.header(HttpHeader.CONTENT_ENCODING.asString(), "gzip");
		});
		System.out.println("running on port '" + Spark.port() + "'");
	}

	private static int getPort() {
		try {
			String port = System.getProperty("port");
			return Integer.parseInt(port);
		} catch (NumberFormatException nfe) {
			return 1337;
		}
	}
}
