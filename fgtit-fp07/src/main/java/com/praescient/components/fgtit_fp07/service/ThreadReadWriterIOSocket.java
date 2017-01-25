package com.praescient.components.fgtit_fp07.service;

import android.content.Context;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ThreadReadWriterIOSocket implements Runnable {

    private Socket client;
    private Context context;
    //private PigProtocol pigProtocol;

    public ThreadReadWriterIOSocket(Context context, Socket client)
    {
        this.client = client;
        this.context = context;
        //pigProtocol = new PigProtocol();
    }

    @Override
    public void run(){

        BufferedOutputStream out;
        BufferedInputStream in;
        try {
            //Header header = null;
            out = new BufferedOutputStream(client.getOutputStream());
            in = new BufferedInputStream(client.getInputStream());
            ConnectService.ioThreadFlag = true;
            while (ConnectService.ioThreadFlag){
                try {

                    if(!client.isConnected()){
                        break;
                    }

					/*
					header = pigProtocol.readHeaderFromSocket(in);
					switch (header.CmdId) {
					case 0x0001:
						//
						break;
					case 0x0002:
						//
						break;
					default:
						break;
					}
					*/
                }
                catch (Exception e)
                {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            }
            out.close();
            in.close();
        }
        catch (Exception e)
        {
            // TODO: handle exception
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (client != null)
                {
                    client.close();
                }
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
