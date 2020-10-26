package be.nabu.libs.tracer.dynatrace;

import com.dynatrace.oneagent.sdk.api.DatabaseRequestTracer;

import be.nabu.libs.tracer.api.DatabaseRequestTrace;

public class DynatraceDatabaseRequestTrace implements DatabaseRequestTrace {

	private DatabaseRequestTracer tracer;
	private DynatraceProvider provider;

	public DynatraceDatabaseRequestTrace(DynatraceProvider provider, DatabaseRequestTracer tracer) {
		this.provider = provider;
		this.tracer = tracer;
	}
	
	@Override
	public void start() {
		tracer.start();
	}

	@Override
	public void addCustomAttribute(String key, Object value) {
		provider.getOneAgent().addCustomRequestAttribute(key, value.toString());
	}

	@Override
	public void stop() {
		tracer.end();
	}

	@Override
	public void error(String code, Exception e) {
		tracer.error(e);
	}

	@Override
	public void setBatchSize(int size) {
		// do nothing
	}

	@Override
	public void setRowCount(int count) {
		tracer.setReturnedRowCount(count);
	}

	@Override
	public void setLazy(boolean lazy) {
		// do nothing
	}

	@Override
	public void setLimit(int limit) {
		// do nothing
	}

	@Override
	public void setOffset(long offset) {
		// do nothing
	}

}
