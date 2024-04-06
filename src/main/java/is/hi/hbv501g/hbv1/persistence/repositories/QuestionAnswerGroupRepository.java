package is.hi.hbv501g.hbv1.persistence.repositories;

import is.hi.hbv501g.hbv1.persistence.entities.QuestionAnswerGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository class for QuestionAnswerGroup objects.
 *
 * @author  Andri Fannar Kristjánsson, afk6@hi.is.
 * @since   2024-04-06
 * @version 1.0
 */
@Repository
public interface QuestionAnswerGroupRepository extends JpaRepository<QuestionAnswerGroup, Long>
{
    /**
     * Saves a new QuestionAnswerGroup to the repository.
     *
     * @param questionAnswerGroup QuestionAnswerGroup to save.
     * @return                    Saved QuestionAnswerGroup.
     */
    @NonNull
    QuestionAnswerGroup save(@NonNull QuestionAnswerGroup questionAnswerGroup);

    /**
     * Delete a QuestionAnswerGroup by ID.
     *
     * @param id ID of QuestionAnswerGroup to delete.
     */
    void deleteById(@NonNull Long id);

    /**
     * Get a QuestionAnswerGroup by ID.
     *
     * @param id ID of QuestionAnswerGroup to get.
     * @return   QuestionAnswerGroup with a matching ID.
     */
    QuestionAnswerGroup getQuestionAnswerGroupById(@NonNull Long id);

    /**
     * Get all QuestionAnswerGroup objects.
     *
     * @return List of all QuestionAnswerGroup stored in repository.
     */
    List<QuestionAnswerGroup> getAllByOrderByIdDesc();
}
