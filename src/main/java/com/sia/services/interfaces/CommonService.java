/**
 * 
 */
package com.sia.services.interfaces;

import java.math.BigInteger;
import java.sql.Connection;

/**
 * @author randd1
 *
 */
public interface CommonService<Any> {
	public Any findById(Connection conn, BigInteger id);
	public Any create(Connection conn, Any object);
	public Any update(Connection conn, Any object);
	public Any delete(Connection conn, Any object);
}
