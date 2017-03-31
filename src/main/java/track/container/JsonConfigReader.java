package track.container;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import track.container.config.Bean;
import track.container.config.ConfigReader;
import track.container.config.InvalidConfigurationException;


class BeanList {
    private List<Bean> beans;

    @JsonCreator
    public BeanList(@JsonProperty("beans") List<Bean> beans) {
        this.beans = beans;
    }

    public List<Bean> getBeans() {
        return beans;
    }

    @Override
    public String toString() {
        StringBuffer bf = new StringBuffer("Beans: ");
        for (Bean b : beans) {
            bf.append(b.toString());
        }
        return bf.toString();
    }

}

/**
 * TODO: Реализовать
 */
public class JsonConfigReader implements ConfigReader {

    @Override
    public List<Bean> parseBeans(File configFile) throws InvalidConfigurationException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            BeanList bl = mapper.readValue(configFile, BeanList.class);
            return bl.getBeans();
        } catch (IOException e) {
            throw new InvalidConfigurationException(e.toString());
        }
    }
}
