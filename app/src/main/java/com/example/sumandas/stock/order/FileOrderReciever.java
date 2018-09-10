package com.example.sumandas.stock.order;

import android.os.Environment;

import com.example.sumandas.stock.interfaces.IOrderManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileOrderReciever extends OrderReceiver {

    public static final String FILE_NAME = "stock.txt";

    public FileOrderReciever(IOrderManager orderManager) {
        super(orderManager);
    }

    @Override
    public void getOrders() {
        BufferedReader br;
        try {
            File sdcard = Environment.getExternalStorageDirectory();
            File file = new File(sdcard, FILE_NAME);
            String[] splitLine;
            br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                splitLine = line.split(" ");
                Order order = new Order(splitLine[0],splitLine[1],splitLine[2],splitLine[3],Integer.parseInt(splitLine[4])
                        ,Float.parseFloat(splitLine[5]));
                onOrderReceived(order);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();

        }

    }
}
