package gpr.com.gprapplication.utility;

public interface GPRConstants {
	//public static final String API_URL = "http://10.0.1.5:8080/gpr";
//	public static final String API_URL = "http://dev.gprapp.com:8080/gpr";
	public static final String API_URL = "https://i.gprapp.com";
//    	public static final String API_URL = "http://jaya-PC:8080";

    public static final String LOGIN_API = "/api/auth/user";
	public static final String VALIDATE_TOKEN_API = "/api/auth/token";
	public static final String LOGIN_URL = "/api/auth/user";
	public static final String PHY_BY_NAME_URL = "/api/enrollments/search";
	public static final String INCOMING_URL = "/api/referrals/get";
	public static final String OUTGOING_URL = "/api/referrals/get";
	public static final String RECENT_URL = "/api/referrals/recent";
	public static final String CREATE_URL = "/api/referrals/create";
	public static final String REJECT_REFFERRAL_URL = "/api/referrals/reject";
	public static final String ACCEPT_REFFERRAL_URL = "/api/referrals/accept";
	public static final String FORWARD_REFFERRAL_URL = "/api/referrals/forward";
	public static final String ADD_FAVORITE_URL = "/api/profile/addFavorite";

	public static final String GET_FAVORITE_URL = "/api/profile/getFavorite";

	public static final String PHY_DETAIL_URL = "/api/enrollments/detail";
	public static final String FILTERS_URL = "/api/filter/get";
	public static final String PROFILE_UPDATE_URL = "/api/profile/update";
	public static final String PROFILE_UPDATE_DELTA_URL = "/api/profile/update/delta";
	public static final String PHY_BY_FILTER_URL = "/api/enrollments/search";
	public static final String PROFILE_IMG_URL = "/api/profile/image";
	public static final String CODE_VERIFY_URL = "/api/signup/code";
	public static final String VALIDATE_USER_URL = "/api/signup/validate/user";
	public static final String LOOKUP_URL = "/api/lookup";
	public static final String CREATE_USER_URL = "/api/signup/create/lite";
	public static final String CHANGE_PASSWORD_URL = "/api/settings/password/update";
	public static final String FORGOT_PASSWORD_URL = "/api/settings/forgot/password";
	public static final String PASSWORD_RESET_URL = "/api/settings/password/reset";
	public static final String GCM_REGISTRATION_API = "/api/settings/register/notification";
	
	public static final String PKG_NAME = "com.gprapp";
	
	public static final String REFERRAL = "referral";
	public static final String PHYSICAN = "physician";
	
	public static final int REFERRAL_LIST_SIZE = 10;
	public static final int REFERRAL_LIST_REFRESH_INTERVAL = 10 * 60 * 1000;
	
	public static final String PROFILE_IMG = "image.jpg";
	
	public static final String SUCCESS = "SUCCESS";
	public static final String ERROR = "ERROR";
	
	public static final String EXTRA_USER_ID = "com.bostonnh.gpr.extra.USER_ID";
	public static final String EXTRA_CODE_RESPONSE = "com.bostonnh.gpr.extra.CODE_RESPONSE";
	public static final String EXTRA_CREATE_USER_REQUEST = "com.bostonnh.gpr.extra.CREATE_USER_REQUEST";
	public static final String EXTRA_SUGGEST = "com.bostonnh.gpr.extra.SUGGEST";
	public static final String EXTRA_SEARCH_TERM = "com.bostonnh.gpr.extra.SEARCH_TERM";
	public static final String EXTRA_RESULT= "com.bostonnh.gpr.extra.RESULT";
	public static final String EXTRA_REQUEST_CODE= "com.bostonnh.gpr.extra.REQUEST_CODE";
	public static final String EXTRA_VERIFICATION_ID= "com.bostonnh.gpr.extra.VERIFICATION_ID";
	
	public static final String TOKEN = "token";
	public static final String FULL_NAME = "fullName";
	public static final String USERNAME = "username";
	public static final String ENROLLMENT_ID = "enrollmentId";
	public static final String PROFILE_IMAGE = "profileImage";
	public static final String IMAGE_URL_PREFIX = "imageUrlPrefix";
	
	public static final int REQUEST_BASIC_EDIT = 100;
	public static final int REQUEST_GENERAL_EDIT = 101;
	public static final int REQUEST_PROFESSIONAL_EDIT = 102;
	public static final int REQUEST_EDUCATION_EDIT = 103;
	public static final int REQUEST_CONTACT_EDIT = 104;
	public static final int REQUEST_LOCATION_EDIT = 105;
	
	public static final String PREF_SEARCH_SPECIALITY = "SEARCH_SPECIALITY";
	public static final String PREF_SEARCH_SUPER_SPECIALITY = "SEARCH_SUPER_SPECIALITY";
	public static final String PREF_SEARCH_STATE = "SEARCH_STATE";
	public static final String PREF_SEARCH_COUNTRY = "SEARCH_COUNTRY";
	
	public static final String PROPERTY_REG_ID = "registration_id";
    public static final String PROPERTY_APP_VERSION = "appVersion";
    public static final String PROPERTY_REG_ID_SENT = "registration_id_sent";
    public static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    
    public String SENDER_ID = "431568359079";

	public static final String RECENT_LIST_LIMIT = "10";

}
