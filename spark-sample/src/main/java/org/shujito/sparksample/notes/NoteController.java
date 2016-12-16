package org.shujito.sparksample.notes;

import com.google.gson.Gson;

import org.eclipse.jetty.http.HttpStatus;
import org.shujito.sparksample.ApiResponse;
import org.skife.jdbi.v2.DBI;

import java.util.List;

import spark.Request;
import spark.Response;

/**
 * @author shujito, 10/28/16
 */
public class NoteController {
	private final NoteDao noteDao;
	private final Gson gson;

	public NoteController(DBI dbi, Gson gson) {
		this.noteDao = dbi.onDemand(NoteDao.class);
		this.gson = gson;
	}

	public List<Note> getAll(Request request, Response response) {
		return this.noteDao.all();
	}

	public Note getOne(Request request, Response response) {
		String id = request.params("id");
		return noteDao.one(Integer.parseInt(id));
	}

	public Note postNote(Request request, Response response) {
		Note note = this.gson.fromJson(request.body(), Note.class);
		int insertedId = noteDao.insert(note);
		return noteDao.one(insertedId);
	}

	public ApiResponse deleteNote(Request request, Response response) {
		String id = request.params("id");
		int deleted = this.noteDao.delete(Integer.parseInt(id));
		return ApiResponse.builder().success(deleted > 0).status(HttpStatus.GONE_410).build();
	}
}
