package gpr.com.gprapplication.service.datamodel;

public class User {
	private String token;
	private String username;
	private String fullName;
	private Long enrollmentId;
	private String profileImage;
	private String imageUrlPrefix;

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Long getEnrollmentId() {
		return enrollmentId;
	}

	public void setEnrollmentId(Long enrollmentId) {
		this.enrollmentId = enrollmentId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	public String getImageUrlPrefix() {
		return imageUrlPrefix;
	}

	public void setImageUrlPrefix(String imageUrlPrefix) {
		this.imageUrlPrefix = imageUrlPrefix;
	}
	
	public boolean isValid() {
		return this.token != null && this.token.length() > 0 && this.enrollmentId != null && this.enrollmentId > 0;
	}
}
