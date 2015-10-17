package com.jollaman999.jollakernel.a2dpchecker;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class SOVCSOTCSwitcher {

    public static void check_permission(){
        Process process;
        Runtime runtime = Runtime.getRuntime();
        int permission_ok_counter = 0;

        try{
            String cmd;
            String line;

            cmd = "ls -al /sys/android_touch/scroff_volctr";
            process = runtime.exec(cmd);
            BufferedReader br1 = new BufferedReader(new InputStreamReader(process.getInputStream()));

            while((line = br1.readLine()) != null) {
                if(line.contains("-rw-rw-rw-")) {
                    permission_ok_counter++;
                    break;
                }
            }

            cmd = "ls -al /sys/android_touch/scroff_trackctr";
            process = runtime.exec(cmd);
            BufferedReader br2 = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while((line = br2.readLine()) != null) {
                if(line.contains("-rw-rw-rw-")) {
                    permission_ok_counter++;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (permission_ok_counter != 2) {
            get_permission();
        }
    }

    private static void get_permission() {
        Runtime runtime = Runtime.getRuntime();
        try{
            String cmd;
            cmd = "su -c chmod 666 /sys/android_touch/scroff_volctr";
            runtime.exec(cmd);
            cmd = "su -c chmod 666 /sys/android_touch/scroff_trackctr";
            runtime.exec(cmd);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sovc_switcher(int on){
        Runtime runtime = Runtime.getRuntime();
        try{
            if(on == 1) {
                String cmd[] = {"/system/bin/sh", "-c",
                        "echo 1 > /sys/android_touch/scroff_volctr"};
                runtime.exec(cmd);
            } else {
                String cmd[] = {"/system/bin/sh", "-c",
                        "echo 0 > /sys/android_touch/scroff_volctr"};
                runtime.exec(cmd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sotc_switcher(int on){
        Runtime runtime = Runtime.getRuntime();
        try{
            if(on == 1) {
                String cmd[] = {"/system/bin/sh", "-c",
                        "echo 1 > /sys/android_touch/scroff_trackctr"};
                runtime.exec(cmd);
            } else {
                String cmd[] = {"/system/bin/sh", "-c",
                        "echo 0 > /sys/android_touch/scroff_trackctr"};
                runtime.exec(cmd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
