package com.donation.donation_platform.controller;

import com.donation.donation_platform.entity.Donation;
import com.donation.donation_platform.entity.Campaign;
import com.donation.donation_platform.service.CampaignService;
import com.donation.donation_platform.service.DonationService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/donations")
public class DonationController {

    private final DonationService donationService;
    private final CampaignService campaignService;
    private final com.donation.donation_platform.service.UserService userService;

    public DonationController(DonationService donationService, CampaignService campaignService, com.donation.donation_platform.service.UserService userService) {
        this.donationService = donationService;
        this.campaignService = campaignService;
        this.userService = userService;
    }

    @GetMapping
    public String listDonations(Model model) {
        org.springframework.security.core.Authentication auth = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        java.util.List<com.donation.donation_platform.entity.Donation> userDonations = donationService.getDonationsByDonorName(username);
        model.addAttribute("donations", userDonations);
        return "donations"; // donations.html
    }

    @GetMapping("/new")
    public String createDonationForm(Model model) {
        model.addAttribute("donation", new Donation());
        // Optionally, provide a default campaign or campaigns list if needed
        return "donations";
    }

    @GetMapping("/new/{campaignId}")
    public String createDonationFormForCampaign(@PathVariable Long campaignId, Model model) {
        model.addAttribute("donation", new Donation());
        model.addAttribute("campaign", campaignService.getCampaignById(campaignId));
        return "donations";
    }

    @PostMapping
    public String saveDonation(@ModelAttribute Donation donation, @RequestParam Long campaignId) {
        Campaign campaign = campaignService.getCampaignById(campaignId);
        donation.setCampaign(campaign);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        com.donation.donation_platform.entity.User user = userService.findByUsername(username);
        donation.setUser(user);
        donation.setDonorName(username); // Always set donorName to logged-in user's username
        donationService.makeDonation(donation);
        return "donation_success";
    }
}
