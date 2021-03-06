package net.sourceforge.seqware.common.business.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import net.sourceforge.seqware.common.business.FileService;
import net.sourceforge.seqware.common.dao.FileDAO;
import net.sourceforge.seqware.common.model.File;
import net.sourceforge.seqware.common.model.Processing;
import net.sourceforge.seqware.common.model.Registration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * FileServiceImpl class.
 * </p>
 *
 * @author boconnor
 * @version $Id: $Id
 */
@Transactional(rollbackFor=Exception.class)
public class FileServiceImpl implements FileService {

    private FileDAO fileDAO = null;
    private static final Log LOG = LogFactory.getLog(FileServiceImpl.class);

    /**
     * <p>
     * Constructor for FileServiceImpl.
     * </p>
     */
    public FileServiceImpl() {
        super();
    }

    /**
     * {@inheritDoc}
     *
     * Sets a private member variable with an instance of an implementation of FileDAO. This method is called by the Spring framework at run
     * time.
     *
     * @param dao
     * @see FileDAO
     */
    @Override
    public void setFileDAO(FileDAO dao) {
        this.fileDAO = dao;
    }

    /**
     * {@inheritDoc}
     *
     * Inserts an instance of File into the database.
     *
     * @return
     */
    @Override
    public Integer insert(File file) {
        return fileDAO.insert(file);
    }

    /**
     * {@inheritDoc}
     *
     * Updates an instance of File in the database.
     */
    @Override
    public void update(File file) {
        fileDAO.update(file);
    }

    /**
     * {@inheritDoc}
     *
     * @param file
     * @param deleteRealFiles
     */
    @Override
    public void delete(File file, boolean deleteRealFiles) {
        Set<Processing> processings = file.getProcessings();

        for (Processing processing : processings) {
            processing.getFiles().remove(file);
        }

        file.getProcessings().clear();

        fileDAO.delete(file);

        if (deleteRealFiles) {
            List<File> deleteFiles = new LinkedList<>();
            deleteFiles.add(file);
            fileDAO.deleteAllWithFolderStore(deleteFiles);
        }

    }

    /**
     * {@inheritDoc}
     *
     * @param files
     * @param deleteRealFiles
     */
    @Override
    public void deleteAll(List<File> files, boolean deleteRealFiles) {
        fileDAO.deleteAll(files);
        if (deleteRealFiles) {
            fileDAO.deleteAllWithFolderStore(files);
        }
    }

    /** {@inheritDoc} */
    @Override
    public boolean isExists(String fileName, String folderStore) {
        boolean isExists = false;
        String path = folderStore + fileName;
        if (findByPath(path) != null) {
            isExists = true;
        }
        return isExists;
    }

    /** {@inheritDoc} */
    @Override
    public List<File> getFiles(Integer fileId) {
        File file = findByID(fileId);
        List<File> files = new ArrayList<>();
        files.add(file);
        return files;
    }

    /** {@inheritDoc} */
    @Override
    public List<File> getFiles(Integer fileId, String metaType) {
        File file = findByID(fileId);
        List<File> files = new ArrayList<>();
        if (metaType.equals(file.getMetaType())) {
            files.add(file);
        }
        return files;
    }

    /** {@inheritDoc} */
    @Override
    public Set<File> setWithHasFile(Set<File> list, String metaType) {
        Set<File> result = new TreeSet<>();
        for (File file : list) {
            if (metaType.equals(file.getMetaType())) {
                result.add(file);
            }
        }
        return result;
    }

    /** {@inheritDoc} */
    @Override
    public File findByPath(String path) {
        File file = null;
        if (path != null) {
            try {
                file = fileDAO.findByPath(path.trim());
            } catch (Exception exception) {
                LOG.debug("Cannot find file by path " + path);
            }
        }
        return file;
    }

    /**
     * {@inheritDoc}
     *
     * @param fileId
     */
    @Override
    public File findByID(Integer fileId) {
        File file = null;
        if (fileId != null) {
            try {
                file = fileDAO.findByID(fileId);
            } catch (Exception exception) {
                LOG.error("Cannot find File by expID " + fileId);
                LOG.error(exception.getMessage());
            }
        }
        return file;
    }

    /** {@inheritDoc} */
    @Override
    public File findBySWAccession(Integer swAccession) {
        File file = null;
        if (swAccession != null) {
            try {
                file = fileDAO.findBySWAccession(swAccession);
            } catch (Exception exception) {
                LOG.error("Cannot find File by swAccession " + swAccession);
                LOG.error(exception.getMessage());
            }
        }
        return file;
    }

    /** {@inheritDoc} */
    @Override
    public List<File> findByCriteria(String criteria, boolean isCaseSens) {
        return fileDAO.findByCriteria(criteria, isCaseSens);
    }

    /** {@inheritDoc} */
    @Override
    public File updateDetached(File file) {
        return fileDAO.updateDetached(file);
    }

    /** {@inheritDoc} */
    @Override
    public List<File> findByOwnerId(Integer registrationId) {
        List<File> files = null;
        if (registrationId != null) {
            try {
                files = fileDAO.findByOwnerId(registrationId);
            } catch (Exception exception) {
                LOG.error("Cannot find Files by registrationId " + registrationId);
                LOG.error(exception.getMessage());
            }
        }
        return files;
    }

    /** {@inheritDoc} */
    @Override
    public List<File> list() {
        return fileDAO.list();
    }

    /** {@inheritDoc} */
    @Override
    public void update(Registration registration, File file) {
        fileDAO.update(registration, file);
    }

    /** {@inheritDoc} */
    @Override
    public Integer insert(Registration registration, File file) {
        return fileDAO.insert(registration, file);
    }

    /** {@inheritDoc} */
    @Override
    public File updateDetached(Registration registration, File file) {
        return fileDAO.updateDetached(registration, file);
    }
}
