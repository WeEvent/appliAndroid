package nf28.weevent.Controller.Gcm;

public interface Config {

	// used to share GCM regId with application server - using php app server
	//static final String APP_SERVER_URL = "http://nicolasrouquette.com/gcm_server.php/?push=1";
    //static final String APP_SERVER_URL = "http://nicolasrouquette.com/gcm_server.php?shareRegId=1";
    static final String APP_SERVER_URL_SEND_NOTIF = "http://nicolasrouquette.com/gcm_server.php/?push=1";

    // GCM server using java
	// static final String APP_SERVER_URL =
	// "http://192.168.1.17:8080/GCM-App-Server/GCMNotification?shareRegId=1";

	// Google Project Number
	static final String GOOGLE_PROJECT_ID = "1030532129081";
	static final String MESSAGE_KEY = "message";

}
