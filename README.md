## SAGRADA by Cranio Creations


#### MEMBRI DEL GRUPPO

*Velati Matteo* - 844365 - 10489761

*Zangrando Niccolò* - 848997 - 10464648

*Stucchi Gabriele* -  845837 - 10531235



#### Contenuti 

[Link al diagramma UML finale delle classi](UML)


Funzionalità implementate:

* Regole complete
* CLI
* GUI
* RMI
* Socket
* Single Player

#### Come avviare Sagrada

Per hostare una partita di Sagrada con SO Windows è necessario disabilitare Windows Defender Firewall.
Si può successivamente avviare il jar del server, passando come argomento la durata 
del timer per la mossa di ogni giocatore, espresso in __millisecondi__ (i.e. 90000).
Il server rimane quindi in attesa della connessione dei giocatori.

#### Limitazioni

Se si intende giocare tramite CLI, per un'ottima esperienza di gioco il jar del client deve essere
eseguito su __Git Bash__ , pena la mancata visualizzazione dei colori.
Inoltre, è richiesto che i client non si connettano contemporaneamente (socket).

Tramite connessione socket non è mostrata al giocatore la distinzione sul tipo di errore che sta commettendo
mentre gioca.
