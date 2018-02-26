package com.zsm.commonexample.REngine;

import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;


/**
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2018/2/24 13:40.
 * @Modified By:
 */
public class SingleRConnection
{
    private static final Object LOCK_OBJECT = new Object();

    private static volatile RConnection RCONNECTION;

    public static RConnection getSingleRConnection()
        throws RserveException
    {
        return getSingleRConnection("127.0.0.1", 6311);
    }

    public static RConnection getSingleRConnection(String host)
        throws RserveException
    {
        return getSingleRConnection(host, 6311);
    }

    public static RConnection getSingleRConnection(String host, int port)
        throws RserveException
    {
        if (RCONNECTION == null)
        {
            synchronized (LOCK_OBJECT)
            {
                if (RCONNECTION == null)
                {
                    RCONNECTION = new RConnection(host, port);
                }
            }
        }
        return RCONNECTION;
    }
}
