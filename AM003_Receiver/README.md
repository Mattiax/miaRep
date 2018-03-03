# Musone Mattia 5IA

# PendingIntent

Applicazione che mostra e spiega le differenze tra i 5 differenti flag del pendingintent

Premessa: il progetto non è stato completato, come avvisato, ma sono state aggiunte delle descrizioni per far capire la funzione dei vari flag

  - FLAG_CANCEL_CURRENT
  - FLAG_IMMUTABLE
  - FLAG_NO_CREATE
  - FLAG_ONE_SHOT
  - FLAG_UPDATE_CURRENT

# Struttura

  - MainActivity
  - MyBroadcastReceiver

MainActivity gestice dei radiobutton che permettono di scegliere il tipo di flag per il pendingintent, mentre la seconda classe gestisce semplicemente un Toast che viene chiamato dal pendingintent a seguito del click di un bottone nella MainActivity.