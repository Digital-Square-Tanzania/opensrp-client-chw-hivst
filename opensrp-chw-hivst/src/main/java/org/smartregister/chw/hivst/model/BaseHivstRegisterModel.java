package org.smartregister.chw.hivst.model;

import org.json.JSONObject;
import org.smartregister.chw.hivst.contract.HivstRegisterContract;
import org.smartregister.chw.hivst.util.HivstJsonFormUtils;

public class BaseHivstRegisterModel implements HivstRegisterContract.Model {

    @Override
    public JSONObject getFormAsJson(String formName, String entityId, String currentLocationId, String gender, int age) throws Exception {
        JSONObject jsonObject = HivstJsonFormUtils.getFormAsJson(formName);
        HivstJsonFormUtils.getRegistrationForm(jsonObject, entityId, currentLocationId);
        JSONObject global = jsonObject.getJSONObject("global");
        global.put("gender", gender);
        global.put("age", age);
        return jsonObject;
    }

}
