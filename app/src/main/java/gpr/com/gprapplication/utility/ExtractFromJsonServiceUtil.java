package gpr.com.gprapplication.utility;

import android.util.Log;



import org.json.JSONException;
import org.json.JSONObject;

import gpr.com.gprapplication.service.datamodel.CreateResponse;
import gpr.com.gprapplication.service.datamodel.PhyFilter;
import gpr.com.gprapplication.service.datamodel.Physician;
import gpr.com.gprapplication.service.datamodel.Referral;
import gpr.com.gprapplication.service.datamodel.SimpleEnrollment;

public class ExtractFromJsonServiceUtil {

	public static String TAG = "ExtractFromJsonServiceUtil";

	public static Referral extractReferralFields(JSONObject r,
			String imageUrlPrefix) {
		Referral referral = new Referral();
		try {
			referral.setId(r.getLong("id"));
			referral.setUrgency(r.getString("urgency"));
			referral.setPatientName(r.getString("patientName"));
			referral.setPatientAge(r.getString("patientAge"));
			referral.setPatientContactPhone(r.getString("patientContactPhone"));
			referral.setPatientContactEmail(r.getString("patientContactEmail"));
			referral.setPatientContactAddress(r.getString("patientContactAddress"));
			referral.setReason(r.getString("reason"));
			referral.setCreatedOn(r.getLong("createdOn"));
			referral.setUpdatedOn(r.getLong("updatedOn"));
			referral.setStatus(r.getString("status"));

			SimpleEnrollment referringTo = referral.getReferringTo();
			JSONObject to = r.getJSONObject("referringTo");
			referringTo.setId(to.getLong("id"));
			referringTo.setFullName(to.getString("fullName"));
			referringTo.setSpeciality(to.getString("speciality"));
			referringTo.setLocation(to.getString("location"));
			referringTo.setProfileImageUrl(ImageUtilities.formUrl(
					imageUrlPrefix, to.getString("profileImage")));
			
			SimpleEnrollment referringFrom = referral.getReferringFrom();
			JSONObject from = r.getJSONObject("referringFrom");
			referringFrom.setId(from.getLong("id"));
			referringFrom.setFullName(from.getString("fullName"));
			referringFrom.setSpeciality(from.getString("speciality"));
			referringFrom.setLocation(from.getString("location"));
			referringFrom.setProfileImageUrl(ImageUtilities.formUrl(
					imageUrlPrefix, from.getString("profileImage")));

		} catch (JSONException e) {
			Log.e(TAG, e.getMessage());
			e.printStackTrace();
		}
		return referral;
	}
	
