import org.testng.annotations.Test;
import java.io.InputStream;

public class MainTest {

    private final String expectedMessageForNoInputFileNameTest = "Nie podano nazwy pliku wejsciowego! Prosze podac nazwe pliku tekstowego w formacie .txt!";

    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = expectedMessageForNoInputFileNameTest)
    public void main_noInputFileName_IllegalArgumentExceptionThrown() {
        String[] args = new String[0];
        final InputStream inputStream = System.in;
        System.setIn(inputStream);
        Main.main(args);
    }
}