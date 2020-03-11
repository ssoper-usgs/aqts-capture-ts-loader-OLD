package gov.usgs.wma.waterdata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.util.Map;

@Component
public class GroundwaterDvDao {
	private static final Logger LOG = LoggerFactory.getLogger(GroundwaterDvDao.class);

	@Autowired
	@Qualifier("jdbcTemplateObservation")
	protected JdbcTemplate jdbcTemplate;

	@Value("classpath:sql/deleteTimeSeries.sql")
	protected Resource deleteQuery;

	@Value("classpath:sql/insertTimeSeries.sql")
	protected Resource insertQuery;

	public Map<String, Object> deleteTimeSeries(String timeSeriesUniqueId) {
		Map<String, Object> rtn = null;
		try {
			String sql = new String(FileCopyUtils.copyToByteArray(deleteQuery.getInputStream()));
			rtn = jdbcTemplate.queryForMap(
					sql,
					timeSeriesUniqueId
			);
		} catch (EmptyResultDataAccessException e) {
			LOG.info("Couldn't find {} - {} ", timeSeriesUniqueId, e.getLocalizedMessage());
		} catch (IOException e) {
			LOG.error("Unable to get SQL statement", e);
			throw new RuntimeException(e);
		}

		// Maybe we'll want to know what was deleted?
		return rtn;
	}

	public Map<String, Object> insertTimeSeries(String timeSeriesUniqueId) {
		Map<String, Object> rtn = null;
		try {
			String sql = new String(FileCopyUtils.copyToByteArray(insertQuery.getInputStream()));
			rtn = jdbcTemplate.queryForMap(
					sql,
					timeSeriesUniqueId
			);
		} catch (EmptyResultDataAccessException e) {
			LOG.info("Couldn't find {} - {} ", timeSeriesUniqueId, e.getLocalizedMessage());
		} catch (IOException e) {
			LOG.error("Unable to get SQL statement", e);
			throw new RuntimeException(e);
		}

		// Maybe we'll want to know what was inserted?
		return rtn;
	}
}
