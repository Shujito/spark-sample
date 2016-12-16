package org.shujito.sparksample.notes;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.ToString;

/**
 * @author shujito, 10/27/16
 */
@Data
@ToString
public class Note {
	public static final String ID = "id";
	public static final String TITLE = "title";
	public static final String CONTENT = "content";
	@SerializedName(ID)
	private int id;
	@SerializedName(TITLE)
	private String title;
	@SerializedName(CONTENT)
	private String content;
}
