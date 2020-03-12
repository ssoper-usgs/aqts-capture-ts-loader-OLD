package gov.usgs.wma.waterdata;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;


@SpringBootTest(webEnvironment=WebEnvironment.NONE,
		classes={DBTestConfig.class, TransformDao.class})
@DatabaseSetup("classpath:/testData/transformDb/groundwaterStatisticalDailyValue/")

@ActiveProfiles("it")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
		DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class,
		TransactionDbUnitTestExecutionListener.class })
@DbUnitConfiguration(
		dataSetLoader=FileSensingDataSetLoader.class,
		databaseConnection={"transform"}
)
@AutoConfigureTestDatabase(replace=Replace.NONE)
@Transactional(propagation=Propagation.NOT_SUPPORTED)
@Import({DBTestConfig.class})
@DirtiesContext
public class TransformDaoIT {

	@Autowired
	public TransformDao transformDao;

	public RequestObject request;
	List<Map<String, Object>> timeSeriesList;
	public static final String tsUniqueId = "17f83e62b06e4dc29e78d96b4426a255";

	@BeforeEach
	public void setup() {
		request = new RequestObject();
		request.setUniqueId(tsUniqueId);

		Map<String, Object> timeSeries1;
		{
			timeSeries1 = new HashMap<>();
			timeSeries1.put("groundwater_daily_value_identifier", "USGS-132624144452771-17f83e62b06e4dc29e78d96b4426a255");
			timeSeries1.put("time_series_unique_id", "17f83e62b06e4dc29e78d96b4426a255");
			timeSeries1.put("monitoring_location_identifier", "USGS-132624144452771");
			timeSeries1.put("observered_property_id", null);
			timeSeries1.put("statistic_id", null);
			timeSeries1.put("time_step", 2008-06-03);
			timeSeries1.put("unit_of_measure", "ft");
			timeSeries1.put("result", 36.02);
			timeSeries1.put("approvals", "Approved");
			timeSeries1.put("qualifiers", "null");
			timeSeries1.put("grades", "50");

		}
		Map<String, Object> timeSeries2;
		{
			timeSeries2 = new HashMap<>();
			timeSeries2.put("groundwater_daily_value_identifier", "USGS-132624144452771-17f83e62b06e4dc29e78d96b4426a255");
			timeSeries2.put("time_series_unique_id", "17f83e62b06e4dc29e78d96b4426a255");
			timeSeries2.put("monitoring_location_identifier", "USGS-132624144452771");
			timeSeries2.put("observered_property_id", null);
			timeSeries2.put("statistic_id", null);
			timeSeries2.put("time_step", 2008-06-04);
			timeSeries2.put("unit_of_measure", "ft");
			timeSeries2.put("result", 35.96);
			timeSeries2.put("approvals", "Approved");
			timeSeries2.put("qualifiers", null);
			timeSeries2.put("grades", "50");
		}
		Map<String, Object> timeSeries3;
		{
			timeSeries3 = new HashMap<>();
			timeSeries3.put("groundwater_daily_value_identifier", "USGS-132624144452771-17f83e62b06e4dc29e78d96b4426a255");
			timeSeries3.put("time_series_unique_id", "17f83e62b06e4dc29e78d96b4426a255");
			timeSeries3.put("monitoring_location_identifier", "USGS-132624144452771");
			timeSeries3.put("observered_property_id", null);
			timeSeries3.put("statistic_id", null);
			timeSeries3.put("time_step", 2008-06-04);
			timeSeries3.put("unit_of_measure", "ft");
			timeSeries3.put("result", 35.96);
			timeSeries3.put("approvals", "Approved");
			timeSeries3.put("qualifiers", null);
			timeSeries3.put("grades", "50");
		}

		timeSeriesList = new ArrayList<>();
		timeSeriesList.add(timeSeries1);
		timeSeriesList.add(timeSeries2);
		timeSeriesList.add(timeSeries3);
	}

	@Test
	public void testGet() {

		// get new data, return unique ids
		System.out.println(request.getUniqueId());
		List<Map<String, Object>> actualData = transformDao.getTimeSeries(request.getUniqueId());
		assertNotNull(actualData);
		System.out.println(actualData);
		assertThat(actualData, containsInAnyOrder(timeSeriesList));
	}
}