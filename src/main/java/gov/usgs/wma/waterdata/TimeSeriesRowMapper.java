package gov.usgs.wma.waterdata;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class TimeSeriesRowMapper implements RowMapper<TimeSeries> {

	@Override
	public TimeSeries mapRow(ResultSet rs, int rowNum) throws SQLException {
		TimeSeries timeSeries = new TimeSeries();

		timeSeries.setGroundwaterDailyValueIdentifier(rs.getString("groundwater_daily_value_identifier"));
		timeSeries.setGroundwaterDailyValueIdentifier(rs.getString("time_series_unique_id"));
		timeSeries.setGroundwaterDailyValueIdentifier(rs.getString("monitoring_location_identifier"));
		timeSeries.setGroundwaterDailyValueIdentifier(rs.getString("observered_property_id"));
		timeSeries.setGroundwaterDailyValueIdentifier(rs.getString("statistic_id"));
		timeSeries.setGroundwaterDailyValueIdentifier(rs.getString("time_step"));
		timeSeries.setGroundwaterDailyValueIdentifier(rs.getString("unit_of_measure"));
		timeSeries.setGroundwaterDailyValueIdentifier(rs.getString("result"));
		timeSeries.setGroundwaterDailyValueIdentifier(rs.getString("approvals"));
		timeSeries.setGroundwaterDailyValueIdentifier(rs.getString("qualifiers"));
		timeSeries.setGroundwaterDailyValueIdentifier(rs.getString("grades"));

		return timeSeries;
	}

}