package org.smartregister.chw.hivst.util;

public interface Constants {

    int REQUEST_CODE_GET_JSON = 2244;
    String ENCOUNTER_TYPE = "encounter_type";
    String STEP_ONE = "step1";
    String STEP_TWO = "step2";

    interface JSON_FORM_EXTRA {
        String JSON = "json";
        String ENCOUNTER_TYPE = "encounter_type";
        String BASE_ENTITY_ID = "BASE_ENTITY_ID";
        String ENTITY_ID = "ENTITY_ID";
    }

    interface EVENT_TYPE {
        String HIVST_REGISTRATION = "Self Testing Registration";
        String HIVST_ISSUE_KITS = "Self Testing Kits Issue";
        String HIVST_RESULTS = "Self Testing Results";
        String HIVST_RESULTS_REGISTRATION = "Self Testing Results Registration";
        String HIVST_MOBILIZATION = "HIVST Mobilization Session";
        String HTS_REGISTRATION = "HTS Registration";
    }

    interface FORMS {
        String HIVST_REGISTRATION = "hivst_registration";
        String HIVST_ISSUE_KITS = "hivst_issue_kits";
        String HIVST_RECORD_RESULTS = "hivst_results";
        String HIVST_FOLLOW_UP_VISIT = "hivst_followup_visit";
        String HIVST_MOBILIZATION_SESSION = "hivst_mobilization_session";
        String HIVST_REFERRAL_FORM = "referrals/hts_hivst_referral_form";
    }

    interface TABLES {
        String HIVST_REGISTER = "ec_hivst_register";
        String HIVST_FOLLOWUP = "ec_hivst_followup";
        String HIVST_RESULTS = "ec_hivst_results";
        String HIVST_MOBILIZATION = "ec_hivst_mobilization";
    }

    interface ACTIVITY_PAYLOAD {
        String BASE_ENTITY_ID = "BASE_ENTITY_ID";
        String FAMILY_BASE_ENTITY_ID = "FAMILY_BASE_ENTITY_ID";
        String ACTION = "ACTION";
        String HIVST_FORM_NAME = "HIVST_FORM_NAME";
        String GENDER = "GENDER";
        String AGE = "AGE";

    }

    interface ACTIVITY_PAYLOAD_TYPE {
        String REGISTRATION = "REGISTRATION";
        String FOLLOW_UP_VISIT = "FOLLOW_UP_VISIT";
    }

    interface CONFIGURATION {
        String HIVST_CONFIRMATION = "hivst_confirmation";
    }

    interface HIVST_MEMBER_OBJECT {
        String MEMBER_OBJECT = "memberObject";
    }

    public static class FORM_SUBMISSION_FIELD {
        public static final String MOBILIZATION_PARTICIPANTS_NUMBER = "participants_number";
        public static final String MOBILIZATION_DATE = "mobilization_date";
        public static final String MOBILIZATION_MALE_CONDOMS_ISSUED = "male_condoms_issued";
        public static final String MOBILIZATION_FEMALE_CONDOMS_ISSUED = "female_condoms_issued";
    }

}