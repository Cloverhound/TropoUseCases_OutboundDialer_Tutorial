package com.tropo.demo;

import static com.voxeo.tropo.Key.FROM;
import static com.voxeo.tropo.Key.TO;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.voxeo.tropo.Tropo;
import com.voxeo.tropo.TropoException;
import com.voxeo.tropo.TropoLaunchResult;
import com.voxeo.tropo.TropoSession;

public class CallServlet extends HttpServlet{
	
//    private static final String TO = "Number to Dial"
	private static final String TO = "18472072743";
//    private static final String FROM_DALLAS = "Caller Id from Dallas";
//    private static final String FROM_SAN_FRANCISCO = "Caller Id from San Francisco";
//    private static final String FROM_CHICAGO = "Caller Id from Chicago";
	private static final String FROM_PARAMETER = "from";
	private static final String FROM_DALLAS = "11111111111";
    private static final String FROM_SAN_FRANCISCO = "12222222222";
    private static final String FROM_CHICAGO = "13333333333";
//    private static final String TOKEN = "Your Tropo App Token";
    private static final String TOKEN = "110febe1c6d1764cb25da8b5201c094066fb7b47fb2ad8364ce74c01b05f9b6db0d9856eb3f8d6caf13ad03f"; 
    private static final String MESSAGE = "Hello. My city is the best.";
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			callFromDallas();
			TimeUnit.SECONDS.sleep(10);
			callFromSanFrancisco();
			TimeUnit.SECONDS.sleep(10);
			callFromChicago();
		} catch (InterruptedException e) {
			// handle exception
		}
        
    }
    
    protected void callFromDallas() {
    	System.out.println("Calling from Dallas");
    	
    	Map params = new HashMap();
        params.put("from", FROM_DALLAS);
        
    	Tropo tropo = new Tropo();
        TropoLaunchResult result = tropo.launchSession(TOKEN, params);
    }
    
    protected void callFromSanFrancisco() {
    	System.out.println("Calling from San Francisco");
    	
    	Map params = new HashMap();
        params.put("from", FROM_SAN_FRANCISCO);
        
    	Tropo tropo = new Tropo();
        TropoLaunchResult result = tropo.launchSession(TOKEN, params);
    }
    
    protected void callFromChicago() {
    	System.out.println("Calling from Chicago");
    	
    	Map params = new HashMap();
        params.put("from",FROM_CHICAGO);
        
        Tropo tropo = new Tropo();
        TropoLaunchResult result = tropo.launchSession(TOKEN, params);
    }
	

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		
        Tropo tropo = new Tropo();
        TropoSession session = tropo.session(request);
       
        String from = session.getParameters().get(FROM_PARAMETER);
        
        System.out.println("Receiving request from tropo to call with callerid: " + from);

        try {
            tropo.call(TO(TO), FROM(from));
            tropo.say(MESSAGE);
            tropo.render(response);
        } catch (TropoException te) {
            te.printStackTrace();
        }
    }

}
