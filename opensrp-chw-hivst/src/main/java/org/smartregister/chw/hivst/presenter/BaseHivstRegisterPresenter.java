package org.smartregister.chw.hivst.presenter;

import android.util.Log;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.smartregister.chw.hivst.contract.HivstRegisterContract;
import org.smartregister.hivst.R;

import java.lang.ref.WeakReference;
import java.util.List;

public class BaseHivstRegisterPresenter implements HivstRegisterContract.Presenter, HivstRegisterContract.InteractorCallBack {

    public static final String TAG = BaseHivstRegisterPresenter.class.getName();

    protected WeakReference<HivstRegisterContract.View> viewReference;
    private HivstRegisterContract.Interactor interactor;
    protected HivstRegisterContract.Model model;

    public BaseHivstRegisterPresenter(HivstRegisterContract.View view, HivstRegisterContract.Model model, HivstRegisterContract.Interactor interactor) {
        viewReference = new WeakReference<>(view);
        this.interactor = interactor;
        this.model = model;
    }

    @Override
    public void startForm(String formName, String entityId, String metadata, String currentLocationId, String gender, int age) throws Exception {
        if (StringUtils.isBlank(entityId)) {
            return;
        }

        JSONObject form = model.getFormAsJson(formName, entityId, currentLocationId, gender, age);
        getView().startFormActivity(form);
    }

    @Override
    public void saveForm(String jsonString) {
        try {
            getView().showProgressDialog(R.string.saving_dialog_title);
            interactor.saveRegistration(jsonString, this);
        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
    }

    @Override
    public void onRegistrationSaved() {
        getView().hideProgressDialog();

    }

    @Override
    public void registerViewConfigurations(List<String> list) {
//        implement
    }

    @Override
    public void unregisterViewConfiguration(List<String> list) {
//        implement
    }

    @Override
    public void onDestroy(boolean b) {
//        implement
    }

    @Override
    public void updateInitials() {
//        implement
    }

    protected HivstRegisterContract.View getView() {
        if (viewReference != null)
            return viewReference.get();
        else
            return null;
    }
}
