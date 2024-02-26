package is.hi.hbv501g.hbv1.controllers;

import is.hi.hbv501g.hbv1.persistence.entities.User;
import is.hi.hbv501g.hbv1.persistence.entities.WaitingListRequest;
import is.hi.hbv501g.hbv1.persistence.entities.dto.ResponseWrapper;
import is.hi.hbv501g.hbv1.persistence.entities.dto.UserDTO;
import is.hi.hbv501g.hbv1.persistence.entities.dto.WaitingListRequestDTO;
import is.hi.hbv501g.hbv1.services.UserService;
import is.hi.hbv501g.hbv1.services.WaitingListService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * API for WaitingListRequest.
 *
 * @author  Andri Fannar Kristjánsson, afk6@hi.is.
 *          Converted to REST API 2024-02-15.
 * @since   2023-09-28
 * @version 2.0
 */
@RestController
@RequestMapping(path = "api/v1/waitingList")
public class WaitingListController
{
    // Variables.
    private final WaitingListService waitingListService;
    private final UserService userService;


    /**
     * Construct a new WaitingListController.
     *
     * @param waitingListService   WaitingListService linked to controller.
     * @param userService          UserService linked to controller.
     */
    @Autowired
    public WaitingListController(WaitingListService waitingListService, UserService userService)
    {
        this.waitingListService = waitingListService;
        this.userService = userService;
    }


    /**
     * Gets a list of all WaitingListRequests saved in the API.
     *
     * @return List of all saved WaitingListRequests.
     */
    @RequestMapping(value="/getAll", method=RequestMethod.GET)
    public ResponseEntity<ResponseWrapper<List<WaitingListRequestDTO>>> getAllRequests()
    {
        List<WaitingListRequest> requests = waitingListService.getAllWaitingListRequests();

        List<WaitingListRequestDTO> waitingListRequestDTOS = requests.stream()
                .map(WaitingListRequestDTO::new).toList();

        return new ResponseEntity<>(new ResponseWrapper<>(waitingListRequestDTOS), HttpStatus.OK);
    }


    /**
     * Gets a list of all WaitingListRequests saved in the API assigned to a physiotherapist.
     *
     * @return List of all saved WaitingListRequests assigned to a physiotherapist.
     */
    @RequestMapping(value="/getByPhysiotherapist", method=RequestMethod.GET)
    public ResponseEntity<ResponseWrapper<List<WaitingListRequestDTO>>> getAllRequests(@RequestBody User physiotherapist)
    {
        List<WaitingListRequest> requests = waitingListService.getWaitingListRequestByPhysiotherapist(physiotherapist);

        List<WaitingListRequestDTO> waitingListRequestDTOS = requests.stream()
                .map(WaitingListRequestDTO::new).toList();

        return new ResponseEntity<>(new ResponseWrapper<>(waitingListRequestDTOS), HttpStatus.OK);
    }


    /**
     * Create a new WaitingListRequest.
     *
     * @param requestDTO WaitingListRequest object to save.
     * @return           HttpStatus 200 if Request was saved successfully.
     *                   If User already has a Request assigned, returns HttpStatus 409.
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<ResponseWrapper<WaitingListRequestDTO>> createNewRequest(WaitingListRequestDTO requestDTO)
    {
        User requester = userService.getUserByID(requestDTO.getPatient().getId());

        // Check if User already has a Request linked.
        WaitingListRequest exists = waitingListService.getWaitingListRequestByPatient(requester);

        // If not, save the new Request.
        if(exists == null)
        {
            WaitingListRequest waitingListRequest = new WaitingListRequest(requestDTO);
            waitingListRequest.setPatient(requester);
            waitingListRequest.setStaff(userService.getUserByID(requestDTO.getStaff().getId()));

            waitingListService.saveNewWaitingListRequest(waitingListRequest);
            User registeredUser = userService.getUserByID(waitingListRequest.getPatient().getId());
            registeredUser.setWaitingListRequest(waitingListRequest);

            return new ResponseEntity<>(HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }


    /**
     * View WaitingListRequest.
     *
     * @param requestID ID of WaitingListRequest to view.
     * @return          Redirect.
     */
    @RequestMapping(value = "/view/{requestID}", method = RequestMethod.GET)
    public ResponseEntity<ResponseWrapper<WaitingListRequestDTO>> getRequest(@PathVariable("requestID") Long requestID)
    {
        // Get User to view.
        WaitingListRequest viewRequest = waitingListService.getWaitingListRequestByID(requestID);

        if (viewRequest != null)
        {
            WaitingListRequestDTO returnRequest = new WaitingListRequestDTO(viewRequest);

            return new ResponseEntity<>(new ResponseWrapper<>(returnRequest), HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    /**
     * Update WaitingListRequest.
     *
     * @param updatedRequest WaitingListRequest with updated info.
     * @return               HttpStatus 200.
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<ResponseWrapper<WaitingListRequestDTO>> updateRequest(@RequestBody WaitingListRequestDTO updatedRequest)
    {
        // Update WaitingListRequest.
        waitingListService.updateWaitingListRequest(updatedRequest);

        waitingListService.updateWaitingListRequestStatus(updatedRequest.getId(), false);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    /**
     * Accept WaitingListRequest.
     *
     * @param requestID ID of WaitingListRequest to accept.
     * @return          Redirect.
     */
    @RequestMapping(value = "/accept/{requestID}", method = RequestMethod.PUT)
    public ResponseEntity<ResponseWrapper<WaitingListRequestDTO>> acceptRequest(@PathVariable("requestID") Long requestID)
    {
        waitingListService.updateWaitingListRequestStatus(requestID, true);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }


    /**
     * Delete WaitingListRequest.
     *
     * @param requestID ID of WaitingListRequest to delete.
     * @return          Redirect.
     */
    @RequestMapping(value = "/delete/{requestID}", method = RequestMethod.DELETE)
    public ResponseEntity<ResponseWrapper<WaitingListRequestDTO>> deleteRequest(@PathVariable("requestID") Long requestID)
    {
        waitingListService.deleteWaitingListRequestByID(requestID);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
