package be.nabu.libs.tracer.dynatrace;

import com.dynatrace.oneagent.sdk.api.infos.DatabaseInfo;

import be.nabu.libs.tracer.api.DatabaseRequestTrace;
import be.nabu.libs.tracer.api.DatabaseRequestTracer;

public class DynatraceDatabaseRequestTracer implements DatabaseRequestTracer {

	private DatabaseInfo info;
	private DynatraceProvider provider;
	private com.dynatrace.oneagent.sdk.api.DatabaseRequestTracer tracer;

	public DynatraceDatabaseRequestTracer(DynatraceProvider provider, DatabaseInfo info) {
		this.provider = provider;
		this.info = info;
	}
	
	@Override
	public DatabaseRequestTrace newTrace(String artifactId, String type, String sql) {
		tracer = provider.getOneAgent().traceSqlDatabaseRequest(info, sql);
		return new DynatraceDatabaseRequestTrace(
			provider,
			tracer
		);
	}

	@Override
	public void close() throws Exception {
		tracer.end();
	}

}
