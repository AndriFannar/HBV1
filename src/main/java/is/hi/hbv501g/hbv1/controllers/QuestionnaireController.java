package is.hi.hbv501g.hbv1.controllers;

import is.hi.hbv501g.hbv1.persistence.entities.*;
import is.hi.hbv501g.hbv1.persistence.entities.dto.QuestionnaireDTO;
import is.hi.hbv501g.hbv1.persistence.entities.dto.ResponseWrapper;
import is.hi.hbv501g.hbv1.services.QuestionnaireService;

import is.hi.hbv501g.hbv1.services.WaitingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * API for Questionnaire.
 *
 * @author  Andri Fannar Kristjánsson, afk6@hi.is.
 *          Converted to REST API 2024-02-15.
 * @since   2023-09-29
 * @version 2.0
 */
@RestController
@RequestMapping(path = "api/v1/questionnaire")
public class QuestionnaireController
{
    // Variables.
    private final QuestionnaireService questionnaireService;
    private final WaitingListService waitingListService;


    /**
     * Construct a new QuestionnaireController.
     *
     * @param questionnaireService QuestionnaireService linked to controller.
     * @param waitingListService   WaitingListService linked to controller.
     */
    @Autowired
    public QuestionnaireController(QuestionnaireService questionnaireService, WaitingListService waitingListService)
    {
        this.questionnaireService = questionnaireService;
        this.waitingListService = waitingListService;
    }


    /**
     * Get all Questionnaires saved to the API.
     *
     * @return List of all Questionnaire saved to the API.
     */
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public ResponseEntity<ResponseWrapper<List<QuestionnaireDTO>>> getAllQuestionnaire()
    {
        List<QuestionnaireDTO> questionnaires = questionnaireService.getAllQuestionnaires()
                .stream().map(QuestionnaireDTO::new).toList();

        return new ResponseEntity<>(new ResponseWrapper<>(questionnaires), HttpStatus.OK);
    }

    /**
     * Get a Questionnaire by ID.
     *
     * @param id ID of Questionnaire to get.
     * @return   Questionnaire with given ID.
     */
    @RequestMapping(value="/get/{id}", method = RequestMethod.GET)
    public ResponseEntity<ResponseWrapper<QuestionnaireDTO>> getQuestionnaire(@PathVariable("id") Long id)
    {
        QuestionnaireDTO questionnaire = new QuestionnaireDTO(questionnaireService.getQuestionnaireByID(id));

        return new ResponseEntity<>(new ResponseWrapper<>(questionnaire), HttpStatus.OK);
    }


    /**
     * Get all Questionnaires saved to the API that are marked to be displayed on the request form.
     *
     * @return List of all Questionnaire saved to the API that are marked be displayed on the request form.
     */
    @RequestMapping(value = "/getAllToDisplay", method = RequestMethod.GET)
    public ResponseEntity<ResponseWrapper<List<QuestionnaireDTO>>> getAllQuestionnaireToDisplay()
    {
        List<QuestionnaireDTO> questionnaires = questionnaireService.getAllQuestionnaires()
                .stream().map(QuestionnaireDTO::new).toList();

        return new ResponseEntity<>(new ResponseWrapper<>(questionnaires), HttpStatus.OK);
    }


    /**
     * Create a new Questionnaire.
     *
     * @param questionnaire New Questionnaire.
     * @return              Saves a new Questionnaire to the database.
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<ResponseWrapper<QuestionnaireDTO>> createQuestionnaire(@RequestBody QuestionnaireDTO questionnaire)
    {
        questionnaireService.saveNewQuestionnaire(questionnaire);

        return new ResponseEntity<>(new ResponseWrapper<>(questionnaire), HttpStatus.CREATED);
    }


    /**
     * Toggle display Questionnaire on registration form.
     *
     * @param questionnaireID ID of Questionnaire to toggle.
     * @return                HttpStatus 200.
     */
    @RequestMapping(value = "/toggleDisplayOnForm/{questionnaireID}", method = RequestMethod.PUT)
    public ResponseEntity<ResponseWrapper<QuestionnaireDTO>> toggleDisplayOnForm(@PathVariable("questionnaireID") Long questionnaireID)
    {
        questionnaireService.toggleDisplayQuestionnaireOnForm(questionnaireID);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    /**
     * Delete a Questionnaire.
     *
     * @param questionnaireID ID of Questionnaire to delete.
     * @return                HttpStatus 200 if deleted successfully,
     *                        HttpStatus 409 if Questionnaire has dependencies.
     *                        HttpStatus 404 if Questionnaire does not exist.
     */
    @RequestMapping(value = "/delete/{questionnaireID}", method = RequestMethod.DELETE)
    public ResponseEntity<ResponseWrapper<QuestionnaireDTO>> deleteQuestionnaire(@PathVariable("questionnaireID") Long questionnaireID)
    {
        Questionnaire questionnaire = questionnaireService.getQuestionnaireByID(questionnaireID);

        // Check if questionnaire exists.
        if (questionnaire != null)
        {
            // If Questionnaire has no connected WaitingListRequests, then delete.
            if(questionnaire.getWaitingListRequests().isEmpty())
            {
                questionnaireService.deleteQuestionnaireByID(questionnaireID);

                return new ResponseEntity<>(HttpStatus.OK);
            }
            // If Questionnaire has dependencies, then return a conflict.
            else
                return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    /**
     * Add a Question to a Questionnaire.
     *
     * @param questionID      ID of Question to add to Questionnaire.
     * @param questionnaireID ID of Questionnaire to add Question to.
     * @return                HttpStatus 200.
     */
    @RequestMapping(value = "edit/{questionnaireID}/addQuestion/{questionID}", method = RequestMethod.PUT)
    public ResponseEntity<ResponseWrapper<QuestionnaireDTO>> addQuestion(@PathVariable("questionID") Long questionID, @PathVariable("questionnaireID") Long questionnaireID)
    {
        questionnaireService.addQuestionToQuestionnaire(questionID, questionnaireID);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    /**
     * Remove a Question from a Questionnaire.
     *
     * @param questionID      ID of Question to remove from Questionnaire.
     * @param questionnaireID ID of Questionnaire to remove Question from.
     * @return                Redirect back to page.
     */
    @RequestMapping(value = "edit/{questionnaireID}/removeQuestion/{questionID}", method = RequestMethod.PUT)
    public ResponseEntity<ResponseWrapper<QuestionnaireDTO>> removeQuestion(@PathVariable("questionID") Long questionID , @PathVariable("questionnaireID") Long questionnaireID)
    {
        questionnaireService.removeQuestionFromQuestionnaire(questionID, questionnaireID);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    /**
     * Form for answering a Questionnaire.
     *
     * @param requestID     ID of WaitingListRequest the answers belong to.
     * @param questionnaire Questionnaire with answers.
     * @return              Redirect.
     */
    @RequestMapping(value = "{requestID}/answerQuestionnaire", method = RequestMethod.POST)
    public ResponseEntity<ResponseWrapper<QuestionnaireDTO>> answerQuestionnaire(@PathVariable("requestID") Long requestID, QuestionnaireDTO questionnaire)
    {
        Questionnaire mainQuestionnaire = questionnaireService.getQuestionnaireByID(questionnaire.getId());
        waitingListService.updateQuestionnaireAnswers(requestID, mainQuestionnaire);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}