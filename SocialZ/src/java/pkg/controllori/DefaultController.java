package pkg.controllori;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pkg.db.DB;
import pkg.oggetti.MailList;
import pkg.oggetti.Messaggio;
import pkg.oggetti.Richiesta;
import pkg.oggetti.Utente;

@Controller
public class DefaultController {

    private String percorso = "D:\\Windows\\Desktop\\miaRep\\SocialZ\\foto";

    @Autowired
    private DB db;

    @RequestMapping(value = "/")
    public String index(ModelMap map) {
        return "redirect:/index"; //reindirizza alla Home Page
    }

    @RequestMapping(value = "/index")
    public String home(ModelMap map) {
        return "index";
    }

    @RequestMapping(value = "/gruppi")
    public String gruppi(ModelMap map) {
        map.addAttribute("listaGruppi", db.getGruppi());
        return "gruppi";
    }

    @RequestMapping(value = "/utenti")
    public String utenti(HttpServletRequest request, ModelMap map) {
        map.addAttribute("listaUtenti", db.getAllUsers(getUtenteAttivo(request.getCookies())));
        return "utenti";
    }

    @RequestMapping(value = "/richiesteAmm")
    public String richiesteAmm(HttpServletRequest request, ModelMap map) {
        List<Richiesta> list = db.getRichiesteAmm(getUtenteAttivo(request.getCookies()));
        map.addAttribute("listaRichieste", list);
        return "amministratoreSocial";
    }

