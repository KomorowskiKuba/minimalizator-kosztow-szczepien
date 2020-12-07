import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        InputFileReader inputFileReader = null;
        Solver solver = null;
        String inputFileName;
        String outputFileName;

        if (args.length > 0) {
            inputFileName = args[0];
        } else {
            throw new IllegalArgumentException("Nie podano nazwy pliku wejsciowego! Prosze podac nazwe pliku tekstowego w formacie .txt!");
        }

        if (args.length > 1) {
            outputFileName = args[1];
        } else {
            outputFileName = "Wynik.txt";
        }

        try {
            inputFileReader = new InputFileReader(inputFileName);
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }

        if (inputFileReader != null) {
            solver = new Solver(inputFileReader.getProducerList(), inputFileReader.getPharmacyList(), inputFileReader.getConnections());
        }

        List<Transaction> transactionList = null;
        if (solver != null) {
            transactionList = solver.solve();
        }

        OutputFileWriter fw = new OutputFileWriter();
        try {
            if (transactionList != null) {
                fw.writeToFile(transactionList, outputFileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
