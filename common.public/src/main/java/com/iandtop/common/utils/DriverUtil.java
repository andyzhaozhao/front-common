package com.iandtop.common.utils;

import java.io.*;

public class DriverUtil
{
    public static DriverFileContent readFileContent(InputStream stream,String fileName) throws Exception
    {
        BufferedInputStream bis = null;
        try
        {
            bis = new BufferedInputStream(stream);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = bis.read(buffer)) != -1)
            {
                bos.write(buffer, 0, len);
            }

            byte[] content = bos.toByteArray();

            return new DriverFileContent(fileName, content);

        }
        catch(Exception e)
        {
            throw e;
        }
        finally
        {
            if (bis != null)
            {
                try
                {
                    bis.close();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    public static DriverFileContent readFileContent(File file) throws Exception
    {
        BufferedInputStream bis = null;
        try
        {
            bis = new BufferedInputStream(new FileInputStream(file));

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = bis.read(buffer)) != -1)
            {
                bos.write(buffer, 0, len);
            }

            byte[] content = bos.toByteArray();

            return new DriverFileContent(file.getName(), content);

        }
        catch(Exception e)
        {
            throw e;
        }
        finally
        {
            if (bis != null)
            {
                try
                {
                    bis.close();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    public static DriverFileContent[] readFilesContent(File[] files) throws Exception
    {
        if (files == null || files.length == 0)
        {
            return null;
        }

        DriverFileContent[] fcs = new DriverFileContent[files.length];
        for (int i = 0, len = fcs.length; i < len; i++)
        {
            fcs[i] = readFileContent(files[i]);
        }

        return fcs;
    }
}
