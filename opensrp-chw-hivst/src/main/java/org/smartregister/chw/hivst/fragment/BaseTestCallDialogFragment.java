package org.smartregister.chw.hivst.fragment;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;
import org.smartregister.chw.hivst.contract.BaseTestCallDialogContract;
import org.smartregister.chw.hivst.domain.MemberObject;
import org.smartregister.chw.hivst.listener.BaseTestCallWidgetDialogListener;
import org.smartregister.hivst.R;

import static android.view.View.GONE;
import static org.smartregister.util.Utils.getName;

public class BaseTestCallDialogFragment extends DialogFragment implements BaseTestCallDialogContract.View {

    public static final String DIALOG_TAG = "BaseHivstCallDialogFragment_DIALOG_TAG";
    private static MemberObject MEMBER_OBJECT;
    private View.OnClickListener listener = null;

    public static BaseTestCallDialogFragment launchDialog(Activity activity, MemberObject MO) {
        BaseTestCallDialogFragment dialogFragment = BaseTestCallDialogFragment.newInstance();
        FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
        Fragment prev = activity.getFragmentManager().findFragmentByTag(DIALOG_TAG);
        MEMBER_OBJECT = MO;
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        dialogFragment.show(ft, DIALOG_TAG);

        return dialogFragment;
    }

    public static BaseTestCallDialogFragment newInstance() {
        return new BaseTestCallDialogFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.ChwTheme_Dialog_FullWidth);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup dialogView = (ViewGroup) inflater.inflate(R.layout.hivst_member_call_widget_dialog_fragment, container, false);
        setUpPosition();
        if (listener == null) {
            listener = new BaseTestCallWidgetDialogListener(this);
        }

        initUI(dialogView);
        return dialogView;
    }

    private void setCallTitle(ViewGroup rootView, int viewId, final String message) {
        TextView callTitle = rootView.findViewById(viewId);
        if (MEMBER_OBJECT.getBaseEntityId().equals(MEMBER_OBJECT.getFamilyHead())) {
            callTitle.setText(String.format("%s %s", message, getResources().getString(R.string.call_family_head)));
        } else if ("0".equals(MEMBER_OBJECT.getAncMember())) {
            callTitle.setText(String.format("%s %s", message, getResources().getString(R.string.call_anc_client)));
        } else if (MEMBER_OBJECT.getBaseEntityId().equals(MEMBER_OBJECT.getPrimaryCareGiver())) {
            callTitle.setText(String.format("%s %s", message, getResources().getString(R.string.call_primary_caregiver)));
        } else if ("0".equals(MEMBER_OBJECT.getPncMember())) {
            callTitle.setText(String.format("%s %s", message, getResources().getString(R.string.call_pnc_client)));
        } else {
            callTitle.setText(String.format("%s %s", message, getResources().getString(R.string.call_hivst_client)));
        }
    }

    private void initUI(ViewGroup rootView) {
        if (StringUtils.isNotBlank(MEMBER_OBJECT.getPhoneNumber())) {
            setCallTitle(rootView, R.id.call_title, getResources().getString(R.string.call));
            if (StringUtils.isNotBlank(MEMBER_OBJECT.getFamilyHead())) {
                TextView familyHeadName = rootView.findViewById(R.id.hivst_call_head_name);
                familyHeadName.setText(MEMBER_OBJECT.getFamilyHeadName());
                TextView clientCallHeadPhone = rootView.findViewById(R.id.hivst_call_head_phone);
                clientCallHeadPhone.setTag(MEMBER_OBJECT.getPhoneNumber());
                clientCallHeadPhone.setText(
                        getName(getCurrentContext().getString(R.string.call), MEMBER_OBJECT.getFamilyHeadPhoneNumber()));
                clientCallHeadPhone.setOnClickListener(listener);

            } else {
                rootView.findViewById(R.id.hivst_layout_family_head).setVisibility(GONE);
            }

            if (!MEMBER_OBJECT.getBaseEntityId().equals(MEMBER_OBJECT.getFamilyHead())) {
                //just a member
                TextView hivstClientNameTextView = rootView.findViewById(R.id.call_hivst_client_name);
                hivstClientNameTextView.setText(String.format("%s %s %s", MEMBER_OBJECT.getFirstName(), MEMBER_OBJECT.getMiddleName(), MEMBER_OBJECT.getLastName()));

                setCallTitle(rootView, R.id.call_hivst_client_title, "");
                TextView callHivstClientPhone = rootView.findViewById(R.id.call_hivst_client_phone);
                callHivstClientPhone.setTag(MEMBER_OBJECT.getPhoneNumber());
                callHivstClientPhone.setText(getName(getCurrentContext().getString(R.string.call), MEMBER_OBJECT.getPhoneNumber()));
                callHivstClientPhone.setOnClickListener(listener);
            } else {
                rootView.findViewById(R.id.layout_hivst_client).setVisibility(GONE);
            }
        }

        rootView.findViewById(R.id.hivst_call_close).setOnClickListener(listener);
    }

    private void setUpPosition() {
        getDialog().getWindow().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
        WindowManager.LayoutParams p = getDialog().getWindow().getAttributes();
        p.width = ViewGroup.LayoutParams.MATCH_PARENT;
        p.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE;
        p.y = 20;
        getDialog().getWindow().setAttributes(p);
    }

    @Override
    public Context getCurrentContext() {
        return getActivity();
    }

    @Override
    public void setPendingCallRequest(BaseTestCallDialogContract.Dialer dialer) {
//        Implement pending call request
//        BaseAncWomanCallDialogContract.Dialer mDialer = dialer;
    }
}
