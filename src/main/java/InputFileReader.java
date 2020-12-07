import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class InputFileReader {
    private static final String producersComment = "# Producenci szczepionek (id | nazwa | dzienna produkcja)";
    private static final String pharmaciesComment = "# Apteki (id | nazwa | dzienne zapotrzebowanie)";
    private static final String connectionsComment = "# Połączenia producentów i aptek (id producenta | id apteki | dzienna maksymalna liczba dostarczanych szczepionek | koszt szczepionki [zł] )";
    private static final String delimiter = "|";
    private static List<Producer> producerList;
    private static List<Pharmacy> pharmacyList;
    private static List<Connection> connections;

    InputFileReader(String filePath) throws FileNotFoundException {
        producerList = new ArrayList<>();
        pharmacyList = new ArrayList<>();
        connections = new ArrayList<>();
        if (!filePath.startsWith(".txt", filePath.length() - 4)) {
            throw new IllegalArgumentException("Nieprawidlowe rozszerzenie pliku! Plik wejsciowy powinien miec rozszerzenie .txt!");
        }
        try {
            readFromFile(filePath);
        } catch (FileNotFoundException fileNotFoundException) {
            throw new FileNotFoundException("Nie odnaleziono pliku: " + filePath + "! Podaj nazwe istniejacego pliku wejsciowego!");
        }
    }

    public static void readFromFile(String path) throws FileNotFoundException {
        int lineNumber = 0;
        File inputFile;
        Scanner scanner;
        String line;

        inputFile = new File(path);
        scanner = new Scanner(inputFile);

        if (scanner.hasNextLine()) {
            line = scanner.nextLine();
            ++lineNumber;
        } else {
            throw new IllegalArgumentException("Brak linii do wczytania!");
        }
        if (checkIfLineIsAComment(line, producersComment)) {
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                ++lineNumber;
                StringTokenizer stringTokenizer = new StringTokenizer(line, delimiter);

                if (line.charAt(0) == '#') {
                    break;
                } else {
                    if (stringTokenizer.countTokens() == 3) {
                        Integer id = Integer.parseInt(removeSpaces(stringTokenizer.nextToken()));
                        String name = removeSpaces(stringTokenizer.nextToken());
                        Integer dailyProduction = Integer.parseInt(removeSpaces(stringTokenizer.nextToken()));

                        Producer producer = new Producer(id, name, dailyProduction);
                        producerList.add(producer);
                    } else {
                        throw new IllegalArgumentException("Nieprawidlowa ilosc atrybutow w linii: " + lineNumber + "! Popraw strukture pliku!");
                    }
                }
            }
        } else {
            throw new IllegalArgumentException("Nieprawidłowe dane wejsciowe! Sprawdz poprawnosc danych w pliku wejsciowym!");
        }

        if (checkIfLineIsAComment(line, pharmaciesComment)) {
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                ++lineNumber;
                StringTokenizer stringTokenizer = new StringTokenizer(line, delimiter);
                if (line.charAt(0) == '#') {
                    break;
                } else {
                    if (stringTokenizer.countTokens() == 3) {
                        Integer id = Integer.parseInt(removeSpaces(stringTokenizer.nextToken()));
                        String name = removeSpaces(stringTokenizer.nextToken());
                        Integer dailyRequirement = Integer.parseInt(removeSpaces(stringTokenizer.nextToken()));

                        Pharmacy pharmacy = new Pharmacy(id, name, dailyRequirement);
                        pharmacyList.add(pharmacy);
                    } else {
                        throw new IllegalArgumentException("Nieprawidlowa ilosc atrybutow w linii: " + lineNumber + "! Popraw strukture pliku!");
                    }
                }
            }
        } else {
            throw new IllegalArgumentException("Nieprawidłowe dane wejsciowe! Sprawdz poprawnosc danych w pliku wejsciowym!");
        }

        if (checkIfLineIsAComment(line, connectionsComment)) {
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                ++lineNumber;
                StringTokenizer stringTokenizer = new StringTokenizer(line, delimiter);
                if (line.charAt(0) == '#') {
                    break;
                } else {
                    if (stringTokenizer.countTokens() == 4) {
                        Integer producerId = Integer.parseInt(removeSpaces(stringTokenizer.nextToken()));
                        Integer pharmacyId = Integer.parseInt(removeSpaces(stringTokenizer.nextToken()));
                        Integer maxDailySupply = Integer.parseInt(removeSpaces(stringTokenizer.nextToken()));
                        Double price = Double.parseDouble(removeSpaces(stringTokenizer.nextToken()));

                        Producer producer = Producer.getProducerById(producerList, producerId);
                        Pharmacy pharmacy = Pharmacy.getPharmacyById(pharmacyList, pharmacyId);

                        Connection connection = new Connection(producer, pharmacy, maxDailySupply, price);
                        connections.add(connection);
                    } else {
                        throw new IllegalArgumentException("Nieprawidlowa ilosc atrybutow w linii: " + lineNumber + "! Popraw strukture pliku!");
                    }
                }
            }
        } else {
            throw new IllegalArgumentException("Nieprawidłowe dane wejsciowe! Sprawdz poprawnosc danych w pliku wejsciowym!");
        }
    }

    private static boolean checkIfLineIsAComment(String line, String comment) {
        if (line.charAt(0) == '#') {
            if (line.equals(comment)) {
                return true;
            } else {
                throw new IllegalArgumentException("Linia z komenatrzem nie jest taka jak oczekiwano!");
            }
        } else {
            return false;
        }
    }

    private static String removeSpaces(String word) {
        String trimmedWord = word;

        if (word.charAt(0) == ' ') {
            trimmedWord = trimmedWord.stripLeading();
        }
        if (word.charAt(word.length() - 1) == ' ') {
            trimmedWord = trimmedWord.stripTrailing();
        }

        return trimmedWord;
    }

    public List<Producer> getProducerList() {
        return producerList;
    }

    public List<Pharmacy> getPharmacyList() {
        return pharmacyList;
    }

    public List<Connection> getConnections() {
        return connections;
    }
}