package com.trivago.rta.files;

import com.trivago.rta.exceptions.CluecumberPluginException;
import com.trivago.rta.exceptions.filesystem.PathCreationException;
import com.trivago.rta.filesystem.FileSystemManager;
import com.trivago.rta.properties.PropertyManager;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FileSystemManagerTest {
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    private PropertyManager propertyManager;
    private FileSystemManager fileSystemManager;

    @Before
    public void setup() {
        propertyManager = mock(PropertyManager.class);
        fileSystemManager = new FileSystemManager(propertyManager);
    }

    @Test(expected = CluecumberPluginException.class)
    public void invalidSourceFeaturesTest() throws Exception {
        when(propertyManager.getSourceJsonReportDirectory()).thenReturn("nonexistentpath");
        fileSystemManager.getJsonFilePaths();
    }

    @Test
    public void validSourceFeaturesTest() throws Exception {
        String jsonPath = testFolder.getRoot().getPath();
        when(propertyManager.getSourceJsonReportDirectory()).thenReturn(jsonPath);
        fileSystemManager.getJsonFilePaths();
    }

    @Test(expected = PathCreationException.class)
    public void createInvalidDirectory() throws Exception {
        fileSystemManager.createDirectory("");
    }

    @Test(expected = CluecumberPluginException.class)
    public void exportResourceTest() throws Exception {
        Class baseClass = this.getClass();
        fileSystemManager.exportResource(baseClass, "resource", "");
    }
}
