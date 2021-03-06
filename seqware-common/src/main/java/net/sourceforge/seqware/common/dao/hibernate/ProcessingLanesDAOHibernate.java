package net.sourceforge.seqware.common.dao.hibernate;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import net.sourceforge.seqware.common.dao.ProcessingLanesDAO;
import net.sourceforge.seqware.common.model.Lane;
import net.sourceforge.seqware.common.model.Processing;
import net.sourceforge.seqware.common.model.ProcessingLanes;
import net.sourceforge.seqware.common.util.NullBeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * ProcessingLanesDAOHibernate class.
 * </p>
 * 
 * @author boconnor
 * @version $Id: $Id
 */
@Transactional(rollbackFor=Exception.class)
public class ProcessingLanesDAOHibernate extends HibernateDaoSupport implements ProcessingLanesDAO {
    private final Logger logger = LoggerFactory.getLogger(ProcessingLanesDAOHibernate.class);

    /** {@inheritDoc} */
    @Override
    public void insert(ProcessingLanes processingLanes) {
        this.getHibernateTemplate().save(processingLanes);
    }

    /** {@inheritDoc} */
    @Override
    public void update(ProcessingLanes processingLanes) {
        this.getHibernateTemplate().update(processingLanes);
    }

    /** {@inheritDoc} */
    @Override
    public void delete(ProcessingLanes processingLanes) {
        this.getHibernateTemplate().delete(processingLanes);
    }

    /** {@inheritDoc} */
    @Override
    @SuppressWarnings("rawtypes")
    public ProcessingLanes findByProcessingLane(Processing processing, Lane lane) {
        String query = "from ProcessingLanes as pl where pl.processing.processingId = ? and pl.lane.laneId = ?";
        ProcessingLanes obj = null;
        Object[] parameters = { processing.getProcessingId(), lane.getLaneId() };
        List list = this.getHibernateTemplate().find(query, parameters);
        if (list.size() > 0) {
            obj = (ProcessingLanes) list.get(0);
        }
        return obj;
    }

    /** {@inheritDoc} */
    @Override
    public ProcessingLanes updateDetached(ProcessingLanes processingLanes) {
        ProcessingLanes dbObject = findByProcessingLane(processingLanes.getProcessing(), processingLanes.getLane());
        try {
            BeanUtilsBean beanUtils = new NullBeanUtils();
            beanUtils.copyProperties(dbObject, processingLanes);
            return this.getHibernateTemplate().merge(dbObject);
        } catch (IllegalAccessException | InvocationTargetException e) {
            logger.error("ProcessingLanesDAOHibernate.updateDetached IllegalAccessException or InvocationTargetException exception:",e);
        }
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public List<ProcessingLanes> list() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
