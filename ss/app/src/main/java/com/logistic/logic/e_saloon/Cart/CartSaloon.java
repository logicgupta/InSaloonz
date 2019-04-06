package com.logistic.logic.e_saloon.Cart;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.logistic.logic.e_saloon.BookSchedule.ScheduleTime;
import com.logistic.logic.e_saloon.Model.CartDetails;
import com.logistic.logic.e_saloon.Model.CouponsDetails;
import com.logistic.logic.e_saloon.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CartSaloon.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CartSaloon#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartSaloon extends Fragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    RecyclerView recyclerView;
    String qty;
    String serviceType;
    String serviceDesc;
    int temp=1;
    String servicePrice;
    String totalPrice;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    CollectionReference collectionReference;
    Button button;
    List<CartDetails> cartDetailsList=new ArrayList<CartDetails>();
    ProgressBar progressBar;
    DocumentReference documentReference;
    ImageView imageView;
    int totalValuePrice;
    String serviceSaloonName;
    TextView totalValuePriceTextView;
    LinearLayout linearLayout;
    Button applyVouponButton;
    EditText writeCouponEditText;
    String DiscountPercentageAvailed;
    int myPrice;
    int originalPrice;
    LinearLayout linearLayout2,linearLayoutTax;
    TextView netPayment;
    TextView taxTextView;
    int tax;
    String stringTax;

    List<CouponsDetails> couponsDetailsList=new ArrayList<>();

    public CartSaloon() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CartSaloon.
     */
    // TODO: Rename and change types and number of parameters
    public static CartSaloon newInstance(String param1, String param2) {
        CartSaloon fragment = new CartSaloon();
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
        return inflater.inflate(R.layout.fragment_cart_saloon, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        cartDetailsList.clear();
        recyclerView=getActivity().findViewById(R.id.addItemCartRecyclerView);
        progressBar=getActivity().findViewById(R.id.loadCartProgressBar);
        button=getActivity().findViewById(R.id.payStep1Button);
        imageView=getView().findViewById(R.id.imageViewCart);
        linearLayout=getActivity().findViewById(R.id.couponLayout);
        writeCouponEditText=getActivity().findViewById(R.id.editTextcouponCode);
        applyVouponButton=getActivity().findViewById(R.id.buttonApply);
        linearLayout2=getActivity().findViewById(R.id.total_Layout1);
        netPayment=getActivity().findViewById(R.id.netPayableAmountTextView);
        linearLayoutTax=getActivity().findViewById(R.id.tax_Layout1);
        taxTextView=getActivity().findViewById(R.id.taxValueTextView);


        applyVouponButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(writeCouponEditText.getText().equals("")){
                    Toast.makeText(getActivity(), "Please Enter the coupon", Toast.LENGTH_SHORT).show();
                }
                else if(writeCouponEditText.getText().toString().length()!=15){
                    Toast.makeText(getActivity(), "Enter Valid Coupon Code", Toast.LENGTH_SHORT).show();
                }
                else{
                    final ProgressDialog progressDialog=new ProgressDialog(getActivity());
                    progressDialog.setTitle("Verifing ...");
                    progressDialog.show();
                    firestore=FirebaseFirestore.getInstance();
                    collectionReference = firestore.collection("ESaloon")
                            .document("Offers")
                            .collection("Coupons");
                    collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                    Log.e("Offers Activity", "" + snapshot.getData());
                                    CouponsDetails couponsDetails = new CouponsDetails();
                                    String title = snapshot.get("couponTitle").toString();
                                    String code = snapshot.get("couponCode").toString();
                                    String date = snapshot.get("couponValidity").toString();
                                    String imgUrl = snapshot.get("imageUrl").toString();
                                    String couponDiscountPercentage = snapshot.get("discountPercentage").toString();
                                    String couponValidOnTransaction=snapshot.get("couponValidOnTransaction").toString();
                                    Log.e("My Coupon is"," " +writeCouponEditText.getText().toString().trim());
                                    if(writeCouponEditText.getText().toString().trim().equalsIgnoreCase(code)){
                                       originalPrice=totalValuePrice;

                                        Log.e("Original Price"," "+originalPrice);
                                        int couponDis=Integer.parseInt(couponDiscountPercentage);
                                        if(originalPrice>=Integer.parseInt(couponValidOnTransaction)){
                                            originalPrice=originalPrice-(couponDis*originalPrice)/100;
                                            Toast.makeText(getActivity(), "You Availed Discount of"+couponDiscountPercentage+"%", Toast.LENGTH_SHORT).show();
                                            temp=0;
                                            myPrice=originalPrice;
                                            totalValuePriceTextView.setText(" Total Payment : ₹ "+originalPrice);
                                            progressDialog.dismiss();
                                            break;
                                        }
                                        else {
                                            Toast.makeText(getActivity(), "Minimum Transaction required "+couponValidOnTransaction, Toast.LENGTH_SHORT).show();

                                            progressDialog.dismiss();
                                            break;
                                        }
                                    }
                                    else
                                       {
                                            Toast.makeText(getActivity(), "InValid Coupon Code", Toast.LENGTH_SHORT).show();
                                            progressDialog.dismiss();
                                        }
                                    }



                            }
                        }
                    });

                }
            }
        });

        totalValuePriceTextView=getActivity().findViewById(R.id.totalValuePriceTextView);
        fetchFirebaseCartData();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp=0;
                if(cartDetailsList.size()>1){
                    for (int i=0;i<cartDetailsList.size();i++){
                        CartDetails details=cartDetailsList.get(i);
                        String x=details.getSaloonName();
                        if (cartDetailsList.size()>=i){
                            String y=cartDetailsList.get(cartDetailsList.size()-i-1).getSaloonName();
                            if(!x.equalsIgnoreCase(y)){
                                temp++;
                            }
                        }
                    }
                }
                else {
                    Toast.makeText(getActivity(), "Please Select The Date and Timing of Appointment !", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getActivity(),ScheduleTime.class);
                    Log.e("Cart SaloonName  ","Total  gotted  "+myPrice);
                    intent.putExtra("totalPrice",""+myPrice);
                    intent.putExtra("saloonName",serviceSaloonName);

                    startActivity(intent);

                }
                if (temp>0){
                    Toast.makeText(getActivity(), "Please Select the service of One Saloon", Toast.LENGTH_SHORT).show();
                }
                if (temp==0){
                    Toast.makeText(getActivity(), "Please Select The Date and Timing of Appointment !", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getActivity(),ScheduleTime.class);
                    Log.e("Cart SaloonName  ","Total  gotted  "+myPrice);
                    intent.putExtra("totalPrice",""+myPrice);
                    intent.putExtra("saloonName",serviceSaloonName);

                    startActivity(intent);

                }
            }
        });
        super.onActivityCreated(savedInstanceState);
    }




    public void fetchFirebaseCartData(){
        totalValuePrice=0;
        cartDetailsList.clear();
        firebaseAuth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
        collectionReference=firestore.collection("ESaloon")
                .document("User")
                .collection("Client")
                .document("Cart")
                .collection(firebaseAuth.getUid());
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot documentSnapshot :task.getResult()){
                        Log.e("Cart Saloon Activity",""+documentSnapshot.getData());
                        CartDetails details=new CartDetails();
                        serviceType=documentSnapshot.get("serviceType").toString();
                        serviceDesc=documentSnapshot.get("serviceDesc").toString();
                        qty=documentSnapshot.get("serviceQty").toString();
                        servicePrice=documentSnapshot.get("serviceOriginalPrice").toString();
                        details.setImageUrl(documentSnapshot.getString("imageUrl"));
                        totalPrice=documentSnapshot.get("serviceTotalPrice").toString();
                        serviceSaloonName=documentSnapshot.get("saloonName").toString();
                        String docKey=documentSnapshot.getId();
                        details.setServiceType(serviceType);
                        details.setServiceDesc(serviceDesc);
                        details.setServiceQty(qty);
                        details.setServiceOriginalPrice(servicePrice);
                        details.setServiceTotalPrice(totalPrice);
                        details.setDocKey(docKey);
                        details.setSaloonName(documentSnapshot.get("saloonName").toString());
                        totalValuePrice+=Integer.parseInt(totalPrice);
                        cartDetailsList.add(details);
                    }
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    MyRecyclerCartAdapter adapter=new MyRecyclerCartAdapter(recyclerView
                            , getActivity(), cartDetailsList, new MyRecyclerCartAdapter.OnClickListener() {
                        @Override
                        public void onDeleteClickListener(final int position, String key) {
                            firebaseAuth=FirebaseAuth.getInstance();
                            firestore=FirebaseFirestore.getInstance();
                            documentReference=firestore.collection("ESaloon")
                                    .document("User")
                                    .collection("Client")
                                    .document("Cart")
                                    .collection(firebaseAuth.getUid()).document(key);
                            documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(getActivity(), "Successfully Removed !", Toast.LENGTH_SHORT).show();
                                        fetchFirebaseCartData();
                                    }
                                }
                            });
                        }
                    });
                    originalPrice=totalValuePrice;
                    myPrice=totalValuePrice;
                    tax=(totalValuePrice*18)/100;
                    taxTextView.setText("Rs."+tax);
                    totalValuePrice=totalValuePrice+tax;
                    totalValuePriceTextView.setText("Rs."+totalValuePrice);
                    netPayment.setText("Payable Amount :  Rs. "+totalValuePrice);
                    button.setVisibility(View.VISIBLE);
                    linearLayout.setVisibility(View.VISIBLE);
                    if (cartDetailsList.isEmpty()){
                        linearLayout2.setVisibility(View.GONE);
                        button.setVisibility(View.GONE);
                        imageView.setVisibility(View.VISIBLE);

                        totalValuePriceTextView.setVisibility(View.GONE);
                        linearLayout.setVisibility(View.GONE);

                    }
                    else {
                        linearLayout2.setVisibility(View.VISIBLE);
                        netPayment.setVisibility(View.VISIBLE);
                        linearLayoutTax.setVisibility(View.VISIBLE);
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
