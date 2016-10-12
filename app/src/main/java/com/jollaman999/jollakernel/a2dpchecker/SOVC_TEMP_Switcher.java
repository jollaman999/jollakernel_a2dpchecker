package com.jollaman999.jollakernel.a2dpchecker;

class SOVC_TEMP_Switcher {

    static void sovc_temp_switcher(int on){
        Runtime runtime = Runtime.getRuntime();
        try{
            if(on == 1) {
                String cmd1[] = {"/system/bin/sh",
                            "-c",
                            "echo 1 | su -c tee /sys/android_touch/scroff_volctr_temp"};
                runtime.exec(cmd1);

                String cmd2[] = {"/system/bin/sh",
                            "-c",
                            "echo 1 | su -c tee /sys/android_touch/doubletap2wake_tmp"};
                runtime.exec(cmd2);
            } else {
                String cmd1[] = {"/system/bin/sh",
                            "-c",
                            "echo 0 | su -c tee /sys/android_touch/scroff_volctr_temp"};
                runtime.exec(cmd1);

                String cmd2[] = {"/system/bin/sh",
                            "-c",
                            "echo 0 | su -c tee /sys/android_touch/doubletap2wake_tmp"};
                runtime.exec(cmd2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
