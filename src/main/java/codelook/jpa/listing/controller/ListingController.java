package codelook.jpa.listing.controller;

import codelook.jpa.listing.model.ListingInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/listings")
public class ListingController {

    @Autowired
    private ListingService listingService;

    /**
     * View all listings.
     */
    @GetMapping
    public String viewAllListings(Model model) {
        List<ListingInfo> listings = listingService.getAllListings();
        model.addAttribute("listings", listings);
        return "allListings";
    }

    /**
     * View a single listing by ID.
     */
    @GetMapping("/{id}")
    public String viewListing(@PathVariable Long id, Model model) {
        ListingInfo listing = listingService.getListingById(id);
        model.addAttribute("listing", listing);
        return "listing";
    }

    /**
     * Search for listings.
     */
    @GetMapping("/search")
    public String searchListings(@RequestParam String keyword, Model model) {
        List<ListingInfo> results = listingService.searchListings(keyword);
        model.addAttribute("listings", results);
        model.addAttribute("keyword", keyword);
        return "allListings";
    }

    /**
     * Add or edit a listing.
     */
    @PostMapping
    public String saveListing(@ModelAttribute ListingInfo listing) {
        listingService.saveListing(listing);
        return "redirect:/listings";
    }

    /**
     * Delete a listing by ID.
     */
    @PostMapping("/delete/{id}")
    public String deleteListing(@PathVariable Long id) {
        listingService.deleteListing(id);
        return "redirect:/listings";
    }
}
