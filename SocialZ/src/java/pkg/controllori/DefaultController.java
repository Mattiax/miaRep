package pkg.controllori;

import com.sun.mail.iap.Response;
import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pkg.db.DBHelper;
import pkg.oggetti.Messaggio;
import pkg.oggetti.Utente;

@Controller
public class DefaultController {

    @Autowired
    private DBHelper db;

    @RequestMapping(value = "/")
    public String index(ModelMap map) {
        return "redirect:/index"; //reindirizza alla Home Page
    }

    @RequestMapping(value = "/index")
    public String home(ModelMap map) {
        return "index";
    }

    @RequestMapping(value = "/provag")
    public String gg(ModelMap map) {
        return "newjsp";
    }

    @RequestMapping(value = "/doLogin", method = RequestMethod.POST)
    public String logIn(HttpServletRequest request, ModelMap map, HttpServletResponse response) {
        System.out.println("login");
        response.addCookie(new Cookie("mittente", request.getParameter("email")));
        List<Utente> lst = db.getAllUsers();
        System.out.println(lst.size());
        map.addAttribute("listaUtenti", lst);
        return "messages";
    }

    @RequestMapping(value = "/nuovoGruppo", method = RequestMethod.GET)
    public String nuovoGruppo(HttpServletRequest request, ModelMap map) {
        System.out.println("login");
        List<Utente> lst = db.getAllUsers();
        System.out.println(lst.size());
        map.addAttribute("listaUtenti", lst);
        return "nuovoGruppo";
    }

    @RequestMapping(value = "/creaGruppo", method = RequestMethod.POST)
    public String crea(@RequestBody String partecipanti) {
        System.out.println(partecipanti);
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
            System.out.println(descrizione);
            db.creaGruppo(amministratore, nome, descrizione, partecipantiList);
        } catch (JSONException ex) {
            Logger.getLogger(DefaultController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "messaggiGruppo";
    }

    @RequestMapping(value = "/personalArea", method = RequestMethod.GET)
    public String setData(HttpServletRequest request, ModelMap map) {
        System.out.println(request.getParameter("mittente"));
        Utente u = db.getUser(request.getParameter("mittente"), "");
        if (u == null) {
            return "signin";
        }
        map.addAttribute("nome", u.getNome());
        map.addAttribute("cognome", u.getCognome());
        map.addAttribute("dataNascita", u.getDataNascita());
        map.addAttribute("email", u.getEmail());
        map.addAttribute("password", u.getPassword());
        map.addAttribute("sesso", u.getSesso());
        return "personalArea";
    }

    @RequestMapping(value = "/signin")
    public String signIn(ModelMap map) {
        return "signin";
    }

    @RequestMapping(value = "/salvaMessaggio", method = RequestMethod.POST)
    public ResponseEntity<String> salvaMess(String mittente, String destinatario, String messaggio) {
        System.out.println("saving" + mittente + destinatario + messaggio);
        Messaggio m = new Messaggio(mittente, destinatario, messaggio, Calendar.getInstance().getTime().toString());
        db.salvaMess(m);
        return new ResponseEntity<>("OK", new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/richiestaPartecipazione", method = RequestMethod.POST)
    public ResponseEntity<String> richiestaPart(String mittente, String destinatario, String messaggio) {
        System.out.println("saving" + mittente + destinatario + messaggio);
        Messaggio m = new Messaggio(mittente, destinatario, messaggio, Calendar.getInstance().getTime().toString());
        db.richiestaPartecipazioneGruppo(m);
        return new ResponseEntity<>("OK", new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/salvaMessaggioGruppo", method = RequestMethod.POST)
    public ResponseEntity<String> salvaMessGruppo(String mittente, String destinatario, String messaggio) {
        System.out.println("saving" + mittente + destinatario + messaggio);
        Messaggio m = new Messaggio(mittente, destinatario, messaggio, Calendar.getInstance().getTime().toString());
        db.aggiungiMessaggioGruppo(m);
        return new ResponseEntity<>("OK", new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/eliminaMessaggio", method = RequestMethod.POST)
    public ResponseEntity<String> eliminaMess(int id) {
        System.out.println("deleting");
        db.eliminaMess(id);
        return new ResponseEntity<>("OK", new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/login")
    public String logIn(ModelMap map) {
        return "login";
    }

    @RequestMapping(value = "/messages")
    public String message(ModelMap map) {
        List<Utente> lst = db.getAllUsers();

        System.out.println(lst.size());
        map.addAttribute("listaUtenti", lst);
        return "messages";
    }

    @RequestMapping(value = "/messaggiGruppo", method = RequestMethod.GET)
    public String messGruppo(HttpServletRequest request, ModelMap map) {
        List<String> lst = db.getGruppi();
        System.out.println(request.getParameter("mittente"));
        map.addAttribute("mittente", request.getParameter("mittente"));
        map.addAttribute("listaGruppi", lst);
        return "messaggiGruppo";
    }

    @RequestMapping(value = "/mostraMessaggio")
    public @ResponseBody
    String getMessaggio(String mittente, String destinatario) {
        System.out.println(mittente + destinatario);
        List<Messaggio> msg = db.getConversazione(mittente, destinatario);

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
                    System.out.println(msg.get(i).getMessaggio());
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
            Logger.getLogger(DefaultController.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(js.toString());
        return js.toString();
    }

    @RequestMapping(value = "/getConvGruppo")
    public @ResponseBody
    String getConvGruppo(String mittente, String destinatario) {
        System.out.println(mittente + destinatario);
        if (db.isPartecipante(mittente, destinatario) < 0) {
            return null;
        }
        List<Messaggio> msg = db.getConversazioneGruppo(mittente, destinatario);

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
                    System.out.println(msg.get(i).getMessaggio());
                    js = new JSONObject();
                    js.put("mittente", msg.get(i).getMittente());
                    js.put("destinatario", msg.get(i).getDestinatario());
                    js.put("messaggio", msg.get(i).getMessaggio());
                    js.put("dataOra", msg.get(i).getDataOra());
                    js.put("pos", msg.get(i).getId());
                    ja.put(js);
                }
            }
            js = new JSONObject();
            js.put("messaggi", ja);
        } catch (JSONException ex) {
            System.out.println("errore json");
            Logger.getLogger(DefaultController.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(js.toString());
        return js.toString();
    }

    @RequestMapping(value = "/doSignin", method = RequestMethod.POST)
    public String signIn(HttpServletRequest request, ModelMap map) {
        System.out.println("reg   ");
        Utente temp = new Utente(request.getParameter("email"), request.getParameter("password"),
                request.getParameter("nome"), request.getParameter("cognome"), request.getParameter("indirizzo").equals("")?null:request.getParameter("indirizzo"),
                request.getParameter("sesso").charAt(0), request.getParameter("dataNascita"), null, request.getParameter("telefono").equals("")?null:request.getParameter("telefono"), (request.getParameter("privacy").equals("T") ? true : false), null);
        db.sigIn(temp);
        return "signin";
    }
}
