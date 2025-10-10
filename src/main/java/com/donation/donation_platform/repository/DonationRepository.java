package com.donation.donation_platform.repository;

import com.donation.donation_platform.entity.Donation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonationRepository extends JpaRepository<Donation, Long> {
	java.util.List<Donation> findByDonorName(String donorName);
}
