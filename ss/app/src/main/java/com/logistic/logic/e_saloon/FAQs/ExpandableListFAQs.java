package com.logistic.logic.e_saloon.FAQs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListFAQs {

    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> q1 = new ArrayList<String>();
        q1.add("You can book with us by downloading our app here." +
                " Alternatively you could also book using our appointment window here on our application. " +
                "If you need the human touch, feel free to call us on XXXXXXXXXX and someone will cater to you personally. All bookings are actively responded to between 9:30am-6:30pm. " +
                "Any requests made after 6:30pm are noted and confirmed next morning at 9:30am.");


        List<String> q2 = new ArrayList<String>();
        q2.add("We love clients who book atleast 1 day prior. " +
                "However, for the unplanned, we also accept bookings 60 minutes in advance of your requirement.");


        List<String> q3 = new ArrayList<String>();
        q3.add("You can contact us on XXXXXXXXXX to reschedule upto 4 hours of your appointment. " +
                "For rescheduling within 4 hours or your original appointment time, we will try our best but cannot assure availability of your new preferred time slot.");



        List<String> q4 = new ArrayList<String>();
        q4.add("All requests made upto 24 hours prior to your appointment time are cancelled with full amounts being refunded to you. For cancellations made within 24 hours, we shall charge 25% of your invoice amount and refund the balance. We take significant pains to plan your appointment to perfection and our technicians strive to be on time. Last minute cancellations can disrupt their schedule and cause inconvenience to others. We are sure you get that!");
        List<String> q5 = new ArrayList<String>();
        q5.add("We do not advocate that since our beauty stars are best trained on our own brands. However, if you would like to do that, we can accommodate that. The beauty star shall charge you the full price of the service and there shall be no discounts for using your own product.");

        List<String> q6 = new ArrayList<String>();
        q6.add("Our beauty stars are currently trained to service women only. Given that, we apologise it will not be possible to do so. However, we shall be starting with services for men soon and be most happy to serve male clients once we have trained experts!");
        List<String> q7 = new ArrayList<String>();
        q7.add("We are starting our online payment facility soon. In the interim, you can choose to pay by Cash on Delivery or Card on Delivery. We request you to keep change as our beauty star may not have change available at all points in time.");
        List<String> q8 = new ArrayList<String>();
        q8.add("No. We have no hidden charges or costs. The invoice amount is the final amount payable by you.");
        List<String> q9 = new ArrayList<String>();
        q9.add("No. Our packages are pre-discounted. Hence they cannot be clubbed with any other offers. You can choose between discount offers on individual services or packages depending on the final amount and your requirements.");

        List<String> q10 = new ArrayList<String>();
        q10.add(" Yes ! All the information is absolutely safe with us ");

        List<String> q11 = new ArrayList<String>();
        q11.add(" You can check for updates using the  Google Play Store  ");

        List<String> q12 = new ArrayList<String>();
        q12.add(" Absolutely. If you love them, why not show them through this small gesture. It encourages them and keeps them motivated. " +
                "All tips earned by our beauty stars are their personal income and the Company does not get any share of that ");







        expandableListDetail.put("How do I book a service?", q1);
        expandableListDetail.put("How much in advance do I need to make the booking?", q2);
        expandableListDetail.put("How do I reschedule an appointment?", q3);
        expandableListDetail.put("Can I get a refund if I cancel my appointment?", q4);
        expandableListDetail.put("Can I provide you my own products?", q5);
        expandableListDetail.put("Would you service aged men at a special request?", q6);
        expandableListDetail.put("How do I pay for my services?", q7);
        expandableListDetail.put("Is there any additional payment for travelling of the beauty star to my house?", q8);
        expandableListDetail.put("Will I get offer discounts on packages too?", q9);
        expandableListDetail.put("Is this mobile app secure ?", q10);
        expandableListDetail.put("How can I update my APP ?", q11);
        expandableListDetail.put("Can I tip the beauty star?", q12);



        return expandableListDetail;
    }

}
