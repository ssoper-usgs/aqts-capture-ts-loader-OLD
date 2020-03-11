package gov.usgs.wma.waterdata;

import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoadGwDvTimeSeries implements Function<RequestObject, ResultObject> {

	private static final Logger LOG = LoggerFactory.getLogger(LoadGwDvTimeSeries.class);

	private GroundwaterStatisticalDvDao gwStatDvDao;
	private GroundwaterDvDao gwDvDao;

	@Autowired
	public LoadGwDvTimeSeries(GroundwaterStatisticalDvDao gwStatDvDao, GroundwaterDvDao gwDvDao) {
		this.gwStatDvDao = gwStatDvDao;
		this.gwDvDao = gwDvDao;
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
			// in the same transaction:
			// 2. delete the time series in the observation db
			// 3. insert the time series in the observation db

			LOG.debug("Successfully inserted time series with unique id: {} ", result.getUniqueId());
		}
		return result;
	}
}