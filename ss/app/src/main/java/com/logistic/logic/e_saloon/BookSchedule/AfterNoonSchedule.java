package com.logistic.logic.e_saloon.BookSchedule;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;

import com.logistic.logic.e_saloon.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AfterNoonSchedule.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AfterNoonSchedule#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AfterNoonSchedule extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
     GridView gridView;
    Button checkAfterAvailable,bookA;

    public AfterNoonSchedule() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AfterNoonSchedule.
     */
    // TODO: Rename and change types and number of parameters
    public static AfterNoonSchedule newInstance(String param1, String param2) {
        AfterNoonSchedule fragment = new AfterNoonSchedule();
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
        return inflater.inflate(R.layout.fragment_after_noon_schedule, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String time1,String type) {
        if (mListener != null) {
            mListener.onFragmentInteraction(time1,type);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        gridView=getView().findViewById(R.id.afterGridView);
        final String [] array={"12:00","12:30"
                ,"1:00","1:30",
                "2:00","2:30"
        };
        ArrayAdapter arrayAdapter=new ArrayAdapter(getActivity(),android.R.layout.select_dialog_singlechoice,array);
        gridView.setAdapter(arrayAdapter);
        checkAfterAvailable=getActivity().findViewById(R.id.checkAfterAvailableButton);
        bookA=getActivity().findViewById(R.id.bookAfterButton);
        bookA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=gridView.getCheckedItemPosition();

                if (position>0){
                    mListener.onFragmentInteraction(array[position],"After");
                }

            }
        });
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
        void onFragmentInteraction(String time1,String type);
    }
}
