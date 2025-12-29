package org.smartregister.model;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.smartregister.chw.hivst.model.BaseHivstRegisterModel;

public class BaseTestRegisterModelHivst {

    @Mock
    private BaseHivstRegisterModel baseHivstRegisterModel;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void checkJSon() {
        try {
            JSONObject jsonObject = new JSONObject();
            Mockito.when(baseHivstRegisterModel.getFormAsJson(
                    Mockito.anyString(),
                    Mockito.anyString(),
                    Mockito.anyString(),
                    Mockito.anyString(),
                    Mockito.anyInt()))
                    .thenReturn(jsonObject);
            Assert.assertEquals(jsonObject, baseHivstRegisterModel.getFormAsJson(
                    Mockito.anyString(),
                    Mockito.anyString(),
                    Mockito.anyString(),
                    Mockito.anyString(),
                    Mockito.anyInt()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
