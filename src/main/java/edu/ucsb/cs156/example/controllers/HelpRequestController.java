package edu.ucsb.cs156.example.controllers;


import edu.ucsb.cs156.example.repositories.HelpRequestRepository;
import edu.ucsb.cs156.example.entities.HelpRequest;
import java.time.ZonedDateTime;
import edu.ucsb.cs156.example.entities.UCSBDate;
import edu.ucsb.cs156.example.errors.EntityNotFoundException;
import edu.ucsb.cs156.example.repositories.UCSBDateRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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

import java.time.LocalDateTime;

/**
 * This is a REST controller for HelpRequests
 */

@Tag(name = "HelpRequests")
@RequestMapping("/api/helprequests")
@RestController
@Slf4j
public class HelpRequestController extends ApiController{

    @Autowired
    HelpRequestRepository helpRequestRepository;


      /**
     * List all HelpRequests
     * 
     * @return an iterable of HelpRequests
     */
    @Operation(summary= "List all help requests")
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/all")
    public Iterable<HelpRequest> allHelpRequests() {
        Iterable<HelpRequest> helprequests = helpRequestRepository.findAll();
        return helprequests;
    }


      /**
     * Create a new HelpRequest
     * 
     * @param requesterEmail  the email of the person submitting helprequest
     * @param teamId        the ID of the team
     * @param tableOrBreakoutRoom the name of the table that needs help
     * @param requestTime the time of the request
     * @param explanation explanation of what need help with
     * @param solved  tells us whether the helprequest is solved or not 
     * 
     * @return the saved helprequest 
     */
    @Operation(summary= "Create a new helprequest")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/post")
    public HelpRequest postHelpRequest(
            @Parameter(name="requesterEmail") @RequestParam String requesterEmail,
            @Parameter(name="teamId") @RequestParam String teamId,
            @Parameter(name="tableOrBreakoutRoom") @RequestParam String tableOrBreakoutRoom,
            @Parameter(name="explanation") @RequestParam String explanation,
            @Parameter(name="solved") @RequestParam Boolean solved,
            @Parameter(name="requestTime", description="date (in iso format, e.g. YYYY-mm-ddTHH:MM:SS; see https://en.wikipedia.org/wiki/ISO_8601)") @RequestParam("requestTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime requestTime)

            throws JsonProcessingException {

        // For an explanation of @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        // See: https://www.baeldung.com/spring-date-parameters

        log.info("requestTime={}", requestTime);


        HelpRequest helpRequest = new HelpRequest();
        helpRequest.setRequesterEmail(requesterEmail);
        helpRequest.setTeamId(teamId);
        helpRequest.setTableOrBreakoutRoom(tableOrBreakoutRoom);
        helpRequest.setRequestTime(requestTime);
        helpRequest.setExplanation(explanation);
        helpRequest.setSolved(solved);



        HelpRequest savedHelpRequest = helpRequestRepository.save(helpRequest);

        return savedHelpRequest;
    }

     /**
     * Get a single HelpRequest by id
     * 
     * @param id the id of the HelpRequest
     * @return a HelpRequest
     */
    @Operation(summary= "Get a single HelpRequest")
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("")
    public HelpRequest getById(
            @Parameter(name="id") @RequestParam Long id) {
        HelpRequest helpRequest = helpRequestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(HelpRequest.class, id));

        return helpRequest;
    }



}