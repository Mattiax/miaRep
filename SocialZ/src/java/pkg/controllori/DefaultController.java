package pkg.controllori;

import com.sun.mail.iap.Response;
import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
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
import org.springframework.web.multipart.MultipartFile;
import pkg.db.DBHelper;
import pkg.oggetti.Messaggio;
import pkg.oggetti.Richiesta;
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
        
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        System.out.println("login" + request.getParameter("amm")+email+password);
        if (request.getParameter("amm") != null) {
            if (db.isAmministratoreSocial(email) > -1) {
                return "amministratoreSocial";
            }
        }
        if (db.isRegistrato(email, password) > -1) {
            response.addCookie(new Cookie("mittente", email));
            List<Utente> lst = db.getAllUsers(email);
            System.out.println(lst.size());
            map.addAttribute("listaUtenti", lst);
            return "messages";
        } else {
            return "index";
        }
    }

    @RequestMapping(value = "/nuovoGruppo", method = RequestMethod.GET)
    public String nuovoGruppo(HttpServletRequest request, ModelMap map) {
        System.out.println("gruppo");
        List<Utente> lst = db.getAllUsers(getUtenteAttivo(request.getCookies()));
        System.out.println(lst.size());
        map.addAttribute("listaUtenti", lst);
        return "nuovoGruppo";
    }

    @RequestMapping(value = "/creaGruppo", method = RequestMethod.POST)
    public void crea(@RequestBody String partecipanti) {
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
    }

    @RequestMapping(value = "/personalArea")
    public String setData(HttpServletRequest request, ModelMap map) {
        String user = getUtenteAttivo(request.getCookies());

        Utente u = db.getUser(user, "");
        if (u == null) {
            return "signin";
        }
        map.addAttribute("nome", u.getNome());
        map.addAttribute("cognome", u.getCognome());
        map.addAttribute("dataNascita", u.getDataNascita());
        map.addAttribute("email", u.getEmail());
        map.addAttribute("password", u.getPassword());
        map.addAttribute("sesso", u.getSesso());
        map.addAttribute("listaHobbies", db.getHobbiesPersona(user));
        return "personalArea";
    }

    @RequestMapping(value = "/signin")
    public String signIn(ModelMap map) {
        System.out.println("signin");
        map.addAttribute("listaHobbies", db.getHobbies());
        return "signin";
    }

    @RequestMapping(value = "/salvaMessaggio", method = RequestMethod.POST)
    public @ResponseBody
    String salvaMess(String mittente, String destinatario, String messaggio) {
        System.out.println("saving" + mittente + destinatario + messaggio);
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
        System.out.println("saving" + mittente + destinatario + messaggio);
        Messaggio m = new Messaggio(mittente, destinatario, messaggio, Calendar.getInstance().getTime().toString());
        db.richiestaPartecipazioneGruppo(m);
        return new ResponseEntity<>("OK", new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/salvaMessaggioGruppo", method = RequestMethod.POST)
    public @ResponseBody
    String salvaMessGruppo(String mittente, String destinatario, String messaggio) {
        System.out.println("saving" + mittente + destinatario + messaggio);
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
        System.out.println("deleting");
        db.eliminaMess(id);
        return new ResponseEntity<>("OK", new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/eliminaMessaggioGruppo", method = RequestMethod.POST)
    public ResponseEntity<String> eliminaMessGrupp(int id) {
        System.out.println("deleting");
        db.eliminaMessGruppo(id);
        return new ResponseEntity<>("OK", new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/login")
    public String logIn(ModelMap map) {
        return "login";
    }

    @RequestMapping(value = "/messages")
    public String message(HttpServletRequest request,ModelMap map) {
        List<Utente> lst = db.getAllUsers(getUtenteAttivo(request.getCookies()));
        System.out.println(lst.size());
        map.addAttribute("listaUtenti", lst);
        return "messages";
    }

    @RequestMapping(value = "/messaggiGruppo", method = RequestMethod.GET)
    public String messGruppo(HttpServletRequest request, ModelMap map) {
        List<String[]> lst = db.getGruppi();
        System.out.println(request.getParameter("mittente"));
        map.addAttribute("listaGruppi", lst);
        return "messaggiGruppo";
    }

    @RequestMapping(value = "/mostraMessaggio")
    public @ResponseBody
    String getMessaggio(String mittente, String destinatario) {
        System.out.println(mittente + destinatario);
        List<Messaggio> msg = db.getConversazione(mittente, destinatario);
        return creaJSONMessaggio(msg);
    }

    @RequestMapping(value = "/getConvGruppo")
    public @ResponseBody
    String getConvGruppo(String mittente, String destinatario) {
        System.out.println("conversazione gruppo  " + mittente + destinatario + db.isPartecipante(mittente, destinatario));
        if (db.isPartecipante(mittente, destinatario) < 0) {
            System.out.println("non partecipo");
            if (db.hasRichiestaPartecipazione(mittente, destinatario) > 0) {
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
            Logger
                    .getLogger(DefaultController.class
                            .getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(js.toString());
        return js.toString();
    }

    @RequestMapping(value = "/doSignin", method = RequestMethod.POST)
    public String signIn(HttpServletRequest request, ModelMap map) {
        /*Part s;
        try {
            s = request.getPart("immagine");
            InputStream imageInputStream = s.getInputStream();
       //read imageInputStream
       
       s.write("img");
            String fileName = Paths.get(s.getSubmittedFileName()).getFileName().toString();
        System.out.println(fileName);
        } catch (Exception ex) {
            Logger.getLogger(DefaultController.class.getName()).log(Level.SEVERE, null, ex);
        }
         */
        System.out.println("reg   " + request.getParameterValues("hobbies")[0]);
        Utente temp = new Utente(request.getParameter("email"), request.getParameter("password"),
                request.getParameter("nome"), request.getParameter("cognome"), request.getParameter("indirizzo").equals("") ? null : request.getParameter("indirizzo"),
                request.getParameter("sesso").charAt(0), request.getParameter("dataNascita"), null, request.getParameter("telefono").equals("") ? null : request.getParameter("telefono"), (request.getParameter("privacy").equals("T") ? true : false), request.getParameterValues("hobbies"));
        db.sigIn(temp);
        return "index";
    }

    @RequestMapping(value = "/richieste")
    public String richieste(HttpServletRequest request, ModelMap map) {
        List<Richiesta> lst = db.getRichieste(getUtenteAttivo(request.getCookies()));
        System.out.println(request.getParameter("mittente"));
        map.addAttribute("listaRichieste", lst);
        return "richieste";
    }

    @RequestMapping(value = "/approvaRichiesta")
    public @ResponseBody
    void approva(String id, String richiedente, String gruppo) {
        db.approvaRichiesta(Integer.parseInt(id), richiedente, gruppo);
        System.out.println("approva  " + id);
    }

    @RequestMapping(value = "/nuovoHobby")
    public @ResponseBody
    void richiestaHobby(String hobby) {
        db.nuovoHobby(hobby);
    }

    @RequestMapping(value = "/esci")
    public String esci(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            System.out.println("logout");
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

}
