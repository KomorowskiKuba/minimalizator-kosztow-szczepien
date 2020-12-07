import org.testng.annotations.Test;
import java.io.FileNotFoundException;
import java.util.List;
import static org.testng.Assert.*;

public class SolverTest {

    @Test
    public void solver_dataNo1Given_ProperOutputExpected() {
        InputFileReader inputFileReader = null;
        String inputFileName = "dane1.txt";
        List<Transaction> transactionList;
        Solver solver;
        double expectedTotalPrice = 189145.5;

        try {
            inputFileReader = new InputFileReader(inputFileName);
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }

        if (inputFileReader != null) {
            solver = new Solver(inputFileReader.getProducerList(), inputFileReader.getPharmacyList(), inputFileReader.getConnections());
            transactionList = solver.solve();
            assertEquals(calculateCost(transactionList), expectedTotalPrice);
        } else {
            fail();
        }
    }

    @Test
    public void solver_dataNo2Given_ProperOutputExpected() {
        InputFileReader inputFileReader = null;
        String inputFileName = "dane2.txt";
        List<Transaction> transactionList;
        Solver solver;
        double expectedTotalPrice = 126150.0;

        try {
            inputFileReader = new InputFileReader(inputFileName);
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }

        if (inputFileReader != null) {
            solver = new Solver(inputFileReader.getProducerList(), inputFileReader.getPharmacyList(), inputFileReader.getConnections());
            transactionList = solver.solve();
            assertEquals(calculateCost(transactionList), expectedTotalPrice);
        } else {
            fail();
        }
    }

    @Test
    public void solver_dataNo3Given_ProperOutputExpected() {
        InputFileReader inputFileReader = null;
        String inputFileName = "dane3.txt";
        List<Transaction> transactionList;
        Solver solver;
        double expectedTotalPrice = 35071.89;

        try {
            inputFileReader = new InputFileReader(inputFileName);
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }

        if (inputFileReader != null) {
            solver = new Solver(inputFileReader.getProducerList(), inputFileReader.getPharmacyList(), inputFileReader.getConnections());
            transactionList = solver.solve();
            assertEquals(calculateCost(transactionList), expectedTotalPrice);
        } else {
            fail();
        }
    }

    @Test
    public void solver_dataNo4Given_ProperOutputExpected() {
        InputFileReader inputFileReader = null;
        String inputFileName = "dane4.txt";
        List<Transaction> transactionList;
        Solver solver;
        double expectedTotalPrice = 23766.09;

        try {
            inputFileReader = new InputFileReader(inputFileName);
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }

        if (inputFileReader != null) {
            solver = new Solver(inputFileReader.getProducerList(), inputFileReader.getPharmacyList(), inputFileReader.getConnections());
            transactionList = solver.solve();
            assertEquals(calculateCost(transactionList), expectedTotalPrice);
        } else {
            fail();
        }
    }

    @Test
    public void solver_dataNo5Given_ProperOutputExpected() {
        InputFileReader inputFileReader = null;
        String inputFileName = "dane5.txt";
        List<Transaction> transactionList;
        Solver solver;
        double expectedTotalPrice = 20837.29;

        try {
            inputFileReader = new InputFileReader(inputFileName);
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }

        if (inputFileReader != null) {
            solver = new Solver(inputFileReader.getProducerList(), inputFileReader.getPharmacyList(), inputFileReader.getConnections());
            transactionList = solver.solve();
            assertEquals(calculateCost(transactionList), expectedTotalPrice);
            assertEquals(transactionList.size(), inputFileReader.getConnections().size());
        } else {
            fail();
        }
    }

    private double calculateCost(List<Transaction> transactions) {
        double sum = 0.0;

        for (Transaction t : transactions) {
            sum += t.getAmountOfVaccines() * t.getVaccinePrice();
        }

        return sum;
    }


}