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
public class GroundwaterStatisticalDvDao {
	private static final Logger LOG = LoggerFactory.getLogger(GroundwaterStatisticalDvDao.class);

	@Autowired
	@Qualifier("jdbcTemplateTransform")
	protected JdbcTemplate jdbcTemplate;

	@Value("classpath:sql/getTimeSeries.sql")
	protected Resource selectQuery;

	public Map<String, Object> getTimeSeries(String timeSeriesUniqueId) {
		Map<String, Object> rtn = null;
		try {
			String sql = new String(FileCopyUtils.copyToByteArray(selectQuery.getInputStream()));
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

		// Maybe we'll want to know what was selected?
		return rtn;
	}
}
