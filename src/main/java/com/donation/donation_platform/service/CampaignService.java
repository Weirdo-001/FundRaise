package com.donation.donation_platform.service;

import com.donation.donation_platform.entity.Campaign;
import com.donation.donation_platform.entity.Donation;
import com.donation.donation_platform.repository.CampaignRepository;
import com.donation.donation_platform.repository.DonationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CampaignService {

    private final CampaignRepository campaignRepository;
    private final DonationRepository donationRepository;

    public CampaignService(CampaignRepository campaignRepository,
                           DonationRepository donationRepository) {
        this.campaignRepository = campaignRepository;
        this.donationRepository = donationRepository;
    }

    public List<Campaign> getAllCampaigns() {
        return campaignRepository.findAll();
    }

    public Campaign getCampaignById(Long id) {
        return campaignRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Campaign not found with id " + id));
    }

    public Campaign saveCampaign(Campaign campaign) {
        return campaignRepository.save(campaign);
    }

    /**
     * Admin delete campaign - cascades and removes its donations too.
     */
    @Transactional
    public void deleteCampaign(Long id) {
        Campaign campaign = campaignRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Campaign not found with id " + id));

        // delete all donations linked to this campaign
        List<Donation> donations = campaign.getDonations();
        if (donations != null && !donations.isEmpty()) {
            donationRepository.deleteAll(donations);
        }

        campaignRepository.delete(campaign);
    }
}
