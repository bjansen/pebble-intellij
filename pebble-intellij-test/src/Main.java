import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.error.PebbleException;
import com.mitchellbosecke.pebble.loader.ClasspathLoader;
import foo.bar.SomeClass;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        PebbleEngine engine = new PebbleEngine.Builder()
                .loader(new ClasspathLoader(Main.class.getClassLoader()))
                .strictVariables(true)
                .build();

        try {
            HashMap<String, Object> context = new HashMap<>();
            context.put("someClass", new SomeClass());

            engine.getTemplate("references.peb").evaluate(new StringWriter(), context);
        } catch (PebbleException | IOException e) {
            e.printStackTrace();
        }
    }
}
