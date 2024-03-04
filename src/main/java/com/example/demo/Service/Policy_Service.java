package com.example.demo.Service;

//import com.example.demo.Controller.Policy_Controller;
import com.example.demo.Model.Claim_Information;
import com.example.demo.Model.Customer_Details;
import com.example.demo.Model.Policy_Details;
import com.example.demo.Projection.ClaimHistory;
import com.example.demo.Projection.PremiumReport;
import com.example.demo.Repository.Claim_Repository;
import com.example.demo.Repository.Customer_Repository;
import com.example.demo.Repository.Policy_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class Policy_Service {
    @Autowired
    Policy_Repository policyRepository;


    @Autowired
    Customer_Repository customerRepository;

    @Autowired
    Claim_Repository claimRepository;
    Policy_Service service;


    //create Customer
    public List<Customer_Details> Create_Customer(List<Customer_Details> c){
       return customerRepository.saveAll(c);
    }

    //read customer
    public List<Customer_Details> get_Customer(){
            return customerRepository.findAll();
        }
 //Upadte Customer
    public Customer_Details update_Customer(int id,Customer_Details p){
        Customer_Details  oldata = null;
        if (customerRepository.findById(id).isPresent()) {
            oldata = customerRepository.findById(id).get();
            oldata.setCustomer_name(p.getCustomer_name());
            oldata.setCustomer_age(p.getCustomer_age());
            oldata.setCustomer_address(p.getCustomer_address());
            oldata.setCustomer_gender(p.getCustomer_gender());
            oldata.setCustomer_martialstatus(p.getCustomer_martialstatus());
            oldata.setCustomer_occupation(p.getCustomer_occupation());
        }
        return customerRepository.save(oldata);
    }

    //delete customer
    public String delete_Customer(int id){
        customerRepository.deleteById(id);
        return "Succesfully customer deleted";
    }















    //create policy
    public void Create_Policy(List<Policy_Details> p){
        policyRepository.saveAll(p);
    }



    //read policy
    public List<Policy_Details> get_Policy(){
            return policyRepository.findAll();
    }

    //update policy
    public Policy_Details update_Policy(int id, Policy_Details p) {
        Policy_Details  oldata = null;
        if (policyRepository.findById(id).isPresent()) {
            oldata = policyRepository.findById(id).get();
            oldata.setPolicy_name(p.getPolicy_name());
              oldata.setPolicy_number(p.getPolicy_number());
            oldata.setPolicy_premium(p.getPolicy_premium());
            oldata.setPolicy_status(p.getPolicy_status());
            oldata.setPolicy_type(p.getPolicy_type());
            oldata.setPolicy_start_date(p.getPolicy_start_date());
//            oldata.setPolicy_end_date(p.getPolicy_end_date());
        }
        return policyRepository.save(oldata);
    }
    //delete policy
    public String delete_Policy(int id){
        policyRepository.deleteById(id);
        return "Succesfully policy deleted";
    }


    public List<Claim_Information> Create_Claim(List<Claim_Information> c) {
        return claimRepository.saveAll(c);
    }
    public List<Claim_Information> get_Claim() {
            return claimRepository.findAll();
    }
    public Claim_Information update_Claim(int id, Claim_Information p) {
       Claim_Information oldata = null;
        if (claimRepository.findById(id).isPresent()) {
            oldata = claimRepository.findById(id).get();
            oldata.setClaim_date(p.getClaim_date());
            oldata.setClaim_description(p.getClaim_description());
            oldata.setAmount(p.getAmount());
        }
        return claimRepository.save(oldata);
    }

    public String delete_Claim(int id) {
        claimRepository.deleteById(id);
        return "Succesfully claim deleted";
    }


    public Policy_Details search_Name(String name) {
        List<Customer_Details> customerDetails = customerRepository.findAll();
        List<Policy_Details> policyDetails = policyRepository.findAll();
        int id ;
            for (Customer_Details customerDetail : customerDetails) {
                if (customerDetail.getCustomer_name().equalsIgnoreCase(name)) {
                    id = customerDetail.getCustomer_id();
                    for(Policy_Details policyDetail : policyDetails) {
                        if(policyDetail.getCustomer().getCustomer_id() == id) {
                            return  policyDetail;
                        }
                    }
                }
            }
            return  null;
        }


    public Policy_Details search_Type(String type) {
        List<Policy_Details> policyDetails = policyRepository.findAll();
        for(Policy_Details policyDetail : policyDetails) {
            if(policyDetail.getPolicy_type() .equalsIgnoreCase(type)) {
                return  policyDetail;
            }
        }
        return null;
    }

    public Policy_Details search_Number(int number) {
        List<Policy_Details> policyDetails = policyRepository.findAll();
        for(Policy_Details policyDetail : policyDetails) {
            if(policyDetail.getPolicy_number() == number) {
                return  policyDetail;
            }
        }
        return null;
    }

    public  List<Policy_Details> policy_status_report() {
        List<Policy_Details> p1 = new ArrayList<>();
       List<Policy_Details> policyDetails = policyRepository.findAll();
       for(Policy_Details policyDetails1 : policyDetails){
           Policy_Details p =  policyDetails1;
           Date date1 = p.getPolicy_end_date();
           Date date2 = p.getPolicy_start_date();
           long millisecondsDifference = date1.getTime() - date2.getTime();
           long years = millisecondsDifference / (1000L * 60 * 60 * 24 * 365);
           if(years > 0)
               p.setPolicy_status("Active");
           else if (years < 0)
               p.setPolicy_status("Expired");
           else
               p.setPolicy_status("Pending");
           p1.add(p);
           if(p!=null)
                service.update_Policy(p.getPolicy_number(),p);
       }
        return  p1 ;

    }

    public List<PremiumReport> policy_premium_report() {
        return policyRepository.premiumReprt();
    }

    public int Calculate_Premium(int policy_no)
    {
        List<Policy_Details> p=policyRepository.findAll();
        List<Customer_Details> c=customerRepository.findAll();
        int premium=0;
        for(Policy_Details pd:p) {
            for (Customer_Details cd : c) {
                if (cd.getCustomer_id() == pd.getCustomer().getCustomer_id()) {
                    if(pd.getPolicy_number()==policy_no) {
                        int age = cd.getCustomer_age();
                        if (age >= 30)
                            premium=pd.getPolicy_premium()*2;
                        else if (age > 45)
                            premium=pd.getPolicy_premium() * 3;
                        else if (age > 60)
                            premium=pd.getPolicy_premium() * 6;
                    }
                }
            }
        }
        return premium;
    }

    public List<ClaimHistory> claimHistory() {
            return claimRepository.claimhistory();
        }

    public Customer_Details Create_sCustomer(Customer_Details c) {
        return customerRepository.save(c);
    }
}
