package gpr.com.gprapplication.fe.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import gpr.com.gprapplication.R;
import gpr.com.gprapplication.service.asynctask.GetPhyDetailServiceTask;
import gpr.com.gprapplication.service.callback.GPRException;
import gpr.com.gprapplication.service.callback.GenericCallback;
import gpr.com.gprapplication.service.datamodel.Physician;
import gpr.com.gprapplication.utility.CommonUtils;
import gpr.com.gprapplication.utility.CredentialManager;
import gpr.com.gprapplication.utility.GPRConstants;
import gpr.com.gprapplication.utility.ImageUtilities;

public class PhysicianDetailActivity extends AppCompatActivity {
    Long selectedPhyId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physician_detail);
        //action bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        getSupportActionBar().setTitle("Physician Detail");


    }

    @Override
    protected void onResume() {
        selectedPhyId = (Long) getIntent().getSerializableExtra(
                GPRConstants.PHYSICAN);
        getPhysicianDetail();
        setContentView(R.layout.activity_physician_detail);
        super.onResume();
    }

    private void getPhysicianDetail() {
        GetPhyDetailServiceTask task = new GetPhyDetailServiceTask(this);
        task.setCallback(new GenericCallback<Physician>() {
            @Override
            public void onComplete(Physician response) {
                fillDetailOnPage(response);

            }

            @Override
            public void onError(Physician response, GPRException exception) {

            }

            @Override
            public void onCancel() {

            }
        });
        task.execute(CredentialManager.getInstance().get(this), selectedPhyId.toString());
    }


    private void fillDetailOnPage(Physician phy) {
        ImageView imgReferPhy = (ImageView) findViewById(R.id.detail_phy_image);
        ImageLoader.getInstance().displayImage(ImageUtilities.formUrl(phy.getImageBaseUrl(), phy.getImageFileName()), imgReferPhy);

        TextView textReferPhy = (TextView) findViewById(R.id.detail_physicianName);
        textReferPhy.setText(phy.getFullName());

        TextView textDegree = (TextView) findViewById(R.id.detail_degree);
        textDegree.setText(CommonUtils.makeBlank(phy.getDegree()));
        if(textDegree.length() > 0) {
            textDegree.setVisibility(View.VISIBLE);
        }

        TextView status = (TextView) findViewById(R.id.detail_status);
        status.setText(CommonUtils.makeBlank(phy.getStatusMessage()));
        if (status.length() > 0) {
            status.setVisibility(View.VISIBLE);
        }

        TextView aboutMe = (TextView) findViewById(R.id.detail_about_me);
        aboutMe.setText(CommonUtils.makeBlank(phy.getDescription()));
        if (aboutMe.length() > 0) {
            aboutMe.setVisibility(View.VISIBLE);
        }

        TextView hostpitalAffiliation = (TextView) findViewById(R.id.detail_hostpital_affiliation);
        hostpitalAffiliation.setText(CommonUtils.makeBlank(phy.getHospitalAffiliation()));
        if (!CommonUtils.isEmpty(hostpitalAffiliation.getText().toString())) {
            hostpitalAffiliation.setVisibility(View.VISIBLE);
        }

        TextView department = (TextView) findViewById(R.id.detail_department);
        department.setText(CommonUtils.makeBlank(phy.getDepartment()));
        if (department.length() > 0) {
            department.setVisibility(View.VISIBLE);
        }

        TextView title = (TextView) findViewById(R.id.detail_title);
        title.setText(CommonUtils.makeBlank(phy.getTitle()));
        if (title.length() > 0) {
            title.setVisibility(View.VISIBLE);
        }

        TextView insuranceAccepted = (TextView) findViewById(R.id.detail_insurance_accepted);
        insuranceAccepted.setText(CommonUtils.makeBlank(phy.getInsuranceAccepted()));
        if (insuranceAccepted.length() > 0) {
            insuranceAccepted.setVisibility(View.VISIBLE);
        }

        TextView apptHotline = (TextView) findViewById(R.id.detail_appt_hotline);
        apptHotline.setText(CommonUtils.makeBlank(phy.getAppointmentHotline()));
        if (apptHotline.length() > 0) {
            apptHotline.setVisibility(View.VISIBLE);
        }

        TextView textCity = (TextView) findViewById(R.id.detail_city);
        textCity.setText(CommonUtils.makeBlank(phy.getCity()));
        if (!CommonUtils.isEmpty(textCity.getText().toString())) {
            textCity.setVisibility(View.VISIBLE);
        }

        TextView textState = (TextView) findViewById(R.id.detail_state);
        textState.setText(CommonUtils.makeBlank(phy.getState()));
        if (!CommonUtils.isEmpty(textState.getText().toString())) {
            textState.setVisibility(View.VISIBLE);
        }

        TextView textCountry = (TextView) findViewById(R.id.detail_country);
        textCountry.setText(CommonUtils.makeBlank(phy.getCountryName()));
        if (!CommonUtils.isEmpty(textCountry.getText().toString())) {
            textCountry.setVisibility(View.VISIBLE);
        }

        TextView textPostalCode = (TextView) findViewById(R.id.detail_postal_code);
        textPostalCode.setText(CommonUtils.makeBlank(phy.getPostalCode()));
        if (!CommonUtils.isEmpty(textPostalCode.getText().toString())) {
            textPostalCode.setVisibility(View.VISIBLE);
        }

        TextView textSpeciality = (TextView) findViewById(R.id.detail_speciality);
        textSpeciality.setText(CommonUtils.makeBlank(phy.getSpecialityName()));
        if (!CommonUtils.isEmpty(textSpeciality.getText().toString())) {
            textSpeciality.setVisibility(View.VISIBLE);
        }

        TextView textSuperSpeciality = (TextView) findViewById(R.id.detail_super_speciality);
        textSuperSpeciality.setText(CommonUtils.makeBlank(phy.getSuperSpecialityName()));
        if (!CommonUtils.isEmpty(textSuperSpeciality.getText().toString())) {
            textSuperSpeciality.setVisibility(View.VISIBLE);
        }

        TextView medicalSchoolAttended = (TextView) findViewById(R.id.detail_medical_school_attended);
        medicalSchoolAttended.setText(CommonUtils.makeBlank(phy.getMedicalSchoolAttended()));
        if (medicalSchoolAttended.length() > 0) {
            medicalSchoolAttended.setVisibility(View.VISIBLE);
        }

        TextView graduationYear = (TextView) findViewById(R.id.detail_graduation_year);
        graduationYear.setText(CommonUtils.makeBlank(phy.getGraduationYear()));
        if (graduationYear.length() > 0) {
            graduationYear.setVisibility(View.VISIBLE);
        }
    }

}
