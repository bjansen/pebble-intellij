import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.error.PebbleException;
import com.mitchellbosecke.pebble.loader.ClasspathLoader;
import com.mitchellbosecke.pebble.loader.StringLoader;
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
            context.put("foo", "foo");
            context.put("baz", "baz");

            StringWriter writer = new StringWriter();
            engine.getTemplate("ticket/foo.peb").evaluate(writer, context);

            System.out.println(writer.toString());
        } catch (PebbleException | IOException e) {
            e.printStackTrace();
        }

        new StringLoader().getReader("{{ 'hello' | upper | abbreviate(2) }}");
    }
}
