package org.shujito.sparksample;

import org.skife.jdbi.v2.DBI;

import java.util.HashMap;
import java.util.UUID;

import spark.Request;
import spark.Response;

/**
 * @author shujito, 10/28/16
 */
public class RootController {
	public static final String TAG = RootController.class.getSimpleName();
	private final RootDao rootDao;

	public RootController(DBI dbi) {
		this.rootDao = dbi.onDemand(RootDao.class);
	}

	public Object getRoot(Request request, Response response) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("millis", this.rootDao.unixtime());
		map.put("uuid", UUID.randomUUID());
		return map;
	}
}
