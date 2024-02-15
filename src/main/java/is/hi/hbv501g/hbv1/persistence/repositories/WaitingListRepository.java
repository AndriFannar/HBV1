package is.hi.hbv501g.hbv1.persistence.repositories;

import is.hi.hbv501g.hbv1.persistence.entities.User;
import is.hi.hbv501g.hbv1.persistence.entities.WaitingListRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository class for WaitingListRequest.
 *
 * @author  Andri Fannar Kristjánsson, afk6@hi.is.
 * @since   2023-09-28
 * @version 1.0
 */
@Repository
public interface WaitingListRepository extends JpaRepository<WaitingListRequest, Long>
{
    /**
     * Save a WaitingListRequest object to database.
     *
     * @param waitingListRequest must not be {@literal null}.
     * @return                   Saved object.
     */
    @NonNull
    WaitingListRequest save(@NonNull WaitingListRequest waitingListRequest);


    /**
     * Delete WaitingListRequest with corresponding ID.
     *
     * @param id must not be {@literal null}.
     */
    void deleteById(@NonNull Long id);


    /**
     * Finds all WaitingListRequest objects in database.
     *
     * @return List of all WaitingListRequest objects in database, if any.
     */
    List<WaitingListRequest> getAllByOrderByGradeDescPatientNameAsc();


    /**
     * Finds WaitingListRequest by unique ID.
     *
     * @param id Unique id of WaitingListRequest.
     * @return   WaitingListRequest with matching id, if any.
     */
    @NonNull
    WaitingListRequest getById(@NonNull Long id);


    /**
     * Finds WaitingListRequest by patient.
     *
     * @param patient Patient registered for the WaitingListRequest.
     * @return        WaitingListRequest with matching patient, if any.
     */
    WaitingListRequest getByPatient(User patient);

    
    /**
     * Finds WaitingListRequest by staff (physiotherapist).
     *
     * @param staff Staff member assigned to the WaitingListRequest.
     * @return      List of WaitingListRequest with matching Staff member, if any.
     */
    List<WaitingListRequest> getByStaff(User staff);
}
