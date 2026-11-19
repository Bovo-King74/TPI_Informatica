# Guida ai Metodi della Classe `java.net.Socket` in Java

La classe `java.net.Socket` rappresenta l'endpoint lato client di una comunicazione bidirezionale tra due macchine sulla rete tramite protocollo TCP. 

Di seguito sono elencati i principali costruttori e **tutti i metodi pubblici** (esclusi quelli ereditati da `java.lang.Object`) organizzati per categoria funzionale.

---

## 1. Costruttori Principali

* **`Socket()`**: Crea un socket non connesso.
* **`Socket(InetAddress address, int port)`**: Crea un socket e lo connette all'indirizzo IP e alla porta specificati.
* **`Socket(String host, int port)`**: Crea un socket e lo connette all'host (nome o IP) e alla porta specificati.
* **`Socket(InetAddress address, int port, InetAddress localAddr, int localPort)`**: Crea un socket, lo associa a un indirizzo e una porta locali specifici, e lo connette all'indirizzo remoto.
* **`Socket(String host, int port, InetAddress localAddr, int localPort)`**: Come il precedente, ma accetta l'host remoto come `String`.
* **`Socket(Proxy proxy)`**: Crea un socket non connesso specificando il tipo di proxy da utilizzare (es. SOCKS).

---

## 2. Gestione della Connessione e Stato

* **`void connect(SocketAddress endpoint)`**
  Connette questo socket al server specificato tramite un oggetto `SocketAddress` (solitamente `InetSocketAddress`).
* **`void connect(SocketAddress endpoint, int timeout)`**
  Connette il socket al server con un valore di timeout specificato (in millisecondi). Se il timeout scade prima della connessione, viene lanciata una `SocketTimeoutException`.
* **`void bind(SocketAddress bindpoint)`**
  Associa (fa il *binding*) il socket a un indirizzo IP e a una porta locali specifici.
* **`void close()`**
  Chiude questo socket. Una volta chiuso, il socket non può più essere riutilizzato per altre connessioni. Chiude anche i relativi `InputStream` e `OutputStream`.
* **`boolean isConnected()`**
  Restituisce `true` se il socket è stato connesso con successo a un server. *Nota: continua a restituire `true` anche dopo che il socket è stato chiuso.*
* **`boolean isBound()`**
  Restituisce `true` se il socket è stato associato con successo a un indirizzo locale.
* **`boolean isClosed()`**
  Restituisce `true` se il socket è stato chiuso tramite il metodo `close()`.

---

## 3. Flussi di Input e Output (I/O)

* **`InputStream getInputStream()`**
  Restituisce uno stream di input per leggere i byte inviati dal server remoto attraverso questo socket.
* **`OutputStream getOutputStream()`**
  Restituisce uno stream di output per inviare byte al server remoto attraverso questo socket.
* **`void shutdownInput()`**
  Posiziona lo stream di input di questo socket alla "fine dello stream" (EOF). Qualsiasi dato inviato successivamente dall'altro capo viene scartato silenziosamente.
* **`void shutdownOutput()`**
  Disabilita lo stream di output per questo socket. Per una connessione TCP, tutti i dati precedentemente scritti verranno inviati, seguiti dalla normale sequenza di terminazione della connessione TCP (pacchetto FIN).
* **`boolean isInputShutdown()`**
  Restituisce `true` se il lato di lettura (input) della connessione socket è stato chiuso tramite `shutdownInput()`.
* **`boolean isOutputShutdown()`**
  Restituisce `true` se il lato di scrittura (output) della connessione socket è stato chiuso tramite `shutdownOutput()`.
* **`SocketChannel getChannel()`**
  Restituisce l'oggetto univoco `SocketChannel` (del pacchetto `java.nio.channels`) associato a questo socket, se presente (altrimenti restituisce `null`).

---

## 4. Informazioni su Indirizzi e Porte

* **`InetAddress getInetAddress()`**
  Restituisce l'indirizzo IP remoto a cui il socket è connesso (o `null` se non è connesso).
* **`InetAddress getLocalAddress()`**
  Restituisce l'indirizzo IP locale a cui il socket è associato.
* **`int getPort()`**
  Restituisce il numero di porta remota a cui questo socket è connesso (o `0` se non è connesso).
* **`int getLocalPort()`**
  Restituisce il numero di porta locale a cui questo socket è associato (o `-1` se non è ancora associato).
* **`SocketAddress getRemoteSocketAddress()`**
  Restituisce l'indirizzo dell'endpoint remoto (IP + Porta) a cui questo socket è connesso, oppure `null` se non è connesso.
* **`SocketAddress getLocalSocketAddress()`**
  Restituisce l'indirizzo dell'endpoint locale (IP + Porta) a cui questo socket è associato.

---

## 5. Opzioni TCP e Configurazione di Rete

