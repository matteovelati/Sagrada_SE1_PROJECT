##MEMBRI DEL GRUPPO

*Velati Matteo* - 844365 - 10489761

*Zangrando Niccolò* - 848997 - 10464648

*Stucchi Gabriele* -  845837 - 10531235



####Contenuti 

Coverage dei test mostrata da Intellij IDEA

![coverage_test](http://oi63.tinypic.com/2pyt4rd.jpg)

[Link al diagramma UML finale delle classi](https://www.google.com/)

Funzionalità implementate:

* Regole complete
* CLI
* GUI
* RMI
* Socket
* Single Player

####Come avviare Sagrada

Per hostare una partita di Sagrada con SO Windows è necessario disabilitare Windows Defender Firewall.
Si può successivamente avviare il jar del server, passando come argomento la durata 
del timer per la mossa di ogni giocatore, espresso in __millisecondi__ (i.e. 90000).
Il server rimane quindi in attesa della connessione dei giocatori.

####Limitazioni

Se si intende giocare tramite CLI, per un'ottima esperienza di gioco il jar del client deve essere
eseguito su __Git Bash__ , pena la mancata visualizzazione dei colori.
Inoltre, è richiesto che i client non si connettano contemporaneamente (socket).

Sono stati gestiti tutti i tipi di disconnessione lato Server e lato Client, eccetto per la caduta 
di connessione del server durante l'attesa di inizio partita e la disconnessione di un client 
quando si trova da solo nello stato LOBBY.

Tramite connessione socket non è mostrata al giocatore la distinzione sul tipo di errore che sta commettendo
mentre gioca.
