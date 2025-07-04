package utilities;

import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.junit.Options;
import com.microsoft.playwright.junit.OptionsFactory;

public class CustomOptions implements OptionsFactory {

    @Override
    public Options getOptions() {
        return new Options()
                .setLaunchOptions(createLaunchOptions())          // Opciones de lanzamiento
                .setContextOptions(null)                          // Puedes personalizar si deseas
                .setHeadless(false)                               // Mostrar navegador
                .setBrowserName("chromium")                       // chromium | firefox | webkit
                .setChannel("chrome")                             // chrome o edge
                .setTestIdAttribute("data-test")                  // para localizadores personalizados
                .setBaseUrl("https://www.saucedemo.com");         // URL base para los tests
    }

    private BrowserType.LaunchOptions createLaunchOptions() {
        //final var arguments = List.of("--start-maximized");

        return new BrowserType.LaunchOptions()
                .setSlowMo(1000)    ;   // ralentiza para observar la ejecución
                //.setArgs(arguments);   // argumentos personalizados
    }
}
