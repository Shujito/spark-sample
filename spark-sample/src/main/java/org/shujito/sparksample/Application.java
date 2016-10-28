package org.shujito.sparksample;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.eclipse.jetty.http.HttpHeader;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

import spark.Spark;

@SuppressWarnings("NewApi")
public class Application {
	public static void main(String[] args) {
		Spark.port(1337);
		DBI dbi = new DBI("jdbc:sqlite:notes.db3");
		try (Handle handle = dbi.open()) {
			handle.begin();
			handle.execute("create table if not exists notes(id integer primary key on conflict fail, title text not null, content text not null);");
			handle.execute("insert into notes(title,content) values('A title','Some text'),('Another title','Read here, you!');");
			handle.commit();
		}
		NoteDao noteDao = dbi.onDemand(NoteDao.class);
		Gson gson = new GsonBuilder().create();
		Spark.exception(Exception.class, (exception, request, response) -> {
			exception.printStackTrace();
			response.status(500);
			response.body("this happened:'" + exception + "'");
		});
		Spark.get("/", (request, response) -> "hi there");
		Spark.get("/notes", (request, response) -> {
			response.header(HttpHeader.CONTENT_TYPE.asString(), "application/json");
			return noteDao.all();
		}, gson::toJson);
	}
}
