package org.shujito.sparksample;

import com.google.gson.Gson;

import org.skife.jdbi.v2.DBI;

import java.util.List;

import spark.Request;
import spark.Response;

/**
 * @author shujito, 10/28/16
 */
public class NoteController {
	public static final String TAG = NoteController.class.getSimpleName();
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
		return noteDao.one(id);
	}

	public Note postNote(Request request, Response response) {
		Note note = this.gson.fromJson(request.body(), Note.class);
		int insertedId = noteDao.insert(note.getTitle(), note.getContent());
		return noteDao.one(String.valueOf(insertedId));
	}
}
