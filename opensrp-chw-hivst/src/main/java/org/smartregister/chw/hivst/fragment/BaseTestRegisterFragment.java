package org.smartregister.chw.hivst.fragment;

import android.widget.ImageView;
import android.widget.TextView;

import org.smartregister.chw.hivst.activity.BaseTestProfileActivity;
import org.smartregister.chw.hivst.contract.TestRegisterFragmentContract;
import org.smartregister.chw.hivst.model.BaseTestRegisterFragmentModel;
import org.smartregister.chw.hivst.presenter.BaseTestRegisterFragmentPresenter;
import org.smartregister.commonregistry.CommonPersonObjectClient;
import org.smartregister.configurableviews.model.View;
import org.smartregister.cursoradapter.RecyclerViewPaginatedAdapter;
import org.smartregister.hivst.R;
import org.smartregister.chw.hivst.provider.TestRegisterProvider;
import org.smartregister.view.customcontrols.CustomFontTextView;
import org.smartregister.view.customcontrols.FontVariant;
import org.smartregister.view.fragment.BaseRegisterFragment;

import java.util.HashMap;
import java.util.Set;

public class BaseTestRegisterFragment extends BaseRegisterFragment implements TestRegisterFragmentContract.View {
    public static final String CLICK_VIEW_NORMAL = "click_view_normal";
    public static final String FOLLOW_UP_VISIT = "follow_up_visit";

    @Override
    public void initializeAdapter(Set<View> visibleColumns) {
        TestRegisterProvider testRegisterProvider = new TestRegisterProvider(getActivity(), paginationViewHandler, registerActionHandler, visibleColumns);
        clientAdapter = new RecyclerViewPaginatedAdapter(null, testRegisterProvider, context().commonrepository(this.tablename));
        clientAdapter.setCurrentlimit(20);
        clientsView.setAdapter(clientAdapter);
    }

    @Override
    public void setupViews(android.view.View view) {
        super.setupViews(view);

        // Update top left icon
        qrCodeScanImageView = view.findViewById(org.smartregister.R.id.scanQrCode);
        if (qrCodeScanImageView != null) {
            qrCodeScanImageView.setVisibility(android.view.View.GONE);
        }

        // Update Search bar
        android.view.View searchBarLayout = view.findViewById(org.smartregister.R.id.search_bar_layout);
        searchBarLayout.setBackgroundResource(R.color.customAppThemeBlue);

        if (getSearchView() != null) {
            getSearchView().setBackgroundResource(R.color.white);
            getSearchView().setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_search, 0, 0, 0);
        }

        // Update sort filter
        TextView filterView = view.findViewById(org.smartregister.R.id.filter_text_view);
        if (filterView != null) {
            filterView.setText(getString(R.string.sort));
        }

        // Update title name
        ImageView logo = view.findViewById(org.smartregister.R.id.opensrp_logo_image_view);
        if (logo != null) {
            logo.setVisibility(android.view.View.GONE);
        }

        CustomFontTextView titleView = view.findViewById(R.id.txt_title_label);
        if (titleView != null) {
            titleView.setVisibility(android.view.View.VISIBLE);
            titleView.setText(getString(R.string.hivst));
            titleView.setFontVariant(FontVariant.REGULAR);
        }
    }

    @Override
    public TestRegisterFragmentContract.Presenter presenter() {
        return (TestRegisterFragmentContract.Presenter) presenter;
    }

    @Override
    protected void initializePresenter() {
        if (getActivity() == null) {
            return;
        }
        presenter = new BaseTestRegisterFragmentPresenter(this, new BaseTestRegisterFragmentModel(), null);
    }

    @Override
    public void setUniqueID(String uniqueID) {
        if (getSearchView() != null) {
            getSearchView().setText(uniqueID);
        }
    }

    @Override
    public void setAdvancedSearchFormData(HashMap<String, String> hashMap) {
//        implement search here
    }

    @Override
    protected String getMainCondition() {
        return presenter().getMainCondition();
    }

    @Override
    protected String getDefaultSortQuery() {
        return presenter().getDefaultSortQuery();
    }

    @Override
    protected void startRegistration() {
//        start forms here if the module requires registration
    }

    @Override
    protected void onViewClicked(android.view.View view) {
        if (getActivity() == null || !(view.getTag() instanceof CommonPersonObjectClient)) {
            return;
        }

        CommonPersonObjectClient client = (CommonPersonObjectClient) view.getTag();
        if (view.getTag(R.id.VIEW_ID) == CLICK_VIEW_NORMAL) {
            openProfile(client.getCaseId());
        } else if (view.getTag(R.id.VIEW_ID) == FOLLOW_UP_VISIT) {
            openFollowUpVisit(client.getCaseId());
        }
    }

    protected void openProfile(String baseEntityId) {
        BaseTestProfileActivity.startProfileActivity(getActivity(), baseEntityId);
    }

    protected void openFollowUpVisit(String baseEntityId) {
//        implement
    }

    @Override
    public void showNotFoundPopup(String s) {
//        implement dialog
    }

}
