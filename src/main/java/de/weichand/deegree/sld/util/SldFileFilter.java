package de.weichand.deegree.sld.util;

import java.io.File;
import java.io.FileFilter;

/**
 *
 * @author weich_ju
 */
public class SldFileFilter implements FileFilter
{

    @Override
    public boolean accept(File pathname)
    {
        if (pathname == null)
        {
            return false;
        }
        if (pathname.toString().toLowerCase().endsWith(".sld"))
        {
            return true;
        }
        if (pathname.toString().toLowerCase().endsWith(".xml"))
        {
            return true;
        }
        return false;
    }

}
