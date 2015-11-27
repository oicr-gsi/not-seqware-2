/*
 * Copyright (C) 2015 SeqWare
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
package net.sourceforge.seqware.common.business.impl;

import java.util.List;
import net.sourceforge.seqware.common.dto.AnalysisProvenance;
import net.sourceforge.seqware.common.business.AnalysisProvenanceService;
import net.sourceforge.seqware.common.dao.AnalysisProvenanceDAO;

/**
 *
 * @author mlaszloffy
 */
public class AnalysisProvenanceServiceImpl implements AnalysisProvenanceService {

    private AnalysisProvenanceDAO analysisProvenanceDAO;

    @Override
    public void setAnalysisProvenanceDAO(AnalysisProvenanceDAO analysisProvenanceDAO) {
        this.analysisProvenanceDAO = analysisProvenanceDAO;
    }

    @Override
    public List<AnalysisProvenance> list() {
        return analysisProvenanceDAO.list();
    }

}
