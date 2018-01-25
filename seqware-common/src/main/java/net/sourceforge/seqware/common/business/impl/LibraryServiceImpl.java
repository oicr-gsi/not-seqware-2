package net.sourceforge.seqware.common.business.impl;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Map;
import net.sourceforge.seqware.common.business.LibraryService;
import net.sourceforge.seqware.common.dao.LibraryDAO;
import net.sourceforge.seqware.common.model.Sample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
/**
 * <p>LibraryServiceImpl class.</p>
 *
 * @author boconnor
 * @version $Id: $Id
 */
@Transactional(rollbackFor=Exception.class)
public class LibraryServiceImpl implements LibraryService {

    @Autowired
    private LibraryDAO libraryDao;

    /** {@inheritDoc} */
    @Override
    public List<Sample> getLibraries() {
        return libraryDao.getLibraries();
    }

    /** {@inheritDoc} */
    @Override
    public List<Sample> getLibraries(Map<String, String> attributes) {
        List<Sample> result = Lists.newArrayList();
        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            List<Sample> libraries = libraryDao.getLibraries(entry.getKey(), entry.getValue());
            if (result.isEmpty()) {
                result = libraries;
            } else {
                result.retainAll(libraries); // Intersection.
            }
        }
        return result;
    }

    /** {@inheritDoc} */
    @Override
    public Sample findBySWAccession(Long swAccession) {
        return libraryDao.findBySWAccession(swAccession);
    }

}
