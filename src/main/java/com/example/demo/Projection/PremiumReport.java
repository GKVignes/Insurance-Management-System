package com.example.demo.Projection;


import com.example.demo.Model.Customer_Details;
import com.example.demo.Model.Policy_Details;

public interface PremiumReport {
    int getPolicy_number();
    String getPolicy_name();
    String getPolicy_type();
    String getPolicy_status();
    int getPolicy_premium();

}
