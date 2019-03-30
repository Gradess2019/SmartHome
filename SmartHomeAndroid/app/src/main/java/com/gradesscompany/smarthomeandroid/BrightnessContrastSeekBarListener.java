package com.gradesscompany.smarthomeandroid;

import android.os.AsyncTask;
import android.widget.SeekBar;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import smarthome.Commands.ScreenBrightnessAndContrast;

public class BrightnessContrastSeekBarListener implements SeekBar.OnSeekBarChangeListener {

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        new AsyncRequest().execute(String.valueOf(progress));
    }

    class AsyncRequest extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... arg) {
            Socket socket = null;
            try {
                socket = new Socket("192.168.1.69", 2546);
                OutputStream socketOutputStream = socket.getOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socketOutputStream);
                int newOption = Integer.parseInt(arg[0]) * 10;
                objectOutputStream.writeObject(new ScreenBrightnessAndContrast(newOption, newOption));
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
