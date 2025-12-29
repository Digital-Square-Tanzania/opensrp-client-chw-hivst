package org.smartregister.fragment;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.reflect.Whitebox;
import org.smartregister.chw.hivst.activity.BaseHivstProfileActivity;
import org.smartregister.chw.hivst.fragment.BaseHivstRegisterFragment;
import org.smartregister.commonregistry.CommonPersonObjectClient;

import static org.mockito.Mockito.times;

public class BaseTestRegisterFragmentHivst {
    @Mock
    public BaseHivstRegisterFragment baseHivstRegisterFragment;

    @Mock
    public CommonPersonObjectClient client;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = Exception.class)
    public void openProfile() throws Exception {
        Whitebox.invokeMethod(baseHivstRegisterFragment, "openProfile", client);
        PowerMockito.mockStatic(BaseHivstProfileActivity.class);
        BaseHivstProfileActivity.startProfileActivity(null, null);
        PowerMockito.verifyStatic(BaseHivstProfileActivity.class, times(1));
        BaseHivstProfileActivity.startProfileActivity(null, null);

    }
}
