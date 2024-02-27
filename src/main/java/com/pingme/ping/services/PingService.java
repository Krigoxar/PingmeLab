package com.pingme.ping.services;

import java.io.IOException;
import java.net.InetAddress;

import com.pingme.ping.dtos.ExtServerRespond;
import com.pingme.ping.dtos.UrlDTO;

public class PingService {
    private PingService() {}

    public static ExtServerRespond pingExternalServer(UrlDTO url)
    {
        try
		{
            return new ExtServerRespond(InetAddress.getByName(url.url()).isReachable(1000));
		}
		catch(IOException e)
		{
			return new ExtServerRespond(false);
		}
    }
}
