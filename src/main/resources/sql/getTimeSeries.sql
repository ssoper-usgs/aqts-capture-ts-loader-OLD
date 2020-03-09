select
    groundwater_daily_value_identifier,
    time_series_unique_id,
    monitoring_location_identifier,
    observed_property_id,
    statistic_id,
    time_step,
    unit_of_measure,
    result,
    approvals,
    qualifiers,
    grades
from
    groundwater_statistical_daily_value
where
    time_series_unique_id = ?
