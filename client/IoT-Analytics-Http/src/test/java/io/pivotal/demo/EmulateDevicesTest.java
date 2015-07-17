package io.pivotal.demo;

import java.net.UnknownHostException;

import org.junit.Test;
import org.springframework.web.client.RestTemplate;

public class EmulateDevicesTest {

	
  private String URL = "http://localhost:9000";
	
	String[] devices = { 
							"ce:57:41:f0:97:5d",
							"6c:40:08:60:bd:a6",
							"d8:30:62:6f:09:ec",
							"f4:37:b7:99:4d:51",
							"04:54:53:85:9f:05",
							"5a:a3:98:1b:bc:7d",
							"04:54:53:a4:63:cb",
							"04:54:53:6a:83:8a",
							"ec:85:2f:8f:dc:fc", 
							"a4:67:06:80:ee:eb",
							"4e:b5:65:c4:39:db",
							"d6:53:7a:b0:de:e7",
							"e2:cc:3d:eb:f7:45"};
	
	String[] pis = {
		
							"Pi-1",
							"Pi-2",
							"Pi-3"
	};
	
	
	private RestTemplate restTemplate = new RestTemplate();

	@Test public void testControlledSignals() throws UnknownHostException {

		
		int frequency = 2412;

		// equaly distance from all circles
		String device1 = devices[0];
		int signalDev1Pi1 = -80;
		int signalDev1Pi2 = -80;
		int signalDev1Pi3 = -80;
		
		ProbeRequest req1 = new ProbeRequest(device1,signalDev1Pi1, pis[0] ,frequency, System.nanoTime());
		ProbeRequest req2 = new ProbeRequest(device1,signalDev1Pi2, pis[1] ,frequency, System.nanoTime());
		ProbeRequest req3 = new ProbeRequest(device1,signalDev1Pi3, pis[2] ,frequency, System.nanoTime());
		
		// Closer to Pi1
		String device2 = devices[1];
		int signalDev2Pi1 = -75;
		int signalDev2Pi2 = -85;
		int signalDev2Pi3 = -85;
		
		ProbeRequest req4 = new ProbeRequest(device2,signalDev2Pi1, pis[0] ,frequency, System.nanoTime());
		ProbeRequest req5 = new ProbeRequest(device2,signalDev2Pi2, pis[1] ,frequency, System.nanoTime());
		ProbeRequest req6 = new ProbeRequest(device2,signalDev2Pi3, pis[2] ,frequency, System.nanoTime());

		// Closer to Pi2
		String device3 = devices[2];
		int signalDev3Pi1 = -85;
		int signalDev3Pi2 = -75;
		int signalDev3Pi3 = -85;
		
		ProbeRequest req7 = new ProbeRequest(device3,signalDev3Pi1, pis[0] ,frequency, System.nanoTime());
		ProbeRequest req8 = new ProbeRequest(device3,signalDev3Pi2, pis[1] ,frequency, System.nanoTime());
		ProbeRequest req9 = new ProbeRequest(device3,signalDev3Pi3, pis[2] ,frequency, System.nanoTime());

		putProbeReq(req1);
		putProbeReq(req2);
		putProbeReq(req3);
		putProbeReq(req4);
		putProbeReq(req5);
		putProbeReq(req6);
		putProbeReq(req7);
		putProbeReq(req8);
		putProbeReq(req9);
		
	}
	
	 public void testSendSignals() throws UnknownHostException {
		
		for (int i=0; i<devices.length; i++){
			
			for (int j=0; j<pis.length; j++){
				long nanoTimestamp = System.nanoTime();
				String deviceId = devices[i];
				String piId = pis[j];
				int signal_dbm = (int)(Math.random()*100*(-1));
				int frequency = 2412;
				
				ProbeRequest req = new ProbeRequest(deviceId,signal_dbm, piId ,frequency, nanoTimestamp);
				putProbeReq(req);
				try {
					Thread.sleep(1, 1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
		

	}
	
	private void putProbeReq(ProbeRequest req){
		System.out.println("Posting to URL: "+URL);
		ProbeRequest response = restTemplate.postForObject(URL, req, ProbeRequest.class);
	}
		
	
}
