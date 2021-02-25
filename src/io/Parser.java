package io;

import dataClasses.in.Car;
import dataClasses.in.InStructure;
import dataClasses.in.Street;
import dataClasses.in.Intersection;
import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parser {


  public static InStructure getA(){
    return parser(readFile("data/in/a.txt"));
  }
  public static InStructure getB(){return null;}
  public static InStructure getC(){return null;}
  public static InStructure getD(){return null;}
  public static InStructure getE(){return null;}

  public static void main(String[] args) {
//    List<String> res = new ArrayList<>(List.of("abc", "def"));
//    var homeDir = FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath();
//    writeFile(homeDir + "/Desktop/foo.txt", res);
    System.out.println(getA());
  }

  public static List<String> readFile(String filePath) {
    List<String> list = new ArrayList<>();
    try {
      File file = new File(filePath);
      if (file.isFile() && file.exists()) {
        InputStreamReader read = new InputStreamReader(new FileInputStream(file));
        BufferedReader bufferedReader = new BufferedReader(read);
        String lineTxt;
        while ((lineTxt = bufferedReader.readLine()) != null) {
          list.add(lineTxt);
        }
        bufferedReader.close();
        read.close();
      }
    } catch (Exception e) {
      System.out.println("读取文件内容出错");
      e.printStackTrace();
    }
    return list;
  }

  public static InStructure parser(List<String> raw) {
    var fstLine = raw.get(0).split(" ");
    var timeLength = Integer.valueOf(fstLine[0]);
    var interNo = Integer.valueOf(fstLine[1]);
    var stNo = Integer.valueOf(fstLine[2]);
    var carNo =  Integer.valueOf(fstLine[3]);
    var bonus = Integer.valueOf(fstLine[4]);

    InStructure ins = new InStructure();
    Intersection[] intersections = new Intersection[interNo];
    ins.simulationLength = timeLength;
    ins.bonusPointPerCar = bonus;
    ins.intersections = Arrays.asList(intersections);

    for (int i = 1; i <= stNo; i++) {
      var line = raw.get(i).split(" ");
      var inter1 = intersections[Integer.parseInt(line[0])];
      var inter2 = intersections[Integer.parseInt(line[1])];
      var street = line[2];
      var weight = Integer.parseInt(line[3]);
      // inter1 = new Intersection(new ArrayList<>(), new ArrayList<>()); 如果每次都初始化的话
      // inter2 = new Intersection(new ArrayList<>(), new ArrayList<>()); 记录的路就没了……？
      //inter1.streets.add(street);
      //inter1.durations.add(0);
      inter2.streets.add(street);
      inter2.durations.add(0);
//      matrix[Integer.parseInt(line[0])][Integer.parseInt(line[1])] = weight;
      // ins.addIntersection(inter1);
      // ins.addIntersection(inter2);
      ins.addStreet(new Street(street, Integer.parseInt(line[0]), Integer.parseInt(line[1]), weight));
    }

    for (int i = stNo + 1; i < raw.size(); i++) {
      var line = raw.get(i).split(" ");
      var stNoForCar = Integer.parseInt(line[0]);
      var startStreetName = line[1];
      ins.streetMap.get(startStreetName).addCar(new Car(Arrays.copyOfRange(line, 1, line.length), stNoForCar, ins));
    }

    return ins;
  }

  public static void writeFile(String filePath, List<String> data) {
    File file = new File(filePath);
    try {
      if (file.createNewFile() && file.exists()) {
        FileWriter fw = new FileWriter(file);
        data.forEach(
            line -> {
              try {
                fw.write(line);
                fw.write("\n");
              } catch (IOException e) {
                System.out.println("创建文件出错");
                e.printStackTrace();
              }
            }
        );
        fw.close();
      }
    } catch (Exception e) {
      System.out.println("创建文件出错");
      e.printStackTrace();
    }
  }
}
