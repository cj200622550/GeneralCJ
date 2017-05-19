package com.toolsclass.chenjun.general.generalcj;

import com.toolsclass.chenjun.general.generalcj.Setting.Utility.CpuUtility;
import com.toolsclass.chenjun.general.generalcj.Utility.MovieUtility;
import com.toolsclass.chenjun.general.generalcj.logger.LogUtils;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.runner.JUnitCore.main;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
        main();
    }

    public static void getProcess() {

        BufferedReader br = null;
        try {
            Process p = Runtime.getRuntime().exec("ping www.baidu.com");
            br = new BufferedReader(new InputStreamReader(p.getInputStream(), "GBK"));
            String line = null;
            StringBuffer sb = new StringBuffer();
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            System.out.println(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
}