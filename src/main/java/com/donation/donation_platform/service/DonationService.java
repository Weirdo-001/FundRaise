package com.donation.donation_platform.service;

import com.donation.donation_platform.entity.Campaign;
import com.donation.donation_platform.entity.Donation;
import com.donation.donation_platform.repository.CampaignRepository;
import com.donation.donation_platform.repository.DonationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DonationService {

    private final DonationRepository donationRepository;
    private final CampaignRepository campaignRepository;

    public DonationService(DonationRepository donationRepository, CampaignRepository campaignRepository) {
        this.donationRepository = donationRepository;
        this.campaignRepository = campaignRepository;
    }

    public List<Donation> getAllDonations() {
        return donationRepository.findAll();
    }

    public List<Donation> getDonationsByDonorName(String donorName) {
        return donationRepository.findByDonorName(donorName);
    }

    public Donation getDonationById(Long id) {
        return donationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Donation not found with id " + id));
    }

    /**
     * Normal user donation - updates campaign collected amount
     */
    @Transactional
    public Donation makeDonation(Donation donation) {
        Campaign campaign = donation.getCampaign();
        campaign.setCollectedAmount(campaign.getCollectedAmount() + donation.getAmount());
        campaignRepository.save(campaign);

        return donationRepository.save(donation);
    }

    /**
     * Admin edit donation (does not alter campaign total)
     */
    @Transactional
    public Donation saveDonation(Donation donation) {
        return donationRepository.save(donation);
    }

    /**
     * Admin delete donation - updates campaign total accordingly
     */
    @Transactional
    public void deleteDonation(Long id) {
        Donation donation = donationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Donation not found with id " + id));

        Campaign campaign = donation.getCampaign();
        if (campaign != null) {
            double newTotal = campaign.getCollectedAmount() - donation.getAmount();
            campaign.setCollectedAmount(Math.max(newTotal, 0)); // prevent negative totals
            campaignRepository.save(campaign);
        }

        donationRepository.deleteById(id);
    }
}
