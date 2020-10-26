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
