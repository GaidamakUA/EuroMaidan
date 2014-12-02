package ua.com.studiovision.euromaidan;

import com.softevol.activity_service_communication.Protocol;

public class AppProtocol extends Protocol {
    // Login activity
    public static final int DO_LOG_IN = 0;
    public static final int LOG_IN_SUCCESSFUL = 1;
    public static final int LOG_IN_UNSUCCESSFUL = 2;

    // Registration activity
    public static final int DO_REGISTER = 3;
    public static final int REGISTRATION_SUCCESSFUL = 4;
    public static final int REGISTRATION_UNSUCCESSFUL = 5;

    // First run activity search
    public static final int REQUEST_COUNTRIES = 6;
    public static final int REQUEST_CITIES = 7;
    public static final int REQUEST_SCHOOLS = 8;
    public static final int REQUEST_UNIVERSITIES = 9;
    // First run activity send data
    public static final int SEND_SCHOOL = 10;
    public static final int SEND_UNIVERSITY = 11;

    // Settings activity send data
    public static final int SEND_PROFILE = 12;
}
