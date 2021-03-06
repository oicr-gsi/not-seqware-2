/*
 * Copyright (C) 2013 SeqWare
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
package net.sourceforge.seqware.pipeline.plugins;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;
import net.sourceforge.seqware.common.module.ReturnValue;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * These tests support command-line tools found in the SeqWare User Tutorial, in this case, ProvisionFiles
 *
 * @author dyuen
 */
public class ProvisionFilesET {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProvisionFilesET.class);
    private Path tmp;

    @BeforeClass
    public static void resetDatabase() {
        ExtendedTestDatabaseCreator.resetDatabaseWithUsers();
    }

    @Before
    public void initialize() {
        try {
            tmp = Files.createTempDirectory(null);
            tmp.toFile().deleteOnExit();
        } catch (IOException ex) {
            fail("Unable to create temp directory for ProvisionFilesET tests");
        }
    }

    /**
     * This test provisions a file in with random input and checks on the accession writing
     *
     * @throws IOException
     */
    @Test
    public void testProvisionFileIn_RandomInput() throws IOException {
        provisionFileWithRandomInput("10");
    }

    /**
     * Provision a file into SeqWare and then provision it back out
     *
     * @throws IOException
     */
    @Test
    public void provisionInAndOut() throws IOException {
        File provisionedFile = this.provisionFileWithRandomInput("10");
        File tempDir = FileUtils.getTempDirectory();

        String listCommand = "-p net.sourceforge.seqware.pipeline.plugins.ModuleRunner -- --module net.sourceforge.seqware.pipeline.modules.utilities.ProvisionFiles "
                + " --no-metadata " + " -- -i" + provisionedFile.getAbsolutePath() + " -o " + tempDir.getAbsolutePath();
        String listOutput = ITUtility.runSeqWareJar(listCommand, ReturnValue.SUCCESS, null);
        LOGGER.info(listOutput);
        File retrievedFile = new File(tempDir.getAbsoluteFile(), provisionedFile.getName());

        Assert.assertTrue("file locations do not differ: " + retrievedFile.getAbsolutePath() + "vs" + provisionedFile.getAbsolutePath(),
                !retrievedFile.getAbsolutePath().equals(provisionedFile.getAbsolutePath()));
        Assert.assertTrue("file contents not the same",
                FileUtils.readFileToString(retrievedFile, StandardCharsets.UTF_8).equals(FileUtils.readFileToString(provisionedFile, StandardCharsets.UTF_8)));
    }

    public File provisionFileWithRandomInput(String sampleAccession) throws IOException {
        // create a random new file and check that the file we want to provision exists
        File inputFile = File.createTempFile("input", "out");
        final String content = "This is a funky funky test file";
        FileUtils.write(inputFile, content, StandardCharsets.UTF_8);

        File metadataFile = File.createTempFile("metadata", "out");

        Random generator = new Random();
        String random = String.valueOf(generator.nextInt());
        String listCommand = "-p net.sourceforge.seqware.pipeline.plugins.ModuleRunner -- --module net.sourceforge.seqware.pipeline.modules.utilities.ProvisionFiles "
                + "--metadata-output-file-prefix "
                + tmp
                + " --metadata-parent-accession "
                + sampleAccession
                + " --metadata-processing-accession-file  "
                + metadataFile.getAbsolutePath()
                + " -- -im text::text/plain::"
                + inputFile.getAbsolutePath() + " -o " + tmp + " --force-copy";
        String listOutput = ITUtility.runSeqWareJar(listCommand, ReturnValue.SUCCESS, null);
        LOGGER.info(listOutput);
        // check that file was ended up being provisioned correctly
        File provisioned = new File(tmp + "/" + inputFile.getName());
        Assert.assertTrue("file did not end up in final location", provisioned.exists());
        String contents = FileUtils.readFileToString(provisioned, StandardCharsets.UTF_8).trim();
        Assert.assertTrue("file contents not as expected", contents.equals(content));

        // check on metadata file
        String metadataContent = FileUtils.readFileToString(metadataFile, StandardCharsets.UTF_8).trim();
        try {
            int processingInt = Integer.parseInt(metadataContent);
            LOGGER.info("provisioned file as sw_accession: " + processingInt);
        } catch (NumberFormatException e) {
            Assert.assertTrue("did not output metadata sw_accession properly", false);
        }
        return provisioned;
    }

}
