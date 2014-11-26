package ua.com.studiovision.euromaidan;

import com.softevol.activity_service_communication.Protocol;

/**
 * Created by gaidamak on 26.11.14.
 */
public class AppProtocol extends Protocol {
    public static final int DO_LOG_IN = 0;
    public static final int LOG_IN_SUCCESSFUL = 1;
    public static final int LOG_IN_UNSUCCESSFUL = 2;
    public static final int DO_REGISTER = 3;
    public static final int REGISTRATION_SUCCESSFUL = 4;
    public static final int REGISTRATION_UNSUCCESSFUL = 5;
}
