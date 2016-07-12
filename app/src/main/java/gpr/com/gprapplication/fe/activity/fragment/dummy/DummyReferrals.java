package gpr.com.gprapplication.fe.activity.fragment.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gpr.com.gprapplication.service.datamodel.Referral;
import gpr.com.gprapplication.service.datamodel.SimpleEnrollment;

/**
 * Created by jaya on 2/12/2016.
 */
public class DummyReferrals {


    /**
     * An array of sample (dummy) items.
     */
    public static final List<Referral> INCOMING_REFERRALS = new ArrayList<Referral>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, Referral> INCOMING_REFERRAL_MAP = new HashMap<String, Referral>();

    /**
     * An array of sample (dummy) items.
     */
    public static final List<Referral> OUTGOING_REFERRALS = new ArrayList<Referral>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, Referral> OUTGOING_REFERRAL_MAP = new HashMap<String, Referral>();

    private static final int COUNT = 5;

    ;

    static {
        // Add some sample items.

        Referral referral = new Referral();
        SimpleEnrollment fromPhysician = new SimpleEnrollment();
        fromPhysician.setFullName("Mathew Alias");
        fromPhysician.setLocation("Massachusetts,USA");
        fromPhysician.setSpeciality("Critical Care");
        referral.setReferringFrom(fromPhysician);
//        referral.setStatus(Referral_Status.DEFAULT.toString());
        referral.setId(new Long(1));
        referral.setPatientName("Thomas Mathew");
        referral.setReason("Complaining about back pain. Dignosed with Radiculopathy. Please check further. \n\n Has BP and diabetes");
        addToIncomingReferralList(referral);

        referral = new Referral();
        fromPhysician = new SimpleEnrollment();
        fromPhysician.setFullName("Katherine Smith");
        fromPhysician.setLocation("Iowa ,USA");
        fromPhysician.setSpeciality("General");
        referral.setReferringFrom(fromPhysician);
//        referral.setStatus(Referral_Status.ACCEPTED.toString());
        referral.setReason("Upper back pain. Need further evaulation \n");
        referral.setPatientName("John Smith");
        referral.setId(new Long(2));
        addToIncomingReferralList(referral);

        referral = new Referral();
        fromPhysician = new SimpleEnrollment();
        fromPhysician.setFullName("Ram Krishnan");
        fromPhysician.setLocation("Kochi, India");
        fromPhysician.setSpeciality("Pediatrics");
        referral.setReferringFrom(fromPhysician);
//        referral.setStatus(Referral_Status.BOUNCED.toString());
        referral.setReason("Dignosed with cancer in the kideny. Please evaluate further. \n");
        referral.setPatientName("Mahesh Nair");
        referral.setId(new Long(3));
        addToIncomingReferralList(referral);

        referral = new Referral();
        fromPhysician = new SimpleEnrollment();
        fromPhysician.setFullName("Sanjay Gupta");
        fromPhysician.setLocation("New York, NY");
        fromPhysician.setSpeciality("Pathology");
        referral.setReferringFrom(fromPhysician);
//        referral.setStatus(Referral_Status.FORWARD.toString());
        referral.setReason("Patient has diabetis and chronic back pain.");
        referral.setPatientName("Carol Smith");
        referral.setId(new Long(4));
        addToIncomingReferralList(referral);

        referral = new Referral();
        fromPhysician = new SimpleEnrollment();
        fromPhysician.setFullName("Anita Rahman");
        fromPhysician.setLocation("Dubai, UAE");
        fromPhysician.setSpeciality("Pediatrics");
        referral.setReferringFrom(fromPhysician);
//        referral.setStatus(Referral_Status.REJECTED.toString());
        referral.setReason("Chronic muscle pain. Muscle relaxant did not help");
        referral.setPatientName("John Smith");
        referral.setId(new Long(5));
        addToIncomingReferralList(referral);



        //Outgoing referrals

         referral = new Referral();
         fromPhysician = new SimpleEnrollment();
        fromPhysician.setFullName("Sanjay Gupta");
        fromPhysician.setLocation("Massachusetts,USA");
        fromPhysician.setSpeciality("Critical Care");
        referral.setReferringTo(fromPhysician);
//        referral.setStatus(Referral_Status.DEFAULT.toString());
        referral.setId(new Long(1));
        referral.setPatientName("Thomas Mathew");
        referral.setReason("Complaining about back pain. Dignosed with Radiculopathy. Please check further. \n\n Has BP and diabetes");
        addToOutgoingReferralList(referral);

        referral = new Referral();
        fromPhysician = new SimpleEnrollment();
        fromPhysician.setFullName("Elias Smith");
        fromPhysician.setLocation("Iowa ,USA");
        fromPhysician.setSpeciality("General");
        referral.setReferringTo(fromPhysician);
//        referral.setStatus(Referral_Status.ACCEPTED.toString());
        referral.setReason("Upper back pain. Need further evaulation \n");
        referral.setPatientName("John Smith");
        referral.setId(new Long(2));
        addToOutgoingReferralList(referral);

        referral = new Referral();
        fromPhysician = new SimpleEnrollment();
        fromPhysician.setFullName("Lakshmi Mohan");
        fromPhysician.setLocation("Kochi, India");
        fromPhysician.setSpeciality("Pediatrics");
        referral.setReferringTo(fromPhysician);
//        referral.setStatus(Referral_Status.BOUNCED.toString());
        referral.setReason("Dignosed with cancer in the kideny. Please evaluate further. \n");
        referral.setPatientName("Mahesh Nair");
        referral.setId(new Long(3));
        addToOutgoingReferralList(referral);

        referral = new Referral();
        fromPhysician = new SimpleEnrollment();
        fromPhysician.setFullName("Sanjay Gupta");
        fromPhysician.setLocation("New York, NY");
        fromPhysician.setSpeciality("Pathology");
        referral.setReferringTo(fromPhysician);
//        referral.setStatus(Referral_Status.FORWARD.toString());
        referral.setReason("Patient has diabetis and chronic back pain.");
        referral.setPatientName("Carol Smith");
        referral.setId(new Long(4));
        addToOutgoingReferralList(referral);

        referral = new Referral();
        fromPhysician = new SimpleEnrollment();
        fromPhysician.setFullName("Jacob Mathew");
        fromPhysician.setLocation("Dubai, UAE");
        fromPhysician.setSpeciality("Pediatrics");
        referral.setReferringTo(fromPhysician);
//        referral.setStatus(Referral_Status.REJECTED.toString());
        referral.setReason("Chronic muscle pain. Muscle relaxant did not help");
        referral.setPatientName("John Smith");
        referral.setId(new Long(5));
        addToOutgoingReferralList(referral);
    }

    private static void addToIncomingReferralList(Referral item) {
        INCOMING_REFERRALS.add(item);
        INCOMING_REFERRAL_MAP.put(item.getId().toString(), item);
    }


    private static void addToOutgoingReferralList(Referral item) {
        OUTGOING_REFERRALS.add(item);
        OUTGOING_REFERRAL_MAP.put(item.getId().toString(), item);
    }

    private static Referral createReferral(int position) {
        Referral referral =  new Referral();
        referral.setId(new Long(position));
        referral.setReason("Reason " + position);
        return referral;

    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }


}

