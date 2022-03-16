# projet_rip

projet java L3

## Notes

### Build&Run

- Client: 
    - build: javac client\client\src\client\*.java -d  client\client\bin 
    - run: java -cp client\client\bin client.Client
    - build&run: javac client\client\src\client\*.java -d client\client\bin  && java -cp client\client\bin client.Client

- Serveur: 
    - build: javac serveur\serveur\src\serveurRip\*.java -d serveur\serveur\bin 
    - run: java -cp serveur\serveur\bin serveurRip.Serveur
    - build&run : javac serveur\serveur\src\serveurRip\*.java -d serveur\serveur\bin && java -cp serveur\serveur\bin serveurRip.Serveur
                  javac src\serveurRip\*.java -d bin && java -cp bin serveurRip.Serveur