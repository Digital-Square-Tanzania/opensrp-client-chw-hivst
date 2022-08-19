package org.smartregister.chw.hivst.fragment;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import org.smartregister.chw.hivst.contract.HivstResultsFragmentContract;
import org.smartregister.chw.hivst.model.BaseHivstResultsFragmentModel;
import org.smartregister.chw.hivst.presenter.BaseHivstResultsFragmentPresenter;
import org.smartregister.chw.hivst.provider.HivstResultsViewProvider;
import org.smartregister.chw.hivst.util.Constants;
import org.smartregister.commonregistry.CommonPersonObjectClient;
import org.smartregister.configurableviews.model.View;
import org.smartregister.cursoradapter.RecyclerViewPaginatedAdapter;
import org.smartregister.hivst.R;
import org.smartregister.view.customcontrols.CustomFontTextView;
import org.smartregister.view.customcontrols.FontVariant;
import org.smartregister.view.fragment.BaseRegisterFragment;

import java.util.HashMap;
import java.util.Set;

public class BaseHivstResultViewFragment extends BaseRegisterFragment implements HivstResultsFragmentContract.View {

    public static final String CLICK_VIEW_NORMAL = "click_view_normal";
    private String baseEntityId;

    @Override
    public void initializeAdapter(Set<View> visibleColumns) {
        HivstResultsViewProvider resultsViewProvider = new HivstResultsViewProvider(getActivity(), paginationViewHandler, registerActionHandler, visibleColumns);
        clientAdapter = new RecyclerViewPaginatedAdapter(null, resultsViewProvider, context().commonrepository(this.tablename));
        clientAdapter.setCurrentlimit(20);
        clientsView.setAdapter(clientAdapter);
    }

    public static BaseHivstResultViewFragment newInstance(String baseEntityId) {
        BaseHivstResultViewFragment baseHivstResultViewFragment = new BaseHivstResultViewFragment();
        Bundle b = new Bundle();
        b.putString(Constants.ACTIVITY_PAYLOAD.BASE_ENTITY_ID, baseEntityId);
        baseHivstResultViewFragment.setArguments(b);
        return baseHivstResultViewFragment;
    }

    @Override
    public void setupViews(android.view.View view) {
        super.setupViews(view);

        // Update top left icon
        qrCodeScanImageView = view.findViewById(org.smartregister.R.id.scanQrCode);
        if (qrCodeScanImageView != null) {
            qrCodeScanImageView.setVisibility(android.view.View.GONE);
        }

        android.view.View searchBarLayout = view.findViewById(org.smartregister.R.id.search_bar_layout);
        searchBarLayout.setVisibility(android.view.View.GONE);

        if (getSearchView() != null) {
            getSearchView().setBackgroundResource(R.color.white);
            getSearchView().setVisibility(android.view.View.GONE);
            getSearchView().setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_search, 0, 0, 0);
        }
        //remove toolbar
        Toolbar toolbar = view.findViewById(org.smartregister.R.id.register_toolbar);
        toolbar.setVisibility(android.view.View.GONE);

        android.view.View topLeftLayout = view.findViewById(R.id.top_left_layout);
        topLeftLayout.setVisibility(android.view.View.GONE);

        android.view.View topRightLayout = view.findViewById(R.id.top_right_layout);
        topRightLayout.setVisibility(android.view.View.VISIBLE);

        android.view.View sortFilterBarLayout = view.findViewById(R.id.register_sort_filter_bar_layout);
        sortFilterBarLayout.setVisibility(android.view.View.GONE);

        android.view.View filterSortLayout = view.findViewById(R.id.filter_sort_layout);
        filterSortLayout.setVisibility(android.view.View.GONE);

        TextView filterView = view.findViewById(org.smartregister.R.id.filter_text_view);
        if (filterView != null) {
            filterView.setVisibility(android.view.View.GONE);
        }
        ImageView logo = view.findViewById(org.smartregister.R.id.opensrp_logo_image_view);
        if (logo != null) {
            logo.setVisibility(android.view.View.GONE);
        }
        CustomFontTextView titleView = view.findViewById(R.id.txt_title_label);
        if (titleView != null) {
            titleView.setVisibility(android.view.View.VISIBLE);
            titleView.setText(getString(R.string.hivst_results_history));
            titleView.setFontVariant(FontVariant.REGULAR);
        }
    }

    @Override
    public HivstResultsFragmentContract.Presenter presenter() {
        return (HivstResultsFragmentContract.Presenter) presenter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (getArguments() != null) {
            this.baseEntityId = getArguments().getString(Constants.ACTIVITY_PAYLOAD.BASE_ENTITY_ID);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initializePresenter() {
        if (getActivity() == null) {
            return;
        }
        presenter = new BaseHivstResultsFragmentPresenter(baseEntityId, this, new BaseHivstResultsFragmentModel(), null);
    }

    @Override
    public void setUniqueID(String s) {
        //implement
    }

    @Override
    public void setAdvancedSearchFormData(HashMap<String, String> hashMap) {
        //implement
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
        //implement
    }

    @Override
    protected void onViewClicked(android.view.View view) {
        if (getActivity() == null || !(view.getTag() instanceof CommonPersonObjectClient)) {
            return;
        }
        CommonPersonObjectClient client = (CommonPersonObjectClient) view.getTag();
        if (view.getTag(R.id.VIEW_ID) == CLICK_VIEW_NORMAL) {
            openResultsForm(client);
        }
    }

    public void openResultsForm(CommonPersonObjectClient client) {
        //implement
    }


    @Override
    public void showNotFoundPopup(String s) {
        //implement
    }
}
