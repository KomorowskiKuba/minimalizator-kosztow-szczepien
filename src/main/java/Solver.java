import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Solver {
    private final List<Producer> producers;
    private final List<Pharmacy> pharmacies;
    private final List<Connection> connections;
    private List<Transaction> transactions;
    private Double[][] mainArray;
    private int xMainSize;
    private int yMainSize;
    private final Double M = 100_000.0;
    private Double[] prices;
    private Integer[] amounts;
    private Double[] cb;
    private Double[] q;
    private Double[] mins;
    private Integer[] ids;

    Solver(List<Producer> producers, List<Pharmacy> pharmacies, List<Connection> connections) {
        this.producers = producers;
        this.pharmacies = pharmacies;
        this.connections = connections;
    }

    List<Transaction> solve() {
        transactions = new ArrayList<>();
        xMainSize = 2 * connections.size() + producers.size() + pharmacies.size();
        yMainSize = connections.size() + producers.size() + pharmacies.size();
        mainArray = new Double[yMainSize][xMainSize];
        prices = new Double[xMainSize];
        amounts = new Integer[yMainSize];
        cb = new Double[yMainSize];
        q = new Double[yMainSize];
        mins = new Double[xMainSize];
        ids = new Integer[yMainSize];
        for(int i = 0; i < yMainSize; i++) {
            ids[i] = -1;
        }

        int w = 0;
        IntStream.range(0, connections.size()).forEach(i -> prices[i] = connections.get(i).getVaccinePrice());
        w += connections.size();
        IntStream.range(w, w + connections.size() + producers.size()).forEach(i -> prices[i] = 0.0);
        w += connections.size() + producers.size();
        IntStream.range(w, w + pharmacies.size()).forEach(i -> prices[i] = M);

        w = 0;
        IntStream.range(0, connections.size()).forEach(i -> amounts[i] = connections.get(i).getMaxDailySupply());
        w += connections.size();
        IntStream.range(w, w + producers.size()).forEach(i -> amounts[i] = producers.get(i - connections.size()).getDailyProduction());
        w += producers.size();
        IntStream.range(w, w + pharmacies.size()).forEach(i -> amounts[i] = pharmacies.get(i - connections.size() - producers.size()).getDailyRequirement());

        if (prices.length - connections.size() >= 0)
            System.arraycopy(prices, connections.size(), cb, 0, prices.length - connections.size());

        for (int i = 0; i < yMainSize; i++) {
            for (int j = 0; j < xMainSize; j++) {
                mainArray[i][j] = 0.0;
            }
        }

        IntStream.range(0, connections.size()).forEach(i -> mainArray[i][i] = 1.0);

        int k = 0;
        for (int i = connections.size(); i < xMainSize; i++) {
            mainArray[k++][i] = 1.0;
        }

        for (int i = connections.size(); i < connections.size() + producers.size(); i++) {
            for (int j = 0; j < connections.size(); j++) {
                Producer p1 = connections.get(j).getProducer();
                Producer p2 = producers.get(i - connections.size());
                if (p1.equals(p2)) {
                    mainArray[i][j] = 1.0;
                }
            }
        }

        for (int i = connections.size() + producers.size(); i < yMainSize; i++) {
            for (int j = 0; j < connections.size(); j++) {
                Pharmacy p1 = connections.get(j).getPharmacy();
                Pharmacy p2 = pharmacies.get(i - connections.size() - producers.size());
                if (p1.equals(p2)) {
                    mainArray[i][j] = 1.0;
                }
            }
        }

        for (int i = 0; i < xMainSize; i++) {
            double local = 0.0;
            for (int j = 0; j < yMainSize; j++) {
                local += mainArray[j][i] * cb[j];
            }
            mins[i] = local - prices[i];
        }

        List<Integer> highestBefore = new ArrayList<>();
        List<Integer> smallestBefore = new ArrayList<>();

        int indexOfTheHighest = getIndexOfTheHighest(mins);

        for (int i = 0; i < yMainSize; i++) {
            if (mainArray[i][indexOfTheHighest] == 0) {
                q[i] = Double.MAX_VALUE;
            } else {
                q[i] = amounts[i] / mainArray[i][indexOfTheHighest];
            }
        }

        int indexOfTheSmallest = getIndexOfTheSmallest(q);
        highestBefore.add(indexOfTheHighest);
        smallestBefore.add(indexOfTheSmallest);

        int its = 1;
        boolean shouldEnd = false;

        while (true) {
            Double[] rowToTransfer = mainArray[indexOfTheSmallest];
            Double[] columnToCompute = new Double[yMainSize];
            Integer transferValueInt = mainArray[indexOfTheSmallest][indexOfTheHighest].intValue();

            for(int i = 0; i < xMainSize; i++) {
                rowToTransfer[i] /= transferValueInt;
            }

            for(int i = 0; i < yMainSize; i++) {
                columnToCompute[i] = mainArray[i][indexOfTheHighest];
            }

            if (transferValueInt != 0) {
                amounts[indexOfTheSmallest] /= transferValueInt;
            }

            Double priceToSwap = prices[indexOfTheHighest];
            ids[indexOfTheSmallest] = indexOfTheHighest;
            cb[indexOfTheSmallest] = priceToSwap;

            if(shouldEnd) {
                break;
            }

            if (checkIfAllAreSmallerThanZero(mins) || its > 20000) {
                shouldEnd = true;
            }

            if (xMainSize >= 0) {
                System.arraycopy(rowToTransfer, 0, mainArray[indexOfTheSmallest], 0, xMainSize);
            }

            for (int i = 0; i < yMainSize; i++) {
                if (i != indexOfTheSmallest && transferValueInt != 0) {
                    amounts[i] = ((amounts[i] * transferValueInt) - (columnToCompute[i].intValue() * amounts[indexOfTheSmallest])) / transferValueInt;
                }
            }

            for (int i = 0; i < yMainSize; i++) {
                if (i != indexOfTheSmallest) {
                    double locked = mainArray[i][indexOfTheHighest];
                    for (int j = 0; j < xMainSize; j++) {
                        if (j != indexOfTheHighest + 1 && transferValueInt != 0) {
                            mainArray[i][j] = ((mainArray[i][j] * transferValueInt) - (locked * mainArray[indexOfTheSmallest][j])) / transferValueInt;
                        }
                    }
               }
            }

            for (int i = 0; i < xMainSize; i++) {
                double local = 0.0;
                for (int j = 0; j < yMainSize; j++) {
                    local += mainArray[j][i] * cb[j];
                }
                mins[i] = local - prices[i];
            }

            for (int i = 0; i < yMainSize; i++) {
                if (i != indexOfTheSmallest) {
                    mainArray[i][indexOfTheHighest] = 0.0;
                }
            }

            indexOfTheHighest = getIndexOfTheHighest(mins);

            for (int i = 0; i < yMainSize; i++) {
                if (mainArray[i][indexOfTheHighest] == 0) {
                    q[i] = Double.MAX_VALUE;
                } else {
                    q[i] = amounts[i] / mainArray[i][indexOfTheHighest];
                }
            }

            indexOfTheSmallest = getIndexOfTheSmallest(q);

            highestBefore.add(indexOfTheHighest);
            smallestBefore.add(indexOfTheSmallest);

            its++;
    }

        for(int i = 0; i < yMainSize; i++) {
            if(ids[i] >= 0 && cb[i] > 0) {
                Connection connection = connections.get(ids[i]);
                Transaction transaction = new Transaction(connection.getProducer(), connection.getPharmacy(), amounts[i], connection.getVaccinePrice());
                transactions.add(transaction);
            }
        }

        return transactions;
    }

    private int getIndexOfTheHighest(Double[] arr) {
        Double max = arr[0];
        int indexMax = 0;

        for(int i = 1; i < arr.length; i++) {
            if(arr[i] > max) {
                max = arr[i];
                indexMax = i;
            }
        }

        return indexMax;
    }

    private int getIndexOfTheSmallest(Double[] arr) { //TODO: CHECK THIS!!!!
        Double min = 100000.0;
        int indexMin = 0;
        for(int i = 1; i < arr.length; i++) {
            if (arr[i] >= 0) {
                min = arr[i];
                indexMin = i;
                break;
            }
        }

        for(int i = 0; i < arr.length; i++) {
            if (arr[i] >= 0) {
                if (arr[i] < min) {
                    min = arr[i];
                    indexMin = i;
                }
            }
        }

        return indexMin;
    }

    private boolean checkIfAllAreSmallerThanZero(Double[] arr) {
        int amountOfSmallers = 0;
        for (Double aDouble : arr) {
            if (aDouble <= 0.0) {
                amountOfSmallers++;
            }
        }

        return amountOfSmallers == arr.length;
    }
}
