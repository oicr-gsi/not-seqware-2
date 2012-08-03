/*
 * Copyright (C) 2012 SeqWare
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
package com.github.seqware.queryengine.plugins;

import com.github.seqware.queryengine.model.FeatureSet;

/**
 * This will describe the interface custom plugins must conform to. Details are
 * backend specific of course. I think we should try to make these plugins
 * compatible with GATK if possible. It would be really cool to be able to drop
 * in, say, LociWalkers from GATK without modification. I'm not sure how
 * practical this this, thought. We can, at least, use their same basic
 * interface, see
 * http://www.broadinstitute.org/gsa/wiki/index.php/Your_first_walker:
 *
 * Here's an extremely basic interface, we will really need to work on this and
 * figure out what the object inputs are for these methods and what they should
 * return (just using ReturnValue as placeholder).
 *
 * @author boconnor
 */
public interface AnalysisPluginInterface {

    /**
     * Initialize this plug-in, this is called once when the plug-in is starting
     * a job
     * @param set input feature set that we will be operating on
     * @param parameters parameters that the plug-in will require
     * @return 
     */
    public ReturnValue init(FeatureSet set, Object ... parameters);

    public ReturnValue test();

    public ReturnValue verifyParameters();

    public ReturnValue verifyInput();

    public ReturnValue filterInit();

    public ReturnValue filter();

    public ReturnValue verifyOutput();

    public ReturnValue cleanup();
    
    /**
     * Non-blocking call to determine whether the result is ready
     * @return 
     */
    public boolean isComplete();
    
    /**
     * Blocking call to get the final result of the plug-in
     * @return feature set containing results of the analysis
     */
    public FeatureSet getFinalResult();
    
    public class ReturnValue{
        
    }
}