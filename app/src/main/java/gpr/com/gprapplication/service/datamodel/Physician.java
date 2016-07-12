package gpr.com.gprapplication.service.datamodel;

public class Physician extends BaseModel {
	private static final long serialVersionUID = -2891662278983175691L;
	private long id;
	private String fullName;
	private Long physicianId;
	private String specialityName;
	private String specialityId;
	private String specialityVersion;
	private String superSpecialityName;
	private String superSpecialityId;
	private String superSpecialityVersion;
	private String hospitalAffiliation;
	private String degree;
	private String cellPhoneNumber;
	private String emailAddress;
	private String city;
	private String state;
	private String postalCode;
	private String countryName;
	private String countryCode;
	private String countryId;
	private String countryVersion;
	private String version;
	private String imageFileName;
	private String imageBaseUrl;

	private String department;
	private String title;
	private String statusMessage;
	private String medicalSchoolAttended;
	private String graduationYear;
	private String description;
	private String registrationNumber;
	
	private String location;
	private String firstName;
	private String lastName;
	private String gender;
	
	private String residencyTraining;
	private String forwardEmailAddress;
	private String workPhoneNumber;
	private String faxNumber;
	private String appointmentHotline;
	private String insuranceAccepted;
	
	private String streetAddress1;
	private String streetAddress2;
	

	public Physician() {
		super();
	}

	public Physician(String id, String fullName, String imageUrlPrefix,
					 String imageName) {
		super();
		this.physicianId = Long.valueOf(id);
		this.fullName = fullName;
		this.imageBaseUrl = imageUrlPrefix;
		this.imageFileName = imageName;
	}

	public synchronized Long getId() {
		return id;
	}

	public synchronized void setId(Long id) {
		this.id = id;
	}

	public synchronized String getFullName() {
		return fullName;
	}

	public synchronized void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public long getPhysicianId() {
		return physicianId;
	}

	public void setPhysicianId(long physicianId) {
		this.physicianId = physicianId;
	}

	public String getSpecialityName() {
		return specialityName;
	}

	public void setSpecialityName(String specialityName) {
		this.specialityName = specialityName;
	}

	public String getSpecialityId() {
		return specialityId;
	}

	public void setSpecialityId(String specialityId) {
		this.specialityId = specialityId;
	}

	public String getSpecialityVersion() {
		return specialityVersion;
	}

	public void setSpecialityVersion(String specialityVersion) {
		this.specialityVersion = specialityVersion;
	}

	public String getSuperSpecialityName() {
		return superSpecialityName;
	}

	public void setSuperSpecialityName(String superSpecialityName) {
		this.superSpecialityName = superSpecialityName;
	}

	public String getSuperSpecialityId() {
		return superSpecialityId;
	}

	public void setSuperSpecialityId(String superSpecialityId) {
		this.superSpecialityId = superSpecialityId;
	}

	public String getSuperSpecialityVersion() {
		return superSpecialityVersion;
	}

	public void setSuperSpecialityVersion(String superSpecialityVersion) {
		this.superSpecialityVersion = superSpecialityVersion;
	}

	public String getHospitalAffiliation() {
		return hospitalAffiliation;
	}

	public void setHospitalAffiliation(String hospitalAffiliation) {
		this.hospitalAffiliation = hospitalAffiliation;
	}

	public String getCellPhoneNumber() {
		return cellPhoneNumber;
	}

	public void setCellPhoneNumber(String cellPhoneNumber) {
		this.cellPhoneNumber = cellPhoneNumber;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	public String getCountryVersion() {
		return countryVersion;
	}

	public void setCountryVersion(String countryVersion) {
		this.countryVersion = countryVersion;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public synchronized String getImageFileName() {
		return imageFileName;
	}

	public synchronized void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public synchronized String getImageBaseUrl() {
		return imageBaseUrl;
	}

	public synchronized void setImageBaseUrl(String imageBaseUrl) {
		this.imageBaseUrl = imageBaseUrl;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public String getMedicalSchoolAttended() {
		return medicalSchoolAttended;
	}

	public void setMedicalSchoolAttended(String medicalSchoolAttended) {
		this.medicalSchoolAttended = medicalSchoolAttended;
	}

	public String getGraduationYear() {
		return graduationYear;
	}

	public void setGraduationYear(String graduationYear) {
		this.graduationYear = graduationYear;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getResidencyTraining() {
		return residencyTraining;
	}

	public void setResidencyTraining(String residencyTraining) {
		this.residencyTraining = residencyTraining;
	}

	public String getForwardEmailAddress() {
		return forwardEmailAddress;
	}

	public void setForwardEmailAddress(String forwardEmailAddress) {
		this.forwardEmailAddress = forwardEmailAddress;
	}

	public String getWorkPhoneNumber() {
		return workPhoneNumber;
	}

	public void setWorkPhoneNumber(String workPhoneNumber) {
		this.workPhoneNumber = workPhoneNumber;
	}

	public String getFaxNumber() {
		return faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	public String getStreetAddress1() {
		return streetAddress1;
	}

	public void setStreetAddress1(String streetAddress1) {
		this.streetAddress1 = streetAddress1;
	}

	public String getStreetAddress2() {
		return streetAddress2;
	}

	public void setStreetAddress2(String streetAddress2) {
		this.streetAddress2 = streetAddress2;
	}

	public String getAppointmentHotline() {
		return appointmentHotline;
	}

	public void setAppointmentHotline(String appointmentHotline) {
		this.appointmentHotline = appointmentHotline;
	}

	public String getInsuranceAccepted() {
		return insuranceAccepted;
	}

	public void setInsuranceAccepted(String insuranceAccepted) {
		this.insuranceAccepted = insuranceAccepted;
	}
}
