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
