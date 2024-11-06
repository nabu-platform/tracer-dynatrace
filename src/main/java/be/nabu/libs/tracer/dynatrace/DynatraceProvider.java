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

import java.net.URI;

import com.dynatrace.oneagent.sdk.OneAgentSDKFactory;
import com.dynatrace.oneagent.sdk.api.OneAgentSDK;
import com.dynatrace.oneagent.sdk.api.enums.ChannelType;
import com.dynatrace.oneagent.sdk.api.infos.DatabaseInfo;

import be.nabu.libs.tracer.api.DatabaseRequestTracer;
import be.nabu.libs.tracer.api.IncomingWebRequestTracer;
import be.nabu.libs.tracer.api.TracerProvider;

public class DynatraceProvider implements TracerProvider {

	// they suggest using the same instance throughout the application, and preferably the entire JVM
	private static OneAgentSDK oneAgentSdk;
	
	@Override
	public DatabaseRequestTracer newDatabaseRequestTracer(String databaseId, String vendor, URI endpoint) {
		DatabaseInfo databaseInfo = getOneAgent().createDatabaseInfo(
			databaseId, 
			vendor, 
			ChannelType.TCP_IP, 
			endpoint.toString()
		);
		return new DynatraceDatabaseRequestTracer(this, databaseInfo);
	}

	@Override
	public IncomingWebRequestTracer newIncomingWebRequestTracer(String applicationId) {
		// TODO Auto-generated method stub
		return null;
	}

	public OneAgentSDK getOneAgent() {
		if (oneAgentSdk == null) {
			synchronized(this) {
				if (oneAgentSdk == null) {
					oneAgentSdk = OneAgentSDKFactory.createInstance();
					System.out.println("Dynatrace OneAgent state: " + oneAgentSdk.getCurrentState());
					
				}
			}
		}
		return oneAgentSdk;
	}

	@Override
	public void setCorrelationId(String correlationId) {
		// do nothing for now?
	}

	@Override
	public String getCorrelationId() {
		return null;
	}
	
}
