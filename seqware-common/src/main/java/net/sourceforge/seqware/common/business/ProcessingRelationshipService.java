package net.sourceforge.seqware.common.business;

import java.util.List;
import net.sourceforge.seqware.common.dao.ProcessingRelationshipDAO;
import net.sourceforge.seqware.common.model.Processing;
import net.sourceforge.seqware.common.model.ProcessingRelationship;

/**
 * <p>
 * ProcessingRelationshipService interface.
 * </p>
 * 
 * @author boconnor
 * @version $Id: $Id
 */
public interface ProcessingRelationshipService {

    /**
     * <p>
     * setProcessingRelationshipDAO.
     * </p>
     * 
     * @param dao
     *            a {@link net.sourceforge.seqware.common.dao.ProcessingRelationshipDAO} object.
     */
    void setProcessingRelationshipDAO(ProcessingRelationshipDAO dao);

    /**
     * <p>
     * findByProcessings.
     * </p>
     * 
     * @param processingParent
     *            a {@link net.sourceforge.seqware.common.model.Processing} object.
     * @param processingChild
     *            a {@link net.sourceforge.seqware.common.model.Processing} object.
     * @return a {@link net.sourceforge.seqware.common.model.ProcessingRelationship} object.
     */
    ProcessingRelationship findByProcessings(Processing processingParent, Processing processingChild);

    /**
     * <p>
     * delete.
     * </p>
     * 
     * @param processingRelationship
     *            a {@link net.sourceforge.seqware.common.model.ProcessingRelationship} object.
     */
    void delete(ProcessingRelationship processingRelationship);

    /**
     * <p>
     * update.
     * </p>
     * 
     * @param processingRelationship
     *            a {@link net.sourceforge.seqware.common.model.ProcessingRelationship} object.
     */
    void update(ProcessingRelationship processingRelationship);

    /**
     * <p>
     * insert.
     * </p>
     * 
     * @param processingRelationship
     *            a {@link net.sourceforge.seqware.common.model.ProcessingRelationship} object.
     */
    void insert(ProcessingRelationship processingRelationship);

    /**
     * <p>
     * updateDetached.
     * </p>
     * 
     * @param processingRelationship
     *            a {@link net.sourceforge.seqware.common.model.ProcessingRelationship} object.
     * @return a {@link net.sourceforge.seqware.common.model.ProcessingRelationship} object.
     */
    ProcessingRelationship updateDetached(ProcessingRelationship processingRelationship);

    /**
     * <p>
     * list.
     * </p>
     * 
     * @return a {@link java.util.List} object.
     */
    List<ProcessingRelationship> list();

    List<ProcessingRelationship> listByParentProcessingId(int parentId);

}
