package com.logistic.logic.e_saloon.Appointments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.logistic.logic.e_saloon.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CancelledAppointment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CancelledAppointment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CancelledAppointment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView;
    TextView statusTextView;
    ProgressBar progressBar;
    CollectionReference collectionReference;
    FirebaseFirestore firestore;
    List<Appointment1> appointment1List=new ArrayList<>();
    FirebaseAuth firebaseAuth;


    private OnFragmentInteractionListener mListener;

    public CancelledAppointment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CancelledAppointment.
     */
    // TODO: Rename and change types and number of parameters
    public static CancelledAppointment newInstance(String param1, String param2) {
        CancelledAppointment fragment = new CancelledAppointment();
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
        return inflater.inflate(R.layout.fragment_cancelled_appointment, container, false);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        firestore=FirebaseFirestore.getInstance();
        recyclerView=getActivity().findViewById(R.id.cancelledAppointmentRecyclerView);
        progressBar=getActivity().findViewById(R.id.cancelledAppointmentProgressBar);
        statusTextView=getActivity().findViewById(R.id.cancelledAppointmentTextView);


       fetchAllApointments();
    }


    public void fetchAllApointments(){

        firebaseAuth=FirebaseAuth.getInstance();
        collectionReference=firestore
                .collection("ESaloon")
                .document("User")
                .collection("Client")
                .document("Appointment")
                .collection(firebaseAuth.getUid());

        collectionReference
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot snapshot :task.getResult()){
                                Appointment1 appointment1=new Appointment1();
                                String orderId=snapshot.get("orderId").toString();
                                String dob=snapshot.get("dob").toString();
                                String doa=snapshot.get("doa").toString();
                                String dot=snapshot.get("toa").toString();
                                String status=snapshot.get("status").toString();
                                appointment1.setOrderId(orderId);
                                appointment1.setAppointmentDate(doa);
                                appointment1.setAppointmentTime(dot);
                                appointment1.setOrderDate(dob);
                                if (status.equalsIgnoreCase("-1")|| status.equalsIgnoreCase("0")){
                                    appointment1List.add(appointment1);
                                }


                            }
                            if (appointment1List.isEmpty()){
                                statusTextView.setVisibility(View.VISIBLE);
                                Toast.makeText(getActivity(), "No Appointments Cancelled !", Toast.LENGTH_SHORT).show();
                            }
                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                            MyRecyclerCancelledAdapter adapter=
                                    new MyRecyclerCancelledAdapter(getActivity()
                                            ,recyclerView,appointment1List);
                            progressBar.setVisibility(View.GONE);
                            recyclerView.setAdapter(adapter);


                        }

                    }
                });
    }




    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
        void onFragmentInteraction(Uri uri);
    }
}
