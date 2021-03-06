/*
 * Copyright (C) 2011 SeqWare
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.sourceforge.seqware.webservice.resources.tables;

import java.io.IOException;
import java.util.Set;
import net.sf.beanlib.CollectionPropertyName;
import net.sf.beanlib.hibernate3.Hibernate3DtoCopier;
import net.sourceforge.seqware.common.business.LaneService;
import net.sourceforge.seqware.common.business.RegistrationService;
import net.sourceforge.seqware.common.business.SampleService;
import net.sourceforge.seqware.common.factory.BeanFactory;
import net.sourceforge.seqware.common.model.Lane;
import net.sourceforge.seqware.common.model.LaneAttribute;
import net.sourceforge.seqware.common.model.LibrarySelection;
import net.sourceforge.seqware.common.model.LibrarySource;
import net.sourceforge.seqware.common.model.LibraryStrategy;
import net.sourceforge.seqware.common.model.Registration;
import net.sourceforge.seqware.common.model.Sample;
import net.sourceforge.seqware.common.model.SequencerRun;

import net.sourceforge.seqware.common.util.xmltools.JaxbObject;
import net.sourceforge.seqware.common.util.xmltools.XmlTools;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * <p>
 * LaneIDResource class.
 * </p>
 *
 * @author mtaschuk
 * @version $Id: $Id
 */
public class LaneIDResource extends DatabaseIDResource {
    private final Logger logger = LoggerFactory.getLogger(LaneIDResource.class);

    /**
     * <p>
     * Constructor for LaneIDResource.
     * </p>
     */
    public LaneIDResource() {
        super("laneId");
    }

    /**
     * <p>
     * getXml.
     * </p>
     */
    @Get
    public void getXml() {
        JaxbObject<Lane> jaxbTool = new JaxbObject<>();
        Hibernate3DtoCopier copier = new Hibernate3DtoCopier();

        Lane dto;
        authenticate();

        LaneService ss = BeanFactory.getLaneServiceBean();
        Lane lane = testIfNull(ss.findBySWAccession(getId()));
        CollectionPropertyName<Lane>[] createCollectionPropertyNames = CollectionPropertyName.createCollectionPropertyNames(Lane.class,
                new String[] { "laneAttributes" });
        dto = copier.hibernate2dto(Lane.class, lane, new Class<?>[] { LibraryStrategy.class, LibrarySource.class, LibrarySelection.class },
                createCollectionPropertyNames);

        if (fields.contains("sequencerRun")) {
            SequencerRun sr = lane.getSequencerRun();
            if (sr != null) {
                SequencerRun copySR = copier.hibernate2dto(SequencerRun.class, sr);
                dto.setSequencerRun(copySR);
            } else {
                logger.info("Could not be found sequencer run");
            }
        }

        Document line = XmlTools.marshalToDocument(jaxbTool, dto, Lane.class);
        getResponse().setEntity(XmlTools.getRepresentation(line));
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    @Put
    public Representation put(Representation entity) {
        authenticate();
        Representation representation = null;
        Lane newLane = null;
        JaxbObject<Lane> jo = new JaxbObject<>();
        try {

            String text = entity.getText();
            newLane = (Lane) XmlTools.unMarshal(jo, Lane.class, text);
        } catch (SAXException | IOException ex) {
            logger.error("LaneIDResource.put SAX/IO exception:",ex);
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, ex);
        }
        try {
            LaneService fs = BeanFactory.getLaneServiceBean();
            Lane lane = testIfNull(fs.findByID(newLane.getLaneId()));
            lane.givesPermission(registration);
            // simple types
            String name = newLane.getName();
            String desc = newLane.getDescription();
            Integer laneIndex = newLane.getLaneIndex();
            String cycleDescriptor = newLane.getCycleDescriptor();
            Boolean skip = newLane.getSkip();
            String tags = newLane.getTags();
            String regions = newLane.getRegions();
            // foreign keys
            Sample sample = newLane.getSample();
            Registration owner = newLane.getOwner();

            Set<LaneAttribute> newAttributes = newLane.getLaneAttributes();

            if (name != null) {
                lane.setName(name);
            }
            if (desc != null) {
                lane.setDescription(desc);
            }
            if (laneIndex != null) {
                lane.setLaneIndex(laneIndex);
            }
            if (cycleDescriptor != null) {
                lane.setCycleDescriptor(cycleDescriptor);
            }
            if (skip != null) {
                lane.setSkip(skip);
            }
            if (tags != null) {
                lane.setTags(tags);
            }
            if (regions != null) {
                lane.setRegions(regions);
            }

            if (sample != null) {
                SampleService ss = BeanFactory.getSampleServiceBean();
                Sample newSample = ss.findByID(sample.getSampleId());
                if (newSample != null && newSample.givesPermission(registration)) {
                    lane.setSample(newSample);
                } else if (newSample == null) {
                    logger.info("Could not be found " + sample);
                }
            }

            if (owner != null) {
                RegistrationService rs = BeanFactory.getRegistrationServiceBean();
                Registration newReg = rs.findByEmailAddress(owner.getEmailAddress());
                if (newReg != null) {
                    lane.setOwner(newReg);
                } else {
                    logger.info("Could not be found " + owner);
                }
            } else if (lane.getOwner() == null) {
                lane.setOwner(registration);
            }
            if (newAttributes != null) {
                // SEQWARE-1577 - AttributeAnnotator cascades deletes when annotating
                LaneIDResource.mergeAttributes(lane.getLaneAttributes(), newAttributes, lane);
            }

            fs.update(registration, lane);

            Hibernate3DtoCopier copier = new Hibernate3DtoCopier();

            Lane detachedLane = copier.hibernate2dto(Lane.class, lane, new Class<?>[] { LibraryStrategy.class, LibrarySource.class,
                    LibrarySelection.class }, new CollectionPropertyName<?>[] {});

            Document line = XmlTools.marshalToDocument(jo, detachedLane, Lane.class);
            representation = XmlTools.getRepresentation(line);
            getResponse().setEntity(representation);
            getResponse().setLocationRef(getRequest().getRootRef() + "/lanes/" + detachedLane.getSwAccession());
            getResponse().setStatus(Status.SUCCESS_CREATED);
        } catch (SecurityException e) {
            getResponse().setStatus(Status.CLIENT_ERROR_FORBIDDEN, e.getMessage());
        } catch (Exception e) {
            logger.error("LaneIDResource.put exception:",e);
            getResponse().setStatus(Status.SERVER_ERROR_INTERNAL, e.getMessage());
        }

        return representation;
    }
}
