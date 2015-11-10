package com.jollaman999.jollakernel.a2dpchecker;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class SOVC_TEMP_Switcher {

    public static void check_permission(){
        Process process;
        Runtime runtime = Runtime.getRuntime();
        boolean is_permission_ok = false;

        try{
            String cmd;
            String line;

            cmd = "ls -al /sys/android_touch/scroff_volctr_temp";
            process = runtime.exec(cmd);
            BufferedReader br1 = new BufferedReader(new InputStreamReader(process.getInputStream()));

            while((line = br1.readLine()) != null) {
                if(line.contains("-rw-rw-rw-")) {
                    is_permission_ok = true;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!is_permission_ok) {
            get_permission();
        }
    }

    private static void get_permission() {
        Runtime runtime = Runtime.getRuntime();
        try{
            String cmd;
            cmd = "su -c chmod 666 /sys/android_touch/scroff_volctr_temp";
            runtime.exec(cmd);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sovc_temp_switcher(int on){
        Runtime runtime = Runtime.getRuntime();
        try{
            if(on == 1) {
                String cmd[] = {"/system/bin/sh", "-c",
                        "echo 1 > /sys/android_touch/scroff_volctr_temp"};
                runtime.exec(cmd);
            } else {
                String cmd[] = {"/system/bin/sh", "-c",
                        "echo 0 > /sys/android_touch/scroff_volctr_temp"};
                runtime.exec(cmd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
