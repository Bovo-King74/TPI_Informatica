### bacheca UDP di classe

Ogni computer esegue lo stesso programma. All’avvio l’alunno sceglie un nickname; il programma apre una porta UDP, ascolta i messaggi e, quando l’utente scrive una riga, la invia all’indirizzo broadcast della LAN. Gli altri computer in ascolto sulla stessa porta mostrano il nome del mittente + il messaggio ricevuto.

## Bacheca senza thread: pseudocodice

Un solo flusso di esecuzione deve occuparsi sia della tastiera sia della rete. Per non restare bloccato indefinitamente in `receive`, si imposta un timeout breve: se non arriva un pacchetto, il programma torna a controllare la tastiera.

```text
leggi nickname e indirizzo broadcast
porta = 50000

crea socket UDP associato alla porta
abilita broadcast sul socket
imposta timeout di ricezione breve

mentre l'utente non ha chiesto di uscire:
    se c'è una riga pronta da tastiera:
        leggi la riga

        se la riga è "/quit":
            esci dal ciclo

        messaggio = nickname + "|" + riga
        converti messaggio in byte UTF-8
        crea un pacchetto diretto a broadcast:porta
        invia il pacchetto

    prova a ricevere un pacchetto
        se arriva:
            converti i byte ricevuti in testo UTF-8
            separa nickname e messaggio usando il primo "|"
            visualizza nickname e messaggio
        se scade il timeout:
            non fare nulla; ricomincia il ciclo

chiudi il socket
```

La verifica “c’è una riga pronta?” è un controllo non bloccante sull’input, per esempio con `BufferedReader.ready()`. Il timeout UDP permette al ciclo di tornare periodicamente a quel controllo.

## 2. Metodi e classi Java essenziali per UDP

Le classi principali sono `DatagramSocket`, che invia e riceve, e `DatagramPacket`, che rappresenta il pacchetto. `InetAddress` rappresenta l’indirizzo di destinazione. La documentazione Java descrive `send` e `receive` come operazioni sul socket e i costruttori del pacchetto per invio e ricezione.

| Classe | Metodo o costruttore | A cosa serve |
|---|---|---|
| `InetAddress` | `InetAddress.getByName("...")` | Rappresenta l’indirizzo IP, per esempio quello broadcast della LAN. |
| `DatagramSocket` | `new DatagramSocket(porta)` | Crea un socket associato alla porta locale, da cui ricevere. |
| `DatagramSocket` | `setBroadcast(true)` | Abilita l’invio di datagrammi broadcast. |
| `DatagramSocket` | `setSoTimeout(millisecondi)` | Imposta il tempo massimo di attesa per `receive`. |
| `DatagramSocket` | `receive(pacchetto)` | Attende un datagramma e ne copia i dati nel pacchetto. |
| `DatagramSocket` | `send(pacchetto)` | Invia il datagramma. |
| `DatagramSocket` | `close()` | Chiude il socket e libera la porta. |
| `DatagramPacket` | `new DatagramPacket(buffer, lunghezza)` | Crea un pacchetto destinato alla ricezione. |
| `DatagramPacket` | `new DatagramPacket(dati, lunghezza, indirizzo, porta)` | Crea un pacchetto da inviare a un destinatario. |
| `DatagramPacket` | `getData()`, `getOffset()`, `getLength()` | Recuperano i byte ricevuti e la parte effettivamente valida del buffer. |
| `DatagramPacket` | `getAddress()`, `getPort()` | Restituiscono l’indirizzo e la porta del mittente. |

Per convertire correttamente testo e byte, usate `StandardCharsets.UTF_8`, ad esempio `testo.getBytes(StandardCharsets.UTF_8)` e `new String(dati, offset, lunghezza, StandardCharsets.UTF_8)`. Nel ricevitore è importante usare la lunghezza effettiva del datagramma, non l’intera dimensione del buffer.

## 3. La stessa bacheca con due thread: pseudocodice

Con due thread non serve più che un unico ciclo alterni i controlli. Un thread può restare in attesa sulla tastiera, mentre l’altro resta in attesa dei datagrammi.

```text
crea socket UDP associato alla porta
abilita broadcast sul socket

thread RICEZIONE:
    ripeti finché il programma non termina:
        attendi un datagramma con receive
        converti i byte in testo UTF-8
        separa nickname e messaggio
        visualizza il messaggio

thread TASTIERA E INVIO:
    mentre l'utente non scrive "/quit":
        leggi una riga dalla tastiera
        costruisci "nickname|riga"
        converti il testo in byte UTF-8
        crea il pacchetto diretto a broadcast:porta
        invia il pacchetto

quando l'utente scrive "/quit":
    chiudi il socket
    termina il programma
```

Un dettaglio pratico: se il thread di ricezione è bloccato in `receive`, la chiusura del socket è un modo semplice per farlo uscire. In alternativa si può usare un timeout e controllare periodicamente una condizione di arresto.

## 4. Metodi essenziali per i thread in Java

| Elemento | A cosa serve |
|---|---|
| `Runnable` | Rappresenta il lavoro che deve eseguire un thread, definito nel metodo `run()`. |
| `new Thread(runnable)` | Crea un thread che eseguirà quel lavoro. |
| `start()` | Avvia un nuovo thread; Java chiamerà il suo `run()`. |
| `run()` | Contiene le istruzioni del thread. Non va chiamato direttamente se si vuole avviare un thread separato. |
| `interrupt()` | Segnala al thread che dovrebbe interrompersi; non forza automaticamente la sua terminazione. |
| `join()` | Fa attendere un thread finché un altro è terminato. |
| `setDaemon(true)` | Marca un thread come daemon: da solo non impedisce la chiusura del programma. |

All'indirizzo 
  https://intronetworks.cs.luc.edu/current2/html/udp.html
trovi un capitolo di libro scritto da umano con tutto quel che serve sapere per maneggiare UDP; e con *esempi di programmazione*.
Non sottovalutarli.