package org.shujito.sparksample;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

/**
 * @author shujito, 10/27/16
 */
@RegisterMapper(Note.Mapper.class)
public interface NoteDao {
	@SqlQuery("select * from notes")
	List<Note> all();

	@SqlQuery("select * from notes where id is :id")
	Note one(@Bind("id") int id);
}
