package com.sendbird.android.sample.dashboard;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sendbird.android.sample.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DashboardList.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DashboardList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashboardList extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //private OnFragmentInteractionListener mListener;

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private DashboardListAdapter mChannelListAdapter;
    private EditText mPostInput;
    private Button mPostButton;
    private PostsDataModel InpObj;
    //private FloatingActionButton mCreateChannelFab;
    //private GroupChannelListQuery mChannelListQuery;
    private SwipeRefreshLayout mSwipeRefresh;

    public static DashboardList newInstance() {
        DashboardList fragment = new DashboardList();
        return fragment;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DashbaordList.
     */
    // TODO: Rename and change types and number of parameters
    public static DashboardList newInstance(String param1, String param2) {
        DashboardList fragment = new DashboardList();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_dashboard_list, container, false);

        setRetainInstance(true);

        // Change action bar title
        ((dashboard) getActivity()).setActionBarTitle("Dashbaord");

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_group_channel_list);
        mPostInput = rootView.findViewById(R.id.post_input);
        mPostButton = rootView.findViewById(R.id.post_button);
        //mCreateChannelFab = (FloatingActionButton) rootView.findViewById(R.id.fab_group_channel_list);
        mSwipeRefresh = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_layout_group_channel_list);

        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefresh.setRefreshing(true);
                refresh();
            }
        });

        mChannelListAdapter = new DashboardListAdapter(getActivity());
        mChannelListAdapter.load();
        mPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mPostInput.getText().equals("")){
                    Log.e("NoInput", "onClick: ");
                }
                else{
                    InpObj = new PostsDataModel("Mehak",mPostInput.getText().toString(),"R.id.ic_sentiment_very_satisfied_black_24dp");
                    mChannelListAdapter.addLast(InpObj);
                }
            }
        });
        setUpRecyclerView();
        setUpChannelListAdapter();

        return rootView;

        //return inflater.inflate(R.layout.fragment_dashbaord_list, container, false);
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onPause(){
        super.onPause();
    }


    // Sets up recycler view
    private void setUpRecyclerView() {
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mChannelListAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        // If user scrolls to bottom of the list, loads more channels.
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (mLayoutManager.findLastVisibleItemPosition() == mChannelListAdapter.getItemCount() - 1) {

                    //loadNextChannelList();
                }
            }
        });
    }


    private void setUpChannelListAdapter() {
        mChannelListAdapter.setOnItemClickListener(new DashboardListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(PostsDataModel obj) {
                //enterGroupChannel(channel);
                Toast.makeText(getContext(),"KeyPress",Toast.LENGTH_LONG).show();
            }
        });

        mChannelListAdapter.setOnItemLongClickLsitener(new DashboardListAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(final PostsDataModel obj) {
                //showChannelOptionsDialog(channel);
                Toast.makeText(getContext(),"LongPress",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void refresh() {
        refreshItemList();
    }

    private void refreshItemList(){
        List<PostsDataModel> list = new ArrayList<>();
        list.equals(mChannelListAdapter);
//        list.add("Hello");
//        list.add("Hello");
//        list.add("Hello");
//        list.add("Hello");
//        list.add("Hello");
        mChannelListAdapter.setGroupChannelList(list);
        if (mSwipeRefresh.isRefreshing()) {
            mSwipeRefresh.setRefreshing(false);
        }
    }


    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//
//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}