	public static Physician extractPhysicianFields(JSONObject r) {
		Physician phy = new Physician();
		try {
			phy.setFullName(CommonUtils.makeBlank(r.getString("fullName")));
			phy.setPhysicianId(r.getInt("id"));
			phy.setVersion(CommonUtils.makeBlank(r.getString("version")));
			phy.setHospitalAffiliation(CommonUtils.makeBlank(r.getString("hospitalAffiliation")));
			phy.setDegree(CommonUtils.makeBlank(r.getString("degree")));
			phy.setCellPhoneNumber(CommonUtils.makeBlank(r.getString("cellPhoneNumber")));
			phy.setEmailAddress(CommonUtils.makeBlank(r.getString("emailAddress")));
			phy.setState(CommonUtils.makeBlank(r.getString("state")));
			phy.setPostalCode(CommonUtils.makeBlank(r.getString("postalCode")));
			phy.setDepartment(CommonUtils.makeBlank(r.getString("department")));
			
			
			phy.setDepartment(CommonUtils.makeBlank(r.getString("department")));
			phy.setTitle(CommonUtils.makeBlank(r.getString("title")));
			phy.setStatusMessage(CommonUtils.makeBlank(r.getString("statusMessage")));
			phy.setMedicalSchoolAttended(CommonUtils.makeBlank(r.getString("medicalSchoolAttended")));
			phy.setGraduationYear(CommonUtils.makeBlank(r.getString("graduationYear")));
			phy.setDescription(CommonUtils.makeBlank(r.getString("description")));
			phy.setRegistrationNumber(CommonUtils.makeBlank(r.getString("registrationNumber")));
			phy.setInsuranceAccepted(CommonUtils.makeBlank(r.getString("insuranceAccepted")));
			
			phy.setLocation(CommonUtils.makeBlank(r.getString("location")));
			phy.setFirstName(CommonUtils.makeBlank(r.getString("firstName")));
			phy.setLastName(CommonUtils.makeBlank(r.getString("lastName")));
			phy.setGender(CommonUtils.makeBlank(r.getString("gender")));
			phy.setResidencyTraining(CommonUtils.makeBlank(r.getString("residencyTraining")));
			phy.setForwardEmailAddress(CommonUtils.makeBlank(r.getString("forwardEmailAddress")));
			phy.setWorkPhoneNumber(CommonUtils.makeBlank(r.getString("workPhoneNumber")));	
			phy.setFaxNumber(CommonUtils.makeBlank(r.getString("faxNumber")));
			phy.setAppointmentHotline(CommonUtils.makeBlank(r.getString("patientAppointmentHotline")));
			phy.setStreetAddress1(CommonUtils.makeBlank(r.getString("streetAddress1")));
			phy.setStreetAddress2(CommonUtils.makeBlank(r.getString("streetAddress2")));
			phy.setCity(CommonUtils.makeBlank(r.getString("city")));

			try {
				if(r.has("speciality") && !r.isNull("speciality")) {
					JSONObject speciality = (JSONObject) r
							.getJSONObject("speciality");
					phy.setSpecialityName(speciality.getString("specialityName"));
					phy.setSpecialityId(speciality.getString("id"));
					phy.setSpecialityVersion(speciality.getString("version"));
				}
			} catch (JSONException e) {
				Log.e(TAG, e.getMessage());
				e.printStackTrace();
			}

			try {
				if(r.has("superSpeciality") && !r.isNull("superSpeciality")) {
					JSONObject superSpeciality = (JSONObject) r
							.getJSONObject("superSpeciality");
					phy.setSuperSpecialityName(superSpeciality
							.getString("superSpecialityName"));
					phy.setSuperSpecialityId(superSpeciality.getString("id"));
					phy.setSuperSpecialityVersion(superSpeciality
							.getString("version"));
				}
			} catch (JSONException e) {
				Log.e(TAG, e.getMessage());
				e.printStackTrace();
			}

			try {
				if(r.has("country") && !r.isNull("country")) {
					JSONObject country = (JSONObject) r.getJSONObject("country");
					phy.setCountryName(country.getString("countryName"));
					phy.setCountryCode(country.getString("countryCode"));
					phy.setCountryId(country.getString("id"));
					phy.setCountryVersion(country.getString("version"));
				}
			} catch (JSONException e) {
				Log.e(TAG, e.getMessage());
				e.printStackTrace();
			}

			phy.setImageFileName(CommonUtils.makeBlank(r.getString("profileImage")));

		} catch (JSONException e) {
			Log.e(TAG, e.getMessage());
			e.printStackTrace();
		}
		return phy;
	}

	public static SimpleEnrollment extractRecentReferrals(JSONObject r,
			String imageBaseUrl) {
		SimpleEnrollment simpleEnrollment = new SimpleEnrollment();
		try {
			simpleEnrollment.setId(r.getLong("id"));
			simpleEnrollment.setFullName(r.getString("fullName"));
			simpleEnrollment.setProfileImageUrl(ImageUtilities.formUrl(imageBaseUrl,
					r.getString("profileImage")));
		} catch (JSONException e) {
			Log.e(TAG, e.getMessage());
			e.printStackTrace();
		}
		return simpleEnrollment;
	}

	public static CreateResponse extractCreateResponse(JSONObject r) {
		CreateResponse response = new CreateResponse();
		try {
			response.setRequestId(r.getInt("requestId"));
			response.setStatus(r.getString("status"));
			if(!r.isNull("message")) {
				response.setMessage(r.getString("message"));
			}
			if(!r.isNull("referralId")) {
				response.setReferralId(r.getInt("referralId"));
			}
		} catch (JSONException e) {
			Log.e(TAG, e.getMessage());
			e.printStackTrace();
		}
		return response;

	}
	
	public static PhyFilter extractFilters(JSONObject r) {
		PhyFilter phyFilter = new PhyFilter();
		try {
			phyFilter.setId(r.getInt("id"));
			phyFilter.setValue(r.getString("value"));
		} catch (JSONException e) {
			Log.e(TAG, e.getMessage());
			e.printStackTrace();
		}
		return phyFilter;
	}

}
