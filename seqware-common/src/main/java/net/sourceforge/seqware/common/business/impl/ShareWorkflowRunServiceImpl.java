package net.sourceforge.seqware.common.business.impl;

import java.util.Date;
import java.util.List;
import net.sourceforge.seqware.common.business.ShareWorkflowRunService;
import net.sourceforge.seqware.common.dao.ShareWorkflowRunDAO;
import net.sourceforge.seqware.common.model.ShareWorkflowRun;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * ShareWorkflowRunServiceImpl class.
 * </p>
 * 
 * @author boconnor
 * @version $Id: $Id
 */
@Transactional(rollbackFor=Exception.class)
public class ShareWorkflowRunServiceImpl implements ShareWorkflowRunService {
    private ShareWorkflowRunDAO dao = null;
    private static final Log LOG = LogFactory.getLog(ShareWorkflowRunServiceImpl.class);

    /**
     * <p>
     * Constructor for ShareWorkflowRunServiceImpl.
     * </p>
     */
    public ShareWorkflowRunServiceImpl() {
        super();
    }

    /**
     * {@inheritDoc}
     * 
     * Sets a private member variable with an instance of an implementation of ShareWorkflowRunDAO. This method is called by the Spring
     * framework at run time.
     * 
     * @param dao
     * @see ShareWorkflowRunDAO
     */
    @Override
    public void setShareWorkflowRunDAO(ShareWorkflowRunDAO dao) {
        this.dao = dao;
    }

    /**
     * {@inheritDoc}
     * 
     * Inserts an instance of ShareWorkflowRun into the database.
     */
    @Override
    public void insert(ShareWorkflowRun shareWorkflowRun) {
        // shareWorkflowRun.setEmail(shareWorkflowRun.getEmail().trim().toLowerCase());
        shareWorkflowRun.setCreateTimestamp(new Date());

        dao.insert(shareWorkflowRun);
    }

    /**
     * {@inheritDoc}
     * 
     * Updates an instance of ShareWorkflowRun in the database.
     */
    @Override
    public void update(ShareWorkflowRun shareWorkflowRun) {
        dao.update(shareWorkflowRun);
    }

    /** {@inheritDoc} */
    @Override
    public void delete(ShareWorkflowRun shareWorkflowRun) {
        dao.delete(shareWorkflowRun);
    }

    /**
     * {@inheritDoc}
     * 
     * @param WorkflowRunId
     */
    @Override
    public boolean isExistsShare(Integer WorkflowRunId, Integer registrationId) {
        boolean isExists = false;
        if (findByWorkflowRunIdAndRegistrationId(WorkflowRunId, registrationId) != null) {
            isExists = true;
        }
        return isExists;
    }

    /** {@inheritDoc} */
    @Override
    public ShareWorkflowRun findByWorkflowRunIdAndRegistrationId(Integer workflowRunId, Integer registrationId) {
        ShareWorkflowRun shareWorkflowRun = null;
        if (workflowRunId != null && registrationId != null) {
            try {
                shareWorkflowRun = dao.findByWorkflowRunIdAndRegistrationId(workflowRunId, registrationId);
            } catch (Exception exception) {
                LOG.error("Cannot find ShareWorkflowRun by sorkflowRunID " + workflowRunId + " registrationId " + registrationId);
                LOG.error(exception.getMessage());
            }
        }
        return shareWorkflowRun;
    }

    /**
     * {@inheritDoc}
     * 
     * @param shareWorkflowRunId
     */
    @Override
    public ShareWorkflowRun findByID(Integer shareWorkflowRunId) {
        ShareWorkflowRun shareWorkflowRun = null;
        if (shareWorkflowRunId != null) {
            try {
                shareWorkflowRun = dao.findByID(shareWorkflowRunId);
            } catch (Exception exception) {
                LOG.error("Cannot find ShareWorkflowRun by expID " + shareWorkflowRunId);
                LOG.error(exception.getMessage());
            }
        }
        return shareWorkflowRun;
    }

    /** {@inheritDoc} */
    @Override
    public ShareWorkflowRun findBySWAccession(Integer swAccession) {
        ShareWorkflowRun shareWorkflowRun = null;
        if (swAccession != null) {
            try {
                shareWorkflowRun = dao.findBySWAccession(swAccession);
            } catch (Exception exception) {
                LOG.error("Cannot find ShareWorkflowRun by swAccession " + swAccession);
                LOG.error(exception.getMessage());
            }
        }
        return shareWorkflowRun;
    }

    /** {@inheritDoc} */
    @Override
    public ShareWorkflowRun updateDetached(ShareWorkflowRun shareWorkflowRun) {
        return dao.updateDetached(shareWorkflowRun);
    }

    /** {@inheritDoc} */
    @Override
    public List<ShareWorkflowRun> list() {
        return dao.list();
    }
}
