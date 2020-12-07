## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)

## General info
The main goal of the program is to minimize the total amount of vaccines purchased by pharmacies while meeting the vaccine needs of each pharmacy.
	
## Technologies
Project is created with:
* Java 11
	
## Setup
To run this project:

$ cd ../minimalizator-kosztow-szczepien\
$ mvn -q clean compile exec:java -Dexec.args="inputFile.txt outputFile.txt"

## Input file format
* Input file should contain 3 lines with comments, placed in specific order. In other case, an exception will be thrown.
* First part must cpntain vaccine producers
* Second part should contain pharmacies
* The last part have to contain connections(contracts) between producers and pharmacies

Sample file:

&#35 Producenci szczepionek (id | nazwa | dzienna produkcja)\
0 | BioTech 2.0 | 900\
1 | Eko Polska 2020 | 1300\
2 | Post-Covid Sp. z o.o. | 1100\
&#35 Apteki (id | nazwa | dzienne zapotrzebowanie)\
0 | CentMedEko Centrala | 450\
1 | CentMedEko 24h | 690\
2 | CentMedEko Nowogrodzka | 1200\
&#35 Połączenia producentów i aptek (id producenta | id apteki | dzienna maksymalna liczba dostarczanych szczepionek | koszt szczepionki [zł] )\
0 | 0 | 800 | 70.5\
0 | 1 | 600 | 70\
0 | 2 | 750 | 90.99\
1 | 0 | 900 | 100\
1 | 1 | 600 | 80\
1 | 2 | 450 | 70\
2 | 0 | 900 | 80\
2 | 1 | 900 | 90\
2 | 2 | 300 | 100\
