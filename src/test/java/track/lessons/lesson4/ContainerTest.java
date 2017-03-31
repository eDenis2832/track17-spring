package track.lessons.lesson4;

import org.junit.*;
import track.container.Container;
import track.container.JsonConfigReader;
import track.container.beans.Car;
import track.container.beans.Engine;
import track.container.beans.Gear;
import track.container.config.Bean;
import track.container.config.ConfigReader;

import java.io.File;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class ContainerTest {

    @Test
    public void test1() {
        try {
            File file = new File("src/main/resources/config.json");
            ConfigReader reader = new JsonConfigReader();
            List<Bean> beans = reader.parseBeans(file);
            Container container = new Container(beans);

            Car car = (Car) container.getById("carBean");
            //System.out.print(car);
            Assert.assertEquals(6, car.getGear().getCount());
            Assert.assertEquals(200, car.getEngine().getPower());
        } catch (Exception e) {
            System.out.print(e);
        }
    }

    @Test
    public void test2() {
        try {
            File file = new File("src/main/resources/config.json");
            ConfigReader reader = new JsonConfigReader();
            List<Bean> beans = reader.parseBeans(file);
            Container container = new Container(beans);

            Car car = (Car) container.getById("carBean");
            //System.out.print(car);
            Assert.assertEquals(6, car.getGear().getCount());
            Assert.assertEquals(200, car.getEngine().getPower());
            Gear gear = (Gear) container.getById("gearBean");
            Engine engine = (Engine) container.getById("engineBean");
            Assert.assertEquals(6, car.getGear().getCount());
            Assert.assertEquals(200, car.getEngine().getPower());
            Assert.assertEquals(6, gear.getCount());
            Assert.assertEquals(200, engine.getPower());
        } catch (Exception e) {
            System.out.print(e);
        }
    }

    @Test
    public void test3() {
        try {
            File file = new File("src/main/resources/config.json");
            ConfigReader reader = new JsonConfigReader();
            List<Bean> beans = reader.parseBeans(file);
            Container container = new Container(beans);

            Gear gear = (Gear) container.getById("gearBean");
            Engine engine = (Engine) container.getById("engineBean");

            Assert.assertEquals(6, gear.getCount());
            Assert.assertEquals(200, engine.getPower());
        } catch (Exception e) {
            System.out.print(e);
        }
    }

}
