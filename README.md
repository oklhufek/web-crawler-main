# Úkol 5 - WebCrawler

[![UTB FAI Task](https://img.shields.io/badge/UTB_FAI-Task-yellow)](https://www.fai.utb.cz/)
[![Java](https://img.shields.io/badge/Java-007396.svg?logo=java&logoColor=white)](https://www.java.com/)
[![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?logo=gradle&logoColor=white)](https://gradle.org/)

|                                  |                                 |
| -------------------------------- | ------------------------------- |
| __Maximální počet bodů__         | 13 bodů                         |
| __Způsob komunikace s aplikací__ | Parametry předané při spuštění  |
| __Způsob testování aplikace__    | Na základě standardního výstupu |

---

## 📝 Zadání úkolu 

Naprogramujte web crawler pro prohledávání webu a identifikaci 20 nejčastěji se vyskytujících slov. Aplikaci bude možné ovládat přes příkazový řádek a umožní specifikovat parametry pro hloubku zanoření a úroveň ladění programu. Implementujte parsování HTML stránek, procházení odkazů na stránce a analýzu četnosti slov. 

### Požadavky:
* __Parsování HTML stránek:__ K implementaci tohoto úkolu můžete využít buď HTML parser přímo v JDK v balíčku _javax.swing.text.html.parser_, nebo open source parser pro HTML 4.0, např. _JSoup_. JSoup knihovna je již v tomto repozitáři zahrnuta a připravena na případné použití. 
* __Procházení odkazů:__ Program musí procházet všechny odkazy na stránce a ukládat je do seznamu nalezených odkazů. Musí také udržovat množinu všech navštívených stránek, aby se stejné stránky neanalyzovaly vícekrát.
* __Analýza četnosti slov:__ Aplikace bude umožnovat analýzu četnosti slov. Pro implementaci je doporučené využít datovou strukturu "vyhledávací tabulka" (HashMap nebo TreeMap).
* __Ošetření kódování stránek:__ Získání kódování stránek z HTTP hlavičky nebo značky META.
* __Multithreading:__ Načítání a analýza každé stránky v nezávislém vláknu pomocí Executoru pro využití více jádrových procesorů. Seznam "visitedURIs" implementujte pomocí ConcurrentHashMap.

### Formát výstupu aplikace

Po spuštění aplikace prohledá web a po dokončení analýzy vypíše slova s ​​jejich absolutní četností seřazená sestupně na standardní výstup _System.out_. __Aplikace musí vypisovat pouze tyto informace a nic jiného navíc a to za celou dobu jejího běhu.__

```
the;47861
to;42777
and;37061
of;31670
your;29636
or;27962
you;22063
a;19607
data;19221
for;17302
information;14858
on;14341
our;12409
in;12381
is;11457
we;11143
with;10905
that;10462
as;9936
may;8774
```

### Struktura pole vstupních parametrů
1. __args[0]__ - URL stránky, na které program zahájí analýzu - např. http://yahoo.com _(String)_
2. __args[1]__ - Tento parametr bude udávat maximální hloubku zanoření při analýze. Tento parametr bude nepovinný. Pokud při spuštění aplikace nebude zadán, bude nastaven na 2. (Hloubka 0 znamená, že bude analyzován obsah jen stránky, na které je analýza zahájena).   _(int)_
3. __args[2]__ - Tento parametr bude udávat, v jaké hloubce zanoření budou vypisovány případné debug informace na System.err. Tento parametr bude nepovinný. Můžete využít pro vlastní účely debugování. Při hodnocení nebude brán v úvahu. _(int)_ 

__Základní kostra programu se nachází v tomto repozitáři. Vaším úkolem ji bude předělat tak, aby splňovala výše uvedené požadavky.__

>_**Poznámka:** Implementace a struktura kódu aplikace je libovolná a je zcela na vás, jak tento problém vyřešíte. Je však důležité, aby aplikace splňovala zadané požadavky._

---

## 🏆 Způsob hodnocení

Vaše implementace bude hodnocena na základě chování aplikace při testování různých scénářů. Automatizovaný testovací nástroj bude předávat vaší aplikaci různé parametry, včetně platných a neplatných, aby otestoval její chování za různých podmínek. V případě testování síťové komunikace mezi více klienty, testovací nástroj bude vytvářet virtuální klienty/servery za účelem ověření funkcionality.

Výsledné hodnocení bude záviset na celkovém počtu úspěšných testovacích případů. Počet bodů získaných z úlohy bude tedy záviset na celkové úspěšnosti při testování. Váš výsledný počet bodů bude určen následujícím vzorcem.

__VP__ = __MB__ * (__UT__ / __CPT__)

### Popis symbolů:

* __VP:__ Výsledný počet bodů.
* __MB:__ Maximální počet bodů pro danou úlohu.
* __UT:__ Počet úspěšných testovacích případů.
* __CPT:__ Celkový počet testovacích případů.

## ⚙️ Jak spustit automatizované hodnocení lokálně na svém počítači?

Automatizované hodnocení můžete spustit lokálně za účelem ověření funkčnosti vaší aplikace. K tomu slouží předpřipravený skript, který je dostupný v repozitáři tohoto úkolu. Výsledný report testování se bude nacházet v souboru ```test_report.html```.

###  Pro uživatele systému Linux:
Spusťte skript s názvem ```run_local_test.sh```.

### Pro uživatele systému Windows:
Spusťte skript s názvem ```run_local_test.bat```.


