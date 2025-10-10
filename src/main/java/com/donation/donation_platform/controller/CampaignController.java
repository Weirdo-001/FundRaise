    @GetMapping("/edit/{id}")
    public String editCampaignForm(@PathVariable Long id, Model model) {
        model.addAttribute("campaign", campaignService.getCampaignById(id));
        return "campaign_edit_form";
    }

    @PostMapping("/edit")
    public String updateCampaign(@ModelAttribute Campaign campaign) {
        campaignService.saveCampaign(campaign);
        return "redirect:/dashboard";
    }
package com.donation.donation_platform.controller;

import com.donation.donation_platform.entity.Campaign;
import com.donation.donation_platform.service.CampaignService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/campaigns")
public class CampaignController {

    private final CampaignService campaignService;

    public CampaignController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @GetMapping
    public String listCampaigns(Model model) {
        model.addAttribute("campaigns", campaignService.getAllCampaigns());
        return "campaigns"; // campaigns.html
    }

    @GetMapping("/new")
    public String createCampaignForm(Model model) {
        model.addAttribute("campaign", new Campaign());
        return "campaign_form"; // campaign_form.html
    }

    @PostMapping
    public String saveCampaign(@ModelAttribute Campaign campaign) {
        campaignService.saveCampaign(campaign);
        return "redirect:/campaigns";
    }

    @GetMapping("/delete/{id}")
    public String deleteCampaign(@PathVariable Long id) {
        campaignService.deleteCampaign(id);
        return "redirect:/campaigns";
    }
}
