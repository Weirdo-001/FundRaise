package com.donation.donation_platform.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.donation.donation_platform.service.UserService;
import com.donation.donation_platform.service.DonationService;
import com.donation.donation_platform.service.CampaignService;
import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;

@Controller
public class HomeController {

    private final UserService userService;
    private final DonationService donationService;
    private final CampaignService campaignService;

    public HomeController(UserService userService, DonationService donationService, CampaignService campaignService) {
        this.userService = userService;
        this.donationService = donationService;
        this.campaignService = campaignService;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/dashboard")
    public String adminDashboard(Model model, Authentication authentication) {
        if (authentication == null || !"admin_hoon".equals(authentication.getName())) {
            return "redirect:/login";
        }
    model.addAttribute("users", userService.getAllUsers());
    model.addAttribute("donations", donationService.getAllDonations());
    model.addAttribute("campaigns", campaignService.getAllCampaigns());
    return "dashboard";
    }
}
