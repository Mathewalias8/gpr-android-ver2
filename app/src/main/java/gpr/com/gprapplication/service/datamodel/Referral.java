package gpr.com.gprapplication.service.datamodel;

/**
 * DTO class for Referrals
 */
public class Referral extends BaseModel {
	private static final long serialVersionUID = 8210577104805189703L;

	private Long id;
	private String urgency;
	private String patientName;
    private String patientAge;
    private String patientContactPhoneCountryCode;
    private String patientContactPhone;
    private String patientContactEmail;
    private String patientContactAddress;
    private String reason;
    
    private Long createdOn;
    private Long updatedOn;
    
    private SimpleEnrollment referringTo = new SimpleEnrollment();
    private SimpleEnrollment referringFrom = new SimpleEnrollment();
    
    private String status;
    
    private Boolean archived;
    
    private ReferralType referralType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrgency() {
		return urgency;
	}

	public void setUrgency(String urgency) {
		this.urgency = urgency;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getPatientAge() {
		return patientAge;
	}

	public void setPatientAge(String patientAge) {
		this.patientAge = patientAge;
	}

	public String getPatientContactPhoneCountryCode() {
		return patientContactPhoneCountryCode;
	}

	public void setPatientContactPhoneCountryCode(
			String patientContactPhoneCountryCode) {
		this.patientContactPhoneCountryCode = patientContactPhoneCountryCode;
	}

	public String getPatientContactPhone() {
		return patientContactPhone;
	}

	public void setPatientContactPhone(String patientContactPhone) {
		this.patientContactPhone = patientContactPhone;
	}

	public String getPatientContactEmail() {
		return patientContactEmail;
	}

	public void setPatientContactEmail(String patientContactEmail) {
		this.patientContactEmail = patientContactEmail;
	}

	public String getPatientContactAddress() {
		return patientContactAddress;
	}

	public void setPatientContactAddress(String patientContactAddress) {
		this.patientContactAddress = patientContactAddress;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Long getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Long createdOn) {
		this.createdOn = createdOn;
	}

	public Long getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Long updatedOn) {
		this.updatedOn = updatedOn;
	}

	public SimpleEnrollment getReferringTo() {
		return referringTo;
	}

	public void setReferringTo(SimpleEnrollment referringTo) {
		this.referringTo = referringTo;
	}

	public SimpleEnrollment getReferringFrom() {
		return referringFrom;
	}

	public void setReferringFrom(SimpleEnrollment referringFrom) {
		this.referringFrom = referringFrom;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Boolean getArchived() {
		return archived;
	}

	public void setArchived(Boolean archived) {
		this.archived = archived;
	}

	public ReferralType getReferralType() {
		return referralType;
	}

	public void setReferralType(ReferralType referralType) {
		this.referralType = referralType;
	}
}
