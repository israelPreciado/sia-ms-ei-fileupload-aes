/**
 * 
 */
package com.sia.services.interfaces;

import java.math.BigInteger;
import java.sql.Connection;
import java.util.List;

import com.sia.model.FilesRepFotografico;

public interface FilesRepFotograficoService extends CommonService<FilesRepFotografico> {
	public List<FilesRepFotografico> findAllByReporteFotograficoId(Connection conn, BigInteger id);
}
