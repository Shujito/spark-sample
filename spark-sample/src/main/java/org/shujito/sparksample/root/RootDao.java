package org.shujito.sparksample.root;

import org.skife.jdbi.v2.sqlobject.SqlQuery;

/**
 * @author shujito, 10/28/16
 */
public interface RootDao {
	@SqlQuery("select unixtime from unixtime")
	int unixtime();
}
