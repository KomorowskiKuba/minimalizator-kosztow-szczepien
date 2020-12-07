import org.testng.annotations.Test;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class InputFileReaderTest {
    private final String properNonExistentName = "dane.txt";

    private final String expectedMessageForWrongFileExtension = "Nieprawidlowe rozszerzenie pliku! Plik wejsciowy powinien miec rozszerzenie .txt!";
    private final String expectedMessageForWrongFileName = "Nie odnaleziono pliku: " + properNonExistentName + "! Podaj nazwe istniejacego pliku wejsciowego!";
    private final String expectedMessageForEmptyFile = "Brak linii do wczytania!";
    private final String expectedMessageForFileWithWrongStructure = "Linia z komenatrzem nie jest taka jak oczekiwano!";
    private final String expectedMessageForWrongAmountOfArguments = "Nieprawidlowa ilosc atrybutow w linii: " + "2" + "! Popraw strukture pliku!";

    @Test
    public void readFromFile_properFileExtension_IllegalArgumentExceptionNotThrown() {
        try {
            InputFileReader inputFileReader = new InputFileReader(properNonExistentName);
        } catch (FileNotFoundException ignored) {
        }
        assertTrue(true);
    }

    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = expectedMessageForWrongFileExtension)
    public void readFromFile_wrongFileExtension_IllegalArgumentExceptionThrown() {
        try {
            String wrongFileExtensionName = "dane.tar";
            InputFileReader inputFileReader = new InputFileReader(wrongFileExtensionName);
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
    }

    @Test
    public void readFromFile_nonExistentFileName_FileNotFoundExceptionThrown() {
        try {
            InputFileReader inputFileReader = new InputFileReader(properNonExistentName);
        } catch (FileNotFoundException fileNotFoundException) {
            assertEquals(fileNotFoundException.getMessage(), expectedMessageForWrongFileName);
        }
    }

    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = expectedMessageForEmptyFile)
    public void readFromFile_emptyFileGiven_IllegalArgumentExceptionThrown() {
        try {
            String emptyFile = "emptyFile.txt";
            InputFileReader inputFileReader = new InputFileReader(emptyFile);
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
    }

    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = expectedMessageForFileWithWrongStructure)
    public void readFromFile_fileWithWrongStructureGiven_IllegalArgumentExceptionThrown() {
        try {
            String fileWithWrongStructure = "wrongStructureFile.txt";
            InputFileReader inputFileReader = new InputFileReader(fileWithWrongStructure);
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
    }

    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = expectedMessageForWrongAmountOfArguments)
    public void readFromFile_fileWithInvalidAmountOfArgumentsGiven_IllegalArgumentExceptionThrown() {
        try {
            String fileWithWrongArgumentsGiven = "wrongArgumentsFile.txt";
            InputFileReader inputFileReader = new InputFileReader(fileWithWrongArgumentsGiven);
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
    }

    @Test
    public void readFromFile_properInputFileGiven_properListsReceived() {
        InputFileReader inputFileReader = null;

        List<Producer> producerList = new ArrayList<>();
        Producer p0 = new Producer(0, "BioTech 2.0", 900);
        Producer p1 = new Producer(1, "Eko Polska 2020", 1300);
        producerList.add(p0);
        producerList.add(p1);

        List<Pharmacy> pharmacyList = new ArrayList<>();
        Pharmacy a0 = new Pharmacy(0, "CentMedEko Centrala", 450);
        Pharmacy a1 = new Pharmacy(1, "CentMedEko Nowogrodzka", 1200);
        pharmacyList.add(a0);
        pharmacyList.add(a1);

        List<Connection> connectionList = new ArrayList<>();
        connectionList.add(new Connection(p0, a0, 800, 70.5));
        connectionList.add(new Connection(p0, a1, 600, 70.0));
        connectionList.add(new Connection(p1, a0, 900, 100.0));
        connectionList.add(new Connection(p1, a1, 600, 80.0));


        try {
            String properFile = "dane2.txt";
            inputFileReader = new InputFileReader(properFile);
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }

        assert inputFileReader != null;
        assertEquals(producerList, inputFileReader.getProducerList());
        assertEquals(pharmacyList, inputFileReader.getPharmacyList());
        assertEquals(connectionList, inputFileReader.getConnections());
    }


}