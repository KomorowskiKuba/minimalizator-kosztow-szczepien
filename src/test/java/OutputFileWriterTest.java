import org.testng.annotations.Test;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import static org.testng.Assert.assertEquals;

public class OutputFileWriterTest {
    private final String expectedMessageForWrongFileName = "Niepoprawna nazwa pliku wynikowego!";
    private final String expectedMessageForEmptyTransactionList = "Lista transakcji jest pusta!";

    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = expectedMessageForWrongFileName)
    public void writeToFile_GivenNullAsNameArgument_ExceptionThrown() {
        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(new Transaction(new Producer(0, "xyz", 200), new Pharmacy(0, "zxy", 500), 500, 55.3));

        OutputFileWriter outputFileWriter = new OutputFileWriter();
        try {
            outputFileWriter.writeToFile(transactionList, null);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = expectedMessageForEmptyTransactionList)
    public void writeToFile_GivenEmptyTransactionList_ExceptionThrown() {
        List<Transaction> transactionList = new ArrayList<>();

        OutputFileWriter outputFileWriter = new OutputFileWriter();
        try {
            outputFileWriter.writeToFile(transactionList, "Plik.txt");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    @Test
    public void writeToFile_properDataGiven_ExceptionThrown() {
        String fileName = "WynikTestu.txt";
        String expectedMessage = "Wynik zostal poprawnie zapisany do pliku: " + fileName + "\n";
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream oldPrintStream = new PrintStream(System.out);
        PrintStream newPrintStream = new PrintStream(byteArrayOutputStream);

        System.setOut(newPrintStream);

        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(new Transaction(new Producer(0, "xyz", 200), new Pharmacy(0, "zxy", 500), 500, 55.3));

        OutputFileWriter outputFileWriter = new OutputFileWriter();
        try {
            outputFileWriter.writeToFile(transactionList, fileName);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        System.out.flush();
        System.setOut(oldPrintStream);

        assertEquals(byteArrayOutputStream.toString(), expectedMessage);
    }

}