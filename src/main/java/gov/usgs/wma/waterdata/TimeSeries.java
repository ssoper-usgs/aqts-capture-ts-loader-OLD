package gov.usgs.wma.waterdata;

import org.postgresql.util.PGobject;

import java.sql.Date;


public class TimeSeries {
	String groundwaterDailyValueIdentifier;
	String timeSeriesUniqueId;
	String monitoringLocationIdentifier;
	String observedPropertyId;
	String statisticId;
	Date timeStep;
	String unitOfMeasure;
	String result;
	PGobject approvals;
	PGobject qualifiers;
	PGobject grades;

	public String getGroundwaterDailyValueIdentifier() {
		return groundwaterDailyValueIdentifier;
	}

	public void setGroundwaterDailyValueIdentifier(String groundwaterDailyValueIdentifier) {
		this.groundwaterDailyValueIdentifier = groundwaterDailyValueIdentifier;
	}

	public String getTimeSeriesUniqueId() {
		return timeSeriesUniqueId;
	}

	public void setTimeSeriesUniqueId(String timeSeriesUniqueId) {
		this.timeSeriesUniqueId = timeSeriesUniqueId;
	}

	public String getMonitoringLocationIdentifier() {
		return monitoringLocationIdentifier;
	}

	public void setMonitoringLocationIdentifier(String monitoringLocationIdentifier) {
		this.monitoringLocationIdentifier = monitoringLocationIdentifier;
	}

	public String getObservedPropertyId() {
		return observedPropertyId;
	}

	public void setObservedPropertyId(String observedPropertyId) {
		this.observedPropertyId = observedPropertyId;
	}

	public String getStatisticId() {
		return statisticId;
	}

	public void setStatisticId(String statisticId) {
		this.statisticId = statisticId;
	}

	public Date getTimeStep() {
		return timeStep;
	}

	public void setTimeStep(Date timeStep) {
		this.timeStep = timeStep;
	}

	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}

	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public PGobject getApprovals() {
		return approvals;
	}

	public void setApprovals(PGobject approvals) {
		this.approvals = approvals;
	}

	public PGobject getQualifiers() {
		return qualifiers;
	}

	public void setQualifiers(PGobject qualifiers) {
		this.qualifiers = qualifiers;
	}

	public PGobject getGrades() {
		return grades;
	}

	public void setGrades(PGobject grades) {
		this.grades = grades;
	}
}
