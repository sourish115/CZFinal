package com.sendbird.android.sample.main;

import android.app.AlertDialog;
import android.content.Context;
import java.util.Calendar;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.sendbird.android.sample.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyMood.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyMood#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyMood extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    CalendarView calendarView;

    private OnFragmentInteractionListener mListener;

    public MyMood() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyMood.
     */
    // TODO: Rename and change types and number of parameters
    public static MyMood newInstance(String param1, String param2) {
        MyMood fragment = new MyMood();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootview = inflater.inflate(R.layout.fragment_my_mood, container, false);
        List<EventDay> events = new ArrayList<>();

//        Calendar calendar = Calendar.getInstance();
//        events.add(new EventDay(calendar, R.drawable.ic_attach_file_black_24dp));

        calendarView = rootview.findViewById(R.id.calendarView);

        calendarView.setEvents(events);


        calendarView.setOnDayClickListener(eventDay -> {
            Calendar clickedDayCalendar = eventDay.getCalendar();
            Calendar calendar = clickedDayCalendar.getInstance();
            showMessageOptionsDialog(calendarView,calendar,clickedDayCalendar,events);
            //Toast.makeText(getContext(),eventDay.getCalendar().getTime().toString(),Toast.LENGTH_LONG).show();
        });

        return rootview;

    }


    private void showMessageOptionsDialog(CalendarView cv, Calendar cal,Calendar ccal ,List<EventDay> ed) {
        String[] options = new String[] { "Happy", "Sad" ,"Amazing","Awful","Bleh"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setItems(options, (dialog, which) -> {
            if (which == 0) {
                cal.setTime(ccal.getTime());
                ed.add(new EventDay(cal, R.drawable.ic_mood_black_24dp, Color.parseColor("#228B22")));
                cv.setEvents(ed);

            } else if (which == 1) {
                cal.setTime(ccal.getTime());
                ed.add(new EventDay(cal, R.drawable.ic_mood_bad_black_24dp, Color.parseColor("#228B22")));
                cv.setEvents(ed);
            }
            else if (which == 2) {
                cal.setTime(ccal.getTime());
                ed.add(new EventDay(cal, R.drawable.ic_sentiment_very_satisfied_black_24dp, Color.parseColor("#228B22")));
                cv.setEvents(ed);
            }
            else if (which == 3) {
                cal.setTime(ccal.getTime());
                ed.add(new EventDay(cal, R.drawable.ic_sentiment_very_dissatisfied_black_24dp, Color.parseColor("#228B22")));
                cv.setEvents(ed);
            }
            else if (which == 4) {
                cal.setTime(ccal.getTime());
                ed.add(new EventDay(cal, R.drawable.ic_sentiment_neutral_black_24dp, Color.parseColor("#228B22")));
                cv.setEvents(ed);
            }
        });
        builder.create().show();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
