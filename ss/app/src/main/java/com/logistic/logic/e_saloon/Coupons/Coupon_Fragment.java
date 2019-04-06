package com.logistic.logic.e_saloon.Coupons;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.logistic.logic.e_saloon.Model.CouponsDetails;
import com.logistic.logic.e_saloon.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Coupon_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Coupon_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Coupon_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    RecyclerView recyclerView;
    CollectionReference collectionReference;
    FirebaseFirestore firestore;
    TextView textView;
    ProgressBar progressBar;
    List<CouponsDetails> couponsDetailsList=new ArrayList<>();


    public Coupon_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Coupon_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Coupon_Fragment newInstance(String param1, String param2) {
        Coupon_Fragment fragment = new Coupon_Fragment();
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
        return inflater.inflate(R.layout.fragment_coupon_, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        recyclerView=getActivity().findViewById(R.id.couponsRecyclerView);
        textView=getActivity().findViewById(R.id.couponTextView);
        progressBar=getActivity().findViewById(R.id.coupon_progress_bar);
        firestore=FirebaseFirestore.getInstance();
        fetchCouponsFirebase();

        super.onActivityCreated(savedInstanceState);
    }
    public  void fetchCouponsFirebase(){

        firestore=FirebaseFirestore.getInstance();
        couponsDetailsList.clear();
        collectionReference=firestore.collection("ESaloon")
                .document("Offers")
                .collection("Coupons");
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot snapshot :task.getResult()){
                        Log.e("Offers Activity",""+snapshot.getData());
                        CouponsDetails couponsDetails=new CouponsDetails();
                        String title=snapshot.get("couponTitle").toString();
                        String code=snapshot.get("couponCode").toString();
                        String date=snapshot.get("couponValidity").toString();
                        String imgUrl=snapshot.get("imageUrl").toString();
                        String couponDiscountPrice=snapshot.get("discountPercentage").toString();
                        couponsDetails.setCouponTitle(title);
                        couponsDetails.setCouponCode(code);
                        couponsDetails.setCouponValidity(date);
                        couponsDetails.setDiscountPercentage(couponDiscountPrice);
                        couponsDetails.setCouponDetails(snapshot.get("couponDetails").toString());
                        couponsDetails.setImageUrl(imgUrl);

                        couponsDetailsList.add(couponsDetails);
                    }
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    AddCouponsRecyclerAdapter adapter=new
                            AddCouponsRecyclerAdapter(getActivity()
                            ,recyclerView,couponsDetailsList);
                    if (couponsDetailsList.isEmpty()){
                        textView.setVisibility(View.VISIBLE);
                    }
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
