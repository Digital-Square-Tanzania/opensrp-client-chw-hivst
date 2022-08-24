package org.smartregister.chw.hivst.fragment;

import static org.smartregister.util.JsonFormUtils.ENTITY_ID;
import static org.smartregister.util.JsonFormUtils.generateRandomUUIDString;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.vijay.jsonwizard.utils.FormUtils;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.smartregister.chw.hivst.adapter.HivstMobilizationRegisterAdapter;
import org.smartregister.chw.hivst.dao.HivstMobilizationDao;
import org.smartregister.chw.hivst.model.HivstMobilizationModel;
import org.smartregister.chw.hivst.model.HivstMobilizationRegisterFragmentModel;
import org.smartregister.chw.hivst.presenter.HivstMobilizationRegisterFragmentPresenter;
import org.smartregister.chw.hivst.provider.HivstMobilizationRegisterProvider;
import org.smartregister.chw.hivst.util.Constants;
import org.smartregister.configurableviews.model.View;
import org.smartregister.cursoradapter.RecyclerViewPaginatedAdapter;
import org.smartregister.hivst.R;
import org.smartregister.view.activity.BaseRegisterActivity;
import org.smartregister.view.customcontrols.CustomFontTextView;

import java.util.List;
import java.util.Set;

import timber.log.Timber;

public class HivstMobilizationRegisterFragment extends BaseHivstRegisterFragment {

    private android.view.View view;

    @Override
    public void initializeAdapter(Set<View> visibleColumns) {
        HivstMobilizationRegisterProvider mobilizationRegisterProvider = new HivstMobilizationRegisterProvider(getActivity(), paginationViewHandler, registerActionHandler, visibleColumns);
        List<HivstMobilizationModel> hivstMobilizationModels = HivstMobilizationDao.getMobilizationSessions();
        clientAdapter = new RecyclerViewPaginatedAdapter(null, mobilizationRegisterProvider, null);
        clientAdapter.setTotalcount(0);
        clientAdapter.setCurrentlimit(20);
        if (hivstMobilizationModels != null && !hivstMobilizationModels.isEmpty()) {
            clientsView.setAdapter(new HivstMobilizationRegisterAdapter(hivstMobilizationModels, requireActivity()));
        }
    }

    @Override
    public void setupViews(android.view.View view) {
        initializePresenter();
        super.setupViews(view);
        this.view = view;

        Toolbar toolbar = view.findViewById(org.smartregister.R.id.register_toolbar);
        toolbar.setContentInsetsAbsolute(0, 0);
        toolbar.setContentInsetsRelative(0, 0);
        toolbar.setContentInsetStartWithNavigation(0);


        android.view.View navbarContainer = view.findViewById(R.id.register_nav_bar_container);
        navbarContainer.setFocusable(false);

        CustomFontTextView titleView = view.findViewById(R.id.txt_title_label);
        if (titleView != null) {
            titleView.setText(getString(R.string.self_testing_sessions));
            titleView.setPadding(0, titleView.getTop(), titleView.getPaddingRight(), titleView.getPaddingBottom());
        }

        android.view.View searchBarLayout = view.findViewById(R.id.search_bar_layout);
        searchBarLayout.setVisibility(android.view.View.GONE);

        android.view.View topLeftLayout = view.findViewById(R.id.top_left_layout);
        topLeftLayout.setVisibility(android.view.View.GONE);

        android.view.View topRightLayout = view.findViewById(R.id.top_right_layout);
        topRightLayout.setVisibility(android.view.View.VISIBLE);

        android.view.View sortFilterBarLayout = view.findViewById(R.id.register_sort_filter_bar_layout);
        sortFilterBarLayout.setVisibility(android.view.View.GONE);

        android.view.View filterSortLayout = view.findViewById(R.id.filter_sort_layout);
        filterSortLayout.setVisibility(android.view.View.GONE);

        android.view.View dueOnlyLayout = view.findViewById(R.id.due_only_layout);
        dueOnlyLayout.setVisibility(android.view.View.GONE);
        dueOnlyLayout.setOnClickListener(registerActionHandler);
        if (getSearchView() != null) {
            getSearchView().setVisibility(android.view.View.GONE);
        }
    }


    @Override
    protected void initializePresenter() {
        if (getActivity() == null) {
            return;
        }
        String viewConfigurationIdentifier = null;
        try {
            viewConfigurationIdentifier = ((BaseRegisterActivity) getActivity()).getViewIdentifiers().get(0);
        } catch (NullPointerException e) {
            Timber.e(e);
        }
        presenter = new HivstMobilizationRegisterFragmentPresenter(this, new HivstMobilizationRegisterFragmentModel(), viewConfigurationIdentifier);
    }

    @Override
    public void onViewCreated(@NonNull android.view.View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (clientsView.getAdapter() != null) {
            clientsView.getAdapter().notifyDataSetChanged();
        }
    }


    @Override
    public void onResume() {
        super.onResume();

        Toolbar toolbar = view.findViewById(org.smartregister.R.id.register_toolbar);
        toolbar.setContentInsetsAbsolute(0, 0);
        toolbar.setContentInsetsRelative(0, 0);
        toolbar.setContentInsetStartWithNavigation(0);

        if (clientsView.getAdapter() != null) {
            clientsView.getAdapter().notifyDataSetChanged();
        }
    }

    @Override
    public void countExecute() {
        Cursor c = null;
        try {

            String query = "select count(*) from " + presenter().getMainTable() + " where " + presenter().getMainCondition();

            if (StringUtils.isNotBlank(filters)) {
                query = query + " and ( " + filters + " ) ";
            }


            c = commonRepository().rawCustomQueryForAdapter(query);
            c.moveToFirst();
            clientAdapter.setTotalcount(c.getInt(0));
            Timber.v("total count here %s", clientAdapter.getTotalcount());

            clientAdapter.setCurrentlimit(20);
            clientAdapter.setCurrentoffset(0);

        } catch (Exception e) {
            Timber.e(e);
        } finally {
            if (c != null) {
                c.close();
            }
        }
    }


    @Override
    protected void openProfile(String baseEntityId) {
        //implement when needed
    }


    @Override
    protected void refreshSyncProgressSpinner() {
        if (syncProgressBar != null) {
            syncProgressBar.setVisibility(android.view.View.GONE);
        }
        if (syncButton != null) {
            syncButton.setVisibility(android.view.View.VISIBLE);
            syncButton.setPadding(0, 0, 10, 0);
            syncButton.setImageDrawable(context().getDrawable(R.drawable.ic_add_24));
            syncButton.setOnClickListener(view -> {
                JSONObject form;
                try {
                    form = (new FormUtils()).getFormJsonFromRepositoryOrAssets(requireActivity(), Constants.FORMS.HIVST_MOBILIZATION_SESSION);
                    if (form != null) {
                        String randomId = generateRandomUUIDString();
                        form.put(ENTITY_ID, randomId);
//TODO: Refactor
                        //   JSONObject chwName = getFieldJSONObject(fields(form, STEP1), "chw_name");
                        //   AllSharedPreferences preferences = ChwApplication.getInstance().getContext().allSharedPreferences();
                        //   chwName.put(VALUE, preferences.getANMPreferredName(preferences.fetchRegisteredANM()));
                        //        requireActivity().startActivityForResult(org.smartregister.chw.core.utils.FormUtils.getStartFormActivity(form, requireActivity().getString(R.string.sbcc), requireActivity()), JsonFormUtils.REQUEST_CODE_GET_JSON);
                    }
                } catch (JSONException e) {
                    Timber.e(e);
                }
            });
        }
    }
}
