# Carrefour Database Demo

🌐 Język: [English](README.md) | **Polski**

Carrefour Database Demo to aplikacja desktopowa napisana w języku Java z wykorzystaniem JavaFX, przeznaczona do zarządzania podstawowymi danymi sklepu.

Projekt prezentuje wykorzystanie programowania obiektowego, interfejsu graficznego JavaFX, zapisu danych do plików, walidacji danych oraz podstawowych operacji zarządzania danymi.

## Funkcjonalności

Aplikacja składa się z czterech głównych sekcji:

### Pracownicy
- Dodawanie i usuwanie pracowników.
- Przechowywanie informacji takich jak imię, nazwisko, wiek, stanowisko oraz data zatrudnienia.
- Tworzenie własnych stanowisk.
- Rozróżnianie stanowisk zwykłych i kierowniczych.
- Zapisywanie danych pracowników pomiędzy uruchomieniami aplikacji.

### Towary
- Dodawanie i usuwanie towarów.
- Przechowywanie nazwy, typu, ceny oraz ilości towaru.
- Tworzenie własnych typów towarów.
- Zapisywanie danych pomiędzy uruchomieniami aplikacji.

### Dostawcy
- Dodawanie i usuwanie dostawców.
- Przypisywanie typu towaru do dostawcy.
- Przechowywanie dat zawarcia umowy.
- Zapisywanie danych dostawców pomiędzy uruchomieniami aplikacji.

### Wynagrodzenia
- Dodawanie wynagrodzeń pracowników.
- Przechowywanie liczby przepracowanych godzin oraz okresu wynagrodzenia.
- Zapobieganie dodaniu dwóch wynagrodzeń dla tego samego pracownika i okresu.
- Sprawdzanie, czy okres wynagrodzenia nie jest wcześniejszy niż data zatrudnienia pracownika.
- Zapisywanie danych pomiędzy uruchomieniami aplikacji.

## Technologie

- Java 21
- JavaFX 21
- Programowanie obiektowe (OOP)
- Java Collections
- Zapis danych do plików
- Git
- GitHub
- Jira

## Struktura projektu

Aplikacja jest podzielona na klasy modelu danych, klasy odpowiedzialne za zapis i odczyt danych oraz klasy interfejsu JavaFX.

Przykłady:

- `Pracownik`, `Kierownik` - modele pracowników
- `Towar` - model towaru
- `Dostawca` - model dostawcy
- `Wynagrodzenie` - model wynagrodzenia
- `Stanowisko` - model stanowiska pracownika
- `TypTowaru` - model typu towaru
- klasy `*Storage` - zapis i odczyt danych aplikacji
- klasy `*Window` - interfejs użytkownika JavaFX
- `MainWindow` - punkt startowy aplikacji

## Przechowywanie danych

Dane aplikacji są przechowywane lokalnie w plikach tekstowych.

Aplikacja wykorzystuje osobne pliki dla:

- pracowników
- towarów
- dostawców
- wynagrodzeń
- stanowisk
- typów towarów

Dzięki temu dane pozostają dostępne po zamknięciu i ponownym uruchomieniu aplikacji.

## Wymagania

Do kompilacji i uruchomienia projektu wymagane są:

- JDK 21
- JavaFX SDK 21

JavaFX SDK musi być zainstalowany lokalnie, a katalog `lib` musi zostać podany w module path.

## Kompilacja

W katalogu projektu wykonaj:

```powershell
javac --module-path "PATH_TO_JAVAFX\lib" --add-modules javafx.controls -d out src\*.java
```

Zastąp `PATH_TO_JAVAFX` ścieżką do zainstalowanego JavaFX SDK.

Przykład:

```powershell
javac --module-path "C:\javafx-sdk-21\lib" --add-modules javafx.controls -d out src\*.java
```

## Uruchomienie aplikacji

Po skompilowaniu projektu wykonaj:

```powershell
java --module-path "PATH_TO_JAVAFX\lib" --add-modules javafx.controls -cp out MainWindow
```

Przykład:

```powershell
java --module-path "C:\javafx-sdk-21\lib" --add-modules javafx.controls -cp out MainWindow
```

## Rozwój projektu

Do kontroli wersji projektu wykorzystywany jest Git.

Zadania programistyczne, poprawki błędów, refaktoryzacja oraz dalsze ulepszenia są zarządzane przy użyciu Jira.

## Możliwe dalsze ulepszenia

Projekt może zostać w przyszłości rozszerzony między innymi o:

- ulepszony interfejs JavaFX oraz style CSS
- integrację z bazą danych zamiast plików tekstowych
- bardziej zaawansowane wyszukiwanie i filtrowanie
- dodatkową walidację i obsługę błędów
- testy automatyczne

## Autor

Artur Tatarskyi