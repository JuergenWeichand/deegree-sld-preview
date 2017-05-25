package de.weichand.deegree.sld.preview;

import de.weichand.deegree.sld.util.SldFileFilter;
import java.awt.Color;
import java.awt.Graphics2D;
import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.KEY_TEXT_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;
import static java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_ON;
import java.awt.image.BufferedImage;
import static java.awt.image.BufferedImage.TYPE_INT_RGB;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import org.deegree.commons.utils.Pair;
import org.deegree.protocol.wms.ops.SLDParser;
import org.deegree.protocol.wms.sld.StylesContainer;
import org.deegree.rendering.r2d.legends.LegendOptions;
import org.deegree.rendering.r2d.legends.Legends;
import org.deegree.style.se.unevaluated.Style;

/**
 * deegree SLD preview utility
 *
 * @author Juergen Weichand
 */
public class Exec {

    public static void main(String[] args)
    {
        if (args.length != 1)
        {
            System.out.println("Usage: java -jar deegree-sld-preview.jar sld_file|sld_dir");
            System.out.println("");
            return;
        }

        String fileName = args[0];
        File file = new File(fileName);

        if (file.isDirectory())
        {
            for (File subFiles : file.listFiles(new SldFileFilter()))
            {
                generatePreview(subFiles);
            }
        }
        else
        {
            generatePreview(file);
        }
    }

    private static void generatePreview(File sldFile)
    {
        System.out.println("...... generating preview for " + sldFile.getName());
        try
        {
            InputStream is = new FileInputStream(sldFile);
            XMLStreamReader xmlReader = XMLInputFactory.newInstance().createXMLStreamReader(is);
            StylesContainer sld = SLDParser.parse(xmlReader);

            Style style = sld.getStyles().get(0).getStyleRef().getStyle();

            LegendOptions opt = new LegendOptions();
            Legends renderer = new Legends(opt);

            Pair<Integer, Integer> size = renderer.getLegendSize(style);
            int width = size.first * 5;
            int height = size.second * 5;

            BufferedImage img = new BufferedImage(width, height, TYPE_INT_RGB);
            Graphics2D g = img.createGraphics();

            g.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
            g.setRenderingHint(KEY_TEXT_ANTIALIASING, VALUE_TEXT_ANTIALIAS_ON);
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, width, height);

            renderer.paintLegend(style, width, height, g);

            g.dispose();

            String outFileName = sldFile.getAbsolutePath();
            outFileName = outFileName + ".png";
            ImageIO.write(img, "png", new File(outFileName));
        }
        catch (Exception ex)
        {
            System.out.println("FAILED for " + sldFile.getName());
            System.out.println(ex.toString());
            System.out.println("");
        }
    }
}
