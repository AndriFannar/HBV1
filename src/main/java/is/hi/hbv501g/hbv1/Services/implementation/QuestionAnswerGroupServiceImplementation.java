package is.hi.hbv501g.hbv1.Services.implementation;

import is.hi.hbv501g.hbv1.persistence.entities.QuestionAnswerGroup;
import is.hi.hbv501g.hbv1.persistence.entities.dto.QuestionAnswerGroupDTO;
import is.hi.hbv501g.hbv1.persistence.repositories.QuestionAnswerGroupRepository;
import is.hi.hbv501g.hbv1.persistence.repositories.QuestionRepository;
import is.hi.hbv501g.hbv1.services.QuestionAnswerGroupService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for QuestionAnswerGroup objects.
 *
 * @author  Andri Fannar Kristjánsson, afk6@hi.is.
 * @since   2024-04-06
 * @version 1.0
 */
@Service
public class QuestionAnswerGroupServiceImplementation implements QuestionAnswerGroupService
{
    private final QuestionAnswerGroupRepository questionAnswerGroupRepository;
    private final QuestionRepository questionRepository;

    /**
     * Constructs a new QuestionAnswerGroupServiceImplementation.
     *
     * @param questionAnswerGroupRepository QuestionAnswerGroupRepository linked to service.
     * @param questionRepository            QuestionRepository linked to service.
     */
    @Autowired
    public QuestionAnswerGroupServiceImplementation(QuestionAnswerGroupRepository questionAnswerGroupRepository, QuestionRepository questionRepository)
    {
        this.questionAnswerGroupRepository = questionAnswerGroupRepository;
        this.questionRepository = questionRepository;
    }

    @Override
    public QuestionAnswerGroup saveNewQuestionAnswerGroup(QuestionAnswerGroupDTO questionAnswerGroupDto) {
        QuestionAnswerGroup questionAnswerGroup = new QuestionAnswerGroup();
        questionAnswerGroup.setQuestionAnswers(questionAnswerGroupDto.getQuestionAnswers());
        questionAnswerGroup.setQuestions(questionRepository.findAllById(questionAnswerGroupDto.getQuestionIDs()));

        return questionAnswerGroupRepository.save(questionAnswerGroup);
    }

    @Override
    public List<QuestionAnswerGroup> getAllQuestionAnswerGroup() {
        return questionAnswerGroupRepository.getAllByOrderByIdDesc();
    }

    @Override
    public QuestionAnswerGroup getQuestionAnswerGroupById(Long questionAnswerGroupID) {
        return questionAnswerGroupRepository.getQuestionAnswerGroupById(questionAnswerGroupID);
    }

    @Override
    @Transactional
    public void updateQuestionAnswerGroup(QuestionAnswerGroupDTO updatedQuestionAnswerGroupDto)
    {
        QuestionAnswerGroup existing = questionAnswerGroupRepository.getQuestionAnswerGroupById(updatedQuestionAnswerGroupDto.getId());

        if (existing != null)
        {
            if (updatedQuestionAnswerGroupDto.getGroupName() != null) existing.setGroupName(updatedQuestionAnswerGroupDto.getGroupName());
            if (updatedQuestionAnswerGroupDto.getQuestionAnswers() != null) existing.setQuestionAnswers(updatedQuestionAnswerGroupDto.getQuestionAnswers());
            if (updatedQuestionAnswerGroupDto.getQuestionIDs() != null) existing.setQuestions(questionRepository.findAllById(updatedQuestionAnswerGroupDto.getQuestionIDs()));
        }
    }

    @Override
    public void deleteQuestionAnswerGroupByID(Long questionAnswerGroupID)
    {
        questionAnswerGroupRepository.deleteById(questionAnswerGroupID);
    }
}
