package gov.usgs.wma.waterdata;

import java.util.List;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoadTimeSeries implements Function<RequestObject, ResultObject> {

	private static final Logger LOG = LoggerFactory.getLogger(LoadTimeSeries.class);

	private TransformDao transformDao;
	private ObservationDao observationDao;

	@Autowired
	public LoadTimeSeries(TransformDao transformDao, ObservationDao observationDao) {
		this.transformDao = transformDao;
		this.observationDao = observationDao;
	}

	@Override
	public  ResultObject apply(RequestObject request) {
		return processRequest(request);
	}

	protected ResultObject processRequest(RequestObject request) {

		String timeSeriesUniqueId = request.getUniqueId();
		ResultObject result = new ResultObject();

		if (null != timeSeriesUniqueId) {
			// 1. select the time series from the transform db
			List<TimeSeries> timeSeries = transformDao.getTimeSeries(timeSeriesUniqueId);
			// in the same transaction:
			// 2. delete the time series in the observation db
			observationDao.deleteTimeSeries(timeSeriesUniqueId);
			// 3. insert the time series in the observation db
			Integer count = 0;
			for (TimeSeries ts : timeSeries) {
				count += observationDao.insertTimeSeries(ts);
			}
			result.setCount(count);

			LOG.debug("Successfully inserted time series with unique id: {} ", timeSeriesUniqueId);
		}
		return result;
	}
}