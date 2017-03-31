package track.container;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import track.container.beans.Car;
import track.container.beans.Gear;
import track.container.config.Bean;
import track.container.config.ConfigReader;
import java.io.File;
import java.lang.reflect.Field;
import java.util.List;
/**
 *
 */

public class Main {

    public static void main(String[] args) {

        /*

        ПРИМЕР ИСПОЛЬЗОВАНИЯ

         */

//        // При чтении нужно обработать исключение
//        ConfigReader reader = new JsonReader();
//        List<Bean> beans = reader.parseBeans("config.json");
//        Container container = new Container(beans);
//
//        Car car = (Car) container.getByClass("track.container.beans.Car");
//        car = (Car) container.getById("carBean");

        try {
            File file = new File("src/main/resources/config.json");
            ConfigReader reader = new JsonConfigReader();
            List<Bean> beans = reader.parseBeans(file);
            Container container = new Container(beans);

            Car car = (Car) container.getById("carBean");
            System.out.print(car);
        } catch (Exception e) {
            System.out.print(e);
        }
    }
}
