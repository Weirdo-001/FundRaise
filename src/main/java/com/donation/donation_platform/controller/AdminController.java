package com.donation.donation_platform.controller;

import com.donation.donation_platform.entity.Admin;
import com.donation.donation_platform.entity.User;
import com.donation.donation_platform.entity.Donation;
import com.donation.donation_platform.service.AdminService;
import com.donation.donation_platform.service.UserService;
import com.donation.donation_platform.service.DonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    private final UserService userService;
    private final DonationService donationService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminController(AdminService adminService, UserService userService, DonationService donationService, PasswordEncoder passwordEncoder) {
        this.adminService = adminService;
        this.userService = userService;
        this.donationService = donationService;
        this.passwordEncoder = passwordEncoder;
    }

    // ----------------- AUTH -----------------

    @GetMapping("/register")
    public String showAdminRegister(Model model) {
        model.addAttribute("admin", new Admin());
        return "admin/register";
    }

    @PostMapping("/register")
    public String registerAdmin(@ModelAttribute Admin admin) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        adminService.saveAdmin(admin);
        return "redirect:/admin/login";
    }

    @GetMapping("/login")
    public String showAdminLogin() {
        return "admin/login";
    }

    // ----------------- DASHBOARD -----------------

    @GetMapping("/")
    public String adminDashboard(Model model) {
        List<User> users = userService.getAllUsers();
        List<Donation> donations = donationService.getAllDonations();
        model.addAttribute("users", users);
        model.addAttribute("donations", donations);
        return "admin/dashboard";
    }

    // ----------------- USER MANAGEMENT -----------------

    @GetMapping("/users/edit/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "admin/edit-user";
    }

    @PostMapping("/users/edit")
    public String editUser(@ModelAttribute User user) {
        userService.saveUser(user);
        return "redirect:/admin/";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/";
    }

    // ----------------- DONATION MANAGEMENT -----------------

    @GetMapping("/donations/edit/{id}")
    public String editDonationForm(@PathVariable Long id, Model model) {
        Donation donation = donationService.getDonationById(id);
        model.addAttribute("donation", donation);
        return "admin/edit-donation";
    }

    @PostMapping("/donations/edit")
    public String editDonation(@ModelAttribute Donation donation) {
        donationService.saveDonation(donation);
        return "redirect:/admin/";
    }

    @GetMapping("/donations/delete/{id}")
    public String deleteDonation(@PathVariable Long id) {
        donationService.deleteDonation(id);
        return "redirect:/admin/";
    }
}
