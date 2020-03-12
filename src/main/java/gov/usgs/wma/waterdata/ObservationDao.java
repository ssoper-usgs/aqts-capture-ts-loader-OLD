package gov.usgs.wma.waterdata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class ObservationDao {
	private static final Logger LOG = LoggerFactory.getLogger(ObservationDao.class);

	@Autowired
	@Qualifier("jdbcTemplateObservation")
	protected JdbcTemplate jdbcTemplate;

	@Value("classpath:sql/deleteTimeSeries.sql")
	protected Resource deleteQuery;

	@Value("classpath:sql/insertTimeSeries.sql")
	protected Resource insertQuery;

	public void deleteTimeSeries(String timeSeriesUniqueId) {
		try {
			String sql = new String(FileCopyUtils.copyToByteArray(deleteQuery.getInputStream()));
			jdbcTemplate.update(
					sql,
					timeSeriesUniqueId
			);
		} catch (EmptyResultDataAccessException e) {
			LOG.info("Couldn't find {} - {} ", timeSeriesUniqueId, e.getLocalizedMessage());
		} catch (IOException e) {
			LOG.error("Unable to get SQL statement", e);
			throw new RuntimeException(e);
		}
	}


	public void insertTimeSeries(TimeSeries timeSeries) {
		try {
			String sql = new String(FileCopyUtils.copyToByteArray(insertQuery.getInputStream()));
			jdbcTemplate.update(
					sql,
					new PreparedStatementSetter() {
						@Override
						public void setValues(PreparedStatement ps) throws SQLException {
							int pos = 1;
							ps.setString(pos++, timeSeries.getGroundwaterDailyValueIdentifier());
							ps.setString(pos++, timeSeries.getTimeSeriesUniqueId());
							ps.setString(pos++, timeSeries.getMonitoringLocationIdentifier());
							ps.setString(pos++, timeSeries.getObservedPropertyId());
							ps.setString(pos++, timeSeries.getStatisticId());
							ps.setDate(pos++, timeSeries.getTimeStep());
							ps.setString(pos++, timeSeries.getUnitOfMeasure());
							ps.setString(pos++, timeSeries.getResult());
							ps.setObject(pos++, timeSeries.getApprovals());
							ps.setObject(pos++, timeSeries.getQualifiers());
							ps.setObject(pos++, timeSeries.getGrades());
						}
					}
			);
		} catch (EmptyResultDataAccessException e) {
			LOG.info("Couldn't find {} - {} ", timeSeries.getTimeSeriesUniqueId(), e.getLocalizedMessage());
		} catch (IOException e) {
			LOG.error("Unable to get SQL statement", e);
			throw new RuntimeException(e);
		}
	}
}
