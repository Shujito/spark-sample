package org.shujito.sparksample.notes;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
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

	@GetGeneratedKeys
	@SqlUpdate("insert into notes(title,content) values (:title,:content)")
	int insert(
		@Bind("title") String title,
		@Bind("content") String content
	);

	@SqlUpdate("delete from notes where id is :id")
	int delete(@Bind("id") int id);
}
