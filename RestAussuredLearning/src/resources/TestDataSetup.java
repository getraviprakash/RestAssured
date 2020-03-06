package resources;

public class TestDataSetup
{
	private static String authUsername = "a1b55e65";
	private static String authPassword = "2c4ffbf6c49135dcb6d00d8be3ad832e";
	private static String grant_type = "password";
	private static String username = "vicente@thinkhr.com";
	private static String password = "Master2013@";
	private static String AuthTokenURL = "v2/oauth/token";
	private static String CompanyTypesURL = "v2/company-types";
	private static String auth_token = "MGRkM2U2ZDk6YThmMTNkNzU2MjVmMGExYTQyZDhmYWFlODUxNTBjNGE=";
	private static String access_token;
	private static String V2URL = "https://restapis.thinkhr-pscluster.com/";
	private static String UserRole = "v2/roles";
	private static String ResetPasswordURL = "v2/users/send-reset-password-email";
	
	
	public static String getAuthUsername() {
		return authUsername;
	}
	public void setAuthUsername(String authUsername) {
		this.authUsername = authUsername;
	}
	
	public static String getAuthPassword() {
		return authPassword;
	}
	public void setAuthPassword(String authPassword) {
		this.authPassword = authPassword;
	}
	
	public static String getGrant_type() {
		return grant_type;
	}
	public void setGrant_type(String grant_type) {
		this.grant_type = grant_type;
	}
	
	public static String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public static String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public static String getAuthTokenURL() {
		return AuthTokenURL;
	}
	public void setAuthTokenURL(String authTokenURL) {
		AuthTokenURL = authTokenURL;
	}
	
	public static String getAuth_token() {
		return auth_token;
	}
	public void setAuth_token(String auth_token) {
		this.auth_token = auth_token;
	}
	
	
	public static String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	
	
	public static String getV2URL() {
		return V2URL;
	}
	public void setV2URL(String v2url) {
		V2URL = v2url;
	}
	public static String getCompanyTypesURL() {
		return CompanyTypesURL;
	}
	public static void setCompanyTypesURL(String companyTypesURL) {
		CompanyTypesURL = companyTypesURL;
	}
	public static String getUserRole() {
		return UserRole;
	}
	public static void setUserRole(String userRole) {
		UserRole = userRole;
	}

	public static String getResetPasswordURL() {
		return UserRole;
	}
	public static void setResetPasswordURL(String ResetPasswordURL) {
		UserRole = ResetPasswordURL;
	}



}


