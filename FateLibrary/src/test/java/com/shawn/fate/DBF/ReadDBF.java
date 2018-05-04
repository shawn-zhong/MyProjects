package com.shawn.fate.DBF;

import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ReadDBF {

    public static void main(String[] args) {
        String path = "/Users/Shawn/Documents/workspace/makeMoney/solar_terms_and_nongli_source.DBF";
        readDBF(path);
    }

    public static void readDBF(String path) {


        try (InputStream fis = new FileInputStream(path)){

            DBFReader reader = new DBFReader(fis);
            reader.setCharactersetName("gb2312");

            int fieldsCount = reader.getFieldCount();

            for (int i=0; i<fieldsCount; i++) {
                DBFField field = reader.getField(i);
               // System.out.print(field.getName() + "\t");
            }

            //System.out.println();


            Object[] rowValues;

            List<Integer> solars = Arrays.asList(17, 19, 21, 23, 25, 27, 29, 31, 33, 35, 37, 15 );

            while ((rowValues = reader.nextRecord()) != null) {

                boolean hasNewLine = false;
                String line = "";

                for (int i=0; i<solars.size(); i++) {

                    String time = rowValues[solars.get(i)].toString();
                    if (time.trim().isEmpty())
                        break;

                    int year = Integer.parseInt( time.substring(0, 4) );
                    if (year >= 1859 && year < 2131) {

                        //System.out.print(time + "\t");
                        // 2120 (0~3)/02 (5~6)/04 (8,9)T23 (11~12):22 (14~15)
                        int month = Integer.parseInt(time.substring(5, 7));
                        int day = Integer.parseInt(time.substring(8, 10));
                        int hour = Integer.parseInt(time.substring(11, 13));
                        int min = Integer.parseInt(time.substring(14).trim());

                        // 像这样打印：{1600020417L, 1600030513, 1600040421, 1600050517, 1600060600, 1600070711, 1600080719, 1600090721, 1600100809, 1600110709, 1600120701, 1601010511}, // 1600

                        if (!hasNewLine) {
                            line += "{";
                        }

                        //System.out.print(String.format("%04d%02d%02d%02d%02dL,", year, month, day, hour, min));

                        line += String.format("%04d%02d%02d%02d%02dL, ", year, month, day, hour, min);

                        hasNewLine = true;
                    }
                }

                if (hasNewLine) {
                    line = line.substring(0, line.length()-2);
                    line += "},";
                    System.out.println(line);
                }
            }

            long a = 184502051103L;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
