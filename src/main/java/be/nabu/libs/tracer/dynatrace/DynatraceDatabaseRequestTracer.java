/*
* Copyright (C) 2020 Alexander Verbruggen
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU Lesser General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU Lesser General Public License for more details.
*
* You should have received a copy of the GNU Lesser General Public License
* along with this program. If not, see <https://www.gnu.org/licenses/>.
*/

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
