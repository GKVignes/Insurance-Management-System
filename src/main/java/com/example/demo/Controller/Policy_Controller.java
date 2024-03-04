package com.example.demo.Controller;

import com.example.demo.Model.Claim_Information;
import com.example.demo.Model.Customer_Details;
import com.example.demo.Model.Policy_Details;
//import com.example.demo.Model.premiumRepor;
import com.example.demo.Projection.ClaimHistory;
import com.example.demo.Projection.PremiumReport;
import com.example.demo.Service.Policy_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/policy")
public class Policy_Controller {
    @Autowired
    Policy_Service policyService;

    @PostMapping("/addCustomer")
    public Customer_Details addCustomer(@RequestBody Customer_Details c){
        return policyService.Create_sCustomer(c);
    }
    @PostMapping("/addAllCustomer")
    public List<Customer_Details> addCustomerDetails(@RequestBody List<Customer_Details> c){
        return policyService.Create_Customer(c);
    }

    @PostMapping("/getAllCustomer")
    public List<Customer_Details> getCustomerDetails(){
        return policyService.get_Customer();
    }

    @GetMapping("/updateCustomer/{id}")
    public Customer_Details updatePolicyDetails(@PathVariable int id,@RequestBody Customer_Details p){
        return policyService.update_Customer(id,p);
    }

    @PostMapping("/deleteCustomer/{id}")
    public String deleteCustomerDetails(@PathVariable int id){
        return policyService.delete_Customer(id);
    }










    @PostMapping("/addPolicy")
    public String addPolicyDetails(@RequestBody List<Policy_Details> p){
        policyService.Create_Policy(p);
        return "Successfuly Added";
    }

    @PostMapping("/addAllPolicy")
    public String addAllPolicyDetails(@RequestBody List<Policy_Details> p){
        policyService.Create_Policy(p);
        return "Successfuly Added";
    }



    @GetMapping("/getPolicy")
    public List<Policy_Details> getPolicyDetails(){
        return policyService.get_Policy();
    }

    @GetMapping("/updatePolicy/{id}")
    public Policy_Details updatePolicyDetails(@PathVariable int id,@RequestBody Policy_Details p){
        return policyService.update_Policy(id,p);
    }

    @PostMapping("/deletePolicy/{id}")
    public String deletePolicydetails(@PathVariable int id){
        return policyService.delete_Policy(id);
    }







    @PostMapping("/addClaim")
    public List<Claim_Information> addClaimDetails(@RequestBody List<Claim_Information> c){
        return policyService.Create_Claim(c);
    }

    @PostMapping("/getClaim")
    public List<Claim_Information> getClaimDetails( ){
        return policyService.get_Claim();
    }

    @GetMapping("/updateClaim/{id}")
    public Claim_Information updateClaimDetails(@PathVariable int id,@RequestBody Claim_Information p){
        return policyService.update_Claim(id,p);
    }

    @PostMapping("/deleteClaim/{id}")
    public String deleteClaimDetails(@PathVariable int id){
        return policyService.delete_Claim(id);
    }


    @PostMapping("/searchByName/{name}")
    public Policy_Details searchByName(@PathVariable String name){
        return policyService.search_Name(name);
    }

    @PostMapping("/searchByPolicyType/{type}")
    public Policy_Details searchByType(@PathVariable String type){
        return policyService.search_Type(type);
    }

    @PostMapping("/searchByPolicyTypeNumber/{number}")
    public Policy_Details searchByNumber(@PathVariable int number){
        return policyService.search_Number(number);
    }


@GetMapping("/policyStatus")
public  List<Policy_Details> policyStatusReport(){
    return policyService.policy_status_report();
}

    @GetMapping("/policyPremium")
    public  List<PremiumReport> policyPremiumReport(){
        return policyService.policy_premium_report();
    }

    @GetMapping("/calculatePremium/{id}")
    public int calculatePremium(@PathVariable int id)
    {
        return policyService.Calculate_Premium(id);
    }
}
