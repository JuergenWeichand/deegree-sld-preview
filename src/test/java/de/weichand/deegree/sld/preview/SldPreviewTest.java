package de.weichand.deegree.sld.preview;


import java.io.File;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author weich_ju
 */
public class SldPreviewTest
{
    public SldPreviewTest()
    {

    }

    @Test
    public void testSldPreview()
    {
        String fileName = "src/test/resources/CP.CadastralParcel.Default.xml";
        String[] args = { fileName };
        Exec.main(args);
        File previewFile = new File(fileName + ".png");
        Assert.assertTrue(previewFile.exists());
        previewFile.delete();
    }

}
