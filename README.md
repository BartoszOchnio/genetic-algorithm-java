# Algorytm genetyczny - optymalizacja funkcji

Desktopowa aplikacja w języku Java prezentująca działanie algorytmu genetycznego na przykładzie optymalizacji funkcji jednej zmiennej.

Program umożliwia wyszukiwanie minimum albo maksimum funkcji, konfigurowanie parametrów algorytmu oraz obserwowanie zmian zachodzących w kolejnych pokoleniach.

![Główny widok aplikacji](docs/images/main-view.png)

## Najważniejsze funkcjonalności

- wyszukiwanie minimum albo maksimum funkcji;
- określanie przedziału poszukiwań;
- konfiguracja liczebności populacji i liczby iteracji;
- wybór prawdopodobieństwa krzyżowania i mutacji;
- binarne kodowanie chromosomów;
- selekcja metodą koła ruletki;
- krzyżowanie jednopunktowe;
- mutacja bitowa;
- opcjonalne zachowywanie najlepszego osobnika (elitaryzm);
- prezentacja populacji końcowej w tabeli;
- wizualizacja postępu ewolucji;
- automatyczne porównywanie zestawów parametrów.

## Optymalizowana funkcja

Algorytm wyszukuje minimum albo maksimum funkcji:

```text
f(x) = cos(20*pi*x) - sin(x)
```

Przedział wartości `x` oraz kierunek optymalizacji są określane przez użytkownika. Ze względu na losowy charakter algorytmu wyniki kolejnych uruchomień mogą się nieznacznie różnić.

## Parametry algorytmu

| Parametr | Znaczenie |
|---|---|
| `a` | Dolna granica przedziału |
| `b` | Górna granica przedziału |
| `n` | Liczebność populacji |
| `T` | Liczba iteracji algorytmu |
| `d` | Dokładność reprezentacji wartości |
| `Pk` | Prawdopodobieństwo wyboru osobnika do krzyżowania |
| `Pm` | Prawdopodobieństwo mutacji pojedynczego bitu |
| Maksimum / Minimum | Kierunek optymalizacji |
| Elita | Zachowywanie najlepszego znalezionego osobnika |

## Przebieg algorytmu

Każda iteracja obejmuje następujące etapy:

1. Wygenerowanie albo przyjęcie bieżącej populacji.
2. Zakodowanie wartości rzeczywistych w postaci binarnej.
3. Obliczenie wartości funkcji dla każdego osobnika.
4. Wyznaczenie przystosowania osobników.
5. Selekcja metodą koła ruletki.
6. Krzyżowanie jednopunktowe.
7. Mutacja poszczególnych bitów.
8. Opcjonalne zachowanie najlepszego osobnika.
9. Zapisanie statystyk nowego pokolenia.

Proces jest powtarzany `T` razy. Długość chromosomu zależy od szerokości przedziału oraz dokładności `d`.

## Prezentacja wyników

Po zakończeniu obliczeń aplikacja prezentuje populację końcową. Tabela zawiera:

- `xReal` - wartość rzeczywistą reprezentowaną przez osobnika;
- `xBin` - binarną reprezentację chromosomu;
- `f(x)` - wartość optymalizowanej funkcji;
- `%` - udział osobników o danej wartości w populacji.

Osobniki o takiej samej wartości funkcji są grupowane, dlatego tabela może zawierać mniej wierszy niż liczebność populacji.

![Tabela populacji końcowej](docs/images/results-table.png)

## Wykres ewolucji

Wykres przedstawia zmiany wartości funkcji w kolejnych pokoleniach:

- `Min f(x)` - najniższa wartość w populacji;
- `Max f(x)` - najwyższa wartość w populacji;
- `Avg f(x)` - średnia wartość w populacji.

Pozwala to obserwować, czy populacja stopniowo zbliża się do korzystniejszego rozwiązania.

![Wykres postępu ewolucji](docs/images/evolution-chart.png)

## Automatyczne testowanie parametrów

Zakładka **Test** porównuje 16 konfiguracji utworzonych z następujących wartości:

| Parametr | Sprawdzane wartości |
|---|---|
| `n` | 30, 60 |
| `Pk` | 0.6, 0.8 |
| `Pm` | 0.0001, 0.001 |
| `T` | 50, 100 |

Każda konfiguracja jest uruchamiana 10 razy. Kolumna `Favg(x)` przedstawia średnią wartość funkcji w populacjach końcowych, a wyniki są uporządkowane od najwyższego do najniższego.

W przykładowym teście najlepszy rezultat uzyskała konfiguracja `n = 60`, `Pk = 0.8`, `Pm = 0.0001` oraz `T = 100`. Ze względu na losowy charakter algorytmu rezultat pojedynczej serii testów nie oznacza, że jest to zawsze najlepszy zestaw parametrów.

![Wyniki testowania parametrów](docs/images/parameter-tests.png)

## Technologie

- Java;
- Swing;
- JFreeChart;
- Maven;
- IntelliJ IDEA.

## Struktura projektu

```text
src/
|-- main/
|   `-- java/
|       |-- Gui/
|       |   `-- Form.java
|       |-- Model/
|       |   |-- Chromosome.java
|       |   |-- EvolutionStats.java
|       |   |-- GaResult.java
|       |   |-- Test.java
|       |   |-- Xbin.java
|       |   |-- XInt.java
|       |   `-- Xreal.java
|       |-- Service/
|       |   `-- GeneticMath.java
|       `-- org/example/
|           `-- Main.java
`-- test/
```

### Najważniejsze klasy

- `Form` - tworzy interfejs, odczytuje parametry oraz prezentuje wyniki;
- `GeneticMath` - zawiera logikę algorytmu genetycznego i testów parametrów;
- `Chromosome` - reprezentuje pojedynczego osobnika;
- `Xreal`, `XInt` i `Xbin` - obsługują różne reprezentacje chromosomu;
- `EvolutionStats` - przechowuje statystyki kolejnych pokoleń;
- `GaResult` - grupuje populację końcową i statystyki;
- `Test` - reprezentuje wynik testu jednej konfiguracji;
- `Main` - stanowi punkt startowy aplikacji.

## Uruchomienie projektu

### Wymagania

- zainstalowane środowisko Java;
- Maven;
- IntelliJ IDEA lub inne środowisko obsługujące projekty Maven.

### Instrukcja

1. Pobierz lub sklonuj repozytorium.
2. Otwórz katalog projektu w IntelliJ IDEA.
3. Poczekaj na pobranie zależności Maven.
4. Otwórz klasę `src/main/java/org/example/Main.java`.
5. Uruchom metodę `main()`.
6. Wprowadź parametry algorytmu i wybierz przycisk **Start**.

## Dokumentacja

Rozszerzony opis działania algorytmu, parametrów, struktury projektu i interfejsu znajduje się w pliku:

[Dokumentacja projektu](docs/Algorytm_genetyczny_dokumentacja.pdf)

## Możliwe kierunki rozwoju

- możliwość wprowadzania własnej funkcji celu;
- dodanie innych metod selekcji i krzyżowania;
- zatrzymywanie po osiągnięciu zadanej jakości rozwiązania;
- eksport wyników do pliku CSV;
- zapis i odczyt konfiguracji;
- wykonywanie dłuższych testów w osobnym wątku.

## Autor

Bartosz Ochnio

Projekt wykonany w ramach zajęć akademickich.