* **`void setSoTimeout(int timeout)`**
  Abilita/disabilita l'opzione `SO_TIMEOUT` con il timeout specificato in millisecondi. Se una chiamata `read()` sull'`InputStream` si blocca per più di questo tempo, viene sollevata una `SocketTimeoutException`. Un valore di `0` indica un timeout infinito.
* **`int getSoTimeout()`**
  Restituisce l'impostazione corrente di `SO_TIMEOUT` in millisecondi (`0` significa disabilitato/infinito).
* **`void setTcpNoDelay(boolean on)`**
  Abilita/disabilita l'opzione `TCP_NODELAY` (disabilita l'algoritmo di Nagle). Se `true`, i pacchetti vengono inviati immediatamente senza attendere di accumulare più dati.
* **`boolean getTcpNoDelay()`**
  Verifica se l'opzione `TCP_NODELAY` è abilitata.
* **`void setKeepAlive(boolean on)`**
  Abilita/disabilita l'opzione `SO_KEEPALIVE`. Se attiva, il protocollo TCP invia periodicamente pacchetti di controllo per verificare che la connessione sia ancora attiva anche in assenza di traffico.
* **`boolean getKeepAlive()`**
  Verifica se `SO_KEEPALIVE` è abilitato.
* **`void setReuseAddress(boolean on)`**
  Abilita/disabilita l'opzione `SO_REUSEADDR`. Permette di riutilizzare immediatamente una porta locale anche se una connessione precedente su quella porta si trova ancora nello stato `TIME_WAIT`.
* **`boolean getReuseAddress()`**
  Verifica se `SO_REUSEADDR` è abilitato.
* **`void setSoLinger(boolean on, int linger)`**
  Abilita/disabilita `SO_LINGER` con il tempo di attesa specificato in secondi. Determina il comportamento del metodo `close()` quando ci sono ancora dati non inviati nel buffer di trasmissione.
* **`int getSoLinger()`**
  Restituisce l'impostazione per `SO_LINGER`. Restituisce `-1` se l'opzione è disabilitata.

---

## 6. Gestione dei Buffer e Priorità del Traffico

* **`void setSendBufferSize(int size)`**
  Imposta un suggerimento per la dimensione (in byte) del buffer di invio sottostante del socket (`SO_SNDBUF`).
* **`int getSendBufferSize()`**
  Restituisce la dimensione del buffer di invio utilizzata dal socket.
* **`void setReceiveBufferSize(int size)`**
  Imposta un suggerimento per la dimensione (in byte) del buffer di ricezione sottostante del socket (`SO_RCVBUF`).
* **`int getReceiveBufferSize()`**
  Restituisce la dimensione del buffer di ricezione utilizzata dal socket.
* **`void setTrafficClass(int tc)`**
  Imposta la classe di traffico (Traffic Class / Type-of-Service) nell'intestazione IP per i pacchetti inviati da questo socket (valore tra `0` e `255`).
* **`int getTrafficClass()`**
  Restituisce la classe di traffico impostata per questo socket.
* **`void setPerformancePreferences(int connectionTime, int latency, int bandwidth)`**
  Imposta le preferenze di prestazione relative per questo socket, indicando l'importanza relativa di tempo di connessione breve, bassa latenza e alta larghezza di banda.

---

## 7. Gestione Dati Urgenti (Out-Of-Band)

* **`void sendUrgentData(int data)`**
  Invia un singolo byte (gli 8 bit meno significativi dell'intero passato) di dati urgenti sul socket.
* **`void setOOBInline(boolean on)`**
  Abilita/disabilita `SO_OOBINLINE` (ricezione di dati TCP urgenti all'interno del normale flusso di dati). Per impostazione predefinita è `false` e i dati urgenti vengono scartati silenziosamente.
* **`boolean getOOBInline()`**
  Verifica se `SO_OOBINLINE` è abilitato.

---

## 8. Metodi Moderni (Java 9+) e Statici

* **`<T> Socket setOption(SocketOption<T> name, T value)`** *(Java 9+)*
  Imposta il valore di una specifica opzione del socket definita in `java.net.StandardSocketOptions`.
* **`<T> T getOption(SocketOption<T> name)`** *(Java 9+)*
  Restituisce il valore di una specifica opzione del socket.
* **`Set<SocketOption<?>> supportedOptions()`** *(Java 9+)*
  Restituisce un `Set` contenente tutte le opzioni di socket supportate da questa implementazione.
* **`static void setSocketImplFactory(SocketImplFactory fac)`** *(Deprecato in Java 17)*
  Imposta la factory di implementazione dei socket client per l'intera applicazione. Può essere chiamato una sola volta.
* **`String toString()`**
  Converte questo socket in una rappresentazione testuale `String` (es. `Socket[addr=...,port=...,localport=...]`).