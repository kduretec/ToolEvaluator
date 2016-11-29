package org.benchmarkdp.toolevaluator.elements;

import java.util.HashMap;
import java.util.Map;

public class MeasureValue {

	private Map<String, Object> measureValues;

	public MeasureValue() {
		measureValues = new HashMap<String, Object>();
	}

	public void addMeasureValue(String mName, Object value) {
		measureValues.put(mName, value);
	}

	public void addMeasureValue(Map<String, Object> values) {
		measureValues.putAll(values);
	}

	public Object getMeasureValue(String name) {
		return measureValues.get(name);
	}

	public Map<String, Object> getAllMeasures() {
		return measureValues;
	}

}
