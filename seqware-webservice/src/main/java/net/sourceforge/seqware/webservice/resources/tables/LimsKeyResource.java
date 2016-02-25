/*
 * Copyright (C) 2016 SeqWare
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
import net.sf.beanlib.hibernate3.Hibernate3DtoCopier;
import net.sourceforge.seqware.common.factory.BeanFactory;
import net.sourceforge.seqware.common.model.LimsKey;
import net.sourceforge.seqware.common.util.xmltools.JaxbObject;
import net.sourceforge.seqware.common.util.xmltools.XmlTools;
import net.sourceforge.seqware.webservice.resources.BasicResource;
import static net.sourceforge.seqware.webservice.resources.BasicResource.parseClientInt;
import static net.sourceforge.seqware.webservice.resources.BasicResource.testIfNull;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import net.sourceforge.seqware.common.business.LimsKeyService;
import net.sourceforge.seqware.common.model.lists.LimsKeyList;

/**
 *
 * @author mlaszloffy
 */
//@Path("/limskey")
public class LimsKeyResource extends BasicResource {

    @Get
    public void getXml() throws IOException {
        authenticate();
        LimsKeyService ss = BeanFactory.getLimsKeyServiceBean();
//        Hibernate3DtoCopier copier = new Hibernate3DtoCopier();

        Document line;
        if (getRequestAttributes().get("limsKeyId") != null) {
            //get by accession
            Integer swid = parseClientInt((String) getRequestAttributes().get("limsKeyId"));
            LimsKey limsKey = (LimsKey) testIfNull(ss.findBySWAccession(swid));
            JaxbObject<LimsKey> jaxbTool = new JaxbObject<>();
            line = XmlTools.marshalToDocument(jaxbTool, limsKey);
        } else if (queryValues.get("id") != null) {
            //get by id
            Integer id = parseClientInt(queryValues.get("id"));
            LimsKey limsKey = (LimsKey) testIfNull(ss.findByID(id));
            JaxbObject<LimsKey> jaxbTool = new JaxbObject<>();
            line = XmlTools.marshalToDocument(jaxbTool, limsKey);
        } else {
            //get all
            JaxbObject<LimsKeyList> jaxbTool = new JaxbObject<>();
            LimsKeyList limsKeyList = new LimsKeyList();
            for (LimsKey key : testIfNull(ss.list())) {
                limsKeyList.add(key);
            }
            line = XmlTools.marshalToDocument(jaxbTool, limsKeyList);
        }

        getResponse().setEntity(XmlTools.getRepresentation(line));
    }

    @Post("xml")
    public void postJaxb(Representation entity) throws ResourceException {
        authenticate();
        try {
            JaxbObject<LimsKey> jo = new JaxbObject<>();
            String text = entity.getText();
            LimsKey o = null;
            try {
                o = (LimsKey) XmlTools.unMarshal(jo, new LimsKey(), text);
            } catch (SAXException ex) {
                throw new ResourceException(Status.CLIENT_ERROR_UNPROCESSABLE_ENTITY, ex);
            }

            // persist object
            LimsKeyService service = BeanFactory.getLimsKeyServiceBean();
            Integer swAccession = service.insert(o);

            //build dto to return
            LimsKey obj = (LimsKey) testIfNull(service.findBySWAccession(swAccession));
            Hibernate3DtoCopier copier = new Hibernate3DtoCopier();
            LimsKey detachedLimsKey = copier.hibernate2dto(LimsKey.class, obj);
            detachedLimsKey.setLastModified(obj.getLastModified());  //hibernate dto copier does not set DateTime

            Document line = XmlTools.marshalToDocument(jo, detachedLimsKey);
            getResponse().setEntity(XmlTools.getRepresentation(line));
            getResponse().setLocationRef(getRequest().getRootRef() + "/limskey/" + detachedLimsKey.getSwAccession());
            getResponse().setStatus(Status.SUCCESS_CREATED);
        } catch (SecurityException e) {
            getResponse().setStatus(Status.CLIENT_ERROR_FORBIDDEN, e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            getResponse().setStatus(Status.SERVER_ERROR_INTERNAL, e.getMessage());
        }

    }

}
