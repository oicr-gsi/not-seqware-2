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
import java.util.ArrayList;
import java.util.List;

import net.sf.beanlib.CollectionPropertyName;
import net.sf.beanlib.hibernate3.Hibernate3DtoCopier;
import net.sourceforge.seqware.common.business.WorkflowService;
import net.sourceforge.seqware.common.factory.BeanFactory;
import net.sourceforge.seqware.common.model.Registration;
import net.sourceforge.seqware.common.model.Workflow;
import net.sourceforge.seqware.common.model.lists.WorkflowList;

import net.sourceforge.seqware.common.util.xmltools.JaxbObject;
import net.sourceforge.seqware.common.util.xmltools.XmlTools;

import org.apache.commons.lang3.ArrayUtils;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * <p>
 * WorkflowResource class.
 * </p>
 * 
 * @author mtaschuk
 * @version $Id: $Id
 */
public class WorkflowResource extends DatabaseResource {
    private final Logger logger = LoggerFactory.getLogger(WorkflowResource.class);

	/**
	 * <p>
	 * Constructor for WorkflowResource.
	 * </p>
	 */
	public WorkflowResource() {
		super("Workflows");
	}

	/**
	 * <p>
	 * getXml.
	 * </p>
	 */
	@Get
	public void getXml() {
		authenticate();
		WorkflowService ss = BeanFactory.getWorkflowServiceBean();
		Document line = null;

		if (queryValues.get("id") != null) {
			Workflow p = (Workflow) testIfNull(ss.findByID(parseClientInt(queryValues.get("id"))));
			line = detachWorkflow(p);

		} else if (queryValues.get("name") != null) {
			List<Workflow> workflows = (List<Workflow>) testIfNull(ss.findByName(queryValues.get("name")));

			if (queryValues.get("version") != null) {
				String version = queryValues.get("version").trim();
				for (Workflow workflow : workflows) {
					if (version.equals(workflow.getVersion())) {
						line = detachWorkflow(workflow);
					}
				}
			} else {
				line = detachWorkflowList(workflows);
			}

		} else {
			List<Workflow> workflows = ss.list();
			line = detachWorkflowList(workflows);
		}

		getResponse().setEntity(XmlTools.getRepresentation(line));
	}

	private Document detachWorkflow(Workflow workflow) {
		Hibernate3DtoCopier copier = new Hibernate3DtoCopier();
		JaxbObject<Workflow> jaxbTool = new JaxbObject<>();
		Workflow dto = copier.hibernate2dto(Workflow.class, workflow, ArrayUtils.EMPTY_CLASS_ARRAY,
				CollectionPropertyName.createCollectionPropertyNames(Workflow.class,
						new String[] { "parameterDefaults" }));
		Document line = XmlTools.marshalToDocument(jaxbTool, dto, Workflow.class);
		return line;
	}

	private Document detachWorkflowList(List<Workflow> workflows) {
		Hibernate3DtoCopier copier = new Hibernate3DtoCopier();
		JaxbObject<WorkflowList> jaxbTool = new JaxbObject<>();
		WorkflowList eList = new WorkflowList();
		eList.setList(new ArrayList<>());

		for (Workflow workflow : workflows) {
			Workflow dto = copier.hibernate2dto(Workflow.class, workflow, ArrayUtils.EMPTY_CLASS_ARRAY,
					CollectionPropertyName.createCollectionPropertyNames(Workflow.class,
							new String[] { "parameterDefaults" }));
			eList.add(dto);
		}
		Document line = XmlTools.marshalToDocument(jaxbTool, eList, WorkflowList.class);
		return line;
	}

	/**
	 * <p>
	 * postJaxb.
	 * </p>
	 * 
	 * @param entity
	 *            a {@link org.restlet.representation.Representation} object.
	 */
	@Post("xml")
	public void postJaxb(Representation entity) {
		authenticate();
		try {
			JaxbObject<Workflow> jo = new JaxbObject<>();
			String text = entity.getText();
			Workflow p;
			try {
				p = (Workflow) XmlTools.unMarshal(jo, Workflow.class, text);
			} catch (SAXException ex) {
				throw new ResourceException(Status.CLIENT_ERROR_UNPROCESSABLE_ENTITY, ex);
			}
			if (p.getOwner() != null) {
				Registration r = BeanFactory.getRegistrationServiceBean()
						.findByEmailAddress(p.getOwner().getEmailAddress());
				if (r != null) {
					p.setOwner(r);
				} else {
                    logger.info("Could not be found: owner" + p.getOwner());
				}
			} else {
				p.setOwner(registration);
			}
			// persist p
			WorkflowService ws = BeanFactory.getWorkflowServiceBean();
			Integer id = ws.insert(registration, p);
			Workflow w = (Workflow) testIfNull(ws.findBySWAccession(id));
			Hibernate3DtoCopier copier = new Hibernate3DtoCopier();
			Workflow detachedW = copier.hibernate2dto(Workflow.class, w, ArrayUtils.EMPTY_CLASS_ARRAY,
					CollectionPropertyName.createCollectionPropertyNames(Workflow.class,
							new String[] { "parameterDefaults" }));

			Document line = XmlTools.marshalToDocument(jo, detachedW, Workflow.class);
			getResponse().setEntity(XmlTools.getRepresentation(line));
			getResponse().setLocationRef(getRequest().getRootRef() + "/workflows/" + detachedW.getSwAccession());
			getResponse().setStatus(Status.SUCCESS_CREATED);

		} catch (SecurityException e) {
			getResponse().setStatus(Status.CLIENT_ERROR_FORBIDDEN, e);
		} catch (IOException e) {
            logger.error("WorkflowResource.postJaxb IO exception:",e);
			getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST, e);
		} catch (Exception e) {
            logger.error("WorkflowResource.postJaxb exception:",e);
			getResponse().setStatus(Status.SERVER_ERROR_INTERNAL, e);
		}

	}
}
