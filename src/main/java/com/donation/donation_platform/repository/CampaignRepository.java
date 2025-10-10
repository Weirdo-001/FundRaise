package com.donation.donation_platform.repository;

import com.donation.donation_platform.entity.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {
}
