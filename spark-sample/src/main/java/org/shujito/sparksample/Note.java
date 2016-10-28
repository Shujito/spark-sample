package org.shujito.sparksample;

import com.google.gson.annotations.SerializedName;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * @author shujito, 10/27/16
 */
@Builder
@ToString
public class Note {
	public static final String ID = "id";
	public static final String TITLE = "title";
	public static final String CONTENT = "content";

	public static final class Mapper implements ResultSetMapper<Note> {
		@Override
		public Note map(int index, ResultSet r, StatementContext ctx) throws SQLException {
			return Note.builder()
				.id(r.getInt(ID))
				.title(r.getString(TITLE))
				.content(r.getString(CONTENT))
				.build();
		}
	}

	@Getter
	@SerializedName(ID)
	private int id;
	@Getter
	@SerializedName(TITLE)
	private String title;
	@Getter
	@SerializedName(CONTENT)
	private String content;
}
