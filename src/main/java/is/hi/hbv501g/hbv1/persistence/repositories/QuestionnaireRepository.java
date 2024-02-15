package is.hi.hbv501g.hbv1.persistence.repositories;

import is.hi.hbv501g.hbv1.persistence.entities.Questionnaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Repository class for Questionnaire objects.
 *
 * @author  Andri Fannar Kristjánsson, afk6@hi.is.
 * @since   2023-11-07
 * @version 1.0
 */
@Repository
public interface QuestionnaireRepository extends JpaRepository<Questionnaire, Long>
{
    /**
     * Save a Questionnaire object to database.
     *
     * @param questionnaire must not be {@literal null}.
     * @return              Saved object.
     */
    @NonNull
    Questionnaire save(@NonNull Questionnaire questionnaire);


    /**
     * Delete Questionnaire with corresponding ID.
     *
     * @param id must not be {@literal null}.
     */
    void deleteById(@NonNull Long id);


    /**
     * Find all Questionnaire objects.
     *
     * @return List of all Questionnaire objects.
     */
    List<Questionnaire> getAllByOrderByNameAsc();


    /**
     * Finds Questionnaire by unique ID.
     *
     * @param id Unique ID of the Questionnaire.
     * @return   Questionnaire with matching id, if any.
     */
    @NonNull
    Questionnaire getById(@NonNull Long id);


    /**
     * Finds Questionnaire by if they should be displayed on the registration form.
     *
     * @param displayOnForm Display on registration form.
     * @return              List of Questionnaire that the user can choose when creating a new WaitingListRequest.
     */
    List<Questionnaire> getByDisplayOnForm(boolean displayOnForm);
}
