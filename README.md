# projet_rip

projet java L3

## Notes

### Build&Run

- Client Console: 
    - build: javac client_console/src/client_console/*.java -d  client_console/bin 
    - run: java -cp client_console/bin client_console.Client
    - build&run: javac client_console/src/client_console/*.java -d client_console/bin -encoding UTF-8 && java -cp client_console/bin client_console.Client

- Serveur: 
    - build: javac serveur/src/serveurRip/*.java -d serveurerveur/bin 
    - run: java -cp serveur/bin serveurRip.Serveur
    - build&run : javac serveur/src/serveurRip/*.java -d serveur/bin -encoding UTF-8 && java -cp serveur/bin serveurRip.Serveur
                  javac src/serveurRip/*.java -d bin && java -cp bin serveurRip.Serveur