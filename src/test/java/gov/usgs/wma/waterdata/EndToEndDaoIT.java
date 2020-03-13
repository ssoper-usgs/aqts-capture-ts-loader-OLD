package gov.usgs.wma.waterdata;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
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
import org.springframework.util.LinkedCaseInsensitiveMap;


@SpringBootTest(webEnvironment=WebEnvironment.NONE,
		classes={DBTestConfig.class, TransformDao.class, ObservationDao.class})
//		classes={DBTestConfig.class, ObservationDao.class})

//@DatabaseSetup("classpath:/testData/transformDb/groundwaterStatisticalDailyValue/")

@ActiveProfiles("it")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
		DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class,
		TransactionDbUnitTestExecutionListener.class })
@DbUnitConfiguration(
		dataSetLoader=FileSensingDataSetLoader.class,
//		databaseConnection={"observation"}
		databaseConnection={"transform", "observation"}
)
@AutoConfigureTestDatabase(replace=Replace.NONE)
@Transactional(propagation=Propagation.NOT_SUPPORTED)
@Import({DBTestConfig.class})
@DirtiesContext
public class EndToEndDaoIT {

	@Autowired
	public TransformDao transformDao;

	@Autowired
	public ObservationDao observationDao;

	public RequestObject request;
	public static final String tsUniqueId = "17f83e62b06e4dc29e78d96b4426a255";

	@BeforeEach
	public void setup() {
		request = new RequestObject();
		request.setUniqueId(tsUniqueId);
	}

	// TODO this is failing, password auth fails for user "schema_owner"
	@Test
	@DatabaseSetup(
			connection="transform",
			value="classpath:/testData/transformDb/groundwaterStatisticalDailyValue/")
	@DatabaseSetup(
			connection="observation",
			value="classpath:/testResult/observationDb/groundwaterDailyValue/empty/")
	@ExpectedDatabase(
			value="classpath:/testResult/observationDb/groundwaterDailyValue/afterInsert/",
			assertionMode= DatabaseAssertionMode.NON_STRICT_UNORDERED,
			connection="observation")
	public void testEndToEnd() {

		// get data
		List<TimeSeries> getData = transformDao.getTimeSeries(request.getUniqueId());
		assertNotNull(getData);

		// delete the data
		observationDao.deleteTimeSeries(request.getUniqueId());
		for (TimeSeries ts : getData) {
			// for each time series, insert it
			observationDao.insertTimeSeries(ts);
		}
	}


}