    @RequestMapping(value = "/doLogin", method = RequestMethod.POST)
    public String logIn(HttpServletRequest request, ModelMap map, HttpServletResponse response) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        //System.out.println("login" + request.getParameter("amm") + email + password);
        try {
            Utente u = db.getUser(email, password);
            if (db.isRegistrato(email, password) > -1) {
                if (u != null) {
                    response.addCookie(new Cookie("mittente", email));
                    if (u.getImage() != null) {
                        eliminaImmagine(u.getEmail());
                        scriviImmagineTemp(u.getImage(), u.getEmail());
                    }
                    if (request.getParameter("amm") != null) {
                        if (db.isAmministratoreSocial(email) > -1) {

                            List<Richiesta> list = db.getRichiesteAmm(email);
                            map.addAttribute("listaRichieste", list);
                            return "amministratoreSocial";
                        } else {
                            return "index";
                        }
                    } else {
                        List<Utente> lst = db.getAllUsers(email);
                        //System.out.println(lst.size());
                        map.addAttribute("listaUtenti", lst);
                        return "redirect:/messages";
                    }
                } else {
                    return "index";
                }
            } else {
                return "index";
            }
        } catch (Exception e) {
            map.addAttribute("errore", "Errore imprevisto \n" + e.getMessage());
            return "errore";
        }
    }

    @RequestMapping(value = "/nuovoGruppo", method = RequestMethod.GET)
    public String nuovoGruppo(HttpServletRequest request, ModelMap map) {
        //System.out.println("gruppo");
        List<Utente> lst = db.getAllUsers(getUtenteAttivo(request.getCookies()));
        //System.out.println(lst.size());
        map.addAttribute("listaUtenti", lst);
        return "nuovoGruppo";
    }

    @RequestMapping(value = "/creaGruppo", method = RequestMethod.POST)
    public void crea(@RequestBody String partecipanti) {
        //System.out.println(partecipanti);
        try {
            JSONObject ob = new JSONObject(partecipanti);
            String nome = ob.getString("nomeGruppo");
            String descrizione;
            try {
                descrizione = ob.getString("descrizione");
            } catch (JSONException e) {
                descrizione = null;
            }
            String amministratore = ob.getString("amministratore");
            JSONArray a = ob.getJSONArray("partecipanti");
            String[] partecipantiList = new String[a.length()];
            for (int i = 0; i < a.length(); i++) {
                partecipantiList[i] = a.getString(i);
            }
            //System.out.println(descrizione);
            db.creaGruppo(amministratore, nome, descrizione, partecipantiList);
        } catch (JSONException ex) {
            Logger.getLogger(DefaultController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @RequestMapping(value = "/personalArea")
    public String setData(HttpServletRequest request, ModelMap map) {
        String user = getUtenteAttivo(request.getCookies());

        Utente u = db.getUser(user, "");
        if (u == null) {
            return "redirect:/signin";
        }
        map.addAttribute("nome", u.getNome());
        map.addAttribute("cognome", u.getCognome());
        map.addAttribute("dataNascita", u.getDataNascita());
        map.addAttribute("email", u.getEmail());
        map.addAttribute("password", u.getPassword());
        map.addAttribute("sesso", u.getSesso());
        map.addAttribute("indirizzo", u.getIndirizzo());
        map.addAttribute("telefono", u.getTelefono());
        map.addAttribute("datiPers", u.getPermesso());
        map.addAttribute("listaHobbies", db.getHobbiesPersona(user));
        return "personalArea";
    }

    @RequestMapping(value = "/signin")
    public String signIn(ModelMap map) {
        //System.out.println("signin");
        map.addAttribute("listaHobbies", db.getHobbies());
        return "signin";
    }

    @RequestMapping(value = "/salvaMessaggio", method = RequestMethod.POST)
    public @ResponseBody
    String salvaMess(String mittente, String destinatario, String messaggio) {
        //System.out.println("saving" + mittente + destinatario + messaggio);
        Messaggio m = new Messaggio(mittente, destinatario, messaggio, Calendar.getInstance().getTime().toString());
        long id = db.salvaMess(m);
        JSONObject json = new JSONObject();
        try {
            json.put("id", id);
        } catch (JSONException ex) {
            Logger.getLogger(DefaultController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return json.toString();
    }

    @RequestMapping(value = "/richiestaPartecipazione", method = RequestMethod.POST)
    public ResponseEntity<String> richiestaPart(String mittente, String destinatario, String messaggio) {
        //System.out.println("saving" + mittente + destinatario + messaggio);
        Messaggio m = new Messaggio(mittente, destinatario, messaggio, Calendar.getInstance().getTime().toString());
        db.richiestaPartecipazioneGruppo(m);
        return new ResponseEntity<>("OK", new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/salvaMessaggioGruppo", method = RequestMethod.POST)
    public @ResponseBody
    String salvaMessGruppo(String mittente, String destinatario, String messaggio) {
        //System.out.println("saving" + mittente + destinatario + messaggio);
        Messaggio m = new Messaggio(mittente, destinatario, messaggio, Calendar.getInstance().getTime().toString());
        long id = db.aggiungiMessaggioGruppo(m);
        JSONObject json = new JSONObject();
        try {
            json.put("id", id);
        } catch (JSONException ex) {
            Logger.getLogger(DefaultController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return json.toString();
    }

    @RequestMapping(value = "/eliminaMessaggio", method = RequestMethod.POST)
    public ResponseEntity<String> eliminaMess(int id) {
        db.eliminaMess(id);
        return new ResponseEntity<>("OK", new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/eliminaMessaggioGruppo", method = RequestMethod.POST)
    public ResponseEntity<String> eliminaMessGrupp(int id) {
        db.eliminaMessGruppo(id);
        return new ResponseEntity<>("OK", new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/login")
    public String logIn(ModelMap map) {
        return "login";
    }

    @RequestMapping(value = "/messages")
    public String message(HttpServletRequest request, ModelMap map) {
        List<Utente> lst = db.getAllUsers(getUtenteAttivo(request.getCookies()));
        map.addAttribute("listaUtenti", lst);
        return "messages";
    }

    @RequestMapping(value = "/messaggiGruppo", method = RequestMethod.GET)
    public String messGruppo(HttpServletRequest request, ModelMap map) {
        List<String[]> lst = db.getGruppi();
        //System.out.println(request.getParameter("mittente"));
        map.addAttribute("listaGruppi", lst);
        return "messaggiGruppo";
    }

    @RequestMapping(value = "/mostraMessaggio")
    public @ResponseBody
    String getMessaggio(String mittente, String destinatario) {
        //System.out.println(mittente + destinatario);
        List<Messaggio> msg = db.getConversazione(mittente, destinatario);
        return creaJSONMessaggio(msg);
    }

    @RequestMapping(value = "/getConvGruppo")
    public @ResponseBody
    String getConvGruppo(String mittente, String destinatario) {
        //System.out.println("conversazione gruppo  " + mittente + destinatario + db.isPartecipante(mittente, destinatario));
        if (db.isPartecipante(mittente, destinatario) < 0) {
           // System.out.println("non partecipo");
            if (db.hasRichiestaPartecipazione(mittente, destinatario) > 0) {
                //System.out.println("richiesta effettuata");
                return "{}";
            } else {
                return null;
            }
        } else {
            List<Messaggio> msg = db.getConversazioneGruppo(mittente, destinatario);
            return creaJSONMessaggio(msg);
        }
    }

    private String creaJSONMessaggio(List<Messaggio> msg) {
        JSONObject js = new JSONObject();
        JSONArray ja = new JSONArray();
        try {
            if (msg == null || msg.isEmpty()) {
                js.put("mittente", "Nessun messaggio");
                js.put("destinatario", "");
                js.put("messaggio", "");
                js.put("dataOra", "");
                ja.put(js);
            } else {
                for (int i = 0; i < msg.size(); i++) {
                    js = new JSONObject();
                    js.put("mittente", msg.get(i).getMittente());
                    js.put("destinatario", msg.get(i).getDestinatario());
                    js.put("messaggio", msg.get(i).getMessaggio());
                    js.put("dataOra", msg.get(i).getDataOra());
                    js.put("id", msg.get(i).getId());
                    ja.put(js);
                }
            }
            js = new JSONObject();
            js.put("messaggi", ja);
        } catch (JSONException ex) {
            System.out.println("errore json");
            Logger
                    .getLogger(DefaultController.class
                            .getName()).log(Level.SEVERE, null, ex);
        }
        //System.out.println(js.toString());
        return js.toString();
    }

    @RequestMapping(value = "/doSignin", method = RequestMethod.POST)
    public String signIn(HttpServletRequest request, ModelMap map) {
        //System.out.println("reg   " + request.getParameterValues("hobbies")[0]);
        Utente temp = new Utente(request.getParameter("email"), request.getParameter("password"),
                request.getParameter("nome"), request.getParameter("cognome"), request.getParameter("indirizzo").equals("") ? null : request.getParameter("indirizzo"),
                request.getParameter("sesso").charAt(0), request.getParameter("dataNascita"), null, request.getParameter("telefono").equals("") ? null : request.getParameter("telefono"), (request.getParameter("privacy").equals("T") ? true : false), request.getParameterValues("hobbies"));
        try {
            db.sigIn(temp);
        } catch (Exception e) {
            if (e.getMessage().contains("SQLITE_CONSTRAINT_PRIMARYKEY")) {
                map.addAttribute("errore", "Sembra che questa email sia già registrata");
                return "errore";
            }
        }
        return "index";
    }

    @RequestMapping(value = "/richieste")
    public String richieste(HttpServletRequest request, ModelMap map) {
        List<Richiesta> lst = db.getRichieste(getUtenteAttivo(request.getCookies()));
       // System.out.println(request.getParameter("mittente"));
        map.addAttribute("listaRichieste", lst);
        return "richieste";
    }

    @RequestMapping(value = "/approvaRichiesta")
    public @ResponseBody
    void approva(String id, String richiedente, String gruppo) {
        db.approvaRichiesta(Integer.parseInt(id), richiedente, gruppo);
        //System.out.println("approva  " + id);
    }

    @RequestMapping(value = "/nuovoHobby")
    public @ResponseBody
    void richiestaHobby(String hobby) {
        db.nuovoHobby(hobby);
    }
    
     @RequestMapping(value = "/eliminaRichiesta")
    public @ResponseBody
    void eliminaRic(int id) {
         System.out.println("elimina "+id);
        db.eliminaRichiesta(id);
    }

    @RequestMapping(value = "/getHobbies", method = RequestMethod.POST)
    public @ResponseBody
    String hobbies(@RequestBody String hobbies) {
        List<String> list = db.getHobbies();
        for (Iterator<String> iter = list.listIterator(); iter.hasNext();) {
            if (hobbies.contains(iter.next())) {
                iter.remove();
            }
        }

        JSONObject js = new JSONObject();
        JSONArray ja = new JSONArray();
        try {
            for (int i = 0; i < list.size(); i++) {
                ja.put(list.get(i));
            }
            js = new JSONObject();
            js.put("hobbies", ja);
        } catch (JSONException ex) {
           // System.out.println("errore json");
            ex.printStackTrace();
        }
        return js.toString();
    }

    @RequestMapping(value = "/esci")
    public String esci(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        eliminaImmagine(getUtenteAttivo(request.getCookies()));
        if (session != null) {
            //System.out.println("logout");
            session.invalidate();
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("mittente")) {
                        cookie.setMaxAge(0);
                        break;
                    }
                }
            }
        }
        return "redirect:/index";
    }

    @RequestMapping(value = "/setNewData", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity aggiungi(@RequestBody String dati) {
        try {
            JSONObject ob = new JSONObject(dati);
            String email = ob.getString("utente");
            String nome = ob.getString("nome");
            String cognome = ob.getString("cognome");
            String password = ob.getString("password");
            String indirizzo = ob.getString("indirizzo");
            String telefono = "" + ob.getString("telefono");
            boolean datiPers = ob.getInt("permesso")==1?true:false;
            Utente u;
            try {
                JSONArray a = ob.getJSONArray("hobbies");
                String[] hobbies = new String[a.length()];
                for (int i = 0; i < a.length(); i++) {
                    hobbies[i] = a.getString(i);
                }
                u = new Utente(email, password, nome, cognome, indirizzo.equals("") ? null : indirizzo, 'X', null, null, telefono.equals("") ? null : telefono, datiPers, hobbies);
            } catch (Exception ex) {
                u = new Utente(email, password, nome, cognome, indirizzo.equals("") ? null : indirizzo, 'X', null, null, telefono.equals("") ? null : telefono, datiPers, null);
            }
            db.updateUtente(u);
        } catch (JSONException ex) {
            Logger.getLogger(DefaultController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ResponseEntity<>("OK", new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/eliminaHobby")
    public @ResponseBody
    ResponseEntity eliminaHobby(String utente, String hobby) {
        //System.out.println("dati "+utente+" hobby "+hobby);
        db.eliminaCollegamentoHobby(utente, hobby);
        return new ResponseEntity<>("OK", new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/inserisciNuovoHobby", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity nuovoHobby(int id, String hobby) {
        db.aggiungiHobby(id, hobby);
        return new ResponseEntity<>("OK", new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/eliminaGruppo", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity eliminaGruppo(String nome) {
        db.rimuoviGruppo(nome);
        return new ResponseEntity<>("OK", new HttpHeaders(), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/eliminaUtente", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity eliminaUtente(String nome) {
        db.rimuoviUtente(nome);
        return new ResponseEntity<>("OK", new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/getImmagine", method = RequestMethod.POST)
    public @ResponseBody
    String immaginea(HttpServletRequest request) {
        return getImmagineProfilo(getUtenteAttivo(request.getCookies()));
    }

    @RequestMapping(value = "/caricaImmagine", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity immagine(HttpServletRequest request) {
        try {
            Part im = null;
            for (Part part : request.getParts()) {
                if (part.getName().equals("image")) {
                    im = part;
                }
            }
            if (im != null) {
                InputStream input = im.getInputStream();
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                byte[] buffer = new byte[10240];
                for (int length = 0; (length = input.read(buffer)) > 0;) {
                    output.write(buffer, 0, length);
                }
                String utente = getUtenteAttivo(request.getCookies());
                String immagine = Base64.getEncoder().encodeToString(output.toByteArray());
                eliminaImmagine(utente);
                scriviImmagineTemp(immagine, utente);
                db.setImmagine(immagine, getUtenteAttivo(request.getCookies()));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>("OK", new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/mailList")
    public @ResponseBody
    String mailList(@RequestBody String hobby) {
        try {
            String json;
            try {
                json = hobby.substring(hobby.indexOf("{"));
            } catch (StringIndexOutOfBoundsException ei) {
                json = hobby;
            }
            JSONObject js = new JSONObject(json);
            String hobbyReq = js.getString("hobby");
            List<String> list = null;
            if (hobbyReq.equals("Tutti")) {
                 MailList a=db.getAllEmailsHobby();
                 JSONObject j=a.getJSON();
               String s = "%";
                //System.out.println("lista " + s);
                System.out.println(j.toString());
                return j.toString();
            } else {
                list = db.getMailList(hobbyReq);
            }
            return list.toString();
        } catch (JSONException ex) {
            Logger.getLogger(DefaultController.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }

    @RequestMapping(value = "/getHobbiesService", method = RequestMethod.POST)
    public @ResponseBody
    String hobbiesService() {
        List<String> list = db.getHobbies();
        return list.toString();
    }

    private String getUtenteAttivo(Cookie[] cookies) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("mittente")) {
                    return cookie.getValue();
                }
            }
        }
        return "";
    }

    private void scriviImmagineTemp(String immagine, String utente) {
        File file = new File(percorso + utente + ".txt");
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(file));
            writer.write(immagine);
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(DefaultController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String getImmagineProfilo(String utente) {
        String foto = "";
        try {
            BufferedReader in = new BufferedReader(new FileReader(percorso + utente + ".txt"));
            foto = in.readLine();
            in.close();
            //System.out.println("getfoto");
        } catch (Exception x) {
            Logger.getLogger(DefaultController.class.getName()).log(Level.SEVERE, null, x);
        }
        return foto;
    }

    private void eliminaImmagine(String utente) {
        try {
            new File(percorso + utente + ".txt").delete();
        } catch (Exception e) {
        }
    }
}
