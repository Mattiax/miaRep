# AM104_GSON

Questo è il nostro primo esempio nell'uso di Retrofit 2. In Gradle abbiamo aggiunto (eventualmente da aggiornare alle nuove versioni)
```
compile 'com.squareup.retrofit2:retrofit:2.3.0'
compile 'com.squareup.retrofit2:converter-gson:2.3.0'
compile 'com.google.code.gson:gson:2.8.2'
```

## GSON

Il link alla libreria lo troviamo [qui](https://github.com/google/gson). Usiamo l'utility in rete [jsonschema2pojo](http://www.jsonschema2pojo.org/), dove **POJO** sta per Plain Old Java Object. Impostiamo: JSON e gson.  

Per poter lavorare naturalmente scegliamo di evitare di copiare tutto il [file](https://raw.githubusercontent.com/opendatajson/football.json/master/2016-17/it.1.json) ma con una versione ridotta [dati.json](dati.json). Ottenuto [preview.java](preview.java) lo semplifichiamo eliminando le classi doppione tipo `Team1` e `Team2`.

GSON usa delle **annotation** e invitiamo a guardare le [API](http://www.javadoc.io/doc/com.google.code.gson/gson/2.8.2) (qui alla versione 2.8.2); usiamo `@expose` e `@@SerializedName`.

## Retrofit 2

Invitiamo a far costante riferimento alle [API](http://square.github.io/retrofit/2.x/retrofit/). Iniziamo col creare il service attraverso la costruzione di un interfaccia, vedi file `RFService.java`. Scaricando un unico oggetto scriviamo
```
Call<Pojo> getPojo();
```
altrimenti se viene fornita uan lista avremmo scritto qualcosa tipo
```
public interface GitHubService {
  @GET("users/{user}/repos")
  Call<List<Repo>> listRepos(@Path("user") String user);
...
```
In `MainActivity` creiamo l'oggetto service nel modo seguente
```
mService = RFService.retrofit.create(RFService.class);
```
Pronto il servizio accodiamo una richiesta.

### Call

Ecco cosa troviamo nelle API

> Each **call** yields its own HTTP request and response pair. Use clone() to make multiple calls with the same parameters to the same webserver; this may be used to implement polling or to retry a failed call.

Il metodo `enque()` ermette di accodare la richiesta lavorando in modo **asincrono**.

### Callback

Nel nostro caso lavorando in modo asincrono dobbiamo creare un oggetto (non un metodo come in JS) che contenga il da farsi in caso di richiesta soddisfatta o di errore. Abbiamo i metodi
```
void onFailure(Call<T> call, Throwable t)
```
per gestire eccezioni (non solo fallimento della risposta) e
```
void 	onResponse(Call<T> call, Response<T> response)
```
arrivata la HTTP response.
```
isSuccessful()
```
ritorna `true` se `code()` è nel range [200..300) (API).
```
public T body()
```
è **deserialized response body** di una response avvenuta con successo, nel nostro caso un oggetto `Pojo`.

## Una View programmatica

Al termine del nostro esempio abbiamo gradito provare un lavoro di solo codice per costruire una `ScrollView` che, come ben sappiamo, può contenere una sola `View` figlia:
```
scrollView.removeAllViews();
LinearLayout linearLayout = new LinearLayout(this);
linearLayout.setOrientation(LinearLayout.VERTICAL);
LayoutParams params = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
params.setMargins(40, 10, 10, 10);
for(final Round round : rounds){
    TextView tv = new TextView(this);
    tv.setLayoutParams(params);
    tv.setText(round.getName());
    tv.setOnClickListener((View view) ->
          Toast.makeText(MainActivity.this, round.getName(), Toast.LENGTH_SHORT).show()
    );
    linearLayout.addView(tv);
}
scrollView.addView(linearLayout);
```
