package com.logistic.logic.e_saloon.Categories;

import android.content.Context;
import android.content.SharedPreferences;
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

import com.logistic.logic.e_saloon.Configure.ConfigName;
import com.logistic.logic.e_saloon.Model.CategoryData1;
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
 * {@link HairStyleFemaleFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HairStyleFemaleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HairStyleFemaleFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    RecyclerView recyclerView;
    FirebaseFirestore firestore;
    CollectionReference collectionReference;
    String saloonName;
    String saloonKey;
    Bundle bundle;
    String categoryType;
    String categoryDesc;
    String categoryPrice;

    List<CategoryData1> categoryData1List=new ArrayList<CategoryData1>();
    ProgressBar progressBar;
    TextView showNoServiceStatus;

    public HairStyleFemaleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HairStyleFemaleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HairStyleFemaleFragment newInstance(String param1, String param2) {
        HairStyleFemaleFragment fragment = new HairStyleFemaleFragment();
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
        recyclerView=getActivity().findViewById(R.id.recyclerViewfemaleHairStyle);
        progressBar=getActivity().findViewById(R.id.progress_bar_fh1);
        showNoServiceStatus=getActivity().findViewById(R.id.noServiceTextViewFCut);
        /**
         *           Shared Preferences saloonName
         */
        SharedPreferences preferences=getActivity()
                .getSharedPreferences("saloonName",0);
        saloonName=preferences.getString("sname",null);
        firestore=FirebaseFirestore.getInstance();
        fetchFirebaseFemaleHairCut();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view=inflater.inflate(R.layout.fragment_hair_style_female, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView=getActivity().findViewById(R.id.recyclerViewfemaleHairStyle);
        progressBar=getActivity().findViewById(R.id.progress_bar_fh1);
        showNoServiceStatus=getActivity().findViewById(R.id.noServiceTextViewFCut);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity());
        ((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);


    }

    public void fetchFirebaseFemaleHairCut()
    {
        firestore=FirebaseFirestore.getInstance();
        Log.e("Female Hair Style "," ++++ "+saloonName);
        collectionReference=firestore
                .collection("ESaloon")
                .document("SaloonName")
                .collection(saloonName)
                .document(ConfigName.HAIR_SALOON_KEY)
                .collection(ConfigName.FEMALE_HAIR_CUT_SALOON_KEY);

        collectionReference
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot snapshot : task.getResult()){

                        Log.e("Female Hair CUT"," "+snapshot.getData());
                        CategoryData1 categoryData=new CategoryData1();
                        categoryType=snapshot.get("serviceType").toString();
                        categoryDesc=snapshot.get("serviceDesc").toString();
                        categoryPrice=snapshot.get("servicePrice").toString();
                        categoryData.setImageUrl(snapshot.getString("imageUrl"));
                        categoryData.setType(categoryType);
                        categoryData.setDesc(categoryDesc);
                        categoryData.setPrice(categoryPrice);
                        categoryData.setSaloonName(saloonName);
                        categoryData1List.add(categoryData);
                    }
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    MyRecyclerAdapter adapter=new MyRecyclerAdapter(recyclerView, getActivity(),
                            categoryData1List, new MyRecyclerAdapter.OnItemClickListener() {
                        @Override
                        public void OnItemClick(int position) {

                        }

                        @Override
                        public void OnButtonClick(int position) {

                        }

                        @Override
                        public void OnDeleteButtonClick(int position) {

                        }
                    });
                    if (categoryData1List.isEmpty()){
                        showNoServiceStatus.setVisibility(View.VISIBLE);
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
