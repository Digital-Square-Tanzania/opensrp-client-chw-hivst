package org.smartregister.activity;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.reflect.Whitebox;
import org.smartregister.chw.hivst.activity.BaseTestProfileActivity;
import org.smartregister.chw.hivst.contract.TestProfileContract;
import org.smartregister.domain.AlertStatus;
import org.smartregister.hivst.R;

import static org.mockito.Mockito.validateMockitoUsage;

public class BaseTestProfileActivityTest {
    @Mock
    public BaseTestProfileActivity baseTestProfileActivity;

    @Mock
    public TestProfileContract.Presenter profilePresenter;

    @Mock
    public View view;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void validate() {
        validateMockitoUsage();
    }

    @Test
    public void assertNotNull() {
        Assert.assertNotNull(baseTestProfileActivity);
    }

    @Test
    public void setOverDueColor() {
        baseTestProfileActivity.setOverDueColor();
        Mockito.verify(view, Mockito.never()).setBackgroundColor(Color.RED);
    }

    @Test
    public void formatTime() {
        BaseTestProfileActivity activity = new BaseTestProfileActivity();
        try {
            Assert.assertEquals("25 Oct 2019", Whitebox.invokeMethod(activity, "formatTime", "25-10-2019"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkHideView() {
        baseTestProfileActivity.hideView();
        Mockito.verify(view, Mockito.never()).setVisibility(View.GONE);
    }

    @Test
    public void checkProgressBar() {
        baseTestProfileActivity.showProgressBar(true);
        Mockito.verify(view, Mockito.never()).setVisibility(View.VISIBLE);
    }

    @Test
    public void medicalHistoryRefresh() {
        baseTestProfileActivity.refreshMedicalHistory(true);
        Mockito.verify(view, Mockito.never()).setVisibility(View.VISIBLE);
    }

    @Test
    public void onClickBackPressed() {
        baseTestProfileActivity = Mockito.spy(new BaseTestProfileActivity());
        Mockito.when(view.getId()).thenReturn(R.id.title_layout);
        Mockito.doNothing().when(baseTestProfileActivity).onBackPressed();
        baseTestProfileActivity.onClick(view);
        Mockito.verify(baseTestProfileActivity).onBackPressed();
    }

    @Test
    public void onClickOpenMedicalHistory() {
        baseTestProfileActivity = Mockito.spy(new BaseTestProfileActivity());
        Mockito.when(view.getId()).thenReturn(R.id.rlLastVisit);
        Mockito.doNothing().when(baseTestProfileActivity).openMedicalHistory();
        baseTestProfileActivity.onClick(view);
        Mockito.verify(baseTestProfileActivity).openMedicalHistory();
    }

    @Test
    public void onClickOpenUpcomingServices() {
        baseTestProfileActivity = Mockito.spy(new BaseTestProfileActivity());
        Mockito.when(view.getId()).thenReturn(R.id.rlUpcomingServices);
        Mockito.doNothing().when(baseTestProfileActivity).openUpcomingService();
        baseTestProfileActivity.onClick(view);
        Mockito.verify(baseTestProfileActivity).openUpcomingService();
    }

    @Test
    public void onClickOpenFamlilyServicesDue() {
        baseTestProfileActivity = Mockito.spy(new BaseTestProfileActivity());
        Mockito.when(view.getId()).thenReturn(R.id.rlFamilyServicesDue);
        Mockito.doNothing().when(baseTestProfileActivity).openFamilyDueServices();
        baseTestProfileActivity.onClick(view);
        Mockito.verify(baseTestProfileActivity).openFamilyDueServices();
    }

    @Test(expected = Exception.class)
    public void refreshFamilyStatusComplete() throws Exception {
        baseTestProfileActivity = Mockito.spy(new BaseTestProfileActivity());
        TextView textView = view.findViewById(R.id.textview_family_has);
        Whitebox.setInternalState(baseTestProfileActivity, "tvFamilyStatus", textView);
        Mockito.doNothing().when(baseTestProfileActivity).showProgressBar(false);
        baseTestProfileActivity.refreshFamilyStatus(AlertStatus.complete);
        Mockito.verify(baseTestProfileActivity).showProgressBar(false);
        PowerMockito.verifyPrivate(baseTestProfileActivity).invoke("setFamilyStatus", "Family has nothing due");
    }

    @Test(expected = Exception.class)
    public void refreshFamilyStatusNormal() throws Exception {
        baseTestProfileActivity = Mockito.spy(new BaseTestProfileActivity());
        TextView textView = view.findViewById(R.id.textview_family_has);
        Whitebox.setInternalState(baseTestProfileActivity, "tvFamilyStatus", textView);
        Mockito.doNothing().when(baseTestProfileActivity).showProgressBar(false);
        baseTestProfileActivity.refreshFamilyStatus(AlertStatus.complete);
        Mockito.verify(baseTestProfileActivity).showProgressBar(false);
        PowerMockito.verifyPrivate(baseTestProfileActivity).invoke("setFamilyStatus", "Family has services due");
    }

    @Test(expected = Exception.class)
    public void onActivityResult() throws Exception {
        baseTestProfileActivity = Mockito.spy(new BaseTestProfileActivity());
        Whitebox.invokeMethod(baseTestProfileActivity, "onActivityResult", 2244, -1, null);
        Mockito.verify(profilePresenter).saveForm(null);
    }

}
