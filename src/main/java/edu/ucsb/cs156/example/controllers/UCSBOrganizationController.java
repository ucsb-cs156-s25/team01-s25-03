package edu.ucsb.cs156.example.controllers;

import edu.ucsb.cs156.example.entities.UCSBOrganization;
import edu.ucsb.cs156.example.errors.EntityNotFoundException;
import edu.ucsb.cs156.example.repositories.UCSBOrganizationRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;


/**
 * This is a REST controller for UCSBOrganizations
 */


 @Tag(name = "UCSBOrganization")
 @RequestMapping("/api/ucsborganization")
 @RestController
 @Slf4j
public class UCSBOrganizationController {
    @Autowired
    UCSBOrganizationRepository ucsbOrganizationRepository;

    /**
     * THis method returns a list of all ucsbdiningcommons.
     * @return a list of all ucsbdiningcommons
     */
    @Operation(summary= "List all ucsb oranizations")
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/all")
    public Iterable<UCSBOrganization> allOrganizations() {
        Iterable<UCSBOrganization> organization = ucsbOrganizationRepository.findAll();
        return organization;
    }

    /**
     * This method returns a single diningcommons.
     * @param code code of the diningcommons
     * @return a single diningcommons
     */
    // @Operation(summary= "Get a single commons")
    // @PreAuthorize("hasRole('ROLE_USER')")
    // @GetMapping("")
    // public UCSBDiningCommons getById(
    //         @Parameter(name="code") @RequestParam String code) {
    //     UCSBDiningCommons commons = ucsbDiningCommonsRepository.findById(code)
    //             .orElseThrow(() -> new EntityNotFoundException(UCSBDiningCommons.class, code));

    //     return commons;
    // }

    /**
     * This method creates a new diningcommons. Accessible only to users with the role "ROLE_ADMIN".
     * @param code code of the diningcommons
     * @param name name of the diningcommons
     * @param hasSackMeal whether or not the commons has sack meals
     * @param hasTakeOutMeal whether or not the commons has take out meals
     * @param hasDiningCam whether or not the commons has a dining cam
     * @param latitude latitude of the commons
     * @param longitude logitude of the commons
     * @return the save diningcommons
     */
    @Operation(summary= "Create a new organization")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/post")
    public UCSBOrganization postOrganization(
        @Parameter(name="orgCode") @RequestParam String orgCode,
        @Parameter(name="orgTranslationShort") @RequestParam String orgTranslationShort,
        @Parameter(name="orgTranslation") @RequestParam String orgTranslation,
        @Parameter(name="inactive") @RequestParam boolean inactive
        )
        {

        UCSBOrganization organization = new UCSBOrganization();
        organization.setOrgCode(orgCode);
        organization.setOrgTranslationShort(orgTranslationShort);
        organization.setOrgTranslation(orgTranslation);
        organization.setInactive(inactive);

        UCSBOrganization savedOrganization = ucsbOrganizationRepository.save(organization);

        return savedOrganization;
    }

    /**
     * Delete a diningcommons. Accessible only to users with the role "ROLE_ADMIN".
     * @param code code of the commons
     * @return a message indiciating the commons was deleted
     */
    // @Operation(summary= "Delete a UCSBDiningCommons")
    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    // @DeleteMapping("")
    // public Object deleteCommons(
    //         @Parameter(name="code") @RequestParam String code) {
    //     UCSBDiningCommons commons = ucsbDiningCommonsRepository.findById(code)
    //             .orElseThrow(() -> new EntityNotFoundException(UCSBDiningCommons.class, code));

    //     ucsbDiningCommonsRepository.delete(commons);
    //     return genericMessage("UCSBDiningCommons with id %s deleted".formatted(code));
    // }

    /**
     * Update a single diningcommons. Accessible only to users with the role "ROLE_ADMIN".
     * @param code code of the diningcommons
     * @param incoming the new commons contents
     * @return the updated commons object
     */
    // @Operation(summary= "Update a single commons")
    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    // @PutMapping("")
    // public UCSBDiningCommons updateCommons(
    //         @Parameter(name="code") @RequestParam String code,
    //         @RequestBody @Valid UCSBDiningCommons incoming) {

    //     UCSBDiningCommons commons = ucsbDiningCommonsRepository.findById(code)
    //             .orElseThrow(() -> new EntityNotFoundException(UCSBDiningCommons.class, code));


    //     commons.setName(incoming.getName());  
    //     commons.setHasSackMeal(incoming.getHasSackMeal());
    //     commons.setHasTakeOutMeal(incoming.getHasTakeOutMeal());
    //     commons.setHasDiningCam(incoming.getHasDiningCam());
    //     commons.setLatitude(incoming.getLatitude());
    //     commons.setLongitude(incoming.getLongitude());

    //     ucsbDiningCommonsRepository.save(commons);

    //     return commons;
    // }
}
