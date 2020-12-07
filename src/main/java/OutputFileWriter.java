import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class OutputFileWriter {
    public void writeToFile(List<Transaction> transactions, String name) throws IOException {
        if (name == null || name.equals("")) {
            throw new IllegalArgumentException("Niepoprawna nazwa pliku wynikowego!");
        }

        if (transactions.size() == 0) {
            throw new IllegalArgumentException("Lista transakcji jest pusta!");
        }

        File file = new File("./Wyniki/" + name);
        FileWriter fileWriter = new FileWriter(file);
        double sumOfTransactions = 0.0;

        for (Transaction t : transactions) {
            double transactionPrice = t.getAmountOfVaccines() * t.getVaccinePrice();
            sumOfTransactions += transactionPrice;
            String line = t.getProducerName() + " -> " + t.getPharmacyName() + " [Koszt = " + t.getAmountOfVaccines() + " * " + t.getVaccinePrice() + " = " + transactionPrice + "]\n";
            fileWriter.append(line);
        }

        fileWriter.append("Oplaty calkowite: ").append(String.valueOf(sumOfTransactions)).append(" zl");
        fileWriter.close();
        System.out.print("Wynik zostal poprawnie zapisany do pliku: " + name + "\n");
    }
}
