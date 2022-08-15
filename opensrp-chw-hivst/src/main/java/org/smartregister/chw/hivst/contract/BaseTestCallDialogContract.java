package org.smartregister.chw.hivst.contract;

import android.content.Context;

public interface BaseTestCallDialogContract {

    interface View {
        void setPendingCallRequest(Dialer dialer);
        Context getCurrentContext();
    }

    interface Dialer {
        void callMe();
    }
}
